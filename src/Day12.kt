fun main() {


    class Graph {
        val adjacentVertex = mutableMapOf<String, MutableList<String>>()

        fun addEdge(from: String, to: String) {
            // Add v to u's list.
            var fromList = adjacentVertex[from]
            if (fromList == null) {
                fromList = mutableListOf()
                adjacentVertex[from] = fromList
            }
            fromList.add(to)
        }

        fun findAllPaths(start: String, end: String): MutableList<List<String>> {
            val allDiscoveredPaths = mutableListOf<List<String>>()
            findAllPathsRecursively(start, end, mutableSetOf(), mutableListOf(start), allDiscoveredPaths)
            return allDiscoveredPaths
        }

        private fun findAllPathsRecursively(
            from: String, to: String,
            visited: MutableSet<String>,
            localPathList: MutableList<String>,
            allDiscoveredPaths: MutableList<List<String>>
        ) {
            if (from == to) {
                allDiscoveredPaths.add(localPathList.toList())
            }

            visited.add(from)

            val fromList = adjacentVertex[from]
            if (fromList != null) {
                for (i in fromList) {
                    if (!visited.contains(i) || i.uppercase() == i) {
                        localPathList.add(i)
                        findAllPathsRecursively(i, to, visited, localPathList, allDiscoveredPaths)
                        localPathList.remove(i)
                    }
                }
            }
            visited.remove(from)
        }
    }

    fun toGraph(input: List<String>): Graph {
        val graph = Graph()

        input.forEach { line ->
            line.split("-").also {
                graph.addEdge(it[0], it[1])
                if (it[0] != "start" && it[1] != "end") {
                    graph.addEdge(it[1], it[0])
                }
            }
        }
        return graph
    }

    fun part1(input: List<String>): Int {
        val graph = toGraph(input)

        val allPaths = graph.findAllPaths("start", "end")

        allPaths.forEach { println(it) }

        return allPaths.size
    }

    fun part2(input: List<String>): Int {
        return -1
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 10)
    //check(part1(testInput) == 36)

    val input = readInput("Day12")
    println(part1(input))
    //println(part1(input))

}
