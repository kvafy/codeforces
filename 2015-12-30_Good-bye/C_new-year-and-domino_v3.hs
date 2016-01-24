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

annotate :: [[a]] -> [((Int,Int),a)]
annotate lst = [((i1,i2),x) | (i1,xs) <- zip [1..] lst, (i2,x) <- zip [1..] xs]

parseBoard :: [String] -> (Array.Array (Int,Int) Integer, Array.Array (Int,Int) Integer)
parseBoard rows = (rowScans, colScans)
  where rowScans = Array.array ((1,1),(rCnt,cCnt)) $ annotate (scans rows)
        colScans = Array.array ((1,1),(cCnt,rCnt)) $ annotate (scans $ transpose rows)
        scans = map (\line -> tail $ scanl score 0 (zip ('#':line) line))
        score aggr (prevCell,curCell)
          | prevCell == '.' && curCell == '.' = aggr + 1
          | otherwise                         = aggr
        rCnt = length rows
        cCnt = length $ head rows

placements :: (Array.Array (Int,Int) Integer, Array.Array (Int,Int) Integer) -> (Int,Int,Int,Int) -> Integer
placements (rowScans, colScans) (r1,c1,r2,c2)
  =   sum [rowScans ! rc | rc <- Array.range ((r1,c2),(r2,c2))]
    - sum [rowScans ! rc | rc <- Array.range ((r1,c1),(r2,c1))]
    + sum [colScans ! cr | cr <- Array.range ((c1,r2),(c2,r2))]
    - sum [colScans ! cr | cr <- Array.range ((c1,r1),(c2,r1))]

transpose([]:_) = []
transpose (rows) = map head rows : transpose (map tail rows)

sublist start size lst = (take size) . (drop start) $ lst

toTuple [a,b,c,d] = (a,b,c,d)

getLines :: Int -> IO [String]
getLines 0 = return []
getLines n = do
  line <- getLine
  rest <- getLines (n-1) -- sadly, not tail recursion
  return $ line:rest

main :: IO ()
main = do
  boardSpecStr <- getLine
  let [rCount, cCount] = map read (words boardSpecStr) :: [Int]

  boardRowsStr <- getLines rCount
  let boardScans = parseBoard boardRowsStr

  qCountStr <- getLine
  let qCount = read qCountStr :: Int

  queriesStr <- getLines qCount
  let queries = map (toTuple . (map read) . words) queriesStr :: [(Int,Int,Int,Int)]

  let answers = map (placements boardScans) queries
  
  mapM_ (putStrLn . show) answers
