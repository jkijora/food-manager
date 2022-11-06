package eu.kijora.foodmanager.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class ProductWriteModel {

    private int quantity;
    private String name;
    private Set<Long> categoryIds;
    private String comment;
    private int quantityThreshold;
    private LocalDate closestExpiration;

}
