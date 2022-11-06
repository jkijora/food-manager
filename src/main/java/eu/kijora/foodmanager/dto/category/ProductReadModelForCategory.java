package eu.kijora.foodmanager.dto.category;

import eu.kijora.foodmanager.dto.product.CategoryReadModelForProduct;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class ProductReadModelForCategory {

    private Long productId;
    private int quantity;
    private String name;
    private Set<CategoryReadModelForProduct> categories;
    private String comment;
    private int quantityThreshold;
    private LocalDate closestExpiration;

}
