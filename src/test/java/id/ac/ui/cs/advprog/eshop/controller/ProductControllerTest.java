package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@AutoConfigureJsonTesters
@WebMvcTest(ProductController.class)
public class ProductControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    void testProductListPage() throws Exception{
        MockHttpServletResponse response = mockMvc.perform(get("/product/list"))
                .andReturn().getResponse();
        assertEquals(HttpStatus.SC_OK, response.getStatus());
    }

    @Test
    void testCreateProductPage() throws Exception{
        MockHttpServletResponse response = mockMvc.perform(get("/product/create"))
                .andReturn().getResponse();
        assertEquals(HttpStatus.SC_OK, response.getStatus());
    }

    @Test
    void testEditProductPage() throws Exception{
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        Mockito.when(service.getProductById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(product1);
        MockHttpServletResponse response = mockMvc.perform(
                        get("/product/edit/eb558e9f-1c39-460e-8860-71af6af63bd6"))
                .andReturn().getResponse();
        assertEquals(HttpStatus.SC_OK, response.getStatus());
    }

    @Test
    void testCreateProduct() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                        post("/product/create")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("productName", "TestProduct")
                                .param("productQuantity", "100"))
                .andReturn().getResponse();
        assertEquals(HttpStatus.SC_MOVED_TEMPORARILY, response.getStatus());
    }

    @Test
    void testEditProduct() throws Exception {
        String productId = "testProductId";
        MockHttpServletResponse response = mockMvc.perform(
                        put("/product/edit/{productId}", productId)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("productName", "UpdatedTestProduct")
                                .param("productQuantity", "50"))
                .andReturn().getResponse();
        assertEquals(HttpStatus.SC_MOVED_TEMPORARILY, response.getStatus());
    }

    @Test
    void testDeleteProduct() throws Exception {
        String productId = "testProductId";
        MockHttpServletResponse response = mockMvc.perform(delete("/product/delete/{productId}", productId))
                .andReturn().getResponse();
        assertEquals(HttpStatus.SC_MOVED_TEMPORARILY, response.getStatus());
    }
}