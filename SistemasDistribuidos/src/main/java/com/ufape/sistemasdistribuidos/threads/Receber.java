/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufape.sistemasdistribuidos.threads;

import com.ufape.sistemasdistribuidos.model.Arquivo;
import com.ufape.sistemasdistribuidos.model.Usuario;
import com.ufape.sistemasdistribuidos.utils.RequisicoesUtils;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;
import org.apache.commons.lang3.SerializationUtils;

/**
 *
 * @author rafael.cavalcanti
 */
public class Receber extends Thread {

    private final RequisicoesUtils requisicoesUtils = new RequisicoesUtils();
    private byte[] arquivoByteArray;
    private Arquivo arquivo;
    private final Usuario usuario;
    private Long id;

    public Receber(Usuario usuario, Long id) {
        this.arquivoByteArray = null;
        this.arquivo = new Arquivo();
        this.usuario = usuario;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            arquivoByteArray = requisicoesUtils.obterArquivo(this.id);
            //arquivo = (Arquivo) SerializationUtils.deserialize(arquivoByteArray);
            if (arquivoByteArray != null) {
                String path = usuario.getDiretorioArquivos() +"\\"+ this.id;
                System.out.println(path);
                try (FileOutputStream fos = new FileOutputStream(path)) {
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(arquivo);
                    oos.close();
                    fos.write(arquivoByteArray);
                }
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
