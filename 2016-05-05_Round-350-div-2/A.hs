{-
On the planet Mars a year lasts exactly n days (there are no leap years on Mars).
But Martians have the same weeks as earthlings — 5 work days and then 2 days off.
Your task is to determine the minimum possible and the maximum possible number
of days off per year on Mars.

Input
The first line of the input contains a positive integer n (1 ≤ n ≤ 1 000 000)
— the number of days in a year on Mars.

Output
Print two integers — the minimum possible and the maximum possible number of
days off per year on Mars.

Available at: http://codeforces.com/contest/670/problem/A
-}

module Main where

solve :: Int -> (Int,Int)
solve n = (wholeWeeks * 2 + (if oddDays == 6 then 1 else 0),
           wholeWeeks * 2 + (if oddDays > 0  then 1 else 0)
                          + (if oddDays > 1  then 1 else 0))
  where wholeWeeks = n `div` 7
        oddDays = n `mod` 7

main :: IO ()
main = do
  line <- getLine
  let n = read line
      (min,max) = solve n
  putStrLn $ show min ++ " " ++ show max
