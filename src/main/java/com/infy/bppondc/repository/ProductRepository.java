package com.infy.bppondc.repository;

import com.infy.bppondc.domain.Product;
import com.infy.bppondc.service.dto.ProductDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //  Optional<ProductDTO> findByTitle(String productName);

    //    @Query("SELECT * FROM public.product WHERE title LIKE '%Milk%' AND store_id = 1001")
    //    Optional<ProductDTO> searchByTitleStoreId(String query)
    //

}
