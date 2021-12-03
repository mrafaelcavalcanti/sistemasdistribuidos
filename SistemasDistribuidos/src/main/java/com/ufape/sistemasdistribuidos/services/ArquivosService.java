package com.ufape.sistemasdistribuidos.services;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.ufape.sistemasdistribuidos.model.Arquivo;
import com.ufape.sistemasdistribuidos.model.Response;
import com.ufape.sistemasdistribuidos.model.Usuario;

public class ArquivosService extends RequestService {
	
	private static ArquivosService instance;
	
	public static ArquivosService getInstance() {
		if (instance == null) instance = new ArquivosService();
		return instance;
	}
	
	private ArquivosService() {
		super();
	}
	
	public Response<String> deletarArquivo(Integer id) {
		try {
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(this.getTimeout()).setSocketTimeout(this.getTimeout()).build();
        	try (CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
	            HttpDelete httpDelete = new HttpDelete(String.format("%s/api/arquivos/delete/%s", this.getBase(), id));
	            httpDelete.setHeader("accept", "application/json");
	            try (CloseableHttpResponse response = httpclient.execute(httpDelete)) {
            		Integer status = response.getStatusLine().getStatusCode();
            		System.out.println("STATUS (" + httpDelete.getURI() + "):" + status);

            		switch (status) {
            			case 200:
            				return new Response<String>(true, "", "Arquivo excluído com sucesso");
        				default:
        					return new Response<String>(false, "", "Erro ao excluir arquivo, tente novamente");
            		}
            	}
            }
		} catch (Exception e) {
			return new Response<String>(false, "", "Erro ao excluir arquivo, tente novamente");
		}
	}
	
	public Response<List<Arquivo>> listarArquivos(Integer id) {
		try {
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(this.getTimeout()).setSocketTimeout(this.getTimeout()).build();
        	try (CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
	            HttpGet httpGet = new HttpGet(String.format("%s/api/arquivos/listar/%s", this.getBase(), id));
	            httpGet.setHeader("accept", "application/json");
	            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            		Integer status = response.getStatusLine().getStatusCode();
            		System.out.println("STATUS (" + httpGet.getURI() + "):" + status);
            		String bodyString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            		
            		JSONParser parser = new JSONParser();
            		JSONArray body = (JSONArray) parser.parse(bodyString);
            		
            		List<Arquivo> arquivos = new ArrayList<Arquivo>();
            		for (int i = 0; i < body.size(); i++) {
            			JSONObject jsonObj = (JSONObject) body.get(i);
            			arquivos.add(new Arquivo(
            					(Long) jsonObj.get("id"),
            					(Long) jsonObj.get("idUsuario"),
            					(String) jsonObj.get("nome"),
            					(byte[]) jsonObj.get("conteudo")));
            		}
            		switch (status) {
            			case 200:
            				return new Response<List<Arquivo>>(true, arquivos, "");
        				default:
        					return new Response<List<Arquivo>>(false, new ArrayList<Arquivo>(), "");
            		}
            	}
            }
		} catch (Exception e) {
			return new Response<List<Arquivo>>(false, new ArrayList<Arquivo>(), "");
		}
	}
}
