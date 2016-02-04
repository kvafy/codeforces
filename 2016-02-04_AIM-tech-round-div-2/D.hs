{-
instructions go here...
-}

module Main where

getLines :: Int -> IO [String]
getLines n = sequence $ replicate n getLine

main :: IO ()
main = do
  line <- getLine
  let lineItems = map read (words line) :: [Integer]
  putStrLn $ show lineItems
