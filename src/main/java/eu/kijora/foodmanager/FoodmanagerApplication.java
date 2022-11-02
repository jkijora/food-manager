package eu.kijora.foodmanager;

import eu.kijora.foodmanager.domain.Category;
import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.repository.CategoryRepository;
import eu.kijora.foodmanager.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
@Slf4j
public class FoodmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodmanagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ProductRepository pr, CategoryRepository cr) {
        return (args) -> {

            Category c1 = new Category("Salty");
            Category c2 = new Category("Sweet");
            Category c3 = new Category("Others");
            Category c4 = new Category("To finish");
            cr.save(c1);
            cr.save(c2);
            cr.save(c3);

            Product p1 = new Product(2, "Buraczki");
            Product p2 = new Product(3, "Kukurydza");
            Product p3 = new Product(5, "Kokosowe Mleczko");

            p1.addCategory(c1);
            p2.addCategory(c2);
            p3.addCategory(c1);

//            pr.save(new Product(12, "Mleko", Set.of(cr.findById(2L).orElse(c3))));
            pr.save(p1);
            pr.save(p2);
            pr.save(p3);


//            pr.findDistinctCategories().forEach(log::info);


        };
    }

}
