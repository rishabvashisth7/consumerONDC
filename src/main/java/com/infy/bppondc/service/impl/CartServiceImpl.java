package com.infy.bppondc.service.impl;

import com.infy.bppondc.domain.Cart;
import com.infy.bppondc.repository.CartRepository;
import com.infy.bppondc.service.CartService;
import com.infy.bppondc.service.dto.CartDTO;
import com.infy.bppondc.service.mapper.CartMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cart}.
 */
@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartDTO save(CartDTO cartDTO) {
        log.debug("Request to save Cart : {}", cartDTO);
        Cart cart = cartMapper.toEntity(cartDTO);
        cart = cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    @Override
    public CartDTO update(CartDTO cartDTO) {
        log.debug("Request to save Cart : {}", cartDTO);
        Cart cart = cartMapper.toEntity(cartDTO);
        cart = cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    @Override
    public Optional<CartDTO> partialUpdate(CartDTO cartDTO) {
        log.debug("Request to partially update Cart : {}", cartDTO);

        return cartRepository
            .findById(cartDTO.getId())
            .map(existingCart -> {
                cartMapper.partialUpdate(existingCart, cartDTO);

                return existingCart;
            })
            .map(cartRepository::save)
            .map(cartMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartDTO> findAll() {
        log.debug("Request to get all Carts");
        return cartRepository.findAll().stream().map(cartMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartDTO> findOne(Long id) {
        log.debug("Request to get Cart : {}", id);
        return cartRepository.findById(id).map(cartMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cart : {}", id);
        cartRepository.deleteById(id);
    }

    @Override
    public void deleteByConsumerId(String consumerId) {
        log.debug("Request to delete Cart : {}", consumerId);
        cartRepository.deleteByConsumerId(consumerId);
    }

    @Override
    public void deleteByConsumerIdAndProductName(String consumerId, String productName) {
        log.debug("Request to delete Cart consumerId and ProductName : {},{}", consumerId, productName);
        cartRepository.deleteByConsumerIdAndProductName(consumerId, productName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cart> findByConsumerIdAndProductNameAndStoreId(String consumerId, String productName, Long storeId) {
        log.debug("Request to get Cart by consumerId and product name: {} {} store Id as {}", consumerId, productName, storeId);
        return cartRepository.findByConsumerIdAndProductNameAndStoreId(consumerId, productName, storeId);
    }

    @Transactional(readOnly = true)
    public List<Cart> findByConsumerIdAndProductName(String consumerId, String productName) {
        log.debug("Request to get Cart by consumerId and product name: {} {} ", consumerId, productName);
        return cartRepository.findByConsumerIdAndProductName(consumerId, productName);
    }

    @Transactional
    public void deleteByReferenceId(String referenceId) {
        log.debug("Request to delete Cart by using referenceId: {}", referenceId);
        cartRepository.deleteByReferenceId(referenceId);
    }

    @Override
    @Transactional
    public void deleteByReferenceIdAndProductName(String referenceId, String productName) {
        log.debug("Request to delete Cart by using referenceId and productName : {},{}", referenceId, productName);
        cartRepository.deleteByReferenceIdAndProductName(referenceId, productName);
    }

    @Override
    @Transactional
    public void deleteByProductName(String productName) {
        log.debug("Request to delete Cart : {}", productName);
        cartRepository.deleteByProductName(productName);
    }

    @Transactional(readOnly = true)
    public List<Cart> findByReferenceIdAndProductName(String referenceId, String productName) {
        log.debug("Request to get Cart by reference id and product name: {} {}", referenceId, productName);
        return cartRepository.findByReferenceIdAndProductName(referenceId, productName);
    }

    @Override
    @Transactional
    public void updateQuantity(Integer quant, String consumerId, String productName, Long storeId) {
        log.debug("Request to get Cart by consumerId and product name: {} {} store Id as {}", consumerId, productName, storeId);
        cartRepository.updateQuantity(quant, consumerId, productName, storeId);
    }
    //    @Override
    //    public void deleteByConsumerIdAndProductNameAndReferenceId(String consumerId, String productName, String referenceId) {
    //
    //    }
}
