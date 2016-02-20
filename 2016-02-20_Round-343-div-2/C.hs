{-
As Famil Door’s birthday is coming, some of his friends (like Gabi) decided to
buy a present for him. His friends are going to buy a string consisted of round
brackets since Famil Door loves string of brackets of length n more than any
other strings!

The sequence of round brackets is called valid if and only if:

  * the total number of opening brackets is equal to the total number
    of closing brackets;
  * for any prefix of the sequence, the number of opening brackets is greater
    or equal than the number of closing brackets.

Gabi bought a string s of length m (m ≤ n) and want to complete it to obtain
a valid sequence of brackets of length n. He is going to pick some strings p
and q consisting of round brackets and merge them in a string p + s + q, that
is add the string p at the beginning of the string s and string q at the end
of the string s.

Now he wonders, how many pairs of strings p and q exists, such that the string
p + s + q is a valid sequence of round brackets. As this number may be pretty
large, he wants to calculate it modulo 109 + 7.

Input
First line contains n and m (1 ≤ m ≤ n ≤ 100 000, n - m ≤ 2000) — the desired
length of the string and the length of the string bought by Gabi, respectively.

The second line contains string s of length m consisting of characters '(' and
')' only.

Output
Print the number of pairs of string p and q such that p + s + q is a valid
sequence of round brackets modulo 109 + 7.

Found at: http://codeforces.com/contest/629/problem/C
-}

module Main where
import Data.Array ((!))
import qualified Data.Array as Array

solve :: Integer -> Integer -> [Int] -> Integer
solve n m s = sum $ [variants pLen pOpen | pLen <- [minLeftOpen .. n-m], pOpen <- [minLeftOpen .. n-m]]
  where sBias = toInteger (sum s)
        minLeftOpen = toInteger $ abs $ min 0 (minimum $ scanl (+) 0 s)
        -- using dynamic programming, compute array of (r,c)
        -- where open(r,c) = number of ways to get valid string of length 'r'
        --                   having 'c' opened braces
        open = Array.listArray ((0, 0), (n-m, n-m)) [dyn r c | (r,c) <- Array.range ((0, 0), (n-m, n-m))]
        dyn :: Integer -> Integer -> Integer
        dyn r c
          | r == 0 && c == 0    = 1
          | r == 0              = 0
          |           c == (-1) = 1
          | c > r               = 0
          | otherwise           = (openGet (r-1) (c-1)) + (openGet (r-1) (c+1))
        openGet r c
          | r < 0 || r > n-m = 0
          | c < 0 || c > n-m = 0
          | otherwise        = open ! (r,c)
        -- Given target length of 'p' and target number of opened braces in 'p',
        -- determine the number of 'p' and 'q' pairs.
        -- Trick is to use the 'open' array for both 'p' and 'q', because
        -- 'open' expresses number of excessive open braces for string of given parameters,
        -- which can be also considered as number of excessive closed braces
        -- when you reverse the string.
        variants pLen pOpen = (openGet pLen pOpen) * (openGet (n-m-pLen) (pOpen+sBias))

main :: IO ()
main = do
  line <- getLine
  let [n,m] = map read (words line) :: [Integer]
  s <- getLine
  let s' = map (\c -> if c == '(' then 1 else (-1)) s
  putStrLn $ show ((solve n m s') `mod` (10^9 + 7))
