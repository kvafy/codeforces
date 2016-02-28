{-
Paul is at the orchestra. The string section is arranged in an r × c rectangular
grid and is filled with violinists with the exception of n violists. Paul really
likes violas, so he would like to take a picture including at least k of them.
Paul can take a picture of any axis-parallel rectangle in the orchestra. Count
the number of possible pictures that Paul can take.

Two pictures are considered to be different if the coordinates of corresponding
rectangles are different.

Input
The first line of input contains four space-separated integers r, c, n, k
(1 ≤ r, c, n ≤ 10, 1 ≤ k ≤ n) — the number of rows and columns of the string
section, the total number of violas, and the minimum number of violas Paul
would like in his photograph, respectively.

The next n lines each contain two integers xi and yi (1 ≤ xi ≤ r, 1 ≤ yi ≤ c):
the position of the i-th viola. It is guaranteed that no location appears more
than once in the input.

Output
Print a single integer — the number of photographs Paul can take which include
at least k violas.

Found at: http://codeforces.com/contest/635/problem/A
-}

module Main where

solve :: (Int,Int) -> Int -> [(Int,Int)] -> Int
solve (r,c) k violas = length $ filter (>= k) $ map containedViolas rects
  where rects = [((r1,c1),(r2,c2)) | r1 <- [1..r], r2 <- [r1..r], c1 <- [1..c], c2 <- [c1..c]]
        containedViolas rect = length $ filter (contains rect) violas
        contains ((r1,c1),(r2,c2)) (x,y)
          = r1 <= x && x <= r2 && c1 <= y && y <= c2

getLines :: Int -> IO [String]
getLines n = sequence $ replicate n getLine

main :: IO ()
main = do
  line <- getLine
  let [r,c,n,k] = map read (words line) :: [Int]
  violaLines <- getLines n
  let violas = map ((\[x,y] -> (x,y)) . map read . words) violaLines :: [(Int,Int)]
  putStrLn $ show (solve (r,c) k violas)
