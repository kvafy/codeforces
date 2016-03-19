{-
The life goes up and down, just like nice sequences. Sequence t1, t2, ..., tn
is called nice if the following two conditions are satisfied:

  * ti < ti + 1 for each odd i < n;
  * ti > ti + 1 for each even i < n.

For example, sequences (2, 8), (1, 5, 1) and (2, 5, 1, 100, 99, 120) are nice,
while (1, 1), (1, 2, 3) and (2, 5, 3, 2) are not.

Bear Limak has a sequence of positive integers t1, t2, ..., tn. This sequence
is not nice now and Limak wants to fix it by a single swap. He is going to
choose two indices i < j and swap elements ti and tj in order to get a nice
sequence. Count the number of ways to do so. Two ways are considered different
if indices of elements chosen for a swap are different.

Input
The first line of the input contains one integer n (2 ≤ n ≤ 150 000) — the
length of the sequence.

The second line contains n integers t1, t2, ..., tn (1 ≤ ti ≤ 150 000) — the
initial sequence. It's guaranteed that the given sequence is not nice.

Output
Print the number of ways to swap two elements exactly once in order to get
a nice sequence.

Found at: http://codeforces.com/contest/653/problem/C
-}

module Main where
import Data.Array (Array, listArray, bounds, inRange, (!))
import Data.List (group, sort)

solve :: [Int] -> Int
solve ts = if length offenders > 4
             then 0
             else length [swap | swap@(i,j) <- swaps
                               , swapHelps tsArray (i-1:i:j-1:j:offenders) (i,j)]
  where pairs :: [(Int, (Int, Int), Bool)]
        pairs = zipWith3 (\i (a,b) cmp -> (i,(a,b), cmp a b)) [0..] (zip ts (tail ts)) (cycle [(<),(>)])
        offenders = map (\(i,_,_) -> i) $ filter (\(_,_,b) -> not b) pairs
        swaps = distinct $ [(i',j') | i <- offenders
                                    , i' <- [i,i+1]
                                    , 0 <= i' && i' < n-1
                                    , j' <- [i'+1..n-1]]
                           ++
                           [(i',j') | j <- offenders
                                    , j' <- [j,j+1]
                                    , 1 <= j' && j' < n
                                    , i' <- [0..j'-1]]
        n = length ts
        tsArray = listArray (0,n-1) ts

swapHelps :: Array Int Int -> [Int] -> (Int,Int) -> Bool
swapHelps ts offenders swap@(i,j) = all isGoodAt offenders
  where isGoodAt idx = if all ((bounds ts) `inRange`) [idx, idx+1]
                         then let cmp = if even idx then (<) else (>)
                              in valueAt idx `cmp` valueAt (idx+1)
                         else True
        valueAt idx | i == idx  = ts ! j
                    | j == idx  = ts ! i
                    | otherwise = ts ! idx

distinct :: (Ord a) => [a] -> [a]
distinct = map head . group . sort

main :: IO ()
main = do
  line1 <- getLine
  line2 <- getLine
  let ts = map read (words line2) :: [Int]
  putStrLn $ show $ solve ts
