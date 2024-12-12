package www.com.ksm_backend.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import www.com.ksm_backend.dto.RegisterProduct;
import www.com.ksm_backend.entity.Category;
import www.com.ksm_backend.entity.Product;
import www.com.ksm_backend.entity.SousCategory;
import www.com.ksm_backend.repository.CategoryRepository;
import www.com.ksm_backend.repository.ProductRepository;
import www.com.ksm_backend.repository.SousCategoryRepository;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    SousCategoryRepository sousCategoryRepository;
    public void addProduct(RegisterProduct registerProduct, HttpServletResponse response) {

        Optional<Category> categoryOptional = categoryRepository.findByName(registerProduct.getCategory());
        Optional<SousCategory> sousCategoryOptional = sousCategoryRepository.findByName(registerProduct.getSousCategory());

        Product product = new Product();
        product.setName(registerProduct.getName().trim());
        product.setCategory(categoryOptional.get());
        product.setSouscategory(sousCategoryOptional.get());
        product.setConditionnement(registerProduct.getConditionnement());
        product.setColoris(registerProduct.getColoris());
        product.setPrix(registerProduct.getPrix());
        product.setPicture_url(registerProduct.getPicture_url().trim());
        product.setPdf_url(registerProduct.getPdf_url().trim());
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
                .conditionnement(product.get().getConditionnement())
                .coloris(product.get().getColoris())
                .prix(product.get().getPrix())
                .picture_url(product.get().getPicture_url())
                .pdf_url(product.get().getPdf_url())
                .description(product.get().getDescription())
                .build();
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public void deleteProduct(String product_name){
        productRepository.deleteProductByName(product_name);
    }

}