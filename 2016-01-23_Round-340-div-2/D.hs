-- There are three points marked on the coordinate plane. The goal is to make
-- a simple polyline, without self-intersections and self-touches, such that it
-- passes through all these points. Also, the polyline must consist of only
-- segments parallel to the coordinate axes. You are to find the minimum number
-- of segments this polyline may consist of.
--
-- Input
-- Each of the three lines of the input contains two integers. The i-th line
-- contains integers xi and yi ( - 109 ≤ xi, yi ≤ 109) — the coordinates of the
-- i-th point. It is guaranteed that all points are distinct.
--
-- Output
-- Print a single number — the minimum possible number of segments of the polyline.
--
-- Found at: http://codeforces.com/contest/617/problem/D
--

module Main where
import Data.List(permutations)

type Coords = (Int,Int)
type Direction = (Int,Int)

direction :: Coords -> Coords -> Direction
direction (x1,y1) (x2,y2) = (signum $ x2 - x1, signum $ y2 - y1)

directions :: [Coords] -> [Direction]
directions pts = zipWith direction pts (tail pts)

diagonal :: Direction -> Bool
diagonal (dx,dy) = (abs dx + abs dy) == 2

opposite :: Direction -> Direction -> Bool
opposite (dx1,dy1) (dx2,dy2) = (dx1,dy1) == (-dx2,-dy2)

segmentCount :: [Coords] -> Int
segmentCount pts = sum $ zipWith segmentsForMove dirs (tail dirs)
  where dirs = (0,0) : directions pts -- (0,0) for 'how to get to first point'

segmentsForMove :: Direction -> Direction -> Int
segmentsForMove d1 d2@(dx2,dy2)
  | d1 == (0,0)       = abs dx2 + abs dy2
  -- Following assumes we have only three points, ie. if diagonals CAN BE
  -- drawn in a continued fashion, we will draw them this way. For 4+ points
  -- this would not work.
  | continuable d1 d2 = if diagonal d2 then 1 else 0
  | diagonal d2       = 2
  | opposite d1 d2    = 3
  | otherwise         = 1

continuable :: Direction -> Direction -> Bool
continuable (dx1,dy1) (dx2,dy2)
  | dx1 /= 0 && dx1 == dx2    = True
  | dy1 /= 0 && dy1 == dy2    = True
  | otherwise                 = False

main :: IO ()
main = do
  line1 <- getLine
  line2 <- getLine
  line3 <- getLine
  let [x1,y1] = map read $ words line1 :: [Int]
      [x2,y2] = map read $ words line2 :: [Int]
      [x3,y3] = map read $ words line3 :: [Int]
  let perms = permutations [(x1,y1),(x2,y2),(x3,y3)]
  let best = minimum $ map segmentCount perms
  putStrLn $ show best
