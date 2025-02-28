package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository implements RepositoryInterface<Product>{
    private List<Product> productData = new ArrayList<>();

    public Product create(@Valid Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String productId) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public Product update(String productId, Product newProductData) {
        Product selectedProduct = findById(productId);
        if (selectedProduct != null) {
            selectedProduct.setProductName(newProductData.getProductName());
            selectedProduct.setProductQuantity(newProductData.getProductQuantity());
        }
        return selectedProduct;
    }

    public Product delete(String productId) {
        Product selectedProduct = findById(productId);
        productData.remove(selectedProduct);
        return selectedProduct;
    }
}