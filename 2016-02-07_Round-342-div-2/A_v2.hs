{-
Kolya Gerasimov loves kefir very much. He lives in year 1984 and knows all the
details of buying this delicious drink. One day, as you probably know, he found
himself in year 2084, and buying kefir there is much more complicated.

Kolya is hungry, so he went to the nearest milk shop. In 2084 you may buy kefir
in a plastic liter bottle, that costs a rubles, or in glass liter bottle, that
costs b rubles. Also, you may return empty glass bottle and get c (c < b)
rubles back, but you cannot return plastic bottles.

Kolya has n rubles and he is really hungry, so he wants to drink as much kefir
as possible. There were no plastic bottles in his 1984, so Kolya doesn't know
how to act optimally and asks for your help.

Input
First line of the input contains a single integer n (1 ≤ n ≤ 10^18) —
the number of rubles Kolya has at the beginning.

Then follow three lines containing integers a, b and c
(1 ≤ a ≤ 10^18, 1 ≤ c < b ≤ 10^18) — the cost of one plastic liter bottle, the
cost of one glass liter bottle and the money one can get back by returning an
empty glass bottle, respectively.

Output
Print the only integer — maximum number of liters of kefir, that Kolya can drink.

Found at: http://codeforces.com/contest/625/problem/A
-}

module Main where

getLines :: Int -> IO [String]
getLines n = sequence $ replicate n getLine

plastic money cost = money `div` cost

glass money cost ret
  | money < cost  = (0, money)
  | otherwise     = let bottles = 1 + ((money - cost) `div` (cost - ret))
                    in (bottles, money - bottles * (cost - ret))

main :: IO ()
main = do
  lines <- getLines 4
  let [money, a, b, c] = map read lines :: [Integer]
  let plasticOnly = plastic money a
      (glassOnly, glassOnlyRem) = glass money b c
      glassAndPlastic = glassOnly + plastic glassOnlyRem a
  putStrLn $ show $ max plasticOnly glassAndPlastic
