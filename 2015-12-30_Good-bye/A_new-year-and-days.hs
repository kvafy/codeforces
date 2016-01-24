-- Today is Wednesday, the third day of the week. What's more interesting is that
-- tomorrow is the last day of the year 2015.

-- Limak is a little polar bear. He enjoyed this year a lot. Now, he is so eager
-- to the coming year 2016.

-- Limak wants to prove how responsible a bear he is. He is going to regularly
-- save candies for the entire year 2016! He considers various saving plans. He
-- can save one candy either on some fixed day of the week or on some fixed day
-- of the month.

-- Limak chose one particular plan. He isn't sure how many candies he will save
-- in the 2016 with his plan. Please, calculate it and tell him.

-- Input
-- The only line of the input is in one of the following two formats:
-- * "x of week" where x (1 ≤ x ≤ 7) denotes the day of the week.
--   The 1-st day is Monday and the 7-th one is Sunday.
-- * "x of month" where x (1 ≤ x ≤ 31) denotes the day of the month.

-- Output
-- Print one integer — the number of candies Limak will save in the year 2016.

module Main where
import Data.Time.Calendar
import Data.Time.Calendar.WeekDate

dayCount :: Integer -> (Day -> Bool) -> Int
dayCount year dayFilter = length $ filter dayFilter (allDaysOfYear year)

allDaysOfYear :: Integer -> [Day]
allDaysOfYear year = takeWhile (\d -> d < endDay) $ iterate (\d -> addDays 1 d) baseDay
  where baseDay = fromGregorian year 1 1
        endDay = fromGregorian (year + 1) 1 1

dayOfWeekFilter :: Integer -> (Day -> Bool)
dayOfWeekFilter n = \d -> third (toWeekDate d) == n

dayOfMonthFilter :: Integer -> (Day -> Bool)
dayOfMonthFilter n = \d -> third (toGregorian d) == n

third (_, _, x) = toInteger x

main :: IO ()
main = do
  line <- getLine
  let [xStr, _, period] = words line
  let x = read xStr :: Integer
  let dayFilter = case period of
                    "week"  -> dayOfWeekFilter x
                    "month" -> dayOfMonthFilter x
  putStrLn $ show (dayCount 2016 dayFilter)
