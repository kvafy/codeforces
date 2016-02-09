{-
People do many crazy things to stand out in a crowd. Some of them dance, some
learn by heart rules of Russian language, some try to become an outstanding
competitive programmers, while others collect funny math objects.

Alis is among these collectors. Right now she wants to get one of k-special
tables. In case you forget, the table n × n is called k-special if the following
three conditions are satisfied:
  * every integer from 1 to n2 appears in the table exactly once;
  * in each row numbers are situated in increasing order;
  * the sum of numbers in the k-th column is maximum possible.

Your goal is to help Alice and find at least one k-special table of size n × n.
Both rows and columns are numbered from 1 to n, with rows numbered from top to
bottom and columns numbered from left to right.

Input
The first line of the input contains two integers n and k (1 ≤ n ≤ 500, 1 ≤ k ≤ n)
— the size of the table Alice is looking for and the column that should have
maximum possible sum.

Output
First print the sum of the integers in the k-th column of the required table.

Next n lines should contain the description of the table itself: first line
should contains n elements of the first row, second line should contain n elements
of the second row and so on.

If there are multiple suitable table, you are allowed to print any.

Found at: http://codeforces.com/contest/625/problem/C
-}

module Main where

solve :: Int -> Int -> [[Int]]
solve n k = take n $ solve' [1 ..] [n*n, n*n-1 ..]
  where solve' :: [Int] -> [Int] -> [[Int]]
        solve' lower upper = row : solve' lower' upper'
          where lower' = drop (k - 1) lower
                upper' = drop (n - k + 1) upper
                row = take (k - 1) lower ++ reverse (take (n - k + 1) upper)

kTableSum :: [[Int]] -> Int -> Int
kTableSum kTable k = sum $ map (!! (k - 1)) kTable

kTableToStr :: [[Int]] -> String
kTableToStr rows = unlines $ map (unwords . numsToStrings) rows
  where numsToStrings = map show :: [Int] -> [String]

main :: IO ()
main = do
  line <- getLine
  let [n, k] = map read (words line) :: [Int]
      ktable = solve n k
  putStrLn $ show $ kTableSum ktable k
  putStrLn $ kTableToStr ktable
