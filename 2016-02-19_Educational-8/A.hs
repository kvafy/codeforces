{-
A tennis tournament with n participants is running. The participants are
playing by an olympic system, so the winners move on and the losers drop out.

The tournament takes place in the following way (below, m is the number of the
participants of the current round):

  * let k be the maximal power of the number 2 such that k ≤ m,
  * k participants compete in the current round and a half of them passes to the
    next round, the other m - k participants pass to the next round directly,
  * when only one participant remains, the tournament finishes.

Each match requires b bottles of water for each participant and one bottle
for the judge. Besides p towels are given to each participant for the whole
tournament.

Find the number of bottles and towels needed for the tournament.

Note that it's a tennis tournament so in each match two participants compete
(one of them will win and the other will lose).

Input
The only line contains three integers n, b, p (1 ≤ n, b, p ≤ 500) — the number
of participants and the parameters described in the problem statement.

Output
Print two integers x and y — the number of bottles and towels need for
the tournament.

Found at: http://codeforces.com/contest/628/problem/A
-}

module Main where

closestPowerOf2 x = last $ takeWhile (<= x) [2 ^ i | i <- [0..]]

solve m b
  | m == 1    = 0
  | otherwise = bottles + bottles'
  where bottles = matches * 2 * b + matches
        matches = k `div` 2
        bottles' = solve (k `div` 2 + m - k) b
        k = closestPowerOf2 m

main :: IO ()
main = do
  line <- getLine
  let [n,b,p] = map read (words line) :: [Int]
      bottles = solve n b
      towels = n * p
  putStrLn $ show bottles ++ " " ++ show towels
