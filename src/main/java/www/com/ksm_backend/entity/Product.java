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
    @Column(name = "description", nullable = false, length = 200)
    private String description;
    @Column(name = "conditionnement", nullable = false)
    private int conditionnement;
    @Column(name = "coloris", nullable = false)
    private String coloris;
    @Column(name = "prix", nullable = false)
    private int prix;
    @Lob
    @Column(name = "picture", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] picture;
    @Column(name = "type", nullable = false)
    private String type;
//    @Lob
//    @Column(name = "pdf")
//    private byte[] pdf;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"))
    private Category categories;

//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "product_souscategory",
//            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
//            inverseJoinColumns = @JoinColumn(name = "souscategory_id", referencedColumnName = "souscategory_id"))
//    private SousCategory sousCategories;

}