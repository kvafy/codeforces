{-
First-rate specialists graduate from Berland State Institute of Peace and
Friendship. You are one of the most talented students in this university. The
education is not easy because you need to have fundamental knowledge in different
areas, which sometimes are not related to each other.

For example, you should know linguistics very well. You learn a structure of
Reberland language as foreign language. In this language words are constructed
according to the following rules. First you need to choose the "root" of the
word — some string which has more than 4 letters. Then several strings with the
length 2 or 3 symbols are appended to this word. The only restriction — it is
not allowed to append the same string twice in a row. All these strings are
considered to be suffixes of the word (this time we use word "suffix" to
describe a morpheme but not the few last characters of the string as you may
used to).

Here is one exercise that you have found in your task list. You are given the
word s. Find all distinct strings with the length 2 or 3, which can be suffixes
of this word according to the word constructing rules in Reberland language.

Two strings are considered distinct if they have different length or there is
a position in which corresponding characters do not match.

Let's look at the example: the word abacabaca is given. This word can be
obtained in the following ways:
  * root(abacabaca)
  * root(abacaba) ca
  * root(abacab) aca
  * root(abaca) ba ca
Thus, the set of possible suffixes for this word is {aca, ba, ca}.

Input
The only line contains a string s (5 ≤ |s| ≤ 10^4) consisting of lowercase
English letters.

Output
On the first line print integer k — a number of distinct possible suffixes.
On the next k lines print suffixes.

Print suffixes in lexicographical (alphabetical) order.

Found at: http://codeforces.com/contest/667/problem/C
-}

module Main where
import Data.List (group, sort, splitAt)
import Data.Set (Set(..), empty, singleton, toList, insert, unions, member)

-- guaranteed O(n) solution
solve :: String -> [String]
solve str = sort . toList . nth3_3 . foldr f f0 $ (drop 5 str)
  where f0 = ([True,False,False,False,False], "-----", empty)
        f :: Char -> ([Bool],[Char],Set String) -> ([Bool],[Char],Set String)
        f x0 (oks@[ok1,ok2,ok3,ok4,ok5], xs@[x1,x2,x3,x4,x5], suffixes) =
          let ok02 = ok2 && ok5
                  || ok2 && [x0,x1] /= [x2,x3]
              ok03 = ok3 && ok5
                  || ok3 && [x0,x1,x2] /= [x3,x4,x5]
              suffixes' = unions [ suffixes
                                 , if ok02 then singleton [x0,x1] else empty
                                 , if ok03 then singleton [x0,x1,x2] else empty
                                 ]
          in (enqueue (ok02 || ok03) oks, enqueue x0 xs, suffixes')
        nth3_3 (_,_,x) = x
        enqueue x xs = x : init xs


-- DFS style, too slow
solve' :: String -> [String]
solve' str = sort . map reverse . toList . fst . suffixes (empty,"",0,empty) $ reverse str
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
