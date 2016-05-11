module Main where
import Data.List (group, sort)

solve :: Int -> Char
solve n = head . drop (n-1) . concat . map show $ [1..]

main :: IO ()
main = do
  line <- getLine
  let n = read line :: Int
  putStrLn $ [solve n]
