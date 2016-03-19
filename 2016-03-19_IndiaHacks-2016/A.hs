{-
Limak is a little polar bear. He has n balls, the i-th ball has size ti.

Limak wants to give one ball to each of his three friends. Giving gifts isn't
easy — there are two rules Limak must obey to make friends happy:

  * No two friends can get balls of the same size.
  * No two friends can get balls of sizes that differ by more than 2.

For example, Limak can choose balls with sizes 4, 5 and 3, or balls with sizes
90, 91 and 92. But he can't choose balls with sizes 5, 5 and 6 (two friends
would get balls of the same size), and he can't choose balls with sizes 30, 31
and 33 (because sizes 30 and 33 differ by more than 2).

Your task is to check whether Limak can choose three balls that satisfy
conditions above.

Input
The first line of the input contains one integer n (3 ≤ n ≤ 50) — the number of
balls Limak has.

The second line contains n integers t1, t2, ..., tn (1 ≤ ti ≤ 1000) where ti
denotes the size of the i-th ball.

Output
Print "YES" (without quotes) if Limak can choose three balls of distinct sizes,
such that any two of them differ by no more than 2. Otherwise, print "NO"
(without quotes).

Found at: http://codeforces.com/contest/653/problem/A
-}

module Main where
import Data.List (group, sort)

solve ts = length ts0 >= 3 && (or $ zipWith (\a c -> c - a <= 2) ts0 ts2)
  where ts0 = map head . group . sort $ ts
        ts2 = tail (tail ts0)

main :: IO ()
main = do
  line1 <- getLine
  line2 <- getLine
  let n = read line1 :: Int
      ts = map read (words line2) :: [Integer]
  putStrLn $ if solve ts
               then "YES"
               else "NO"
