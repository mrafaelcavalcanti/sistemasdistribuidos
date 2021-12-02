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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import org.apache.commons.lang3.SerializationUtils;
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
            InputStream is = new FileInputStream(usuario.getDiretorioArquivos()+"\\"+this.id);
            arquivoByteArray = IOUtils.toByteArray(is);
            //ObjectInputStream ois = new ObjectInputStream(fin);
            //arquivo = (Arquivo) ois.readObject();
            //ois.close();
            //arquivoByteArray = SerializationUtils.serialize(arquivo);
            requisicoesUtils.enviarArquivo(arquivoByteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
