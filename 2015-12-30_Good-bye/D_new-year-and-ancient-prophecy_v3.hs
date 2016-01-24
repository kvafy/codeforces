-- Limak is a little polar bear. In the snow he found a scroll with the ancient
-- prophecy. Limak doesn't know any ancient languages and thus is unable to
-- understand the prophecy. But he knows digits!

-- One fragment of the prophecy is a sequence of n digits. The first digit
--  isn't zero. Limak thinks that it's a list of some special years. It's hard
--  to see any commas or spaces, so maybe ancient people didn't use them. Now
--  Limak wonders what years are listed there.

-- Limak assumes three things:
--   Years are listed in the strictly increasing order;
--   Every year is a positive integer number;
--   There are no leading zeros.

-- Limak is going to consider all possible ways to split a sequence into numbers
--  (years), satisfying the conditions above. He will do it without any help.
--  However, he asked you to tell him the number of ways to do so. Since this
--  number may be very large, you are only asked to calculate it modulo 10^9 + 7.

-- Input
-- The first line of the input contains a single integer n (1 ≤ n ≤ 5000)
-- — the number of digits.

-- The second line contains a string of digits and has length equal to n.
-- It's guaranteed that the first digit is not '0'.

-- Output
-- Print the number of ways to correctly split the given sequence modulo 109 + 7.

module Main where
import Data.Array ((!))
import qualified Data.Array as Array

-- Solution: sum_of {S(_,_,n)}
--
-- Dynamic programming solution explanation:
--   S(0,0,_) = 1
--   S(i,j,k) = sum_of {S(i',i-1,j) | year (i'..i-1) < year (i..j)}
--
-- S(i,j,k) ~ How many splits are possible for input if the previous year number
--            is expressed by characters input[i..j] and the current year number
--            is expressed by characters input[j+1..k].
--            Note: string is indexed from 1
splits :: String -> Integer
splits input = foldl (+) 0 [sijk ! (i,j,n) | i <- [0..n-1], j <- [i..n-1]]
  where n = length input
        sijk = Array.listArray bounds [splits' ijk | ijk <- Array.range bounds]
        bounds = ((0,0,0), (n,n,n))
        splits' (i,j,k)
          | i == 0 && j == 0 && k > 0  = 1
          | not $ isValid (i,j,k)      = 0
          | otherwise                  = foldl (+) 0 [sijk ! (i',i-1,j) | i' <- [0..i-1]]
        isValid (i,j,k) = 0 < i && i <= j && j < k && k <= n
                       && input' ! (j+1) /= '0'
                       && num i j < num (j+1) k
        num i j = read $ [input' ! idx | idx <- [i..j]] :: Integer
        input' = Array.listArray (1,n) input -- for O(1) string look-ups

main :: IO ()
main = do
  _ <- getLine
  input <- getLine
  let splitCount = splits input
  putStrLn $ show (splitCount `mod` (10^9 + 7))
