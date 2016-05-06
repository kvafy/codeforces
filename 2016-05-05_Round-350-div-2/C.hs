{-
Moscow is hosting a major international conference, which is attended by n
scientists from different countries. Each of the scientists knows exactly one
language. For convenience, we enumerate all languages of the world with integers
from 1 to 10^9.

In the evening after the conference, all n scientists decided to go to the cinema.
There are m movies in the cinema they came to. Each of the movies is characterized
by two distinct numbers — the index of audio language and the index of subtitles
language. The scientist, who came to the movie, will be very pleased if he knows
the audio language of the movie, will be almost satisfied if he knows the language
of subtitles and will be not satisfied if he does not know neither one nor the
other (note that the audio language and the subtitles language for each movie
are always different).

Scientists decided to go together to the same movie. You have to help them
choose the movie, such that the number of very pleased scientists is maximum
possible. If there are several such movies, select among them one that will
maximize the number of almost satisfied scientists.

Input
The first line of the input contains a positive integer n (1 ≤ n ≤ 200 000) —
the number of scientists.

The second line contains n positive integers a1, a2, ..., an (1 ≤ ai ≤ 10^9),
where ai is the index of a language, which the i-th scientist knows.

The third line contains a positive integer m (1 ≤ m ≤ 200 000) — the number
of movies in the cinema.

The fourth line contains m positive integers b1, b2, ..., bm (1 ≤ bj ≤ 10^9),
where bj is the index of the audio language of the j-th movie.

The fifth line contains m positive integers c1, c2, ..., cm (1 ≤ cj ≤ 10^9),
where cj is the index of subtitles language of the j-th movie.

It is guaranteed that audio languages and subtitles language are different
for each movie, that is bj ≠ cj.

Output
Print the single integer — the index of a movie to which scientists should
go. After viewing this movie the number of very pleased scientists should be
maximum possible. If in the cinema there are several such movies, you need
to choose among them one, after viewing which there will be the maximum
possible number of almost satisfied scientists.

If there are several possible answers print any of them.

Available at: http://codeforces.com/contest/670/problem/C
-}

{-# LANGUAGE BangPatterns #-}

module Main where
import Data.List (maximumBy)
import qualified Data.Map.Strict as M
import Data.Monoid (mappend)
import Data.Maybe (fromMaybe)


solve :: [Int] -> [Int] -> [Int] -> Int
solve sc aud sub = nth3_3 . maximumBy comp $ scoredMovies
  where -- map: language -> count
        langCounts = M.fromListWith (+) [(lang,1) | lang <- sc] :: M.Map Int Int
        !scoredMovies = zipWith3 (\movieId audLang subLang ->
                                   ( fromMaybe 0 $ M.lookup audLang langCounts
                                   , fromMaybe 0 $ M.lookup subLang langCounts
                                   , movieId))
                                 [1..] aud sub
        comp (a1,b1,_) (a2,b2,_) = (a1 `compare` a2) `mappend` (b1 `compare` b2)
        nth3_3 (_,_,x) = x

main :: IO ()
main = do
  _ <- getLine
  scStr <- getLine
  _ <- getLine
  audioStr <- getLine
  subStr <- getLine

  let !sc = map read (words scStr) :: [Int]
      !audio = map read (words audioStr) :: [Int]
      !sub = map read (words subStr) :: [Int]
  putStrLn $ show (solve sc audio sub)
