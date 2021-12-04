import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)


fun <R> transpose(matrix: List<List<R>>): List<List<R>> {
    check(matrix.isNotEmpty()) { "Expected at least one row" }
    check(matrix.map { it.count() }.distinct().size == 1) { "Expected all rows to have same length" }
    val colCount = matrix[0].size
    return (0 until colCount).map { colNum -> matrix.map { row -> row[colNum] } }
}