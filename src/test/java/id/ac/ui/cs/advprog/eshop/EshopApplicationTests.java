package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = "id.ac.ui.cs.advprog.eshop")
class EshopApplicationTests {

    @Test
    void contextLoads() {
        EshopApplication.main(new String[]{});
    }
}
