package br.com.springredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CacheService {

    @Autowired
    private CacheManager cacheManager;

    /*
    * Se encontrar o objeto ele limpa a chave
     */
    public void evictAllCacheValues(String cache){
        Objects.requireNonNull(cacheManager.getCache(cache)).clear();
    }
}
