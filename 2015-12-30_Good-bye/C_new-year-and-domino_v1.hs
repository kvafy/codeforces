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

parseBoard :: [String] -> [[Bool]]
parseBoard rowsStr = map parseRow rowsStr
  where parseRow rowStr = map parseCol rowStr
        parseCol colChr = case colChr of
                            '.' -> True
                            '#' -> False

placements :: [[Bool]] -> ([[Integer]], [[Integer]])
placements board = (rowPlacements, colPlacements)
  where rowPlacements = placements' board
        colPlacements = placements' (transpose board)
        placements' boardLines = map (map cellNo) goodAdjacentCells
          where allAdjacentCells = map (\line -> zip3 [1..] line (tail line)) boardLines
                goodAdjacentCells = map (filter cellIsOccupiable) allAdjacentCells
                cellIsOccupiable (_,a,b) = a && b
                cellNo (n,_,_) = n

answerQuery :: ([[Integer]], [[Integer]]) -> [Integer] -> Integer
answerQuery (rPlacements, cPlacements) [r1,c1,r2,c2]
  = toInteger $ foldl (+) 0 (map length relevantRPlacements) + foldl (+) 0 (map length relevantCPlacements)
    where relevantRPlacements = map (takeWhile (\c -> c < c2) . dropWhile (\c -> c < c1)) $ sublist (r1-1) (r2-r1+1) rPlacements
          relevantCPlacements = map (takeWhile (\r -> r < r2) . dropWhile (\r -> r < r1)) $ sublist (c1-1) (c2-c1+1) cPlacements

sublist start size lst = (take $ fromInteger size) . (drop $ fromInteger start) $ lst

transpose ([]:_) = []
transpose x = (map head x) : transpose (map tail x)

main :: IO ()
main = do
  rawInput <- getContents
  let rawLines = lines rawInput

  let boardSpecStr = head $ sublist 0 1 rawLines
  let [rCount, cCount] = map read (words boardSpecStr) :: [Integer]

  let boardRowsStr = sublist 1 rCount rawLines
  let board = parseBoard boardRowsStr

  let legalPlacements = placements board

  let qCountStr = head $ sublist (1 + rCount) 1 rawLines
  let qCount = read qCountStr :: Integer

  let queriesStr = sublist (1 + rCount + 1) qCount rawLines
  let queries = map ((map read) . words) queriesStr :: [[Integer]]

  let answers = map (answerQuery legalPlacements) queries

  mapM_ (putStrLn . show) answers
