package hr.tjakopan.cryptopalls.challenges

object Pkcs7Padding {
  fun pad(data: ByteArray, blockLength: Byte): ByteArray {
    require(blockLength >= 1)
    val dataLength = data.size
    val byteToAppend: Byte = ((dataLength / blockLength + 1) * blockLength - dataLength).toByte()
    var newData = data
    for (i in 1..byteToAppend) {
      newData += byteToAppend
    }
    return newData
  }
}