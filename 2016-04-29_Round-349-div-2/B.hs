{-
As some of you know, cubism is a trend in art, where the problem of constructing
volumetrical shape on a plane with a combination of three-dimensional geometric
shapes comes to the fore.

A famous sculptor Cicasso, whose self-portrait you can contemplate, hates cubism.
He is more impressed by the idea to transmit two-dimensional objects through
three-dimensional objects by using his magnificent sculptures. And his new
project is connected with this. Cicasso wants to make a coat for the haters of
anticubism. To do this, he wants to create a sculpture depicting a well-known
geometric primitive — convex polygon.

Cicasso prepared for this a few blanks, which are rods with integer lengths, and
now he wants to bring them together. The i-th rod is a segment of length li.

The sculptor plans to make a convex polygon with a nonzero area, using all rods
he has as its sides. Each rod should be used as a side to its full length. It
is forbidden to cut, break or bend rods. However, two sides may form a straight
angle 180°.

Cicasso knows that it is impossible to make a convex polygon with a nonzero area
out of the rods with the lengths which he had chosen. Cicasso does not want to
leave the unused rods, so the sculptor decides to make another rod-blank with
an integer length so that his problem is solvable. Of course, he wants to make
it as short as possible, because the materials are expensive, and it is improper
deed to spend money for nothing.

Help sculptor!

Input
The first line contains an integer n (3 ≤ n ≤ 105) — a number of rod-blanks.

The second line contains n integers li (1 ≤ li ≤ 109) — lengths of rods, which
Cicasso already has. It is guaranteed that it is impossible to make a polygon
with n vertices and nonzero area using the rods Cicasso already has.

Output
Print the only integer z — the minimum length of the rod, so that after adding
it it can be possible to construct convex polygon with (n + 1) vertices and
nonzero area from all of the rods.

Found at: http://codeforces.com/contest/667/problem/B
-}

module Main where
import Data.List (group, sort)

solve :: [Integer] -> Integer
solve rods = delta $ foldr f (0,0) (sort rods)
  where f :: Integer -> (Integer,Integer) -> (Integer,Integer)
        f x (a,b) | a < b     = (a+x,b)
                  | otherwise = (a,b+x)
        delta :: (Integer,Integer) -> Integer
        delta (a,b) = 1 + abs (a - b)


main :: IO ()
main = do
  _ <- getLine
  rodsStr <- getLine
  let rods = map read (words rodsStr) :: [Integer]
  putStrLn $ show (solve rods)
