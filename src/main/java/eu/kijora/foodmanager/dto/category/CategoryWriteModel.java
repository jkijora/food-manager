package eu.kijora.foodmanager.dto.category;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Set;

@Data
@Builder
public class CategoryWriteModel {

    String name;
    boolean auxiliaryCategory;
    @Nullable
    Set<Long> productIds;
}
