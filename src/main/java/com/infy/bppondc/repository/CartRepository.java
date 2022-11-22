package com.infy.bppondc.repository;

import com.infy.bppondc.domain.Cart;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    void deleteByReferenceId(String referenceId);

    void deleteByReferenceIdAndProductName(String referenceId, String productName);

    void deleteByProductName(String productName);

    List<Cart> findByReferenceIdAndProductName(String referenceId, String productName);
}
