{-
Limak is a little polar bear. Polar bears hate long strings and thus they like
to compress them. You should also know that Limak is so young that he knows only
first six letters of the English alphabet: 'a', 'b', 'c', 'd', 'e' and 'f'.

You are given a set of q possible operations. Limak can perform them in any
order, any operation may be applied any number of times. The i-th operation is
described by a string ai of length two and a string bi of length one. No two of
q possible operations have the same string ai.

When Limak has a string s he can perform the i-th operation on s if the first
two letters of s match a two-letter string ai. Performing the i-th operation
removes first two letters of s and inserts there a string bi. See the notes
section for further clarification.

You may note that performing an operation decreases the length of a string s
exactly by 1. Also, for some sets of operations there may be a string that
cannot be compressed any further, because the first two letters don't match
any ai.

Limak wants to start with a string of length n and perform n - 1 operations
to finally get a one-letter string "a". In how many ways can he choose the
starting string to be able to get "a"? Remember that Limak can use only
letters he knows.

Input
The first line contains two integers n and q (2 ≤ n ≤ 6, 1 ≤ q ≤ 36) — the
length of the initial string and the number of available operations.

The next q lines describe the possible operations. The i-th of them contains
two strings ai and bi (|ai| = 2, |bi| = 1). It's guaranteed that ai ≠ aj
for i ≠ j and that all ai and bi consist of only first six lowercase English
letters.

Output
Print the number of strings of length n that Limak will be able to transform
to string "a" by applying only operations given in the input.

Found at: http://codeforces.com/contest/653/problem/B
-}

module Main where
import Data.Array ((!), listArray, range)

getLines :: Int -> IO [String]
getLines n = sequence $ replicate n getLine

solve :: Int -> [(String, Char)] -> Integer
solve n qs = sum [arr ! (c,n) | c <- ['a'..'f']]
  where arr = listArray arrBounds [dp c l | (c,l) <- range arrBounds]
        arrBounds = (('a',1), ('f',n))
        -- dp(c,l) ~ how many ways can I end up with string of length 'l'
        --           starting with character 'c'
        dp :: Char -> Int -> Integer
        dp 'a' 1 = 1
        dp _ 1 = 0
        dp fstChr len = sum [toInteger (length $ viableExpansions fstChr reduceTo qs)
                            * (arr ! (reduceTo, len - 1))
                            | reduceTo <- "abcdef"]

viableExpansions :: Char -> Char -> [(String, Char)] -> [(String, Char)]
viableExpansions lp r = filter (\(lp':_, r') -> lp == lp' && r == r')

main :: IO ()
main = do
  line1 <- getLine
  let [n,q] = map read (words line1) :: [Int]
  qLines <- getLines q
  let qs = map ((\[a,b] -> (a, head b)) . words) qLines :: [(String, Char)]
  putStrLn $ show $ solve n qs
