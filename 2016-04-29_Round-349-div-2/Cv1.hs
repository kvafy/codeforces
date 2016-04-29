{-
instructions go here...
-}

module Main where
import Data.List (group, sort)
import Data.Set (Set(..), empty, toList, member, insert, union)

solve :: String -> [String]
solve str = toList $ suffixes empty (reverse str)
  where suffixes :: Set String -> String -> Set String
        suffixes seen str
          | length str < 6 = seen
          | otherwise       = let suffix2 = take 2 str
                                  suffixes2 = if suffix2 `member` seen
                                                then seen
                                                else suffixes (insert suffix2 seen) (drop 2 str)
                                  suffix3 = take 3 str
                                  suffixes3 = if suffix3 `member` seen
                                                then seen
                                                else suffixes (insert suffix3 seen) (drop 3 str)
                              in union suffixes2 suffixes3
        trySuffix :: Int -> Set String -> String -> Maybe (Set String)
        trySuffix n seen str
          | length str < 4 + n = Nothing
          | otherwise          = let suffix = take n str
                                 in if (take n str) `member` seen
                                      then Nothing
                                      else Just $ insert (take n str) seen

distinct :: (Ord a) => [a] -> [a]
distinct = map head . group . sort

main :: IO ()
main = do
  word <- getLine
  let suffixes = solve word
  putStrLn $ show (length suffixes)
  mapM_ putStrLn (map show suffixes)
