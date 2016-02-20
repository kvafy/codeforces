{-
Max wants to buy a new skateboard. He has calculated the amount of money that
is needed to buy a new skateboard. He left a calculator on the floor and went
to ask some money from his parents. Meanwhile his little brother Yusuf came and
started to press the keys randomly. Unfortunately Max has forgotten the number
which he had calculated. The only thing he knows is that the number is divisible
by 4.

You are given a string s consisting of digits (the number on the display of the
calculator after Yusuf randomly pressed the keys). Your task is to find the
number of substrings which are divisible by 4. A substring can start with
a zero.

A substring of a string is a nonempty sequence of consecutive characters.

For example if string s is 124 then we have four substrings that are divisible
by 4: 12, 4, 24 and 124. For the string 04 the answer is three: 0, 4, 04.

As input/output can reach huge size it is recommended to use fast input/output
methods: for example, prefer to use gets/scanf/printf instead of getline/cin/cout
in C++, prefer to use BufferedReader/PrintWriter instead of Scanner/System.out
in Java.

Input
The only line contains string s (1 ≤ |s| ≤ 3·105). The string s contains only
digits from 0 to 9.

Output
Print integer a — the number of substrings of the string s that are divisible
by 4.

Note that the answer can be huge, so you should use 64-bit integer type to store
it. In C++ you can use the long long integer type and in Java you can use long
integer type.

Found at: http://codeforces.com/contest/628/problem/B
-}

module Main where
import Data.Char (ord)

solve :: String -> Integer
solve number = fst3 $ foldl aggr (0,0,0) number
  where aggr (total,pos,last2) digit = (total',pos',last2')
          where total' = total + totalInc
                pos' = pos + 1
                totalInc = if divisibleBy4 last2'
                             then   1                 -- the whole number ...xy up to pos'
                                  + max 0 (pos' - 2)  -- all prefixes of the xy number
                                  + if divisibleBy4 digit' && pos' >= 2
                                      then 1 -- number y
                                      else 0
                             else if divisibleBy4 digit'
                               then 1
                               else 0
                last2' = (last2 `mod` 10) * 10 + digit'
                digit' = fromIntegral $ ord digit - ord '0'
        fst3 (x,_,_) = x
        divisibleBy4 x = x `mod` 4 == 0

main :: IO ()
main = do
  number <- getLine
  putStrLn $ show $ solve number

{-
5
58         -> 8
581
5810       -> 0
58104      -> 58104, 8104, 104, 04, 4
581043
5810438    -> 8
58104381
581043817
5810438174 -> 4
-}
