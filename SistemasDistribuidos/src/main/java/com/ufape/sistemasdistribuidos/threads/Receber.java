/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufape.sistemasdistribuidos.threads;

import com.ufape.sistemasdistribuidos.model.ArquivoAux;
import com.ufape.sistemasdistribuidos.model.Usuario;
import com.ufape.sistemasdistribuidos.utils.RequisicoesUtils;
import java.io.FileOutputStream;
import java.util.Objects;
import org.apache.commons.lang3.SerializationUtils;

/**
 *
 * @author rafael.cavalcanti
 */
public class Receber extends Thread {

    private final RequisicoesUtils requisicoesUtils = new RequisicoesUtils();
    private byte[] arquivoByteArray;
    private ArquivoAux arquivo;
    private final Usuario usuario;
    private Long id;

    public Receber(Usuario usuario, Long id) {
        this.arquivoByteArray = null;
        this.arquivo = new ArquivoAux();
        this.usuario = usuario;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            arquivoByteArray = requisicoesUtils.obterArquivo(this.id);
            arquivo = (ArquivoAux) SerializationUtils.deserialize(arquivoByteArray);
            if (arquivoByteArray != null) {
                String path = null;
                if (Objects.equals(arquivo.getIdUsuario(), usuario.getId())) {
                    arquivoByteArray = arquivo.getConteudo();
                    path = usuario.getDiretorioArquivos() + "\\" + arquivo.getNome();
                } else {
                    path = usuario.getDiretorioArquivos() + "\\" + this.id;
                }
                try (FileOutputStream fos = new FileOutputStream(path)) {
                    //ObjectOutputStream oos = new ObjectOutputStream(fos);
                    //oos.writeObject(arquivo);
                    //oos.close();
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
