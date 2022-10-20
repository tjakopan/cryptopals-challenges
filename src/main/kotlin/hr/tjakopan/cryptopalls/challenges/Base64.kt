package hr.tjakopan.cryptopalls.challenges

import java.nio.charset.Charset
import java.util.*

fun String.decodeBase64(charset: Charset = DEFAULT_CHARSET): ByteArray =
  Base64.getMimeDecoder().decode(this.asByteArray(charset))

fun ByteArray.encodeToBase64(charset: Charset = DEFAULT_CHARSET): String =
  Base64.getMimeEncoder().encode(this).asString(charset)