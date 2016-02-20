{-
instructions go here...
-}

module Main where

solve :: Char -> Integer -> (Integer,Integer) -> Integer
solve d m (a,b) = foldl (\acc _ -> acc + 1) 0 $ filter (isDMagic d) multiples
  where multiples = takeWhile (<= b) $ iterate (+ m) (closestMultiple a m)

closestMultiple start m = if start `mod` m == 0
                            then start
                            else start + (m - (start `mod` m))

isDMagic :: Char -> Integer -> Bool
isDMagic d num = all (== d) (everyNth 2 numStr)
                 &&
                 all (/= d) (everyNth 2 ('x':numStr))
  where numStr = show num

everyNth :: Integer -> [a] -> [a]
everyNth n xs = [snd x | x <- (zip [1..] xs), fst x `mod` n == 0]

main :: IO ()
main = do
  line <- getLine
  let [mStr, dStr] = words line
      [d] = dStr
      m = read mStr :: Integer
  aStr <- getLine
  bStr <- getLine
  let a = read aStr :: Integer
      b = read bStr :: Integer
  putStrLn $ show $ (solve d m (a,b)) `mod` (10^9 + 7)
