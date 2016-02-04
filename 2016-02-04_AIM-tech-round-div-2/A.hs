{-
Luke Skywalker got locked up in a rubbish shredder between two presses. R2D2 is
already working on his rescue, but Luke needs to stay alive as long as possible.
For simplicity we will assume that everything happens on a straight line, the
presses are initially at coordinates 0 and L, and they move towards each other
with speed v1 and v2, respectively. Luke has width d and is able to choose any
position between the presses. Luke dies as soon as the distance between the
presses is less than his width. Your task is to determine for how long Luke
can stay alive.

Input
The first line of the input contains four integers d, L, v1, v2 (1 ≤ d, L,
v1, v2 ≤ 10 000, d < L) — Luke's width, the initial position of the second
press and the speed of the first and second presses, respectively.

Output
Print a single real value — the maximum period of time Luke can stay alive for.
Your answer will be considered correct if its absolute or relative error does
not exceed 10^(-6).

Found at: http://codeforces.com/contest/624/problem/A
-}

module Main where

solve :: Integer -> Integer -> Integer -> Integer -> Float
solve d l v1 v2 = (fromIntegral (l - d)) / (fromIntegral (v1 + v2))

main :: IO ()
main = do
  line <- getLine
  let [d, l, v1, v2] = map read (words line) :: [Integer]
  putStrLn $ show (solve d l v1 v2)
