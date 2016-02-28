{-
A remote island chain contains n islands, labeled 1 through n. Bidirectional
bridges connect the islands to form a simple cycle — a bridge connects islands
1 and 2, islands 2 and 3, and so on, and additionally a bridge connects islands
n and 1. The center of each island contains an identical pedestal, and all but
one of the islands has a fragile, uniquely colored statue currently held on the
pedestal. The remaining island holds only an empty pedestal.

The islanders want to rearrange the statues in a new order. To do this, they
repeat the following process: First, they choose an island directly adjacent
to the island containing an empty pedestal. Then, they painstakingly carry the
statue on this island across the adjoining bridge and place it on the empty
pedestal.

Determine if it is possible for the islanders to arrange the statues in the
desired order.

Input
The first line contains a single integer n (2 ≤ n ≤ 200 000) — the total
number of islands.

The second line contains n space-separated integers ai (0 ≤ ai ≤ n - 1) — the
statue currently placed on the i-th island. If ai = 0, then the island has no
statue. It is guaranteed that the ai are distinct.

The third line contains n space-separated integers bi (0 ≤ bi ≤ n - 1) — the
desired statues of the ith island. Once again, bi = 0 indicates the island
desires no statue. It is guaranteed that the bi are distinct.

Output
Print "YES" (without quotes) if the rearrangement can be done in the existing
network, and "NO" otherwise.

Found at: http://codeforces.com/contest/635/problem/B
-}

module Main where
import Data.List (findIndex)

solve :: [Int] -> [Int] -> Bool
solve initial target = normalize initial == normalize target

normalize lst = filter (/= 0) $ after ++ before
  where (Just zeroPos) = findIndex (== 1) lst
        (before, after) = splitAt zeroPos lst

main :: IO ()
main = do
  line1 <- getLine
  let n = read line1 :: Int
  line2 <- getLine
  let initial = map read (words line2) :: [Int]
  line3 <- getLine
  let target = map read (words line3) :: [Int]
  putStrLn $ if solve initial target
               then "YES"
               else "NO"
