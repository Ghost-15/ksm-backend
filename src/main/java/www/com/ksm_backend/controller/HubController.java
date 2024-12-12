package www.com.ksm_backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import www.com.ksm_backend.dto.RegisterProduct;
import www.com.ksm_backend.entity.Product;
import www.com.ksm_backend.repository.ProductRepository;
import www.com.ksm_backend.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/HUB")
@AllArgsConstructor
@RequiredArgsConstructor
public class HubController {
    private ProductService service;
    private ProductRepository repository;

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody RegisterProduct registerProduct, HttpServletResponse response) {
        service.addProduct(registerProduct, response);
    }
    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() {
        return repository.findAll();
    }
    @GetMapping("/getByCategory/{categoryName}")
    public List<Product> getByCategorie(@PathVariable("categoryName") String categoryName) {
        return repository.findAllByCategorie(categoryName);
    }
    @GetMapping("/getBySousCategory/{souscategoryName}")
    public List<Product> getBySousCategory(@PathVariable("souscategoryName") String souscategoryName) {
        return repository.findAllBySousCategory(souscategoryName);
    }
    @GetMapping("/getProduct/{productName}")
    public ResponseEntity<Product> getProduct(@PathVariable("productName") String productName) {
        return ResponseEntity.ok(service.getProduct(productName));
    }

}
