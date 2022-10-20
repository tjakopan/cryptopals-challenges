package hr.tjakopan.cryptopalls.challenges

import java.nio.charset.Charset

fun resourceAsText(name: String, charset: Charset = DEFAULT_CHARSET): String? =
  object {}.javaClass.getResource(name)?.readText(charset)