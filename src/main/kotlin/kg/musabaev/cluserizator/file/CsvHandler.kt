package kg.musabaev.cluserizator.file

import java.io.*
import java.util.stream.Stream
import kotlin.streams.asSequence

class CsvHandler(filename: String) {

    constructor(file: File) : this (file.toString())

    private val bufReader by lazy { BufferedReader(FileReader(filename), 128) }
    private val bufWriter by lazy { BufferedWriter(FileWriter(filename), 128) }


    fun linesAsStream(): Stream<List<String>> {
        return bufReader
            .lines()
//            .parallel()
            .map { it.split(Regex("\\s*,\\s*")) }
    }

    fun linesAsSequence(): Sequence<List<String>> {
        return bufReader
            .lines()
            .asSequence()
            .map { it.split(Regex("\\s*,\\s*")) }
    }

    fun writeLine(line: List<String>) { // TODO
        bufWriter.write(line.joinToString(separator = ","))
    }
}