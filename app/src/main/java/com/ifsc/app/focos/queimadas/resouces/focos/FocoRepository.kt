package com.ifsc.app.focos.queimadas.resouces.focos

import com.ifsc.app.focos.queimadas.resouces.focos.model.FocoRequest
import com.ifsc.app.focos.queimadas.resouces.focos.model.FocoResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.Single

class FocoRepository {

    private val service: FocoService
        get() {
            return FocoService()
        }

    fun consultarFoco(request : FocoRequest.Query) : Single<MutableList<FocoResponse.Result>> {
        return Single.create {
            try {
                val response = service.consultarFoco(request.paisId,
                    request.estadoId,
                    request.municipioId,
                    request.satelite)
                when {
                    response.isNotEmpty() -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception("Nenhum possível foco de queimada encontrado para o estado selecioando"))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro ao buscar os focos de queimadas"))
            }
        }
    }

    fun consultarFoco(request : FocoRequest.Query, observer: DisposableObserver<MutableList<FocoResponse.Result>>){
        consultarFoco(request)
            .toObservable()
            .map { it }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}