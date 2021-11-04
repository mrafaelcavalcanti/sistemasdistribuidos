package com.ufape.sistemasdistribuidosserver.apirest;

import java.util.List;

import com.ufape.sistemasdistribuidosserver.model.Arquivo;
import com.ufape.sistemasdistribuidosserver.model.ArquivosCompartilhadosUsuarios;
import com.ufape.sistemasdistribuidosserver.model.Requisicao;
import com.ufape.sistemasdistribuidosserver.repository.ArquivosCompartilhadosUsuariosDAOI;
import com.ufape.sistemasdistribuidosserver.repository.RequisicoesDAOI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/requisicoes")
public class RequisicoesRESTContoller {
    
    @Autowired
    RequisicoesDAOI requisicoesDAOI;

    @Autowired
    ArquivosCompartilhadosUsuariosDAOI arquivosCompartilhadosUsuariosDAOI;

    @GetMapping("/obter/{id}")
    public List<Requisicao> getRequisicaoByUserId(@PathVariable("id") Long id) {
        return requisicoesDAOI.findByUserId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Requisicao> requisitar(@RequestBody Arquivo idArquivo) {
        List<ArquivosCompartilhadosUsuarios> acu = arquivosCompartilhadosUsuariosDAOI.findAllByIdArquivo(idArquivo.getId());
        for(int i=0; i<acu.size(); i++) {
            Requisicao r = new Requisicao();
            r.setId(10000000L);
            r.setIdArquivo(idArquivo.getId());
            r.setIdUsuario(acu.get(i).getIdUsuario());
            r.setTipoRequisicao(1L);
            requisicoesDAOI.save(r);
        }
        Requisicao r = new Requisicao();
        return new ResponseEntity<Requisicao>(r,HttpStatus.OK);
    }
}
