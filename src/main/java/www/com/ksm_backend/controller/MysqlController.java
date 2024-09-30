package www.com.ksm_backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import www.com.ksm_backend.dto.RegisterProduct;
import www.com.ksm_backend.entity.Product;
import www.com.ksm_backend.service.ProductService;

import java.io.IOException;
import java.util.zip.DataFormatException;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/SQL")
@AllArgsConstructor
@NoArgsConstructor
public class MysqlController {
    @Autowired
    private ProductService service;

    @PostMapping("/addProduct")
    public void addProduct(RegisterProduct register, @RequestParam("picture") MultipartFile file, HttpServletResponse response) throws IOException {
        service.addProduct(register, file, response);
    }
    @GetMapping("/getProduct/{pictureName}")
    public ResponseEntity<Product> getProduct(@PathVariable("pictureName") String product) throws DataFormatException, IOException {
        return ResponseEntity.ok(service.getProduct(product));
    }
    @GetMapping("/display/{pictureName}")
    public ResponseEntity<?> displayPicture(@PathVariable("pictureName") String name) throws DataFormatException, IOException {
        byte[] picture = service.displayPicture(name);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(picture);
    }
}
