package www.com.ksm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import www.com.ksm_backend.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);
}
