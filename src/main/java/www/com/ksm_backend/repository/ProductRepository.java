package www.com.ksm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import www.com.ksm_backend.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
}
