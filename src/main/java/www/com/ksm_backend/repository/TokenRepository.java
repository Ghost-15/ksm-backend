package www.com.ksm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.com.ksm_backend.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByCode(String code);
}
