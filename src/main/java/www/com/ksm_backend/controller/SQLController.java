package www.com.ksm_backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import www.com.ksm_backend.dto.RegisterRequest;
import www.com.ksm_backend.repository.ProductRepository;
import www.com.ksm_backend.service.AuthService;

@RestController
@RequestMapping("/SQL")
//@AllArgsConstructor
@RequiredArgsConstructor
public class SQLController {
    private final AuthService service;
    private final ProductRepository repository;

    @PostMapping("/register")
    public void addUser(@RequestBody RegisterRequest request, HttpServletResponse response){
        service.addUser(request, response);
    }
    @PostMapping("/deleteProduct/{productName}")
    public void deleteProductByName(@PathVariable("productName") String productName) {
        repository.deleteProductByName(productName);
    }
}
