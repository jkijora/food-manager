package eu.kijora.foodmanager.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    String name;
    boolean auxiliaryCategory;

    public Category(String name) {
        this.name = name;
        auxiliaryCategory = false;
    }

    public Category(String name, boolean auxiliaryCategory) {
        this.name = name;
        this.auxiliaryCategory = auxiliaryCategory;
    }
}
