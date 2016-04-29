{-
instructions go here...
-}

module Main where
import Data.List (group, sort)
import Data.Set (Set(..), empty, toList, member, insert, union)

solve :: String -> [String]
solve str = sort . map reverse . toList . suffixes empty $ reverse str
  where suffixes :: Set String -> String -> Set String
        suffixes seen str =
          let suffixes2 = case trySuffix 2 seen str of
                            Nothing           -> seen
                            Just (seen',str') -> suffixes seen' str'
              suffixes3 = case trySuffix 3 seen str of
                                Nothing           -> seen
                                Just (seen',str') -> suffixes seen' str'
          in union suffixes2 suffixes3
        trySuffix :: Int -> Set String -> String -> Maybe (Set String, String)
        trySuffix n seen str
          | length str < 5 + n = Nothing
          | otherwise          = let suffix = take n str
                                 in if (take n str) `member` seen
                                      then Nothing
                                      else Just $ (insert (take n str) seen, drop n str)

main :: IO ()
main = do
  word <- getLine
  let suffixes = solve word
  putStrLn $ show (length suffixes)
  mapM_ putStrLn suffixes
