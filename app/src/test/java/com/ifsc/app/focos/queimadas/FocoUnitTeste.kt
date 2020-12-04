package com.ifsc.app.focos.queimadas

import com.ifsc.app.focos.queimadas.resouces.focos.FocoService
import org.junit.Test

class FocoUnitTeste {
    @Test
    fun focoResultadoConsulta() {
        var focoService = FocoService()
        var response = focoService.consultarFoco(33, 51, null, "AQUA_M-T")
        assert(response.isNotEmpty())
    }
}