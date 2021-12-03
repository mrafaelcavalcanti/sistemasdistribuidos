/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufape.sistemasdistribuidos.threads;

import com.ufape.sistemasdistribuidos.model.ArquivoAux;
import com.ufape.sistemasdistribuidos.model.Usuario;
import com.ufape.sistemasdistribuidos.utils.RequisicoesUtils;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.poi.util.IOUtils;

/**
 *
 * @author rafael.cavalcanti
 */
public class Enviar extends Thread {

    private final RequisicoesUtils requisicoesUtils = new RequisicoesUtils();
    private byte[] arquivoByteArray;
    private ArquivoAux arquivo;
    private final Usuario usuario;
    private final Long id;

    public Enviar(Usuario usuario, Long id) {
        this.arquivoByteArray = null;
        this.arquivo = new ArquivoAux();
        this.usuario = usuario;
        this.id = id;        
    }

    @Override
    public void run() {
        try {
            InputStream is = new FileInputStream(usuario.getDiretorio()+"\\"+this.id);
            arquivoByteArray = IOUtils.toByteArray(is);
            requisicoesUtils.enviarArquivo(arquivoByteArray);
            requisicoesUtils.confirmarRecebimento(usuario.getId(), this.id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
