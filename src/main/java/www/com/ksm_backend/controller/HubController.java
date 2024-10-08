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
import www.com.ksm_backend.dto.RegisterProduct;
import www.com.ksm_backend.entity.Product;
import www.com.ksm_backend.service.ProductService;

import java.io.IOException;
import java.util.Arrays;
import java.util.zip.DataFormatException;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/HUB")
@RequiredArgsConstructor
public class HubController {
    @Autowired
    private ProductService service;

    @PostMapping("/addProduct")
    public void addProduct(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("category") String category,
//            @RequestParam("sousCategory") String sousCategory,
            @RequestParam("description") String description,
            @RequestParam("conditionnement") int conditionnement,
            @RequestParam("coloris") String coloris,
            @RequestParam("prix") int prix,
            HttpServletResponse response) throws IOException {

        service.addProduct(
                file,
                name,
                category,
//                sousCategory,
                description,
                conditionnement,
                coloris,
                prix,
                response);
    }
    @GetMapping("/getProduct/{productName}")
    public ResponseEntity<Product> getProduct(@PathVariable("productName") String productName) throws DataFormatException, IOException {
        return ResponseEntity.ok(service.getProduct(productName));
    }
    @GetMapping("/display/{pictureName}")
    public ResponseEntity<?> displayPicture(@PathVariable("pictureName") String pictureName) throws DataFormatException, IOException {
        byte[] picture = service.displayPicture(pictureName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(picture);
    }
}
