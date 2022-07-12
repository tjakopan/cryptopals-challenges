package hr.tjakopan.cryptopalls.challenges

import java.util.*

fun String.decodeHex(): ByteArray = HexFormat.of().parseHex(this)

fun ByteArray.encodeToHex(): String = HexFormat.of().formatHex(this)