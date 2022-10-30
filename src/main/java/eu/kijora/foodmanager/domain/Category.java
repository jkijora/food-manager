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
//    MainCategory mainCategory;
    String name;

    public Category(String name) {
//        this.mainCategory = mainCategory;
        this.name = name;
    }
}
