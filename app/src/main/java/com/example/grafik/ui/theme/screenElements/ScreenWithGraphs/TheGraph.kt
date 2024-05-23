package com.example.grafik.ui.theme.screenElements.ScreenWithGraphs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.LineChartData
import com.example.grafik.logic.Constants
import com.example.grafik.logic.ScreenWithGraphsLogic
import com.example.grafik.ui.theme.screenElements.Background

@Composable
fun TheGraph(        //sample of the graph
    constants: Constants,
    text: String,
    newChart: LineChartData,
    def: String,
    list: List<Double>
){
    val screenWithGraphsLogic = ScreenWithGraphsLogic()
    val contentMax = screenWithGraphsLogic.valuation(constants, constants.maxValue, list, def)
    val contentMin = screenWithGraphsLogic.valuation(constants, constants.minValue, list, def)

    //graph name
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp)
        .height(40.dp), contentAlignment = Alignment.Center){
        Text(text = text, fontSize = 30.sp)
    }
    //graph name

    //actual graph
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(3.dp, color = Color.Black)
            .padding(top = 7.dp)
            .height(400.dp),
    ) {
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            lineChartData = newChart
        )
    }
    //actual graph

    BoxForContent(text = contentMax, height = 60, fontSize = 20)   //maxValue box
    BoxForContent(text = contentMin, height = 60, fontSize = 20)   //minValue box
}
