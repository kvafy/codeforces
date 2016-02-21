{-
Famil Door wants to celebrate his birthday with his friends from Far Far Away.
He has n friends and each of them can come to the party in a specific range of
days of the year from ai to bi. Of course, Famil Door wants to have as many
friends celebrating together with him as possible.

Far cars are as weird as Far Far Away citizens, so they can only carry two people
of opposite gender, that is exactly one male and one female. However, Far is so
far from here that no other transportation may be used to get to the party.

Famil Door should select some day of the year and invite some of his friends,
such that they all are available at this moment and the number of male friends
invited is equal to the number of female friends invited. Find the maximum
number of friends that may present at the party.

Input
The first line of the input contains a single integer n (1 ≤ n ≤ 5000) — then
number of Famil Door's friends.

Then follow n lines, that describe the friends. Each line starts with a capital
letter 'F' for female friends and with a capital letter 'M' for male friends.
Then follow two integers ai and bi (1 ≤ ai ≤ bi ≤ 366), providing that the i-th
friend can come to the party from day ai to day bi inclusive.

Output
Print the maximum number of people that may come to Famil Door's party.

Found at: http://codeforces.com/contest/629/problem/B
-}

module Main where

solve :: [(Char, Int, Int)] -> Int
solve friends = 2 * (maximum $ zipWith (\m f -> min m f) mTotal fTotal)
  where mFriends = filter (\(g,_,_) -> g == 'M') friends
        mMasks = map mask mFriends
        mTotal = foldl addMasks mask0 mMasks
        fFriends = filter (\(g,_,_) -> g == 'F') friends
        fMasks = map mask fFriends
        fTotal = foldl addMasks mask0 fMasks

mask :: (Char,Int,Int) -> [Int]
mask (_,a,b) = [if a <= i && i <= b then 1 else 0 | i <- [1..366]]

mask0 = [0 | i <- [1..366]]

addMasks [] [] = []
addMasks (x:xs) (y:ys) = (x+y) : addMasks xs ys

getLines :: Int -> IO [String]
getLines n = sequence $ replicate n getLine

parseFriend :: [String] -> (Char, Int, Int)
parseFriend [genderStr, aStr, bStr] = (head genderStr, read aStr, read bStr)

main :: IO ()
main = do
  friendCountStr <- getLine
  let n = read friendCountStr :: Int
  friendLines <- getLines n
  let friends = map (parseFriend . words) friendLines
  putStrLn $ show (solve friends)
