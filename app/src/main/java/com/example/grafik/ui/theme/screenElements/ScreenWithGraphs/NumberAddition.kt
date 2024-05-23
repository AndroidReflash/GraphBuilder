package com.example.grafik.ui.theme.screenElements.ScreenWithGraphs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.grafik.logic.Constants

@Composable
//actual field with number and buttons to modify it
fun NumberAddition(
    constants: Constants,
    graphType: String,
    number: String,
    size: Int, //defines the size of buttons and numbers as well
    _length: Int, //defines how many chars would be in number: 5 for integer, 2 for fractional
    onValueChange: (String) -> Unit
) {
    var str = number                    //start value which would contain numbers or signs
    val extra = _length - str.length
    //branch is needed to take from any value which is not weight fractional part
    //it means that only in weightGraph you can input numbers with fractional part
    val length = if(graphType != constants.weightGraph){
        _length - 3
    }else{
        _length
    }
    var add = ""
    //adding extra zeros from left side to number, if number of digits is not equal to length value
    if(str.length < length){
        for(i in 1..extra){
            if(i==length-2 && graphType == constants.weightGraph){
                add+="."
            }else{
                add+="0"
            }
        }
        str = add + str
    }
    Row {
        //forming row that equals to number
        for (i in 0..<length) {
            if(i==length-3 && graphType == constants.weightGraph){
                Point(size=size)
            }else{
                //in here, if incoming number of digits is not equal to length app is going to crash
                ChangingNumber(size = size, value = str[i].toString()) {
                    val new = str.substring(0,i) + it + str.substring(i+1, length)
                    onValueChange(new)
                }
            }
        }
    }
}

//point to imitate divide between integer and fractional parts of a number
@Composable
fun Point(size: Int){
    val font = size/1.5
    Column {
        //empty spaces (in normal case here should be button to increase/decrease number)
        Box(
            modifier = Modifier
                .width(size.dp)
                .height(size.dp), contentAlignment = Alignment.Center
        ) {
            Text(text = " ", fontSize = font.sp)
        }
        //the point itself
        Box(
            modifier = Modifier
                .width(size.dp)
                .height(size.dp), contentAlignment = Alignment.Center
        ) {
            Text(text = ".", fontSize = font.sp)
        }
        //empty spaces (in normal case here should be button to increase/decrease number)
        Box(
            modifier = Modifier
                .width(size.dp)
                .height(size.dp), contentAlignment = Alignment.Center
        ) {
            Text(text = " ", fontSize = font.sp)
        }
    }
}

//this composable describes one column of field for inputting numbers
@Composable
fun ChangingNumber(size: Int, value: String, onValueChange:(String)->Unit) {
    val font = size/1.5
    Column {
        //button for increasing number
        Box(
            modifier = Modifier
                .width(size.dp)
                .clickable {
                    var num = value.toInt()
                    //these two branches are existing to imitate count from 0 to 9 on circle
                    if (num <= 9) {
                        num++
                    }
                    if (num > 9) { //branch to transition from 9 to 0
                        num = 0
                    }
                    onValueChange(num.toString())
                }
                .height(size.dp), contentAlignment = Alignment.Center
        ) {
            Text(text = "+", fontSize = font.sp)
        }
        //box with the number
        Box(
            modifier = Modifier
                .width(size.dp)
                .height(size.dp), contentAlignment = Alignment.Center
        ) {
            Text(text = value, fontSize = font.sp)
        }
        //button for decreasing number
        Box(
            modifier = Modifier
                .width(size.dp)
                .clickable {
                    var num = value.toInt()
                    //these two branches are existing to imitate count from 9 to 0 on circle
                    if (num >= 0) {
                        num--
                    }
                    if (num < 0) { //branch to transition from 0 to 9
                        num = 9
                    }
                    onValueChange(num.toString())
                }
                .height(size.dp), contentAlignment = Alignment.Center
        ) {
            Text(text = "−", fontSize = font.sp)
        }
    }
}
