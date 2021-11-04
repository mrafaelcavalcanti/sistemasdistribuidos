package com.ufape.sistemasdistribuidos.main;

import com.ufape.sistemasdistribuidos.model.Requisicao;
import com.ufape.sistemasdistribuidos.model.Usuario;
import com.ufape.sistemasdistribuidos.threads.Enviar;
import com.ufape.sistemasdistribuidos.threads.Receber;
import com.ufape.sistemasdistribuidos.utils.RequisicoesUtils;
import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author rafael.cavalcanti
 */
public class Manager {

    private static final RequisicoesUtils requisicoesUtils = new RequisicoesUtils();
    public static Usuario usuario;
    public static final Long envio = 1L;
    public static final Long recebimento = 0L;

    //iniciar duas threads
    //thread 1 verifica se tem arquivos para enviar
    //thread 2 verifica se tem arquivos para receber
    public static void main(String[] args) throws Exception {
        obterDadosUsuario();
        verificarDiretorio();
        while (true) {
            List<Requisicao> requisicoes = requisicoesUtils.existeRequisicaoEnvio(usuario.getId());
            iniciarThreads(requisicoes);
        }
    }

    public static void obterDadosUsuario() throws Exception {
        usuario = new Usuario();
        usuario.jsonToObject();
    }

    public static void verificarDiretorio() {
        File diretorio = new File(usuario.getDiretorioArquivos());
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }

    public static void iniciarThreads(List<Requisicao> requisicoes) throws Exception {
        for (Requisicao requisicao : requisicoes) {
            if (Objects.equals(requisicao.getTipoRequisicao(), recebimento)) {
                //new Receber(usuario, requisicao.getIdArquivo()).start();
            } else if (Objects.equals(requisicao.getTipoRequisicao(), envio)) {
                new Enviar(usuario, requisicao.getIdArquivo()).start();
            }
        }
        Thread.sleep(30000);
    }
}
