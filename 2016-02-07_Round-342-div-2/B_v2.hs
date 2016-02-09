{-
A long time ago, in a galaxy far far away two giant IT-corporations Pineapple
and Gogol continue their fierce competition. Crucial moment is just around the
corner: Gogol is ready to release it's new tablet Lastus 3000.

This new device is equipped with specially designed artificial intelligence (AI).
Employees of Pineapple did their best to postpone the release of Lastus 3000 as
long as possible. Finally, they found out, that the name of the new artificial
intelligence is similar to the name of the phone, that Pineapple released
200 years ago. As all rights on its name belong to Pineapple, they stand on
changing the name of Gogol's artificial intelligence.

Pineapple insists, that the name of their phone occurs in the name of AI as
a substring. Because the name of technology was already printed on all devices,
the Gogol's director decided to replace some characters in AI name with "#". As
this operation is pretty expensive, you should find the minimum number of
characters to replace with "#", such that the name of AI doesn't contain the
name of the phone as a substring.

Substring is a continuous subsequence of a string.

Input
The first line of the input contains the name of AI designed by Gogol, its
length doesn't exceed 100 000 characters. Second line contains the name of
the phone released by Pineapple 200 years ago, its length doesn't exceed 30.
Both string are non-empty and consist of only small English letters.

Output
Print the minimum number of characters that must be replaced with "#" in order
to obtain that the name of the phone doesn't occur in the name of AI as
a substring.

Found at: http://codeforces.com/contest/625/problem/B
-}


module Main where
import Data.Text as T hiding (length)

solve haystack needle = length (T.splitOn needle haystack) - 1

main :: IO ()
main = do
  haystack <- getLine
  needle <- getLine
  putStrLn $ show $ solve (T.pack haystack) (T.pack needle)
