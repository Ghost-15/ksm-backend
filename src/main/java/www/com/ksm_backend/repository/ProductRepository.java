package www.com.ksm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import www.com.ksm_backend.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
    @Query(value = """
      select p from Product p inner join Category c\s
      on p.categories.name = c.name\s
      where c.name = :name \s
      """)
    List<Product> findAllByCategorie(String name);
}
