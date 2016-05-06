{-
In late autumn evening n robots gathered in the cheerful company of friends.
Each robot has a unique identifier — an integer from 1 to 10^9.

At some moment, robots decided to play the game "Snowball". Below there are the
rules of this game. First, all robots stand in a row. Then the first robot says
his identifier. After that the second robot says the identifier of the first
robot and then says his own identifier. Then the third robot says the identifier
of the first robot, then says the identifier of the second robot and after that
says his own. This process continues from left to right until the n-th robot
says his identifier.

Your task is to determine the k-th identifier to be pronounced.

Input
The first line contains two positive integers n and k (1 ≤ n ≤ 100 000,
1 ≤ k ≤ min(2·10^9, n·(n + 1) / 2).

The second line contains the sequence id1, id2, ..., idn (1 ≤ idi ≤ 10^9) —
identifiers of roborts. It is guaranteed that all identifiers are different.

Output
Print the k-th pronounced identifier (assume that the numeration starts from 1).

Available at: http://codeforces.com/contest/670/problem/B
-}

module Main where

solve :: [Int] -> Int -> Int -> Int
solve ids k me | k <= me   = ids !! (k - 1)
               | otherwise = solve ids (k - me) (me + 1)

main :: IO ()
main = do
  line <- getLine
  let [n,k] = map read (words line) :: [Int]
  line2 <- getLine
  let ids = map read (words line2) :: [Int]
  putStrLn $ show (solve ids k 1)
