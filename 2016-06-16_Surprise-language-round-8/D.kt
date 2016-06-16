
fun main(args: Array<String>) {
    val n = readLine()!!.toInt()
    
    for(i in 1..n) {
        val (n,m,p) = readLine()!!.split(' ').map(String::toInt)
        var possible = false
        for(nn in 1..n) {
            for(mm in 1..m) {
                if(nn * mm == p) {
                    possible = true
                    break
                }
                if (possible) {
                    break
                }
            }
        }
        if (possible) {
            println("Yes")
        } else {
            println("No")
        }
    }
}