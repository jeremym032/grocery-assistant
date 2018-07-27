package com.jeremycarvermartin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A StoreGroceryItem.
 */
@Entity
@Table(name = "store_grocery_item")
public class StoreGroceryItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "favorite_ind")
    private Boolean favoriteInd;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Store store;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Item item;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public StoreGroceryItem price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean isFavoriteInd() {
        return favoriteInd;
    }

    public StoreGroceryItem favoriteInd(Boolean favoriteInd) {
        this.favoriteInd = favoriteInd;
        return this;
    }

    public void setFavoriteInd(Boolean favoriteInd) {
        this.favoriteInd = favoriteInd;
    }

    public Store getStore() {
        return store;
    }

    public StoreGroceryItem store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Item getItem() {
        return item;
    }

    public StoreGroceryItem item(Item item) {
        this.item = item;
        return this;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StoreGroceryItem storeGroceryItem = (StoreGroceryItem) o;
        if (storeGroceryItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), storeGroceryItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StoreGroceryItem{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", favoriteInd='" + isFavoriteInd() + "'" +
            "}";
    }
}
