package com.ifsc.app.focos.queimadas.resouces

import com.ifsc.app.focos.queimadas.resouces.focos.model.FocoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    interface Focos {
        @GET("focos/")
        fun focos(@Query("pais_id") paisId : Long, @Query("estado_id")estadoId : Long?,
                  @Query("municipio_id")municipioId : Long?, @Query("satelite")satelite : String): Call<MutableList<FocoResponse.Result>>

        @GET("focos/count/")
        fun focosCount(@Query("pais_id") paisId : Long, @Query("estado_id")estadoId : Long,
                       @Query("municipio_id")municipioId : Long, @Query("satelite")satelite : String): Call<Void>
    }

    interface Auxilio {

    }
}