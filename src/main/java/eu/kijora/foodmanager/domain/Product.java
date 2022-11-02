package eu.kijora.foodmanager.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private int quantity;
    private String name;
    @ManyToMany
    @JoinTable(name = "categories_products", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name="category_id"))
    private Set<Category> categories;
//    private String category;

    @Nullable
    private String comment;
    @Nullable
    private int quantityThreshold;
    @Nullable
    private LocalDate closestExpiration;


    public Product(int quantity, String name, Set<Category> categories) {
        this.quantity = quantity;
        this.name = name;
        this.categories = categories;
    }

    public Product(int quantity, String name,  Set<Category> categories, @Nullable String comment, int quantityThreshold, @Nullable LocalDate closestExpiration) {
        this.quantity = quantity;
        this.name = name;
        this.categories = categories;
        this.comment = comment;
        this.quantityThreshold = quantityThreshold;
        this.closestExpiration = closestExpiration;
    }
}
