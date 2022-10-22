package eu.kijora.foodmanager;

import eu.kijora.foodmanager.domain.Category;
import eu.kijora.foodmanager.domain.MainCategory;
import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.repository.CategoryRepository;
import eu.kijora.foodmanager.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FoodmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodmanagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ProductRepository pr, CategoryRepository cr) {
        return (args) -> {

            Category piwo = new Category(MainCategory.ALCOHOL, "Piwo");
            cr.save(piwo);
            Category puszki = cr.save(new Category(MainCategory.SALTY, "Puszki"));
            Category sloiki = cr.save(new Category(MainCategory.SALTY, "Słoiki"));
            Category sweet_general = cr.save(new Category(MainCategory.SWEET, null));
            Category wino = cr.save(new Category(MainCategory.ALCOHOL, "Wino"));
            Category pranie = cr.save(new Category(MainCategory.DETERGENTS, "Pranie"));
            Category mycie = cr.save(new Category(MainCategory.DETERGENTS, "Mycie"));
            Category save = cr.save(new Category(MainCategory.DETERGENTS, null));
            Category others = cr.save(new Category(MainCategory.OTHERS, null));


            pr.save(new Product(12, "Mleko", sweet_general));
            pr.save(new Product(2, "Buraczki", sloiki));
            pr.save(new Product(2, "Kukurydza", cr.findByName("Puszki").orElse(others)));
            pr.save(new Product(2, "Kokosowe Mleczko", puszki));

        };
    }

}
