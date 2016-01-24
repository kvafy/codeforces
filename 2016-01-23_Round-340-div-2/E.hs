--
-- Bob has a favorite number k and ai of length n. Now he asks you to answer m
-- queries. Each query is given by a pair li and ri and asks you to count the
-- number of pairs of integers i and j, such that l ≤ i ≤ j ≤ r and the xor of
-- the numbers ai, ai + 1, ..., aj is equal to k.
--
-- Input
-- The first line of the input contains integers n, m and k
-- (1 ≤ n, m ≤ 100 000, 0 ≤ k ≤ 1 000 000) — the length of the array, the number
-- of queries and Bob's favorite number respectively.
--
-- The second line contains n integers ai (0 ≤ ai ≤ 1 000 000) — Bob's array.
--
-- Then m lines follow. The i-th line contains integers li and ri
-- (1 ≤ li ≤ ri ≤ n) — the parameters of the i-th query.
--
-- Output
-- Print m lines, answer the queries in the order they appear in the input.
--
-- Available at: http://codeforces.com/contest/617/problem/E
--

module Main where
import Data.Bits(xor)

type Query = (Int,Int)

solve :: Int -> [Int] -> [Query] -> [Int]
solve k a queries = [length $ filter (withinRange q) goodRanges | q <- queries]
  where -- no need to compute XORs out of (minL,maxR) range
        minL = minimum $ map fst queries
        maxR = maximum $ map snd queries
        lscanRanges = [(l,maxR) | l <- [minL..maxR]]
        rangesWithXor = concatMap (lscans a) lscanRanges
        goodRanges = map snd $ filter (\(xor,lr) -> xor == k) rangesWithXor

lscans :: [Int] -> (Int,Int) -> [(Int,(Int,Int))]
lscans a (l,r) = zip (scanl1 xor a') [(l,r') | r' <- [l..r]]
  where a' = (take (r - l + 1) . drop (l - 1)) a

withinRange (l,r) (a,b) = (l <= a && b <= r)

getLines :: Int -> IO [String]
getLines n = getLines' n []
  where getLines' :: Int -> [String] -> IO [String]
        getLines' 0 acc = return $ reverse acc
        getLines' n acc = do line <- getLine
                             getLines' (n - 1) (line:acc)

main :: IO ()
main = do
  line1 <- getLine
  let [n, m, k] = (map read) . words $ line1 :: [Int]

  line2 <- getLine
  let a = (map read) . words $ line2 :: [Int]

  queryLines <- getLines m
  let queries = map ((\[l,r] -> (l,r)) . (map read) . words) queryLines :: [Query]

  let answers = solve k a queries
  mapM_ (putStrLn . show) answers
