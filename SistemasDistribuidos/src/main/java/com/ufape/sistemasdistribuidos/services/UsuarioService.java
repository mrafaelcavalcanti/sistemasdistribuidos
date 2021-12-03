package com.ufape.sistemasdistribuidos.services;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.ufape.sistemasdistribuidos.model.Response;
import com.ufape.sistemasdistribuidos.model.Usuario;

public class UsuarioService extends RequestService {
	
	private static UsuarioService instance;
	private static Usuario usuarioLogado;
	
	public static UsuarioService getInstance() {
		if (instance == null) instance = new UsuarioService();
		return instance;
	}
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(Usuario usuario) {
		usuarioLogado = usuario;
	}
	
	private UsuarioService() {
		super();
	}
	
	
	public Response<Usuario> login(String nome, String senha) {
		try {
			HashMap<String, Object> mapObj = new HashMap<String, Object>();
			mapObj.put("nome", nome);
			mapObj.put("senha", senha);
			
			JSONObject jsonObj = new JSONObject(mapObj);
			
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(this.getTimeout()).setSocketTimeout(this.getTimeout()).build();
			try (CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
	            HttpPost httpPost = new HttpPost(String.format("%s/api/usuarios/login", this.getBase()));
	            String json = jsonObj.toJSONString();
	            StringEntity entity = new StringEntity(json);
	            httpPost.setEntity(entity);
	            httpPost.setHeader("Content-type", "application/json");
	            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            		Integer status = response.getStatusLine().getStatusCode();
            		System.out.println("STATUS (" + httpPost.getURI() + "):" + status);
            		String bodyString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            		
            		JSONParser parser = new JSONParser();
            		JSONObject body = (JSONObject) parser.parse(bodyString);

            		Usuario usuario = new Usuario(
            				(Long) body.get("id"),
            				nome, senha,
            				(String) body.get("espacoDisponivel"),
            				(String) body.get("espacoSolicitado"),
            				(String) body.get("diretorio"));
            		usuario.objectToJson();
        			this.setUsuarioLogado(usuario);

            		switch (status) {
	        			case 202:
	        				return new Response<Usuario>(true, usuario, "");
	    				default:
	    					return new Response<Usuario>(false, null, (String) body.get("message"));
	        		}
            	}
            }
		} catch (Exception ex) {
			return new Response<Usuario>(false, null, "Erro na requisição");			
		}
	}
	
	public Response<Usuario> cadastrarUsuario(Integer id, String nome, String senha, Integer espacoSolicitado, Integer espacoDisponivel) {
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
            		
            		Usuario usuario = new Usuario(
            				id.longValue(),
            				nome,
            				senha,
            				espacoSolicitado.toString(),
            				espacoDisponivel.toString(),
            				"/home/vinesnts/Documentos/BCC/");
            		usuario.objectToJson();
            		
            		switch (status) {
            			case 201:
            				return new Response<Usuario>(true, usuario, "Usuário cadastrado com sucesso");
        				default:
            				return new Response<Usuario>(false, null, "Erro ao cadastrar usuário, tente novamente");
            		}
            	}
            }
    	} catch (Exception e) {
    		return new Response<Usuario>(false, null, "Erro ao cadastrar usuário, tente novamente");
    	}
    }
}
