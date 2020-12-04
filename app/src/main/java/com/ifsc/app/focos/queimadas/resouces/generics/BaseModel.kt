package com.ifsc.app.focos.queimadas.resouces.generics

class BaseModel<T>(
    val success: Boolean,
    val message: String,
    val data: T?
)