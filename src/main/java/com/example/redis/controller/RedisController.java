package com.example.redis.controller;

import com.example.redis.entity.Product;
import com.example.redis.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.redis.repository.ProductDao.HASH_KEY;

@RestController
@RequestMapping("/product")
public class RedisController {

    @Autowired
    private ProductDao productDao;

    @PostMapping
    public Product save(@RequestBody Product product) {
        return productDao.save(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productDao.findAllProducts();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id", value = HASH_KEY)
    public Product findProductById(@PathVariable int id) {
        return productDao.findProductById(id);
    }

    @DeleteMapping("{id}")
    @CacheEvict(key = "#id", value = HASH_KEY)
    public String deleteById(@PathVariable int id) {
        return productDao.deleteProductById(id);
    }
}
