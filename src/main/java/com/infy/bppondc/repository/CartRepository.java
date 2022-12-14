package com.infy.bppondc.repository;

import com.infy.bppondc.domain.Cart;
import com.infy.bppondc.service.dto.CartDTO;
import java.util.List;
import liquibase.pro.packaged.P;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
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

    void deleteByConsumerId(String consumerId);

    void deleteByConsumerIdAndProductName(String consumerId, String productName);

    List<Cart> findByConsumerIdAndProductName(String consumerId, String productName);

    @Query("From Cart c where c.consumerId = :consumerId and c.productName = :productName and c.store.id = :storeId")
    List<Cart> findByConsumerIdAndProductNameAndStoreId(
        @Param("consumerId") String consumerId,
        @Param("productName") String productName,
        @Param("storeId") Long storeId
    );

    @Modifying
    @Query(
        "Update Cart c set c.quantity = :quant where c.consumerId = :consumerId and c.productName = :productName and c.store.id = :storeId"
    )
    void updateQuantity(
        @Param(value = "quant") Integer quant,
        @Param("consumerId") String consumerId,
        @Param("productName") String productName,
        @Param("storeId") Long storeId
    );
}
