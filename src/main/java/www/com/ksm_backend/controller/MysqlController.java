package www.com.ksm_backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import www.com.ksm_backend.dto.RegisterProduct;
import www.com.ksm_backend.dto.RegisterRequest;
import www.com.ksm_backend.entity.Product;
import www.com.ksm_backend.service.AuthService;
import www.com.ksm_backend.service.ProductService;

import java.io.IOException;
import java.util.zip.DataFormatException;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/SQL")
@RequiredArgsConstructor
public class MysqlController {
    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public void addUser(@RequestBody RegisterRequest request, HttpServletResponse response){
        service.addUser(request, response);
    }
}
