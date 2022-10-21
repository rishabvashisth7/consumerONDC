package com.infy.bppondc.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.infy.bppondc.domain.Store} entity.
 */
public class StoreDTO implements Serializable {

    private Long id;

    private String storeName;

    private String storeAddress;

    private Double storeRating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public Double getStoreRating() {
        return storeRating;
    }

    public void setStoreRating(Double storeRating) {
        this.storeRating = storeRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreDTO)) {
            return false;
        }

        StoreDTO storeDTO = (StoreDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, storeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StoreDTO{" +
            "id=" + getId() +
            ", storeName='" + getStoreName() + "'" +
            ", storeAddress='" + getStoreAddress() + "'" +
            ", storeRating=" + getStoreRating() +
            "}";
    }
}
