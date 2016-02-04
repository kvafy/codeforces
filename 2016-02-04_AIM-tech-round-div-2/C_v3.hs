{-
One day student Vasya was sitting on a lecture and mentioned a string s1 s2... sn,
onsisting of letters "a", "b" and "c" that was written on his desk. As the
lecture was boring, Vasya decided to complete the picture by composing
a graph G with the following properties:

 * G has exactly n vertices, numbered from 1 to n.
 * For all pairs of vertices i and j, where i ≠ j, there is an edge connecting
   them if and only if characters si and sj are either equal or neighbouring
   in the alphabet. That is, letters in pairs "a"-"b" and "b"-"c" are
   neighbouring, while letters "a"-"c" are not.

Vasya painted the resulting graph near the string and then erased the string.
Next day Vasya's friend Petya came to a lecture and found some graph at his desk.
He had heard of Vasya's adventure and now he wants to find out whether it could
be the original graph G, painted by Vasya. In order to verify this, Petya needs
to know whether there exists a string s, such that if Vasya used this s he would
produce the given graph G.

Input
The first line of the input contains two integers n and m  — the number of
vertices and edges in the graph found by Petya, respectively.

Each of the next m lines contains two integers ui and vi (1 ≤ ui, vi ≤ n, ui ≠ vi)
— the edges of the graph G. It is guaranteed, that there are no multiple edges,
that is any pair of vertexes appear in this list no more than once.

Output
In the first line print "Yes" (without the quotes), if the string s Petya is
interested in really exists and "No" (without the quotes) otherwise.

If the string s exists, then print it on the second line of the output. The
length of s must be exactly n, it must consist of only letters "a", "b" and "c"
only, and the graph built using this string must coincide with G. If there are
multiple possible answers, you may print any of them.

Found at: http://codeforces.com/contest/624/problem/C
-}

module Main where
import qualified Data.Foldable as Foldable
import qualified Data.Set as Set


solve :: Int -> Set.Set (Int,Int) -> Maybe String
solve n edges
  = if Foldable.all (\a -> isFullyConnected a aPositions edges) aPositions
    && Foldable.all (\c -> isFullyConnected c cPositions edges) cPositions
      then Just $ makeExample n aPositions bPositions cPositions
      else Nothing
  where bPositions  = Set.fromList $ filter (\u -> isFullyConnected u (Set.fromList [1..n]) edges) [1..n]
        acPositions = Set.difference (Set.fromList [1..n]) bPositions
        (aPositions, cPositions) = partition2 acPositions edges

partition2 :: Set.Set Int -> Set.Set (Int,Int) -> (Set.Set Int, Set.Set Int)
partition2 vertices edges
  | Set.null vertices = (Set.empty, Set.empty)
  | otherwise         = (aPositions, cPositions)
  where aPositions = visitTransitive (head verticesList) vertices edges
        cPositions = Set.difference vertices aPositions
        verticesList = Set.toList vertices

visitTransitive :: Int -> Set.Set Int -> Set.Set (Int,Int) -> Set.Set Int
visitTransitive start vertices edges = grow Set.empty (Set.singleton start)
  where grow visited toVisit
          | Set.null toVisit = visited
          | otherwise        = grow (Set.union visited toVisit) toVisit'
          where toVisit' = Set.fromList $ [v | u <- Set.toList toVisit,
                                               v <- Set.toList vertices,
                                               v `Set.notMember` (Set.union visited toVisit),
                                               (u,v) `Set.member` edges]

makeExample n aPositions bPositions cPositions = map idxToChar [1..n]
  where idxToChar idx
          | idx `Set.member` aPositions = 'a'
          | idx `Set.member` bPositions = 'b'
          | idx `Set.member` cPositions = 'c'

isFullyConnected v toVertices edges =
  all (`Set.member` edges) [(u,v) | u <- (Set.toList toVertices), u /= v]


getLines :: Int -> IO [String]
getLines n = sequence $ replicate n getLine


main :: IO ()
main = do
  line1 <- getLine
  let [n, m] = map read (words line1) :: [Int]

  edgeLines <- getLines m
  let edgeList = map ((\[a,b] -> (a,b)) . map read . words) edgeLines :: [(Int,Int)]
      -- the edge set will include also reverse edges
      edgeSet = Set.fromList $ edgeList ++ map (\(u,v) -> (v,u)) edgeList

  case solve n edgeSet of
    Just sampleSolution -> do
      putStrLn "Yes"
      putStrLn sampleSolution
    Nothing ->
      putStrLn "No"
