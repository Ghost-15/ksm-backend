package www.com.ksm_backend.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import www.com.ksm_backend.dto.RegisterProduct;
import www.com.ksm_backend.entity.Category;
import www.com.ksm_backend.entity.Product;
import www.com.ksm_backend.repository.CategoryRepository;
import www.com.ksm_backend.repository.ProductRepository;
import www.com.ksm_backend.repository.SousCategoryRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.zip.DataFormatException;

import static www.com.ksm_backend.config.ByteUtils.compressByte;
import static www.com.ksm_backend.config.ByteUtils.decompressByte;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SousCategoryRepository sousCategoryRepository;
    public void addProduct(
            MultipartFile file,
            String name,
            String category,
//          String sousCategory,
            String description,
            int conditionnement,
            String coloris,
            int prix,
            HttpServletResponse response) throws IOException {

        Optional<Category> categoryOptional = categoryRepository.findByName(category);
//        Optional<SousCategory> sousCategory = sousCategoryRepository.findByName(register.getSousCategory());

        Product product = new Product();
        product.setName(name);
        product.setCategories(categoryOptional.get());
        product.setDescription(description);
        product.setConditionnement(conditionnement);
        product.setColoris(coloris);
        product.setPrix(prix);
        product.setPicture(compressByte(file.getBytes()));
        product.setType(file.getContentType());
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
//                .category(product.get().getCategory())
                .description(product.get().getDescription())
                .conditionnement(product.get().getConditionnement())
                .coloris(product.get().getColoris())
                .prix(product.get().getPrix())
                .picture(decompressByte(product.get().getPicture()))
                .type(product.get().getType())
                .build();
    }

    public byte[] displayPicture(String pictureName) throws DataFormatException, IOException {
        Optional<Product> product = productRepository.findByName(pictureName);
        return decompressByte(product.get().getPicture());
    }
}