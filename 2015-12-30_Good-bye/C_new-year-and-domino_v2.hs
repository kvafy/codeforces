-- They say "years are like dominoes, tumbling one after the other". But would
-- a year fit into a grid? I don't think so.

-- Limak is a little polar bear who loves to play. He has recently got
-- a rectangular grid with h rows and w columns. Each cell is a square, either
-- empty (denoted by '.') or forbidden (denoted by '#'). Rows are numbered 1
-- through h from top to bottom. Columns are numbered 1 through w from left to
-- right.

-- Also, Limak has a single domino. He wants to put it somewhere in a grid.
-- A domino will occupy exactly two adjacent cells, located either in one row
-- or in one column. Both adjacent cells must be empty and must be inside a grid.

-- Limak needs more fun and thus he is going to consider some queries. In each
-- query he chooses some rectangle and wonders, how many way are there to put
-- a single domino inside of the chosen rectangle?

-- Input
-- The first line of the input contains two integers h and w (1 ≤ h, w ≤ 500)
-- – the number of rows and the number of columns, respectively.

-- The next h lines describe a grid. Each line contains a string of the length w.
-- Each character is either '.' or '#' — denoting an empty or forbidden cell,
-- respectively.

-- The next line contains a single integer q (1 ≤ q ≤ 100 000) — the number
-- of queries.

-- Each of the next q lines contains four integers r1i, c1i, r2i, c2i
-- (1 ≤ r1i ≤ r2i ≤ h, 1 ≤ c1i ≤ c2i ≤ w) — the i-th query. Numbers r1i and c1i
-- denote the row and the column (respectively) of the upper left cell of the
-- rectangle. Numbers r2i and c2i denote the row and the column (respectively)
-- of the bottom right cell of the rectangle.

-- Output
-- Print q integers, i-th should be equal to the number of ways to put a single
-- domino inside the i-th rectangle.

module Main where
import Data.Array ((!))
import qualified Data.Array as Array

parseBoard :: [String] -> Array.Array (Int,Int) Bool
parseBoard rows = Array.array bounds $ concatMap parseRow (zip [1..] rows)
  where parseRow :: (Int, String) -> [((Int,Int),Bool)]
        parseRow (rIdx,row) = map parseCol $ zip3 (repeat rIdx) [1..] row
        parseCol :: (Int,Int,Char) -> ((Int,Int),Bool)
        parseCol (rIdx,cIdx,col) = case col of
                                     '.' -> ((rIdx,cIdx),True)
                                     '#' -> ((rIdx,cIdx),False)
        bounds = ((1,1), (rCnt,cCnt))
        rCnt = length rows
        cCnt = length $ head rows

placements :: Array.Array (Int,Int) Bool -> Array.Array (Int,Int) Int
placements board = Array.listArray bounds [combPlacements rc | rc <- Array.range bounds]
  where combPlacements (r,c) = if placeable (r,c)
                                 then (if placeable (r+1,c) then 1 else 0)
                                    + (if placeable (r,c+1) then 1 else 0)
                                 else 0
        placeable (r,c) = Array.inRange bounds (r,c) && board ! (r,c)
        bounds = Array.bounds board

answerQuery :: Array.Array (Int,Int) Bool -> (Int,Int,Int,Int) -> Integer
answerQuery board (r1,c1,r2,c2)
  =   foldl add 0 [dirPlacements rc (1,0) | rc <- Array.range ((r1,c1),(r2-1,c2))]
    + foldl add 0 [dirPlacements rc (0,1) | rc <- Array.range ((r1,c1),(r2,c2-1))]
    where dirPlacements (r,c) (dr,dc) = if placeable (r,c) && placeable (r+dr,c+dc)
                                          then 1
                                          else 0
          placeable (r,c) = Array.inRange bounds (r,c) && board ! (r,c)
          bounds = Array.bounds board
          add :: Integer -> Int -> Integer
          add x y = x + toInteger y

sublist start size lst = (take $ fromInteger size) . (drop $ fromInteger start) $ lst

toTuple [a,b,c,d] = (a,b,c,d)

main :: IO ()
main = do
  rawInput <- getContents
  let rawLines = lines rawInput

  let boardSpecStr = head $ sublist 0 1 rawLines
  let [rCount, cCount] = map read (words boardSpecStr) :: [Integer]

  let boardRowsStr = sublist 1 rCount rawLines
  let board = parseBoard boardRowsStr

  let qCountStr = head $ sublist (1 + rCount) 1 rawLines
  let qCount = read qCountStr :: Integer

  let queriesStr = sublist (1 + rCount + 1) qCount rawLines
  let queries = map (toTuple . (map read) . words) queriesStr :: [(Int,Int,Int,Int)]

  let answers = map (answerQuery board) queries

  mapM_ (putStrLn . show) answers
