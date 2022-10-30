package eu.kijora.foodmanager;

import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.repository.CategoryRepository;
import eu.kijora.foodmanager.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class FoodmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodmanagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ProductRepository pr) {
        return (args) -> {

//            Product.builder()
//                            .name()

            pr.save(new Product(12, "Mleko", "Płynne"));
            pr.save(new Product(2, "Buraczki", "Słoiki"));
            pr.save(new Product(2, "Kukurydza", "Puszki"));
            pr.save(new Product(4, "Kokosowe Mleczko", "Puszki"));



            pr.findDistinctCategories().forEach(log::info);
        };
    }

}
