package hr.tjakopan.cryptopalls.challenges

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AesCipher {
  private fun secretKey(key: ByteArray): SecretKey = SecretKeySpec(key, "AES")

  fun generateKey(keySizeInBits: Int): SecretKey =
    KeyGenerator.getInstance("AES")
      .run {
        init(keySizeInBits)
        generateKey()
      }

  fun decrypt(
    data: ByteArray,
    key: ByteArray,
    mode: String,
    padding: String = "NoPadding",
    iv: ByteArray? = null
  ): ByteArray {
    val cipher = Cipher.getInstance("AES/$mode/$padding")
    val secretKey = secretKey(key)
    val ivParameterSpec = if (iv != null) IvParameterSpec(iv) else null
    cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
    return cipher.doFinal(data)
  }

  fun encrypt(
    data: ByteArray,
    key: ByteArray,
    mode: String,
    padding: String = "NoPadding",
    iv: ByteArray? = null
  ): ByteArray {
    val cipher = Cipher.getInstance("AES/$mode/$padding")
    val secretKey = secretKey(key)
    val ivParameterSpec = if (iv != null) IvParameterSpec(iv) else null
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
    return cipher.doFinal(data)
  }

  fun encryptCbcUsingEcb(
    data: ByteArray,
    key: ByteArray,
    padding: String = "NoPadding",
    initialIv: ByteArray
  ): ByteArray {
    val blocks = data.toList()
      .windowed(16, 16)
      .map { it.toByteArray() }
    var iv = initialIv
    var result = byteArrayOf()
    for (dn in blocks) {
      val cn = encrypt(dn xor iv, key, "ECB", padding)
      result += cn
      iv = cn
    }
    return result
  }
}