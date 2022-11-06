package eu.kijora.foodmanager.service;

import eu.kijora.foodmanager.controller.CategoryNotFoundException;
import eu.kijora.foodmanager.domain.Category;
import eu.kijora.foodmanager.dto.category.CategoryWriteModel;
import eu.kijora.foodmanager.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;
    ProductCategoryService productCategoryService;

    public CategoryService(CategoryRepository categoryRepository, ProductCategoryService productCategoryService) {
        this.categoryRepository = categoryRepository;
        this.productCategoryService = productCategoryService;
    }

    public Category save(CategoryWriteModel cwm) {
        return categoryRepository.save(productCategoryService.convertCategoryDtoIntoCategory(cwm));
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(String.format("Id %d not found in database", id)));
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
