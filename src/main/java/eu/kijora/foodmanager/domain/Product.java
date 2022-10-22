package eu.kijora.foodmanager.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private int quantity;
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(int quantity, String name, Category category) {
        this.quantity = quantity;
        this.name = name;
        this.category = category;
    }
}
