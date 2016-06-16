
fun main(args: Array<String>) {
    val pupils = mutableListOf<Pupil>()

    val n = readLine()!!.toInt()
    for (i in 1..n) {
        val (name, height) = readLine()!!.split(' ')
        pupils.add(Pupil(name, height.toInt()))
    }

    pupils.sortBy{p -> p.height}

    for (p in pupils) {
        println(p.name)
    }
}

data class Pupil(val name: String, val height: Int)
