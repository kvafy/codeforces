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
import Data.Char (ord)

solve :: Int -> [(Int,Int)] -> Maybe String
solve n edges = solve' ""
  where solve' :: String -> Maybe String
        solve' str
          | length str == n = Just str
          | otherwise       = case results of
                                []                  -> Nothing
                                (Just solution : _) -> Just solution
          where strs = filter (isValidLastChar edges) [str ++ [addition] | addition <- "abc"] -- not ideal O(n) concat
                results = filter isJust (map solve' strs)

isValidLastChar edges str
  | length str <= 1  = True
  | otherwise        = all (\(u,v) -> (u,v) `elem` edges || (v,u) `elem` edges) expectedEdges
                       &&
                       all (\(u,v) -> (u,v) `notElem` edges && (v,u) `notElem` edges) unexpectedEdges
  where lastChar = last str
        lastCharIdx = length str
        expectedEdges   = [(i,lastCharIdx) | (i,c) <- zip [1..lastCharIdx-1] str, c `isAdjacent` lastChar]
        unexpectedEdges = [(i,lastCharIdx) | (i,c) <- zip [1..lastCharIdx-1] str, not $ c `isAdjacent` lastChar]
        isAdjacent x y = abs (ord x - ord y) <= 1

isJust (Just _) = True
isJust Nothing  = False

getLines :: Int -> IO [String]
getLines n = sequence $ replicate n getLine


main :: IO ()
main = do
  line1 <- getLine
  let [n, m] = map read (words line1) :: [Int]

  edgeLines <- getLines m
  let edges = map ((\[a,b] -> (a,b)) . map read . words) edgeLines :: [(Int,Int)]

  case solve n edges of
    Just sampleSolution -> do
      putStrLn "Yes"
      putStrLn sampleSolution
    Nothing ->
      putStrLn "No"
