package ru.ivos.strooper.data

import android.content.Context
import android.graphics.Color
import android.util.Log
import ru.ivos.strooper.R
import kotlin.random.Random

class Generator (context: Context) {

    private val listOfNames = listOf(
        context.getString(R.string.red),
        context.getString(R.string.blue),
        context.getString(R.string.purple),
        context.getString(R.string.green),
        context.getString(R.string.pink),
        context.getString(R.string.yellow),
        context.getString(R.string.orange),
        context.getString(R.string.light_blue),
        context.getString(R.string.white),
        context.getString(R.string.black),
        context.getString(R.string.light_green),
        context.getString(R.string.brown),
        context.getString(R.string.gray)
    )

    private val listOfColors = listOf(
        context.getColor(R.color.red),
        context.getColor(R.color.blue),
        context.getColor(R.color.purple),
        context.getColor(R.color.green),
        context.getColor(R.color.pink),
        context.getColor(R.color.yellow),
        context.getColor(R.color.orange),
        context.getColor(R.color.light_blue),
        context.getColor(R.color.white),
        context.getColor(R.color.black),
        context.getColor(R.color.light_green),
        context.getColor(R.color.brown),
        context.getColor(R.color.gray)
//        Color.rgb(255, 99, 99),
//        Color.rgb(99, 143, 255),
//        Color.rgb(168, 99, 255),
//        Color.rgb(54, 209, 0),
//        Color.rgb(255, 99, 211),
//        Color.rgb(252, 255, 99),
//        Color.rgb(255, 193, 99),
//        Color.rgb(99, 227, 255),
//        Color.rgb(255, 255, 255),
//        Color.rgb(16, 16, 16),
//        Color.rgb(177, 255, 99),
//        Color.rgb(138, 83, 0),
//        Color.rgb(177, 177, 177),
    )

    fun getMap(): LinkedHashMap<String, Int> {
        val map = linkedMapOf<String, Int>()

        val firstElement = Random.nextInt(12)
        map[listOfNames[firstElement]] = listOfColors[firstElement]

        while (map.size != 7) {
            val pook = Random.nextInt(12)
//            val key = Random.nextInt(12)
//            val value = Random.nextInt(12)
//
//            if (key != value) {
                map.putIfAbsent(listOfNames[pook], listOfColors[pook])
//            }
        }

        return map
    }

    fun getName(position: Int) = listOfNames[position]

    fun getNames(): Set<String> {

        val list = mutableSetOf<String>()
        while (list.size != 7) {
            list.add(listOfNames[Random.nextInt(12)])
        }

        return list
    }

    fun getColors(): Set<Int> {

        val list = mutableSetOf<Int>()
        while (list.size != 6) {
            list.add(listOfColors[Random.nextInt(12)])
        }

        return list
    }
}