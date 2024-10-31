package www.com.ksm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import www.com.ksm_backend.entity.Code;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Integer> {
    Optional<Code> findByCode(String code);
    @Query(value = "select c.validated from Code c where c.code = :code")
    LocalDateTime findValidatedWhereCodeIs(String code);
}
