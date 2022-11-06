package eu.kijora.foodmanager.controller;

import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.dto.ProductReadModel;
import eu.kijora.foodmanager.dto.ProductWriteModel;
import eu.kijora.foodmanager.service.ProductCategoryService;
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

    ProductService productService;
    ProductCategoryService productCategoryService;

    public ProductController(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/{id}")
    public ProductReadModel getProduct(@PathVariable Long id) {
        Product product = productService.findById(id);
        return productCategoryService.convertProductIntoDto(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductReadModel>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll().stream()
                .map(p -> productCategoryService.convertProductIntoDto(p))
                .collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductReadModel addProduct(@RequestBody ProductWriteModel productWriteModel) {
        Product product = productService.save(productWriteModel);
        log.info("Successfully added a new product");
        return productCategoryService.convertProductIntoDto(product);
    }

    @PatchMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductReadModel> changeQuantity(@RequestBody Integer quantity, @PathVariable Long id) {
        Product product = productService.changeQuantity(quantity, id);
        log.info("Successfully changed the quantity of the product");
        return ResponseEntity.ok().body(productCategoryService.convertProductIntoDto(product));
    }


    @ExceptionHandler
    public ResponseEntity<?> handle(ProductNotFoundException productNotFoundException) {
        log.error(productNotFoundException.getMessage());
        return ResponseEntity.notFound().build();
    }
}
