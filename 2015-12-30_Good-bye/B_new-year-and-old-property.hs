-- The year 2015 is almost over.

-- Limak is a little polar bear. He has recently learnt about the binary system.
-- He noticed that the passing year has exactly one zero in its representation
-- in the binary system — 2015_10 = 11111011111_2. Note that he doesn't care about
-- the number of zeros in the decimal representation.

-- Limak chose some interval of years. He is going to count all years from this
-- interval that have exactly one zero in the binary representation. Can you do
-- it faster?

-- Assume that all positive integers are always written without leading zeros.

-- Input
-- The only line of the input contains two integers a and b (1 ≤ a ≤ b ≤ 10^18)
-- which are the first year and the last year in Limak's interval respectively.

-- Output
-- Print one integer – the number of years Limak will count in his chosen
-- interval.

module Main where
import Data.Bits

rInnerZeroPositions :: Integer -> [Integer]
rInnerZeroPositions n
  | n == 0       = []
  | n .&. 1 == 0 = 0 : (map (+1) $ rInnerZeroPositions (shiftR n 1))
  | otherwise    = map (+1) $ rInnerZeroPositions (shiftR n 1)

zeroCount :: Integer -> Int
zeroCount n = length $ rInnerZeroPositions n

limakizeRightmost0 n = n .|. (shiftL 1 idx)
  where idx = (fromInteger . head . rInnerZeroPositions) n

nextLimakYear :: Integer -> Integer
nextLimakYear y = closestLimakYear (y + 1)

closestLimakYear :: Integer -> Integer
closestLimakYear y
  | zeroCount y == 1 = y
  | zeroCount y == 0 = closestLimakYear (y + 1)
  | otherwise        = closestLimakYear (limakizeRightmost0 y)

limakYearsSince :: Integer -> [Integer]
limakYearsSince y = iterate nextLimakYear (closestLimakYear y)

main :: IO ()
main = do
  line <- getLine
  let [a, b] = map read (words line) :: [Integer]
  let limakYears = takeWhile (\y -> y <= b) (limakYearsSince a)
  putStrLn $ (show . length) limakYears
