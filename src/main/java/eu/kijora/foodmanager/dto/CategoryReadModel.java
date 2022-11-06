package eu.kijora.foodmanager.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CategoryReadModel {

    private Long id;
    String name;
    boolean auxiliaryCategory;
    //TODO Another projection but more Category focused
//    private Set<ProductReadModel> products;

}
