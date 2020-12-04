package com.ifsc.app.focos.queimadas.ui.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifsc.app.focos.queimadas.resouces.focos.FocoRepository
import com.ifsc.app.focos.queimadas.resouces.focos.FocoService
import com.ifsc.app.focos.queimadas.resouces.focos.model.FocoRequest
import com.ifsc.app.focos.queimadas.resouces.focos.model.FocoResponse
import com.ifsc.app.focos.queimadas.resouces.generics.BaseModel
import io.reactivex.observers.DisposableObserver

class MapsViewModel (var activity: Context, var repository : FocoRepository) : ViewModel() {

    private val _focoQuery = MutableLiveData<BaseModel<MutableList<FocoResponse.Result>>>()
    var focoQuery : LiveData<BaseModel<MutableList<FocoResponse.Result>>> = _focoQuery


    fun consultarFocosQueimadas(query : FocoRequest.Query) {
        repository.consultarFoco(query, object : DisposableObserver<MutableList<FocoResponse.Result>>() {
            override fun onComplete() {}

            override fun onNext(t: MutableList<FocoResponse.Result>) {
                _focoQuery.value = BaseModel(success = true, message = "Total de possíveis focos:${t?.size}", data = t)
            }

            override fun onError(e: Throwable) {
                _focoQuery.value = BaseModel(success = false, message = e.message ?: "Não foi possível buscar as informações", data = null)
            }
        })
    }
}