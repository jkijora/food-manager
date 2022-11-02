package eu.kijora.foodmanager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {

    private Long id;
    String name;
    boolean auxiliaryCategory;
}
