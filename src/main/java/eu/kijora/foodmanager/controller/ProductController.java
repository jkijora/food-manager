package eu.kijora.foodmanager.controller;

import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.dto.ProductReadModel;
import eu.kijora.foodmanager.dto.ProductWriteModel;
import eu.kijora.foodmanager.repository.CategoryRepository;
import eu.kijora.foodmanager.repository.ProductRepository;
import eu.kijora.foodmanager.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("products")
@Slf4j
public class ProductController {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ProductService productService;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ProductReadModel getProduct(@PathVariable Long id) {
        Product product = productService.findById(id);
        return productService.convertProductIntoDto(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductReadModel>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll().stream()
                .map(p -> productService.convertProductIntoDto(p))
                .collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductReadModel addProduct(@RequestBody ProductWriteModel productWriteModel) {
        Product product = productService.save(productWriteModel);
        log.debug("Successfully added a new product");
        return productService.convertProductIntoDto(product);
    }

    @PatchMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductReadModel> changeQuantity(@RequestBody Integer quantity, @PathVariable Long id) {
        Product product = productService.changeQuantity(quantity, id);
        log.debug("Successfully changed the quantity of the product");
        return ResponseEntity.ok().body(productService.convertProductIntoDto(product));
    }


    @ExceptionHandler
    public ResponseEntity<?> handle(ProductNotFoundException productNotFoundException) {
        log.error(productNotFoundException.getMessage());
        return ResponseEntity.notFound().build();
    }
}
