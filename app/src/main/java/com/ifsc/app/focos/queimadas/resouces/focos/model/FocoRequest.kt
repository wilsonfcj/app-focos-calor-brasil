package com.ifsc.app.focos.queimadas.resouces.focos.model

import com.google.gson.annotations.SerializedName
import java.util.*

sealed class FocoRequest {

    open class Query(
        var paisId: Long,

        var estadoId: Long? = null,

        var municipioId: Long? = null,

        var satelite: String
    )
}