package www.com.ksm_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "token_id")
  private int token_id;
  @Column(unique = true)
  private String code;
  @Column(unique = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Instant creation;
  @Column(unique = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Instant expiration;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  public User user;
}
