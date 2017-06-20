package com.erroronserver.eventosdecaridade.model.enumerations;

import com.erroronserver.eventosdecaridade.util.Constantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raniere de Lima - contato@erroronserver.com on 21/05/2017.
 */
public enum EnumTipoEvento {

    EVENTO_DOACAO_ROUPA(1, Constantes.LABEL_DOACAO_ROUPA, Constantes.DESCRICAO_DOACAO_ROUPA),
    EVENTO_DOACA0_ALIMENTACAO(2, Constantes.LABEL_DOACA0_ALIMENTACAO, Constantes.DESCRICAO_DOACA0_ALIMENTACAO),
    EVENTO_ALIMENTANDO_NECESSITADOS(3, Constantes.LABEL_ALIMENTE_NECESSITADOS, Constantes.DESCRICAO_ALIMENTE_NECESSITADOS);

    public int id;
    public String nomeExibicao;
    public String descricao;

    private EnumTipoEvento(int id, String nomeExibicao, String descricao ){
        this.id = id;
        this.descricao = descricao;
        this.nomeExibicao = nomeExibicao;
    }

    public static List<String> getAll(){
        List<String> retorno = new ArrayList<>();
        for(EnumTipoEvento e : EnumTipoEvento.values()){
            retorno.add(e.nomeExibicao);
        }

        return retorno;
    }
}
