package com.example.shoppinglistyunosheva.utils

object ProgressHelper {
    fun getProgress(allItemsCount: Int, selectedItemsCount: Int): Float{
        return if(allItemsCount == 0) 0.0f
        else selectedItemsCount.toFloat() / allItemsCount.toFloat()
    }
}