package com.ifsc.app.focos.queimadas.props

enum class EEstados constructor (val estadoId: Long?,
                                      val estadoName: String,
                                      val estadoNameDes: String
                                        ) {

    TODOS(null, "TODOS", "Todos"),
    MATO_GROSSO(51, "MATO GROSSO", "Mato Grosso"),
    GOIAS(52, "GOIÁS", "Goiás"),
    DISTRITO_FEDERAL(53, "DISTRITO FEDERAL", "Distrito Federal"),
    RONDONIA(11, "RONDONIA", "Rondonia"),
    ACRE(12, "ACRE", "Acre"),
    AMAZONAS(13, "AMAZONAS", "Amazonas"),
    RORAIMA(14, "RORAIMA", "Roraima"),
    PARA(15, "PARÁ", "Pará"),
    AMAPA(16, "AMAPÁ", "Amapá"),
    TOCANTINS(17, "TOCANTINS", "Tocantins"),
    MARANHAO(21, "MARANHÃO", "Maranão"),
    PIAUI(22, "PIAUÍ", "Piauí"),
    CEARA(23, "CEARÁ","Ceará"),
    PARAIBA(33, "PARAÍBA", "Paraíba"),
    RIO_GRANDE_DO_NORTE(24, "RIO GRANDE DO NORTE", "Rio Grande Do Norte"),
    PERNAMBUCO(26, "PERNAMBUCO", "Pernambuco"),
    ALAGOAS(27, "ALAGOAS", "Alagoas"),
    SERGIPE(28, "SERGIPE", "Sergipe"),
    BAHIA(29, "BAHIA", "Bahia"),
    MINAS_GERAIS(31, "MINAS GERAIS", "Minas Gerais"),
    ESPIRITO_SANTO(32, "ESPÍRITO SANTO", "Espírito Santo"),
    RIO_DE_JANEIRO(33, "RIO DE JANEIRO", "Rio De Janeiro"),
    SAO_PAULO(35, "SÃO PAULO", "São Paulo"),
    PARANA(41, "PARANÁ", "Paraná"),
    SANTA_CATARINA(42, "SANTA CATARINA", "Santa Catarina"),
    RIO_GRANDE_DO_SUL(43, "RIO GRANDE DO SUL", "Rio Grande Do Sul"),
    MATO_GROSSO_DO_SUL(50, "MATO GROSSO DO SUL", "Moto Grosso Do Sul");

    companion object {
        fun fromCode(aCode: Long): EEstados {
            for (lEnum in values()) {
                if (lEnum.estadoId == aCode) {
                    return lEnum
                }
            }
            return TODOS
        }

        fun fromDescriptions(aValues: String): EEstados {
            for (lEnum in values()) {
                if (lEnum.estadoName.equals(aValues, true)) {
                    return lEnum
                }
            }
            return TODOS
        }

        fun getValues() : Array<EEstados> {
            return values()
        }
    }
}