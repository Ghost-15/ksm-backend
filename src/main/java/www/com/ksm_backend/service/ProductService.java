package www.com.ksm_backend.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import www.com.ksm_backend.dto.RegisterProduct;
import www.com.ksm_backend.entity.Product;
import www.com.ksm_backend.repository.ProductRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

import static www.com.ksm_backend.config.ImageUtils.compressImage;
import static www.com.ksm_backend.config.ImageUtils.decompressImage;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public void addProduct(RegisterProduct register, MultipartFile file, HttpServletResponse response) throws IOException {
        Product product = Product.builder()
                .name(register.getName())
                .category(register.getCategory())
                .description(register.getDescription())
                .conditionnement(register.getConditionnement())
                .coloris(register.getColoris())
                .prix(register.getPrix())
                .picture(compressImage(file.getBytes()))
                .type(file.getContentType())
                .build();
        try {
            productRepository.save(product);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(406);
        }
    }

    public Product getProduct(String productName) throws DataFormatException, IOException {
        Optional<Product> product = productRepository.findByName(productName);
        return Product.builder()
                .name(product.get().getName())
                .category(product.get().getCategory())
                .description(product.get().getDescription())
                .conditionnement(product.get().getConditionnement())
                .coloris(product.get().getColoris())
                .prix(product.get().getPrix())
                .picture(decompressImage(product.get().getPicture()))
                .type(product.get().getType())
                .build();
    }

    public byte[] displayPicture(String name) throws DataFormatException, IOException {
        Optional<Product> product = productRepository.findByName(name);
        return decompressImage(product.get().getPicture());
    }
}
