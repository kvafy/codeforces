
fun main(args: Array<String>) {
    val (a,x,y) = readLine()!!.split(' ').map(String::toInt)
    
    var solution: Int
    if (0 <= x && x <= a && 0 <= y && y <= a) {
        if (x == 0 || x == a || y == 0 || y == a) {
            solution = 1
        } else {
            solution = 0
        }
    } else {
        solution = 2
    }
    println(solution)
}
