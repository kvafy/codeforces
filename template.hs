{-
instructions go here...
-}

module Main where
import Data.List (group, sort)

getLines :: Int -> IO [String]
getLines n = sequence $ replicate n getLine

distinct :: (Ord a) => [a] -> [a]
distinct = map head . group . sort

shorterThan :: [a] -> Int -> Bool
shorterThan [] n = n > 0
shorterThan (_:xs) n = shorterThan xs (n-1)

main :: IO ()
main = do
  line <- getLine
  let lineItems = map read (words line) :: [Integer]
  putStrLn $ show lineItems
