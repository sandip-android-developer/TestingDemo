package com.example.unittestingdemo

object Validator {
    fun validateInput(amount:Int,des:String): Boolean {
          return !(amount<=0 || des.isEmpty())
    }
}