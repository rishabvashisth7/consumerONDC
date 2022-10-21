package com.infy.bppondc.service.mapper;

import com.infy.bppondc.domain.Product;
import com.infy.bppondc.domain.Store;
import com.infy.bppondc.service.dto.ProductDTO;
import com.infy.bppondc.service.dto.StoreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "store", source = "store", qualifiedByName = "storeId")
    ProductDTO toDto(Product s);

    @Named("storeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StoreDTO toDtoStoreId(Store store);
}
