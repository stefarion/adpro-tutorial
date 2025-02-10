package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Getter @Setter
public class Product {
    private String productId;

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be a positive integer")
    private int productQuantity;

    public Product(){
        this.productId = UUID.randomUUID().toString();
    }
}