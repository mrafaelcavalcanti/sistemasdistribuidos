package com.ufape.sistemasdistribuidos.services;

import java.util.HashMap;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

import com.ufape.sistemasdistribuidos.model.Response;

public class UsuarioService extends RequestService {
	
	private static UsuarioService instance;
	
	public static UsuarioService getInstance() {
		if (instance == null) instance = new UsuarioService();
		return instance;
	}
	
	private UsuarioService() {
		super();
	}
	
	public Response<String> cadastrarUsuario(Integer id, String nome, String senha, Integer espacoSolicitado, Integer espacoDisponivel) {
    	try {
    		HashMap<String, Object> mapObj = new HashMap<String, Object>();
    		mapObj.put("id", id);
    		mapObj.put("nome", nome);
    		mapObj.put("senha", senha);
    		mapObj.put("espacoSolicitado", espacoSolicitado.toString());
    		mapObj.put("espacoDisponivel", espacoDisponivel);

    		JSONObject jsonObj = new JSONObject(mapObj);
    		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(this.getTimeout()).setSocketTimeout(this.getTimeout()).build();
        	try (CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
	            HttpPost httpPost = new HttpPost(String.format("%s/api/usuarios", this.getBase()));
	            String json = jsonObj.toJSONString();
	            StringEntity entity = new StringEntity(json);
	            httpPost.setEntity(entity);
	            httpPost.setHeader("Content-type", "application/json");
	            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            		Integer status = response.getStatusLine().getStatusCode();
            		System.out.println("STATUS (" + httpPost.getURI() + "):" + status);
            		switch (status) {
            			case 201:
            				return new Response<String>(true, "Usuário cadastrado com sucesso");
        				default:
            				return new Response<String>(false, "Erro ao cadastrar usuário, tente novamente");
            		}
            	}
            }
    	} catch (Exception e) {
    		return new Response<String>(false, "Erro ao cadastrar usuário, tente novamente");
    	}
    }
}
