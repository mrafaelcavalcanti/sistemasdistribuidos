package com.ufape.sistemasdistribuidos.utils;

import com.ufape.sistemasdistribuidos.model.Requisicao;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.util.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author rafael.cavalcanti
 */
public class RequisicoesUtils {

    private final int TIMEOUT = 240000;
    private final String server = "https://sistemasdistribuidosserver.herokuapp.com";

    public List<Requisicao> existeRequisicaoEnvio(Long id) throws Exception {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();
        try (CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
            HttpGet httpGet = new HttpGet(String.format("%s/api/requisicoes/obter/%s", server, id));
            httpGet.addHeader("accept", "application/json");
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new Exception(String.format("EXISTE_REQUISICAO_ENVIO (%s)",
                            response.getStatusLine().getStatusCode()));
                }
                switch (response.getStatusLine().getStatusCode()) {
                    case 200:
                        String json = EntityUtils.toString(response.getEntity());
                        return jsonToListOfObject(json);
                    case 404:
                        return null;
                    default:
                        throw new Exception(String.format("(%s): NAO FOI POSSIVEL CONSULTAR REQUISICOES", response.getStatusLine().getStatusCode()));
                }
            }
        }
    }

    public byte[] obterArquivo(Long id) throws Exception {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();
        try (CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
            HttpGet httpGet = new HttpGet(String.format("%s/api/arquivos/obter/%s", server, id));
            httpGet.addHeader("accept", "application/octet-stream");
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new Exception(String.format("OBTER_ARQUIVO(%s)",
                            response.getStatusLine().getStatusCode()));
                }
                try (InputStream is = response.getEntity().getContent()) {
                    return IOUtils.toByteArray(is);
                }
            }
        }
    }

    public void enviarArquivo(byte[] bytearray) throws Exception {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();
        try (CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
            HttpPost httpPost = new HttpPost(String.format("%s/api/arquivos", server));
            httpPost.addHeader("Content-Type", "application/octet-stream");
            ByteArrayEntity bae = new ByteArrayEntity(bytearray);
            httpPost.setEntity(bae);
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() != 201) {
                    throw new Exception(String.format("ENVIAR_ARQUIVO(%s)",
                            response.getStatusLine().getStatusCode()));
                }
            }
        }
    }
    
    public void confirmarRecebimento(String ids) throws Exception {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();
        try (CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
            HttpPost httpPost = new HttpPost(String.format("%s/api/arquivos/confimarRecebimento", server, ids));
            httpPost.addHeader("Content-Type", "application/json");
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() != 201) {
                    throw new Exception(String.format("CONFIRMAR_RECEBIMENTO(%s)",
                            response.getStatusLine().getStatusCode()));
                }
            }
        }
    }

    public List<Requisicao> jsonToListOfObject(String json) throws Exception {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(json);
        List<Requisicao> requsicoes = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jobj = (JSONObject) jsonArray.get(i);
            Requisicao requisicao = new Requisicao();
            requisicao.setIdUsuario((Long) jobj.get("idUsuario"));
            requisicao.setIdArquivo((Long) jobj.get("idArquivo"));
            requisicao.setTipoRequisicao((Long) jobj.get("tipoRequisicao"));
            requsicoes.add(requisicao);
        }
        return requsicoes;
    }
}
