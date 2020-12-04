package com.ifsc.app.focos.queimadas.resouces.focos

import br.edu.ifsc.cancontrol.resources.generics.RetrofitImpl
import com.ifsc.app.focos.queimadas.resouces.Api
import com.ifsc.app.focos.queimadas.resouces.focos.model.FocoResponse

class FocoService {
    fun consultarFoco (paisId: Long, estadoId: Long?, municipioId: Long?, satelite: String) : MutableList<FocoResponse.Result> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.Focos::class.java)

        val objectCall = api.focos(paisId, estadoId, municipioId, satelite)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Não foi possível realizar a consulta")
        }
    }
}