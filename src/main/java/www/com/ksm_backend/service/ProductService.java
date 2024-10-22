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
import java.util.List;
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
    public void addProduct(RegisterProduct registerProduct, HttpServletResponse response) {

        Optional<Category> categoryOptional = categoryRepository.findByName(registerProduct.getCategory());
//        Optional<SousCategory> sousCategory = sousCategoryRepository.findByName(register.getSousCategory());

        Product product = new Product();
        product.setName(registerProduct.getName().trim());
        product.setCategories(categoryOptional.get());
        product.setConditionnement(registerProduct.getConditionnement());
        product.setColoris(registerProduct.getColoris());
        product.setPrix(registerProduct.getPrix());
        product.setPicture_url(registerProduct.getPicture_url().trim());
        product.setDescription(registerProduct.getDescription());
        try {
            productRepository.save(product);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(406);
        }
    }

    public Product getProduct(String productName) {
        Optional<Product> product = productRepository.findByName(productName);
        return Product.builder()
                .name(product.get().getName())
//                .category(product.get().getCategory())
                .conditionnement(product.get().getConditionnement())
                .coloris(product.get().getColoris())
                .prix(product.get().getPrix())
                .picture_url(product.get().getPicture_url())
                .description(product.get().getDescription())
                .build();
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

}