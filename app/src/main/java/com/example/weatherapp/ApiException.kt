package com.example.weatherapp

class ApiException(val errorCode: Int, message: String?) : Exception(message)
