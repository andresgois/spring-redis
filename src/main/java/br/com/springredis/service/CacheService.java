package br.com.springredis.service;

import br.com.springredis.entity.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CacheService {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private EmpresaService empresaService;

    /*
    * Se encontrar o objeto ele limpa a chave
     */
    public void evictAllCacheValues(String cache){
        Objects.requireNonNull(cacheManager.getCache(cache)).clear();
    }

    @CachePut("empresas")
    public List<Empresa> atualizarCacheEmpresa() {
        return (List<Empresa>) empresaService.findAll();
    }
}
