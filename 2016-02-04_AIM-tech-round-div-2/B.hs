{-
You are given an alphabet consisting of n letters, your task is to make
a string of the maximum possible length so that the following conditions are
satisfied:

 * the i-th letter occurs in the string no more than ai times;
 * the number of occurrences of each letter in the string must be distinct for
   all the letters that occurred in the string at least once.

Input
The first line of the input contains a single integer n (2  ≤  n  ≤  26) —
the number of letters in the alphabet.

The next line contains n integers ai (1 ≤ ai ≤ 109) — i-th of these integers
gives the limitation on the number of occurrences of the i-th character in the
string.

Output
Print a single integer — the maximum length of the string that meets all the
requirements.

Found at: http://codeforces.com/contest/624/problem/B
-}

module Main where

solve :: [Integer] -> Integer
solve constraints = sum constraints'
  where constraints' = foldl (\used v ->
                               let v' = closestFree used v
                               in (v':used)
                             )
                             []
                             constraints
        closestFree _  0  = 0
        closestFree [] v = v
        closestFree xs v = if v `elem` xs then closestFree xs (v - 1)
                                          else v



main :: IO ()
main = do
  line1 <- getLine
  let n = read line1 :: Integer

  line2 <- getLine
  let constraints = map read (words line2) :: [Integer]

  putStrLn $ show $ solve constraints
