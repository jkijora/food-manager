package eu.kijora.foodmanager.repository;

import eu.kijora.foodmanager.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

//    @Query(value = "SELECT DISTINCT p.category FROM PRODUCTS p", nativeQuery = true)
//    List<String> findDistinctCategories();

}
