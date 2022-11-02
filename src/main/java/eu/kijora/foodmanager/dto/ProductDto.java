package eu.kijora.foodmanager.dto;

import eu.kijora.foodmanager.domain.Category;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class ProductDto {

    private Long productId;
    private int quantity;
    private String name;
    private Set<CategoryDto> categories;
    private String comment;
    private int quantityThreshold;
    private LocalDate closestExpiration;

}
