package eu.kijora.foodmanager.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

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
//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category category;
    private String category;

    @Nullable
    private String comment;
    @Nullable
    private int quantityThreshold;
    @Nullable
    private LocalDate closestExpiration;


    public Product(int quantity, String name, String category) {
        this.quantity = quantity;
        this.name = name;
        this.category = category;
    }
}
