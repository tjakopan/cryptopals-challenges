package hr.tjakopan.cryptopalls.challenges.set01

import hr.tjakopan.cryptopalls.challenges.resourceAsText

fun main() {
  val lines = resourceAsText("/set01/challenge08.txt")!!.lines()
  val linesAndDuplicateBlocksCount = lines.map { line ->
    val countPerBlock = mutableMapOf<String, Int>()
    line.windowed(32, 32)
      .forEach { block ->
        countPerBlock.compute(block) { _, n -> if (n == null) 1 else n + 1 }
      }
    val countOfDuplicateBlocks = countPerBlock.values
      .filter { it >= 2 }
      .sum()
    Pair(line, countOfDuplicateBlocks)
  }
    .sortedByDescending { it.second }

  val lineLikelyEncryptedWithEcb = linesAndDuplicateBlocksCount[0]
  println(lineLikelyEncryptedWithEcb)
  // line 133
  // d880619740a8a19b7840a8a31c810a3d08649af70dc06f4fd5d2d69c744cd283e2dd052f6b641dbf9d11b0348542bb5708649af70dc06f4fd5d2d69c744cd2839475c9dfdbc1d46597949d9c7e82bf5a08649af70dc06f4fd5d2d69c744cd28397a93eab8d6aecd566489154789a6b0308649af70dc06f4fd5d2d69c744cd283d403180c98c8f6db1f2a3f9c4040deb0ab51b29933f2c123c58386b06fba186a
}