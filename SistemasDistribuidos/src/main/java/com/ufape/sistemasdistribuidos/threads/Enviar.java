/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufape.sistemasdistribuidos.threads;

import com.ufape.sistemasdistribuidos.model.Arquivo;
import com.ufape.sistemasdistribuidos.model.Usuario;
import com.ufape.sistemasdistribuidos.utils.RequisicoesUtils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;

/**
 *
 * @author rafael.cavalcanti
 */
public class Enviar extends Thread {

    private final RequisicoesUtils requisicoesUtils = new RequisicoesUtils();
    private byte[] arquivoByteArray;
    private Arquivo arquivo;
    private final Usuario usuario;
    private final Long id;

    public Enviar(Usuario usuario, Long id) {
        this.arquivoByteArray = null;
        this.arquivo = new Arquivo();
        this.usuario = usuario;
        this.id = id;        
    }

    @Override
    public void run() {
        try {
            FileInputStream fin = new FileInputStream(usuario.getDiretorioArquivos()+"\\"+this.id);
            ObjectInputStream ois = new ObjectInputStream(fin);
            arquivo = (Arquivo) ois.readObject();
            ois.close();
            arquivoByteArray = Arquivo.serialize(arquivo);
            requisicoesUtils.enviarArquivo(arquivoByteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
