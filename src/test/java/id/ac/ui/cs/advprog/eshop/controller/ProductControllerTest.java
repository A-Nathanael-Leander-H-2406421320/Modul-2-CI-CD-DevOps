package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = ProductController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CarController.class)
)
@Import(ProductControllerTest.TestConfig.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService service;

    @Test
    void createProductPage_shouldReturnCreateViewAndModel() throws Exception {
        mockMvc.perform(get("/product/createProduct"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void createProductPost_shouldCallServiceAndRedirectToList() throws Exception {
        String id = UUID.randomUUID().toString();
        mockMvc.perform(post("/product/createProduct")
                        .param("productId", id)
                        .param("productName", "TestProduct")
                        .param("productQuantity", "5"))
                .andExpect(status().is3xxRedirection());

        verify(service, times(1)).create(any(Product.class));
    }

    @Test
    void listProductPage_shouldReturnListViewWithProducts() throws Exception {
        Product p = new Product();
        p.setProductId("id-1");
        p.setProductName("A");
        p.setProductQuantity(1);
        List<Product> list = Collections.singletonList(p);
        when(service.findAll()).thenReturn(list);

        mockMvc.perform(get("/product/listProduct"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasSize(1)));

        verify(service, times(1)).findAll();
    }

    @Test
    void editProductPage_shouldReturnEditViewWhenProductExists() throws Exception {
        Product p1 = new Product();
        p1.setProductId("id-1");
        p1.setProductName("A");
        p1.setProductQuantity(1);
        Product p2 = new Product();
        p2.setProductId("id-2");
        p2.setProductName("B");
        p2.setProductQuantity(2);
        when(service.findById("id-2")).thenReturn(p2);

        mockMvc.perform(get("/product/editProduct/id-2"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"));

        verify(service, times(1)).findById("id-2");
    }

    @Test
    void editProductPost_shouldCallServiceAndRedirectToList() throws Exception {
        mockMvc.perform(post("/product/editProduct")
                        .param("productId", "id-1")
                        .param("productName", "Updated")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection());

        verify(service, times(1)).update(eq("id-1"), any(Product.class));
    }

    @Test
    void deleteProductPost_shouldCallServiceAndRedirectToList() throws Exception {
        mockMvc.perform(post("/product/deleteProduct")
                        .param("productId", "id-1")
                        .param("productName", "A")
                        .param("productQuantity", "1"))
                .andExpect(status().is3xxRedirection());

        // ProductController now exposes POST /product/deleteProduct -> service.delete(productId)
        verify(service, times(1)).delete(eq("id-1"));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public ProductService productService() {
            return mock(ProductService.class);
        }
    }
}
