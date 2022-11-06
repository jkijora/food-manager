package eu.kijora.foodmanager.dto.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryReadModelForProduct {

    private Long id;
    String name;
    boolean auxiliaryCategory;

}
