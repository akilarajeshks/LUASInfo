package com.zestworks.luasinfo.extensions

fun CharSequence.isDigitsOnly(): Boolean {
    val len = length
    var cp: Int
    var i = 0
    while (i < len) {
        cp = Character.codePointAt(this, i)
        if (!Character.isDigit(cp)) {
            return false
        }
        i += Character.charCount(cp)
    }
    return true
}
