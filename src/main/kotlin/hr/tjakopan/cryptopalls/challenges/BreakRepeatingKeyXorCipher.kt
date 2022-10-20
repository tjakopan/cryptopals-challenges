package hr.tjakopan.cryptopalls.challenges

import java.nio.charset.Charset

class BreakRepeatingKeyXorCipher(private val charset: Charset = DEFAULT_CHARSET, maxKeySize: Int = 40) {
  private val keySizes = IntRange(2, maxKeySize)

  private fun guessKeySizes(data: ByteArray, numberOfKeySizeGuesses: Int = 3): IntArray {
    return keySizes.map { keySize ->
      val numOfBlocks = data.size / keySize
      var totalDistance = 0
      var numOfDistances = 0
      for (i in 0 until numOfBlocks) {
        val firstBlock = data.drop(i * keySize)
          .take(keySize)
          .toByteArray()
        val secondBlock = data.drop((i + 1) * keySize)
          .take(keySize)
          .toByteArray()
        if (firstBlock.size != secondBlock.size) continue
        val distance = editDistance(firstBlock, secondBlock)
        totalDistance += distance
        numOfDistances++
      }
      Pair(keySize, totalDistance.toDouble() / keySize / numOfDistances)
    }
      .sortedBy { it.second }
      .take(numberOfKeySizeGuesses)
      .map { it.first }
      .toIntArray()
  }

  private fun breakIntoBlocks(data: ByteArray, keySize: Int): List<ByteArray> =
    data.toList()
      .windowed(keySize, keySize, true)
      .map { it.toByteArray() }

  private fun transposeBlocks(blocks: List<ByteArray>, keySize: Int): List<ByteArray> {
    val result = mutableListOf<ByteArray>()
    for (i in 0 until keySize) {
      val bytes = mutableListOf<Byte>()
      for (block in blocks) {
        if (i >= block.size) continue
        bytes.add(block[i])
      }
      result.add(bytes.toByteArray())
    }
    return result
  }

  private fun guessKey(blocks: List<ByteArray>): ByteArray {
    val breakCipher = BreakSingleByteXorCipher(charset)
    return blocks.map { block -> breakCipher.guessKeyAndScore(block).first }
      .toByteArray()
  }

  fun tryDecrypt(data: ByteArray, numberOfKeySizeGuesses: Int = 3): List<ByteArray> {
    val keySizes = guessKeySizes(data, numberOfKeySizeGuesses)
    val decryptedDataList = mutableListOf<ByteArray>()
    for (keySize in keySizes) {
      val blocks = breakIntoBlocks(data, keySize)
      val transposedBlocks = transposeBlocks(blocks, keySize)
      println("key size : $keySize")
      println(blocks.map { it.encodeToHex() })
      println(transposedBlocks.map { it.encodeToHex() })

      val key = guessKey(transposedBlocks)
      println("key: ${key.asString(charset)}")

      val decryptedData = RepeatingKeyXorCipher.decrypt(data, key)
      decryptedDataList.add(decryptedData)
    }
    return decryptedDataList
  }
}