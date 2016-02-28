{-
Two positive integers a and b have a sum of s and a bitwise XOR of x. How many
possible values are there for the ordered pair (a, b)?

Input
The first line of the input contains two integers s and x
(2 ≤ s ≤ 1012, 0 ≤ x ≤ 1012), the sum and bitwise xor of the pair of positive
integers, respectively.

Output
Print a single integer, the number of solutions to the given conditions. If no
solutions exist, print 0.

Found at: http://codeforces.com/contest/635/problem/C
-}

module Main where
import Data.Bits ((.&.))

solve :: Integer -> Integer -> Integer
solve s x = solve' sBits' xBits' 0 (True, True)
  where sBits = num2bits s
        xBits = num2bits x
        (sBits', xBits') = equilength 0 (sBits, xBits)

solve' :: [Int] -> [Int] -> Int -> (Bool,Bool) -> Integer
solve' [] [] 0 (False, False) = 1 -- must have 0 carry and, neither a nor b can be all 0s
solve' [] [] _ _ = 0
solve' (s:ss) (x:xs) carry (a0s,b0s)
  -- a0s indicates whether the A consists of 0s only (same for b0s)
  | (s,x,carry) == (0,0,0) = solve' ss xs 0 (a0s, b0s)     -- (a,b) = (0,0)
                           + solve' ss xs 1 (False, False) -- (a,b) = (1,1)
  | (s,x,carry) == (0,1,0) = 0
  | (s,x,carry) == (1,0,0) = 0
  | (s,x,carry) == (1,1,0) = solve' ss xs 0 (a0s, False)   -- (a,b) = (0,1)
                           + solve' ss xs 0 (False, b0s)   -- (a,b) = (1,0)
  | (s,x,carry) == (0,0,1) = 0
  | (s,x,carry) == (0,1,1) = solve' ss xs 1 (a0s, False)   -- (a,b) = (0,1)
                           + solve' ss xs 1 (False, b0s)   -- (a,b) = (1,0)
  | (s,x,carry) == (1,0,1) = solve' ss xs 0 (a0s, b0s)     -- (a,b) = (0,0)
                           + solve' ss xs 1 (False, False) -- (a,b) = (1,1)
  | (s,x,carry) == (1,1,1) = 0


num2bits :: Integer -> [Int]
num2bits 0 = [0]
num2bits n = num2bits' n
  where num2bits' :: Integer -> [Int]
        num2bits' 0 = []
        num2bits' n = (if (n .&. 1 == 1) then 1 else 0) : num2bits' (n `div` 2)

equilength padding (xs, ys) = (xs ++ xPadding, ys ++ yPadding)
  where maxLen = max (length xs) (length ys)
        xPadding = replicate (maxLen - length xs) padding
        yPadding = replicate (maxLen - length ys) padding


main :: IO ()
main = do
  line <- getLine
  let [s,x] = map read (words line) :: [Integer]
  putStrLn $ show (solve s x)
