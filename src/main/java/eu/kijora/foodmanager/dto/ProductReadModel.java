package eu.kijora.foodmanager.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class ProductReadModel {

    private Long productId;
    private int quantity;
    private String name;
    private Set<CategoryReadModel> categories;
    private String comment;
    private int quantityThreshold;
    private LocalDate closestExpiration;

}
