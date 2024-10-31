package www.com.ksm_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "code")
public class Code {
    @Id
    @GeneratedValue
    @Column(name = "code_id")
    private Integer code_id;
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "created", nullable = false, unique = true)
    private LocalDateTime created;
    @Column(name = "expires", nullable = false, unique = true)
    private LocalDateTime expires;
    @Column(name = "validated", unique = true)
    private LocalDateTime validated;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
