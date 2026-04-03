package br.com.springredis.controller;


import br.com.springredis.entity.Empresa;
import br.com.springredis.service.CacheService;
import br.com.springredis.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @PostMapping
    public ResponseEntity<String> clear(@RequestParam("cacheName") String cacheName) {
        cacheService.evictAllCacheValues(cacheName);
        return ResponseEntity.ok("Limpeza de cache concluida!");
    }
}
