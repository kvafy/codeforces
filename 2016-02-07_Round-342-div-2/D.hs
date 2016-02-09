{-
instructions go here...
-}

module Main where

solve :: [Char] -> Maybe String
solve n = if (n == reverse n)
             && even (length n) || splittableMiddle n
          then Just (splitPalindrome n)
          else Nothing

splittableMiddle n = midChar `elem` "02468"
  where midChar = n !! (length n `div` 2)

splitPalindrome n = sideL ++ middle ++ sideR
  where sideL = take sideCnt n
        sideR = replicate sideCnt '0'
        middle = if even (length n)
                   then []
                   else [div2 (n !! sideCnt)]
        sideCnt = length n `div` 2

div2 '0' = '0'
div2 '2' = '1'
div2 '4' = '2'
div2 '6' = '3'
div2 '8' = '4'

main :: IO ()
main = do
  n <- getLine
  case solve n of
    Just solution -> putStrLn solution
    Nothing       -> putStrLn $ show 0
