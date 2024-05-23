package com.example.grafik.logic

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine

class FormingGraphs {
    //provides data for further use in graphs in Y-axis
    //takes, for example, listOfWeight, then creates points with each element of list
    private fun getPointList(
        newList: List<Float>
    ): List<Point> {
        val list = ArrayList<Point>()
        val size = newList.size - 1
        for (i in 0..size) {
            list.add(
                Point(
                    i.toFloat(),
                    newList[i]
                )
            )
        }
        return list
    }

    //needed to form the graph largest value (depends of highest value user typed)
    private fun getMax(list: List<Point>): Float {
        var max = 0F
        list.forEach { point ->
            if (max < point.y) max = point.y
        }
        return max
    }

    //takes the list of Y-axis, then looking for biggest element
    //provides the calculation for steps of graph Y-axis
    //without it Y-axis will show all values on it which would be hard to read
    //graph with float values needs this (like weight)
    fun maxValue(list: List<Float>): Float {
        var max = 0F
        list.forEach { point ->
            if (max < point) max = point
        }
        return max
    }
    fun minValue(list: List<Float>): Float {
        var max = if(list.size > 1){
            list[1]
        }else{
            0f
        }
        val newList = list.subList(1, list.size)
        newList.forEach { point ->
            if (max > point) max = point
        }
        return max
    }


    //same as previous
    //graph with int values needs this (like repeats or caloric)
   private fun maxValueInt(list: List<Int>): Int {
        var max = 0F
        list.forEach { point ->
            if (max < point) max = point.toFloat()
        }
        return max.toInt()
    }

    //needed to form the graph smallest value (in this project always zero), yet still calculated
    private fun getMin(list: List<Point>): Float {
        var min = 100F
        list.forEach { point ->
            if (min > point.y) min = point.y
        }
        return min
    }


    //graph for float values
    //all the same as in createLineChartDataInt, except points values: here they are float
    fun createLineChartDataFloat(
        listOfData: List<String>,
        listOfYAxis: List<Double>,
        definition: String
    ): LineChartData {
        val correction =
            if(maxValue(listOfYAxis.map { it.toFloat() })>13){
                maxValue(listOfYAxis.map { it.toFloat() })/10
            }else{
                1
            }
        val steps =  maxValue(listOfYAxis.map { it.toFloat() })/correction.toInt()
        val pointsList = getPointList(listOfYAxis.map { it.toFloat() })
        val max = getMax(pointsList)
        val min = getMin(pointsList)
        val xAxisData = AxisData.Builder()
            .axisStepSize(100.dp)
            .backgroundColor(Color.Transparent)
            .steps(pointsList.size-1)
            .labelData { i -> listOfData[i] }
            .labelAndAxisLinePadding(15.dp)
            .build()

        val yAxisData = AxisData.Builder()
            .steps(steps.toInt())
            .backgroundColor(Color.Transparent)
            .labelAndAxisLinePadding(15.dp)
            .labelData { i ->
                val yScale = ((max - min) / steps)
                ((i * yScale) + min).toInt().toString() + " $definition"
            }.build()

        val lineChartData = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = pointsList,
                        LineStyle(color = Color.Green),
                        IntersectionPoint(),
                        SelectionHighlightPoint(),
                        ShadowUnderLine(),
                        SelectionHighlightPopUp()
                    )
                ),
            ),
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            gridLines = GridLines(),
            backgroundColor = Color.White
        )
        return lineChartData
    }
    //graph for float values


    //graph for int values
    fun createLineChartDataInt(
        listOfData: List<String>,   //X-axis data list
        listOfYAxis: List<Int>,     //Y-axis data list
        definition: String          //unit of measurement
    ): LineChartData {
        var newListY = listOf<Float>()
        //float needed for correction value
        for(element in listOfYAxis){
            newListY += element.toFloat()
        }

        //needed to decide how many steps on Y-axis needs to be shown
        //one value is minimum
        val correction =
            if(maxValueInt(listOfYAxis)>13){
                maxValueInt(listOfYAxis)/10
            }else{
                1
            }

        val steps =  maxValueInt(listOfYAxis)/correction
        val pointsList = getPointList(newListY)
        val max = getMax(pointsList)
        val min = getMin(pointsList)

        //forming X-axis values list
        val xAxisData = AxisData.Builder()
            .axisStepSize(100.dp)
            .backgroundColor(Color.Transparent)
            .steps(pointsList.size-1)
            .labelData { i -> listOfData[i] }
            .labelAndAxisLinePadding(20.dp)
            .build()
        //forming X-axis values list


        //forming Y-axis values list
        val yAxisData = AxisData.Builder()
            .steps(steps)
            .backgroundColor(Color.Transparent)
            .labelAndAxisLinePadding(35.dp)
            .labelData { i ->
                val yScale = ((max - min) / steps).toInt()
                ((i * yScale) + min).toInt().toString() + " $definition"
            }.build()
        //forming Y-axis values list

        //actual graph
        val lineChartData = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = pointsList,
                        LineStyle(color = Color.Green),
                        IntersectionPoint(),
                        SelectionHighlightPoint(),
                        ShadowUnderLine(),
                        SelectionHighlightPopUp()
                    )
                ),
            ),
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            gridLines = GridLines(),
            backgroundColor = Color.White
        )
        //actual graph

        return lineChartData
    }
    //graph for int values
}