package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    void createProductPage_shouldReturnCreateViewAndModel() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_product"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void createProductPost_shouldCallServiceAndRedirectToList() throws Exception {
        String id = UUID.randomUUID().toString();
        mockMvc.perform(post("/product/create")
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

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("list_product"))
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
        when(service.findAll()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/product/edit/id-2"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit_product"))
                .andExpect(model().attributeExists("product"));

        verify(service, times(1)).findAll();
    }

    @Test
    void editProductPage_shouldThrowWhenProductDoesNotExist() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptyList());

        Exception ex = assertThrows(Exception.class, () -> mockMvc.perform(get("/product/edit/unknown-id")).andReturn());
        Throwable cause = ex.getCause();
        assertTrue(cause instanceof IllegalArgumentException || (cause != null && cause.getCause() instanceof IllegalArgumentException));

        verify(service, times(1)).findAll();
    }

    @Test
    void editProductPost_shouldCallServiceAndRedirectToList() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("productId", "id-1")
                        .param("productName", "Updated")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection());

        verify(service, times(1)).edit(any(Product.class));
    }

    @Test
    void deleteProductPage_shouldReturnDeleteViewWhenProductExists() throws Exception {
        Product p1 = new Product();
        p1.setProductId("id-1");
        p1.setProductName("A");
        p1.setProductQuantity(1);
        when(service.findAll()).thenReturn(Collections.singletonList(p1));

        mockMvc.perform(get("/product/delete/id-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("delete_product"))
                .andExpect(model().attributeExists("product"));

        verify(service, times(1)).findAll();
    }

    @Test
    void deleteProductPage_shouldThrowWhenProductDoesNotExist() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptyList());

        Exception ex = assertThrows(Exception.class, () -> mockMvc.perform(get("/product/delete/unknown-id")).andReturn());
        Throwable cause = ex.getCause();
        assertTrue(cause instanceof IllegalArgumentException || (cause != null && cause.getCause() instanceof IllegalArgumentException));

        verify(service, times(1)).findAll();
    }

    @Test
    void deleteProductPost_shouldCallServiceAndRedirectToList() throws Exception {
        mockMvc.perform(post("/product/delete")
                        .param("productId", "id-1")
                        .param("productName", "A")
                        .param("productQuantity", "1"))
                .andExpect(status().is3xxRedirection());

        verify(service, times(1)).delete(any(Product.class));
    }
}
