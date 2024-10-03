package www.com.ksm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import www.com.ksm_backend.entity.SousCategory;

import java.util.Optional;

public interface SousCategoryRepository extends JpaRepository<SousCategory, Integer> {
    Optional<SousCategory> findByName(String name);
}
