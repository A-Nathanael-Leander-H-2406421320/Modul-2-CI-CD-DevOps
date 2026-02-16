package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl service;

    private Product p1;
    private Product p2;

    @BeforeEach
    void setUp() {
        p1 = new Product();
        p1.setProductId("id-1");
        p1.setProductName("A");
        p1.setProductQuantity(1);

        p2 = new Product();
        p2.setProductId("id-2");
        p2.setProductName("B");
        p2.setProductQuantity(2);
    }

    @Test
    void create_shouldCallRepositoryAndReturnProduct() {
        when(productRepository.create(p1)).thenReturn(p1);

        Product result = service.create(p1);

        assertSame(p1, result);
        verify(productRepository, times(1)).create(p1);
    }

    @Test
    void findAll_shouldReturnEmptyListWhenNoProducts() {
        when(productRepository.findAll()).thenReturn(Collections.emptyIterator());

        var result = service.findAll();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findAll_shouldReturnAllProducts() {
        Iterator<Product> it = Arrays.asList(p1, p2).iterator();
        when(productRepository.findAll()).thenReturn(it);

        var result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("id-1", result.get(0).getProductId());
        assertEquals("id-2", result.get(1).getProductId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void edit_shouldCallRepositoryAndReturnUpdatedProduct() {
        Product updated = new Product();
        updated.setProductId("id-1");
        updated.setProductName("Updated");
        updated.setProductQuantity(10);

        when(productRepository.edit(updated)).thenReturn(updated);

        Product result = service.edit(updated);

        assertSame(updated, result);
        verify(productRepository, times(1)).edit(updated);
    }

    @Test
    void delete_shouldCallRepositoryAndReturnDeletedProduct() {
        when(productRepository.delete(p1)).thenReturn(p1);

        Product result = service.delete(p1);

        assertSame(p1, result);
        verify(productRepository, times(1)).delete(p1);
    }

    @Test
    void edit_shouldPropagateRepositoryException() {
        Product updated = new Product();
        updated.setProductId("unknown");
        when(productRepository.edit(updated)).thenThrow(new RuntimeException("not found"));

        assertThrows(RuntimeException.class, () -> service.edit(updated));
        verify(productRepository, times(1)).edit(updated);
    }
}

