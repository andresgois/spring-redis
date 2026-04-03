package br.com.springredis.service;

import br.com.springredis.controller.apresentation.response.IbgeResponse;
import br.com.springredis.rest.Ibge;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class IbgeService {

    @Autowired
    private Ibge ibge;

    @Cacheable(value = "estados", condition = "#estado.equalsIgnoreCase('CE')")
    public List<IbgeResponse> findAllCidades(String estado){
        log.info("Vai buscar no cache redis!");
        return ibge.findAllCidades(estado);
    }
}
