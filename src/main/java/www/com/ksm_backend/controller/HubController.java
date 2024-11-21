package www.com.ksm_backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import www.com.ksm_backend.dto.RegisterProduct;
import www.com.ksm_backend.entity.Product;
import www.com.ksm_backend.repository.ProductRepository;
import www.com.ksm_backend.service.ProductService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DataFormatException;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/HUB")
@RequiredArgsConstructor
public class HubController {
    @Autowired
    private ProductService service;
    @Autowired
    private ProductRepository repository;

    @GetMapping("/message")
    public String message(){
        return "Bien joue Negro";
    }

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
