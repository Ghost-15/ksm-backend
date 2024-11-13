package www.com.ksm_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int product_id;
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;
//    @Column(name = "category", nullable = false, length = 100)
//    private String category;
    @Column(name = "conditionnement", nullable = false)
    private int conditionnement;
    @Column(name = "coloris", nullable = false)
    private String coloris;
    @Column(name = "prix", nullable = false)
    private int prix;
    @Column(name = "picture_url", nullable = false)
    private String picture_url;
    @Column(name = "pdf_url", nullable = false)
    private String pdf_url;
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"))
    private Category category;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_souscategory",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "souscategory_id", referencedColumnName = "souscategory_id"))
    private SousCategory souscategory;

}