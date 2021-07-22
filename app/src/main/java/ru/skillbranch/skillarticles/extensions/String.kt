package ru.skillbranch.skillarticles.extensions

fun String?.indexesOf(
    substr: String,
    ignoreCase: Boolean = true
): List<Int> {
    var index = 0
    val set = mutableSetOf<Int>()
    for(i in 0..(this?.length ?:20)){
        index = this?.indexOf(substr, index +1, ignoreCase) ?: 0
        set.add(index)
    }
    return set.filter { it!= -1 }
}

