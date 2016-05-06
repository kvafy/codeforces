{-
This problem is given in two versions that differ only by constraints. If you
can solve this problem in large constraints, then you can just write a single
solution to the both versions. If you find the problem too difficult in large
constraints, you can write solution to the simplified version only.

Waking up in the morning, Apollinaria decided to bake cookies. To bake one
cookie, she needs n ingredients, and for each ingredient she knows the value
ai — how many grams of this ingredient one needs to bake a cookie. To prepare
one cookie Apollinaria needs to use all n ingredients.

Apollinaria has bi gram of the i-th ingredient. Also she has k grams of a magic
powder. Each gram of magic powder can be turned to exactly 1 gram of any of
the n ingredients and can be used for baking cookies.

Your task is to determine the maximum number of cookies, which Apollinaria is
able to bake using the ingredients that she has and the magic powder.

Input
The first line of the input contains two positive integers n and k
(1 ≤ n, k ≤ 1000) — the number of ingredients and the number of grams of the
magic powder.

The second line contains the sequence a1, a2, ..., an (1 ≤ ai ≤ 1000), where
the i-th number is equal to the number of grams of the i-th ingredient,
needed to bake one cookie.

The third line contains the sequence b1, b2, ..., bn (1 ≤ bi ≤ 1000), where
the i-th number is equal to the number of grams of the i-th ingredient,
which Apollinaria has.

Output
Print the maximum number of cookies, which Apollinaria will be able to
bake using the ingredients that she has and the magic powder.

Available at: http://codeforces.com/contest/670/problem/D1
-}

module Main where
import Data.List (group, sort)

solve :: Int -> [Int] -> [Int] -> Int
solve m r s = c + solve' 0 m (bakeCookies c s)
  where c = minimum $ zipWith div s r
        bakeCookies :: Int -> [Int] -> [Int]
        bakeCookies cnt s' = zipWith (\req supply -> max 0 (supply - req * cnt)) r s'
        solve' :: Int -> Int -> [Int] -> Int
        solve' c' m' s' = if m' < sum (requiredMagic s')
          then c'
          else solve' (c' + 1) (m' - sum (requiredMagic s')) (bakeCookies 1 s')
        requiredMagic :: [Int] -> [Int]
        requiredMagic s' = zipWith (\req supply -> max 0 (req - supply)) r s'

main :: IO ()
main = do
  line1 <- getLine
  line2 <- getLine
  line3 <- getLine

  let [_,m] = map read (words line1) :: [Int]
      r = map read (words line2) :: [Int]
      s = map read (words line3) :: [Int]
  putStrLn $ show (solve m r s)
