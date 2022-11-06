package eu.kijora.foodmanager.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    String name;
    boolean auxiliaryCategory;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    Set<Product> products;

    public Category(String name) {
        this.name = name;
        this.auxiliaryCategory = false;
        this.products = new HashSet<>();
    }

    public Category(String name, boolean auxiliaryCategory) {
        this.name = name;
        this.auxiliaryCategory = auxiliaryCategory;
        this.products = new HashSet<>();

    }
}
