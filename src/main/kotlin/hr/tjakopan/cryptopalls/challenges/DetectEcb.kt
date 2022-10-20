package hr.tjakopan.cryptopalls.challenges

fun detectLineEncryptedWithEcbMode(lines: List<String>): String {
  val linesAndDuplicateBlockCount = lines.map { line ->
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

  return linesAndDuplicateBlockCount[0].first
}
