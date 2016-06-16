
fun main(args: Array<String>) {
    val set1 = mutableSetOf<Int>()
    val set2 = mutableSetOf<Int>()
    
    val set1Desc = readLine()!!.split(' ').map(String::toInt)
    val set2Desc = readLine()!!.split(' ').map(String::toInt)
    
    for (i in 1..set1Desc.size-1) {
        set1.add(set1Desc[i])
    }

    for (i in 1..set2Desc.size-1) {
        set2.add(set2Desc[i])
    }
    
    val solution = set1.subtract(set2).union(set2.subtract(set1))
    
    print(solution.size)
    for(n in solution) {
        print(' ')
        print(n)
    }
    println()
}