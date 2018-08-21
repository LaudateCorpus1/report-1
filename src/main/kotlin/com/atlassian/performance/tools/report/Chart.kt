package com.atlassian.performance.tools.report

import javax.json.Json
import javax.json.JsonArrayBuilder
import javax.json.JsonObject

class Chart<X>(
    private val lines: List<ChartLine<X>>
) where X : Comparable<X> {

    fun toJson(): JsonObject {
        val dataBuilder = Json.createObjectBuilder()
        dataBuilder.add("labels", Json.createArrayBuilder(getLabels()).build())
        dataBuilder.add("datasets", getLines())
        return dataBuilder.build()
    }

    private fun getLines(): JsonArrayBuilder {
        val linesBuilder = Json.createArrayBuilder()
        lines.forEach {
            linesBuilder.add(it.toJson())
        }
        return linesBuilder
    }

    private fun getLabels(): Set<String> = lines
        .flatMap { it.data }
        .sortedBy { it.x }
        .map { it.labelX() }
        .toSet()
}