package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;
    private String baseUrl;

    @BeforeEach
    void setUpTest(){
        baseUrl = String.format("%s:%d",testBaseUrl,serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");
        String pageTitle = driver.getTitle();
        assertEquals("Create New Product", pageTitle);
    }

    @Test
    void createPage_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");
        WebElement productNameInput = driver.findElement(By.id("nameInput"));
        assertNotNull(productNameInput);

        WebElement productQuantityInput = driver.findElement(By.id("quantityInput"));
        assertNotNull(productQuantityInput);

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        assertNotNull(submitButton);
    }

    @Test
    void createProduct_isCorrect(ChromeDriver driver) throws Exception{
        driver.get(baseUrl+"/product/create");
        WebElement productNameInput = driver.findElement(By.id("nameInput"));
        WebElement productQuantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        productNameInput.sendKeys("Product Testing");
        productQuantityInput.sendKeys("200");
        submitButton.click();
        String redirectUrl = driver.getCurrentUrl();
        assertTrue(redirectUrl.endsWith("/product/list"));
        WebElement productNameAdded = driver.findElement(By.xpath("//td[contains(text(),'Product Testing')]"));
        WebElement productQuantityAdded = driver.findElement(By.xpath("//td[contains(text(),'200')]"));
        assertNotNull(productNameAdded);
        assertNotNull(productQuantityAdded);
    }
}