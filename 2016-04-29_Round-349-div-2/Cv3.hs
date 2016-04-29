{-
instructions go here...
-}

module Main where
import Data.List (group, sort)

solve :: String -> [String]
solve str = sort . map reverse . suffixes [] $ reverse str
  where suffixes :: [String] -> String -> [String]
        suffixes seen str =
          let suffixes2 = case trySuffix 2 seen str of
                            Nothing           -> seen
                            Just (seen',str') -> suffixes seen' str'
              suffixes3 = case trySuffix 3 seen str of
                                Nothing           -> seen
                                Just (seen',str') -> suffixes seen' str'
          in distinct (suffixes2 ++ suffixes3)
        trySuffix :: Int -> [String] -> String -> Maybe ([String], String)
        trySuffix n seen str
          | length str < 5 + n = Nothing
          | otherwise          = let suffix = take n str
                                 in if (take n str) `isHeadOf` seen
                                      then Nothing
                                      else Just $ (take n str : seen, drop n str)
        x `isHeadOf` xs | null xs   = False
                        | otherwise = x == head xs

distinct :: (Ord a) => [a] -> [a]
distinct = map head . group . sort

main :: IO ()
main = do
  word <- getLine
  let suffixes = solve word
  putStrLn $ show (length suffixes)
  mapM_ putStrLn suffixes
