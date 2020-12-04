package com.ifsc.app.focos.queimadas.ui.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ifsc.app.focos.queimadas.resouces.focos.FocoRepository
import com.ifsc.app.focos.queimadas.resouces.focos.FocoService

class MapsViewModelFactory (var activity: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            return MapsViewModel(activity = activity, repository = FocoRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class") as Throwable
    }

}