{-
instructions go here...
-}

module Main where
import Data.List (group, sort, splitAt)
import Data.Set (Set(..), empty, singleton, toList, insert, unions, member)

solve :: String -> [String]
solve str = sort . map reverse . toList . fst . suffixes (empty,"",0,empty) $ reverse str
  where suffixes :: (Set String, String, Int, Set Int) -> String -> (Set String, Set Int)
        suffixes (seen,prev,pos,processed) str =
          if pos `member` processed
            then (seen,processed) -- prune search of this branch - this state has already been explored
            else
              let (suffixes2,processed2) = case trySuffix 2 (seen,prev) str of
                                            Nothing ->
                                              (seen,processed)
                                            Just (seen',prev',str') ->
                                              suffixes (seen',prev',pos+2,processed) str'
                  (suffixes3,processed3) = case trySuffix 3 (seen,prev) str of
                                            Nothing ->
                                              (seen,processed2)
                                            Just (seen',prev',str') ->
                                              suffixes (seen',prev',pos+3,processed2) str'
              in (unions [suffixes2, suffixes3], unions [processed2, processed3, singleton pos])
        trySuffix :: Int -> (Set String, String) -> String -> Maybe (Set String, String, String)
        trySuffix n (seen,prev) str
          | str `shorterThan` (5 + n) = Nothing
          | otherwise          = let (suffix,rest) = splitAt n str
                                 in if suffix == prev
                                      then Nothing
                                      else Just (insert suffix seen, suffix, rest)

shorterThan [] n = n > 0
shorterThan xs n = shorterThan (tail xs) (n-1)

main :: IO ()
main = do
  word <- getLine
  let suffixes = solve word
  putStrLn $ show (length suffixes)
  mapM_ putStrLn suffixes
