package com.zestworks.luasinfo.extensions

import io.kotlintest.shouldBe
import org.junit.Test


class CharSequenceExtensionsTest {
    @Test
    fun isDigitsOnly() {
        "1234".isDigitsOnly() shouldBe true
        "a123".isDigitsOnly() shouldBe false
        ".asd123..^^".isDigitsOnly() shouldBe false
        " ".isDigitsOnly() shouldBe false
        "0".isDigitsOnly() shouldBe true
    }
}