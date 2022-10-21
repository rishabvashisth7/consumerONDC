package com.infy.bppondc.service.mapper;

import com.infy.bppondc.domain.Cart;
import com.infy.bppondc.domain.Product;
import com.infy.bppondc.domain.Store;
import com.infy.bppondc.service.dto.CartDTO;
import com.infy.bppondc.service.dto.ProductDTO;
import com.infy.bppondc.service.dto.StoreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cart} and its DTO {@link CartDTO}.
 */
@Mapper(componentModel = "spring")
public interface CartMapper extends EntityMapper<CartDTO, Cart> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    @Mapping(target = "store", source = "store", qualifiedByName = "storeId")
    CartDTO toDto(Cart s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);

    @Named("storeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StoreDTO toDtoStoreId(Store store);
}
