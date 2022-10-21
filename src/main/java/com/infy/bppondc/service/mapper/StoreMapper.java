package com.infy.bppondc.service.mapper;

import com.infy.bppondc.domain.Store;
import com.infy.bppondc.service.dto.StoreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Store} and its DTO {@link StoreDTO}.
 */
@Mapper(componentModel = "spring")
public interface StoreMapper extends EntityMapper<StoreDTO, Store> {}
