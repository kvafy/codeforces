
fun main(args: Array<String>) {
    val n = readLine()!!.toInt()

    val order = Array<Int?>(n, {i -> null})

    var i = 1
    for (iPrev in readLine()!!.split(' ').map(String::toInt)) {
        var skips = iPrev
        for (j in 0 .. (n - 1)) {
            if (order[j] == null) {
                if (skips == 0) {
                    order[j] = i
                    break
                } else {
                    skips--;
                }
            }
        }
        i++
    }

    //4
    //2 0 1 0
    //0 . 1 .
    //2 4 1 3

    /*val preds = Array(n, {i -> mutableListOf<Int>()})

    var i = 1
    for (iPreds in readLine()!!.split(' ').map(String::toInt)) {
        preds[iPreds].add(i++)
    }


    val order = Array<Int?>(n, {i -> null})
    for (j in (n - 1) downTo 0) {
        preds[j].sort()
        var next = j
        for (p in preds[j]) {
            while (order[next] != null) {
                next++;
            }
            order[next] = p
        }
    }*/

    for (i in order) {
        print(i)
        print(' ')
    }
    println()
}