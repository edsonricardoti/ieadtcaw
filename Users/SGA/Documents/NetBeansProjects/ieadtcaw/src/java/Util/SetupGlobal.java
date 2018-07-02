/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 * Classe destinada a manter atributos de alcance global
 * dentro do sistema como descrição de serviços de PF e PJ emitidos no cadastro
 */


public class SetupGlobal {
    private String descricaoServicos;

    public String getDescricaoServicos() {
        return descricaoServicos;
    }

    public void setDescricaoServicos(String descricaoServicos) {
        this.descricaoServicos = descricaoServicos;
    }      
    
}
