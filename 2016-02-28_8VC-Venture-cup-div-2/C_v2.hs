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
import qualified Data.Array as Array
import Data.Array ((!))
import Data.Bits ((.&.))

solve :: Integer -> Integer -> Integer
solve s x = let (abCombinations, aOfZerosPossible, bOfZerosPossible) = arr ! (n,0)
            in abCombinations
               - (if aOfZerosPossible then 1 else 0)
               - (if bOfZerosPossible then 1 else 0)
  where [sBitsRaw, xBitsRaw] = [num2bits s, num2bits x]
        n = max (length sBitsRaw) (length xBitsRaw)
        [sBits, xBits] = map (padL 0 n) [sBitsRaw, xBitsRaw]
        arr = Array.listArray arrBounds [dp len carry | (len,carry) <- Array.range arrBounds]
        arrBounds = ((1,0),(n,1))
        -- dp l c ~ what is the number of A,B combinations to get number
        --          of length 'l' so that their sum results with 'c' carry
        dp :: Int -> Int -> (Integer, Bool, Bool)
        dp len carry
          -- TODO simplify all the checks for len == 1
          -- | len == 0 = if carry == 0
          --                then (1, True, True)
          --                else (0, False, False)
          | (s,x,carry) == (0,0,0) = -- (a,b,cPrev) in {(0,0,0)}
                                     if len == 1
                                       then (1, True, True)
                                       else let (combs_c0, a0s_c0, b0s_c0) = arr ! (len - 1, 0)
                                            in (combs_c0, a0s_c0, b0s_c0)
          | (s,x,carry) == (0,1,0) = (0, False, False)
          | (s,x,carry) == (1,0,0) = -- (a,b,cPrev) in {(0,0,1)}
                                     if len == 1
                                       then (0, False, False)
                                       else let (combs_c1, a0s_c1, b0s_c1) = arr ! (len - 1, 1)
                                            in (combs_c1, a0s_c1, b0s_c1)
          | (s,x,carry) == (1,1,0) = -- (a,b,cPrev) in {(0,1,0),(1,0,0)}
                                     if len == 1
                                       then (2, True, True)
                                       else let (combs_c0, a0s_c0, b0s_c0) = arr ! (len - 1, 0)
                                            in (2 * combs_c0, a0s_c0, b0s_c0)
          | (s,x,carry) == (0,0,1) = -- (a,b,cPrev) in {(1,1,0)}
                                     if len == 1
                                       then (1, False, False)
                                       else let (combs_c0, a0s_c0, b0s_c0) = arr ! (len - 1, 0)
                                            in (combs_c0, False, False)
          | (s,x,carry) == (0,1,1) = -- (a,b,cPrev) in {(0,1,1),(1,0,1)}
                                     if len == 1
                                       then (0, False, False)
                                       else let (combs_c1, a0s_c1, b0s_c1) = arr ! (len - 1, 1)
                                            in (2 * combs_c1, a0s_c1, b0s_c1)
          | (s,x,carry) == (1,0,1) = -- (a,b,cPrev) in {(1,1,1)}
                                     if len == 1
                                       then (0, False, False)
                                       else let (combs_c1, a0s_c1, b0s_c1) = arr ! (len - 1, 1)
                                            in (combs_c1, False, False)
          | (s,x,carry) == (1,1,1) = (0, False, False)
          where s = sBits !! (n - len)
                x = xBits !! (n - len)

num2bits :: Integer -> [Int]
num2bits 0 = [0]
num2bits n = reverse $ num2bits' n
  where num2bits' :: Integer -> [Int]
        num2bits' 0 = []
        num2bits' n = (if (n .&. 1 == 1) then 1 else 0) : num2bits' (n `div` 2)

padL padItem targetLen xs = (replicate (targetLen - length xs) padItem) ++ xs


main :: IO ()
main = do
  line <- getLine
  let [s,x] = map read (words line) :: [Integer]
  putStrLn $ show (solve s x)
