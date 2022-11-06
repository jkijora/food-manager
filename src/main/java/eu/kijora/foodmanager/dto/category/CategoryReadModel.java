package eu.kijora.foodmanager.dto.category;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CategoryReadModel {

    private Long id;
    String name;
    boolean auxiliaryCategory;
    private Set<ProductReadModelForCategory> products;

}
