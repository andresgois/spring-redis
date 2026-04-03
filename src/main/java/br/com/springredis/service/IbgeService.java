package br.com.springredis.service;

import br.com.springredis.controller.apresentation.response.IbgeResponse;
import br.com.springredis.rest.Ibge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IbgeService {

    @Autowired
    private Ibge ibge;

    @Cacheable(value = "estados", condition = "#estado.equalsIgnoreCase('CE')")
    public List<IbgeResponse> findAllCidades(String estado){
        return ibge.findAllCidades(estado);
    }
}
