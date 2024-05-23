package com.example.grafik.logic

class ScreenWithGraphsLogic {

    //gives data for category by searching it with the name of it
    fun chooseCategory(name: String) {
        for (category in dataList) {
            if (category.categoryName == name) {
                categoryChoice = category
            }
        }
    }

    //creates list with modified category, modified raises to first position
    fun addToDataList(
        oldCategory: Category,
        category: Category,
    ): List<Category> {
        val list = emptyList<Category>().toMutableList()
        dataList -= oldCategory
        list += category
        list += dataList
        return list
    }

    //getting max or min of graph
    fun valuation(constants: Constants, definedValue: String, list: List<Double>, def: String): String {
        val formingGraphs = FormingGraphs()
        //defines max or min it should be and forming it
        var content = if (definedValue == constants.maxValue) {
            formingGraphs.maxValue(list.map { it.toFloat() }).toString()
        } else {
            formingGraphs.minValue(list.map { it.toFloat() }).toString()
        }
        //making int if there is no point of fraction
        if (content[content.length - 1] == '0' && content[content.length - 2] == '.') {
            content = content.substring(0, content.length - 2)
        }
        //forming actual text
        val text = "$definedValue: $content $def"
        return text
    }
}