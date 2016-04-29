{-
instructions go here...
-}

module Main where
import Data.List (group, sort)

solve :: [Integer] -> Integer
solve rods = delta $ foldr f (0,0) (sort rods)
  where f :: Integer -> (Integer,Integer) -> (Integer,Integer)
        f x (a,b) | a < b     = (a+x,b)
                  | otherwise = (a,b+x)
        delta :: (Integer,Integer) -> Integer
        delta (a,b) = 1 + abs (a - b)


main :: IO ()
main = do
  _ <- getLine
  rodsStr <- getLine
  let rods = map read (words rodsStr) :: [Integer]
  putStrLn $ show (solve rods)
