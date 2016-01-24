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
-- -107 ≤ x1, y1, x2, y2 ≤ 107) — the number of flowers, the coordinates of
-- the first and the second fountain.
--
-- Next follow n lines. The i-th of these lines contains integers xi and yi
-- (-107 ≤ xi, yi ≤ 107) — the coordinates of the i-th flower.
--
-- It is guaranteed that all n + 2 points in the input are distinct.
--
-- Output
-- Print the minimum possible value r1^2 + r2^2. Note, that in this problem
-- optimal answer is always integer.
--
-- Found at: http://codeforces.com/contest/617/problem/C
--

module Main where

type Point = (Int,Int)

dist2 :: Point -> Point -> Integer
dist2 (x1,y1) (x2,y2) = (toInteger $ x1 - x2) ^ 2 + (toInteger $ y1 - y2) ^ 2

brute :: Point -> Point -> [Point] -> (Integer,Integer)
brute s1 s2 flowers = minimumWith (uncurry (+)) possibleRs
  where r12s = map (dist2 s1) flowers
        possibleRs = map (\r12 -> bruteGivenR12 s1 s2 r12 flowers) r12s

bruteGivenR12 s1 s2 r12 flowers = (r12,r22)
  where dist2s = map (\flower -> (dist2 flower s1, dist2 flower s2)) flowers
        notCoveredByS1 = filter (\(d12,d22) -> r12 < d12) dist2s
        r22s = map snd notCoveredByS1
        r22 = foldl max 0 r22s

minimumWith :: (Ord b) => (a -> b) -> [a] -> a
minimumWith _ [] = error "List cannot be empty"
minimumWith f lst = minimumWith' (head lst, f (head lst)) lst
  where minimumWith' (xmin, _) [] = xmin
        minimumWith' (xmin, fxmin) (x:xs)
          | f x < fxmin  = minimumWith' (x, f x) xs
          | otherwise    = minimumWith' (xmin, fxmin) xs

getLines :: Int -> IO [String]
getLines 0 = return []
getLines n = do
  line <- getLine
  rest <- getLines (n-1) -- sadly, not tail recursion
  return $ line:rest

main :: IO ()
main = do
  line1 <- getLine
  let [n, s1x, s1y, s2x, s2y] = (map read . words) line1 :: [Int]
  flowerLines <- getLines n
  let flowers = map ((\[x, y] -> (x,y)) . (map read) . words) flowerLines :: [Point]
  -- have to brute force for all possibilities of r12 being non-zero and r22 being non-zero
  let (r12', r22')  = brute (s1x,s1y) (s2x,s2y) flowers
      (r12'',r22'') = (0, maximum $ map (dist2 (s2x,s2y)) flowers)
      (r12,r22) = minimumWith (uncurry (+)) [(r12', r22'), (r12'',r22'')]
  putStrLn $ show (r12 + r22)
