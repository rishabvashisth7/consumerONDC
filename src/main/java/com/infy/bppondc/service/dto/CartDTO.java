package com.infy.bppondc.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.infy.bppondc.domain.Cart} entity.
 */
public class CartDTO implements Serializable {

    private Long id;

    private String referenceId;

    private String productName;

    private String price;

    private Integer quantity;

    private ProductDTO product;

    private StoreDTO store;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public StoreDTO getStore() {
        return store;
    }

    public void setStore(StoreDTO store) {
        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartDTO)) {
            return false;
        }

        CartDTO cartDTO = (CartDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cartDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CartDTO{" +
            "id=" + getId() +
            ", referenceId='" + getReferenceId() + "'" +
            ", productName='" + getProductName() + "'" +
            ", price='" + getPrice() + "'" +
            ", quantity=" + getQuantity() +
            ", product=" + getProduct() +
            ", store=" + getStore() +
            "}";
    }
}
