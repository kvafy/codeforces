-- A flowerbed has many flowers and two fountains.
--
-- You can adjust the water pressure and set any values r1(r1 ≥ 0) and r2(r2 ≥ 0),
-- giving the distances at which the water is spread from the first and second
-- fountain respectively. You have to set such r1 and r2 that all the flowers
-- are watered, that is, for each flower, the distance between the flower and
-- the first fountain doesn't exceed r1, or the distance to the second fountain
-- doesn't exceed r2. It's OK if some flowers are watered by both fountains.
--
-- You need to decrease the amount of water you need, that is set such r1 and r2
-- that all the flowers are watered and the r1^2 + r2^2 is minimum possible.
-- Find this minimum value.
--
-- Input
-- The first line of the input contains integers n, x1, y1, x2, y2 (1 ≤ n ≤ 2000,
-- - 107 ≤ x1, y1, x2, y2 ≤ 107) — the number of flowers, the coordinates of
-- the first and the second fountain.
--
-- Next follow n lines. The i-th of these lines contains integers xi and yi
-- ( - 107 ≤ xi, yi ≤ 107) — the coordinates of the i-th flower.
--
-- It is guaranteed that all n + 2 points in the input are distinct.
--
-- Output
-- Print the minimum possible value r1^2 + r2^2. Note, that in this problem
-- optimal answer is always integer.


-- Found at: http://codeforces.com/contest/617/problem/C
--

module Main where
import GHC.Exts(sortWith)

dist2 :: (Int,Int) -> (Int,Int) -> Integer
dist2 (x1,y1) (x2,y2) = (toInteger $ x1 - x2) ^ 2 + (toInteger $ y1 - y2) ^ 2

getLines :: Int -> IO [String]
getLines 0 = return []
getLines n = do
  line <- getLine
  rest <- getLines (n-1) -- sadly, not tail recursion
  return $ line:rest

grow1 :: (Integer,Integer) -> (Integer,Integer) -> (Integer,Integer)
grow1 (r2a,r2b) (r2toA,r2toB)
  | r2toA <= r2a              = (r2a,r2b)
  | r2toB <= r2b              = (r2a,r2b)
  | r2toA + r2b < r2a + r2toB = (r2toA,r2b)
  | otherwise                 = (r2a,r2toB)

shrink :: (Integer,Integer) -> [(Integer,Integer)] -> (Integer,Integer)
shrink (r2a,r2b) flowerR2s = foldl grow1 (r2aMin,r2bMin) abBoth
  where aOnly  = filter (\(r2toA,r2toB) -> r2toB > r2b) flowerR2s
        bOnly  = filter (\(r2toA,r2toB) -> r2toA > r2a) flowerR2s
        abBoth = filter (\(r2toA,r2toB) -> r2toA <= r2a && r2toB <= r2b) flowerR2s
        r2aMin = maximum $ map fst aOnly
        r2bMin = maximum $ map snd bOnly

main :: IO ()
main = do
  line1 <- getLine
  let [n, x1, y1, x2, y2] = (map read . words) line1 :: [Int]
  flowerLines <- getLines n
  let flowerCoords = map ((\[x, y] -> (x,y)) . (map read) . words) flowerLines :: [(Int,Int)]
  let dist2A = map (dist2 (x1,y1)) flowerCoords
      dist2B = map (dist2 (x2,y2)) flowerCoords
      flowerR2s = zip dist2A dist2B
  let (r2a,r2b) = foldl grow1 (0,0) flowerR2s
      (r2a',r2b') = shrink (r2a,r2b) flowerR2s
  putStrLn $ show (r2a' + r2b')
