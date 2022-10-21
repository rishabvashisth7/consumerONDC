package com.infy.bppondc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Store.
 */
@Entity
@Table(name = "store")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_address")
    private String storeAddress;

    @Column(name = "store_rating")
    private Double storeRating;

    @OneToMany(mappedBy = "store")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "store", "carts" }, allowSetters = true)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "store")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "product", "store" }, allowSetters = true)
    private Set<Cart> carts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Store id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public Store storeName(String storeName) {
        this.setStoreName(storeName);
        return this;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return this.storeAddress;
    }

    public Store storeAddress(String storeAddress) {
        this.setStoreAddress(storeAddress);
        return this;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public Double getStoreRating() {
        return this.storeRating;
    }

    public Store storeRating(Double storeRating) {
        this.setStoreRating(storeRating);
        return this;
    }

    public void setStoreRating(Double storeRating) {
        this.storeRating = storeRating;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setStore(null));
        }
        if (products != null) {
            products.forEach(i -> i.setStore(this));
        }
        this.products = products;
    }

    public Store products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public Store addProduct(Product product) {
        this.products.add(product);
        product.setStore(this);
        return this;
    }

    public Store removeProduct(Product product) {
        this.products.remove(product);
        product.setStore(null);
        return this;
    }

    public Set<Cart> getCarts() {
        return this.carts;
    }

    public void setCarts(Set<Cart> carts) {
        if (this.carts != null) {
            this.carts.forEach(i -> i.setStore(null));
        }
        if (carts != null) {
            carts.forEach(i -> i.setStore(this));
        }
        this.carts = carts;
    }

    public Store carts(Set<Cart> carts) {
        this.setCarts(carts);
        return this;
    }

    public Store addCart(Cart cart) {
        this.carts.add(cart);
        cart.setStore(this);
        return this;
    }

    public Store removeCart(Cart cart) {
        this.carts.remove(cart);
        cart.setStore(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Store)) {
            return false;
        }
        return id != null && id.equals(((Store) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Store{" +
            "id=" + getId() +
            ", storeName='" + getStoreName() + "'" +
            ", storeAddress='" + getStoreAddress() + "'" +
            ", storeRating=" + getStoreRating() +
            "}";
    }
}
