package com.ifsc.app.focos.queimadas.resouces.focos.model

import com.google.gson.annotations.SerializedName
import java.util.*

sealed class FocoResponse {

    open class Result(
        @SerializedName("geometry")
        var geometry: Geometria? = null,

        @SerializedName("properties")
        var properties: Properties? = null,

        @SerializedName("type")
        var tipo: String? = null,

        @SerializedName("id")
        var id: String? = null,

        @SerializedName("geometry_name")
        var nomeGeometria: String? = null
    )

    open class Geometria(
        @SerializedName("type")
        var tipo: String? = null,

        @SerializedName("coordinates")
        var coodenadas: MutableList<Double>? = null
    )

    open class Properties(

        @SerializedName("latitude")
        var latitude: Double? = null,

        @SerializedName("longitude")
        var longitude: Double? = null,

        @SerializedName("data_hora_gmt")
        var dataHoraGmt: Date? = null,

        @SerializedName("pais")
        var pais: String? = null,

        @SerializedName("estado")
        var estado: String? = null,

        @SerializedName("municipio")
        var municipio: String? = null,

        @SerializedName("satelite")
        var satelite: String? = null,

        @SerializedName("risco_fogo")
        var riscoFogo: String? = null,

        @SerializedName("precipitacao")
        var precipitacao: String? = null,

        @SerializedName("numero_dias_sem_chuva")
        var numeroDiasSemChuva: Int? = null
    )

}