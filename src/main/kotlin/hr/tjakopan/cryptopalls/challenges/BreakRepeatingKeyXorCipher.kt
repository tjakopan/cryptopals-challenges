package hr.tjakopan.cryptopalls.challenges

class BreakRepeatingKeyXorCipher(maxKeySize: Int = 40) {
  private val keySizes = IntRange(2, maxKeySize)

  fun guessKeySizes(data: ByteArray, numberOfKeys: Int = 3): IntArray {
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
      .take(numberOfKeys)
      .map { it.first }
      .toIntArray()
  }

  fun breakIntoBlocks(data: ByteArray, keySize: Int): List<ByteArray> = data.toList()
    .windowed(keySize, keySize, true)
    .map { it.toByteArray() }

  fun transposeBlocks(blocks: List<ByteArray>, keySize: Int): List<ByteArray> {
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

  fun key(blocks: List<ByteArray>): ByteArray {
    val singleByteXorCipher = SingleByteXorCipher()
    return blocks.map { block -> singleByteXorCipher.keyAndScore(block).first }
      .toByteArray()
  }
}