package hr.tjakopan.cryptopalls.challenges

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object Aes {
  fun secretKey(key: ByteArray): SecretKey = SecretKeySpec(key, "AES")

  fun decrypt(
    data: ByteArray,
    key: ByteArray,
    keySizeInBits: Int,
    mode: String,
    padding: String = "NoPadding",
    iv: ByteArray? = null
  ): ByteArray {
    val cipher = Cipher.getInstance("AES_$keySizeInBits/$mode/$padding")
    val secretKey = secretKey(key)
    val ivParameterSpec = if (iv != null) IvParameterSpec(iv) else null
    cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
    return cipher.doFinal(data)
  }
}