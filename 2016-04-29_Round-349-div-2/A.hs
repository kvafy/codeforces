{-
A lot of people in Berland hates rain, but you do not. Rain pacifies, puts your
thoughts in order. By these years you have developed a good tradition — when it
rains, you go on the street and stay silent for a moment, contemplate all around
you, enjoy freshness, think about big deeds you have to do.

Today everything had changed quietly. You went on the street with a cup
contained water, your favorite drink. In a moment when you were drinking a water
you noticed that the process became quite long: the cup still contained water
because of rain. You decided to make a formal model of what was happening and
to find if it was possible to drink all water in that situation.

Thus, your cup is a cylinder with diameter equals d centimeters. Initial level
of water in cup equals h centimeters from the bottom.

You drink a water with a speed equals v milliliters per second. But rain goes
with such speed that if you do not drink a water from the cup, the level of
water increases on e centimeters per second. The process of drinking water
from the cup and the addition of rain to the cup goes evenly and continuously.

Find the time needed to make the cup empty or find that it will never happen.
It is guaranteed that if it is possible to drink all water, it will happen not
later than after 10^4 seconds.

Note one milliliter equals to one cubic centimeter.

Input
The only line of the input contains four integer numbers d, h, v, e
(1 ≤ d, h, v, e ≤ 10^4), where:

d — the diameter of your cylindrical cup,
h — the initial level of water in the cup,
v — the speed of drinking process from the cup in milliliters per second,
e — the growth of water because of rain if you do not drink from the cup.
Output
If it is impossible to make the cup empty, print "NO" (without quotes).

Otherwise print "YES" (without quotes) in the first line. In the second line
print a real number — time in seconds needed the cup will be empty. The answer
will be considered correct if its relative or absolute error doesn't exceed
10^(-4). It is guaranteed that if the answer exists, it doesn't exceed 10^4.

Found at: http://codeforces.com/contest/667/problem/A
-}

module Main where
import Data.List (group, sort)

solve :: Float -> Float -> Float -> Float -> Maybe Float
solve d h v e = if vPlus >= vMinus
                  then Nothing
                  else Just $ vInit / (vMinus - vPlus)
  where vPlus  = e * pi * (d/2)^2
        vMinus = v
        vInit = pi * (d/2)^2 * h

main :: IO ()
main = do
  line <- getLine
  let [d,h,v,e] = map read (words line) :: [Float]
  -- convert to SI units
  let d' = d / 100 -- cm to m
      h' = h / 100 -- cm to m
      v' = v / (1000*1000) -- ml/s to m3/s
      e' = e / 100 -- cm/s to m/s
  case solve d' h' v' e' of
               Nothing -> putStrLn "NO"
               Just t  -> do
                 putStrLn "YES"
                 putStrLn (show t)
