-- Bob loves everything sweet. His favorite chocolate bar consists of pieces,
-- each piece may contain a nut. Bob wants to break the bar of chocolate into
-- multiple pieces so that each part would contain exactly one nut and any break
-- line goes between two adjacent pieces.
--
--You are asked to calculate the number of ways he can do it. Two ways to break
-- chocolate are considered distinct if one of them contains a break between some
-- two adjacent pieces and the other one doesn't.
--
--Please note, that if Bob doesn't make any breaks, all the bar will form one
-- piece and it still has to have exactly one nut.
--
--Input
--The first line of the input contains integer n (1 ≤ n ≤ 100) — the number of
-- pieces in the chocolate bar.
--
--The second line contains n integers ai (0 ≤ ai ≤ 1), where 0 represents a piece
-- without the nut and 1 stands for a piece with the nut.
--
--Output
--Print the number of ways to break the chocolate into multiple parts so that
-- each part would contain exactly one nut.
--
-- Found at: http://codeforces.com/contest/617/problem/B
--

module Main where
import Data.List.Split(splitWhen)

breakCount :: [Char] -> Integer
breakCount chocolate = product $ map (\seg -> toInteger $ 1 + length seg) breakableSegments
  where chocolate' = trimZeros (reverse $ trimZeros chocolate)
        breakableSegments = splitWhen (== '1') chocolate'
        trimZeros = dropWhile (== '0')

main :: IO ()
main = do
  _ <- getLine
  piecesStr <- getLine
  let pieces = concat $ words piecesStr
  let possibilities = if all (== '0') pieces
                        then 0
                        else breakCount pieces
  putStrLn $ show possibilities
