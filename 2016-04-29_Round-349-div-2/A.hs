{-
instructions go here...
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
