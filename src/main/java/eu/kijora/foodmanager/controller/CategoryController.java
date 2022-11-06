package eu.kijora.foodmanager.controller;

import eu.kijora.foodmanager.domain.Category;
import eu.kijora.foodmanager.dto.category.CategoryReadModel;
import eu.kijora.foodmanager.dto.category.CategoryWriteModel;
import eu.kijora.foodmanager.service.CategoryService;
import eu.kijora.foodmanager.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("categories")
@Slf4j
public class CategoryController {

    CategoryService categoryService;
    ProductCategoryService productCategoryService;

    public CategoryController(CategoryService categoryService, ProductCategoryService productCategoryService) {
        this.categoryService = categoryService;
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/{id}")
    public CategoryReadModel getCategory(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return productCategoryService.convertCategoryIntoDto(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryReadModel>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll().stream()
                .map(c -> productCategoryService.convertCategoryIntoDto(c))
                .collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryReadModel addCategory(@RequestBody CategoryWriteModel categoryWriteModel) {
        Category category = categoryService.save(categoryWriteModel);
        log.info("Successfully added a new category");
        return productCategoryService.convertCategoryIntoDto(category);
    }


    @ExceptionHandler
    public ResponseEntity<?> handle(CategoryNotFoundException categoryNotFoundException) {
        log.error(categoryNotFoundException.getMessage());
        return ResponseEntity.notFound().build();
    }
}
