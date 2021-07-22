package ru.skillbranch.skillarticles.extensions

fun String?.indexesOf(
    substr: String,
    ignoreCase: Boolean = true
): List<Int> {
    var index = 0
    val set = mutableSetOf<Int>()
    if (this != null && substr !="") {
        for (i in 0..this.length) {
            index = this.indexOf(substr, index + 1, ignoreCase)
            set.add(index)
        }
    }else return listOf<Int>()
    return set.filter { it!= -1 }
}

