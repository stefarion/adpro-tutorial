package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product getProductById(String productId) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public Product updateProduct(Product product) {
        Product oldProduct = getProductById(product.getProductId());
        if (oldProduct == null) {
            return null;
        }
        oldProduct.setProductName(product.getProductName());
        oldProduct.setProductQuantity(product.getProductQuantity());
        return product;
    }

    public boolean deleteProduct(String productId) {
        return productData.removeIf(product -> product.getProductId().equals(productId));
    }
}