package com.infy.bppondc.web.rest;

import com.infy.bppondc.domain.Cart;
import com.infy.bppondc.service.CartService;
import com.infy.bppondc.service.ProductService;
import com.infy.bppondc.service.StoreService;
import com.infy.bppondc.service.dto.CartDTO;
import com.infy.bppondc.service.dto.ProductDTO;
import com.infy.bppondc.service.dto.StoreDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SelectController {

    private final StoreService storeService;

    private final CartService cartService;

    private final ProductService productService;

    public SelectController(StoreService storeService, CartService cartService, ProductService productService) {
        this.storeService = storeService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @Operation(description = "Select products from BPP 1 in the cart")
    @PostMapping("/1001")
    public String bpp1(@RequestBody String product) {
        CartDTO cartDTO = new CartDTO();
        List<CartDTO> cart = cartService.findAll();
        Optional<StoreDTO> storeDTO = storeService.findOne(Long.parseLong("1001"));

        List l = List.of(product.substring(1, product.length() - 1).split(","));
        System.out.println("-------" + l);

        List<ProductDTO> productDTOS = productService.findAll();
        int flag = 0;

        for (ProductDTO prod : productDTOS) {
            String refId = l.get(0).toString().substring(1, l.get(0).toString().length() - 1);
            Integer quant = Integer.valueOf(l.get(1).toString().substring(1, l.get(1).toString().length() - 1));
            String name = l.get(2).toString().substring(1, l.get(2).toString().length() - 1);
            Long storeId = Long.valueOf((l.get(3).toString().substring(1, l.get(3).toString().length() - 1)));
            String customer = l.get(4).toString().substring(1, l.get(4).toString().length() - 1);
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

            List<Cart> cartfind = cartService.findByConsumerIdAndProductNameAndStoreId(customer, name, storeId);
            if (prod.getTitle().equals(name) && prod.getStore().getId() == 1001 && cartfind.size() == 0) {
                cartDTO.setReferenceId(refId);
                cartDTO.setQuantity(quant);
                cartDTO.setProductName(name);
                cartDTO.setStore(storeDTO.get());
                System.out.println("storeDTO.get() 1001 if-------------------->>>>>>>>" + storeDTO.get());
                cartDTO.setPrice(prod.getPrice().toString());
                cartDTO.setConsumerId(customer);
                cartService.save(cartDTO);
                flag = 1;
                break;
            } else if (prod.getTitle().equals(name) && prod.getStore().getId() == 1001 && cartfind.size() != 0) {
                System.out.println("inside else ifcartByConsumer.get(4).toString() ------------ " + cartfind.get(0).getQuantity());
                int total = cartfind.get(0).getQuantity() + quant;
                cartService.updateQuantity(total, customer, name, storeId);
                flag = 1;
                break;
            }
        }

        if (flag == 1) {
            return "Items are selected in Store 1";
        } else {
            return "Item is not present in Store 1";
        }
    }

    @Operation(description = "Select products from BPP 2 in the cart")
    @PostMapping("/1002")
    public String bpp2(@RequestBody String product) {
        CartDTO cartDTO = new CartDTO();
        Optional<StoreDTO> storeDTO = storeService.findOne(Long.parseLong("1002"));

        System.out.println("STOREDTO --------" + storeDTO);

        System.out.println("****** BPP id 2 is selected for product " + product);

        List l = List.of(product.substring(1, product.length() - 1).split(","));

        List<ProductDTO> productDTOS = productService.findAll();

        int flag = 0;

        for (ProductDTO prod : productDTOS) {
            String name = l.get(2).toString().substring(1, l.get(2).toString().length() - 1);
            String refId = l.get(0).toString().substring(1, l.get(0).toString().length() - 1);
            Integer quant = Integer.valueOf(l.get(1).toString().substring(1, l.get(1).toString().length() - 1));
            Long storeId = Long.valueOf(l.get(3).toString().substring(1, l.get(3).toString().length() - 1));
            String customer = l.get(4).toString().substring(1, l.get(4).toString().length() - 1);

            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println(
                "cartService.findByReferenceIdAndProductName(refId,name) ::::: - " +
                cartService.findByReferenceIdAndProductName(refId, name)
            );

            List<Cart> cartfind = cartService.findByConsumerIdAndProductNameAndStoreId(customer, name, storeId);
            if (prod.getTitle().equals(name) && prod.getStore().getId() == 1002 && cartfind.size() == 0) {
                cartDTO.setReferenceId(refId);
                cartDTO.setQuantity(quant);
                cartDTO.setProductName(name);
                cartDTO.setStore(storeDTO.get());
                System.out.println("storeDTO.get() 1002 if  -------------------->>>>>>>>" + storeId);
                cartDTO.setPrice(prod.getPrice().toString());
                cartDTO.setConsumerId(customer);
                cartService.save(cartDTO);
                flag = 1;
                break;
            } else if (prod.getTitle().equals(name) && prod.getStore().getId() == 1002 && cartfind.size() != 0) {
                System.out.println("cartByConsumer.get(4).toString() ------------ " + cartfind.get(0).getQuantity());
                int total = cartfind.get(0).getQuantity() + quant;
                cartService.updateQuantity(total, customer, name, storeId);
                flag = 1;
                break;
            }
        }

        if (flag == 1) {
            return "Items are selected in Store 2";
        } else {
            return "Item is not present in Store 2";
        }
    }

    @Operation(description = "total price of consumerId")
    @PostMapping("/totalPriceByConsumerId/{consumerId}")
    public Double totalPriceByConsumerId(@PathVariable String consumerId) {
        List<CartDTO> cartDTO = cartService.findAll();
        System.out.println(" CartDTO :" + cartDTO);
        System.out.println("consumerId" + consumerId);
        double sum = 0;
        for (int i = 0; i < cartDTO.size(); i++) {
            if (cartDTO.get(i).getConsumerId().equals(consumerId)) {
                int quan = cartDTO.get(i).getQuantity();
                double price = Double.parseDouble(cartDTO.get(i).getPrice());
                sum += quan * price;
            }
        }
        return sum;
    }

    @Operation(description = "total price using consumerId and storeId")
    @PostMapping("/totalPriceByConsumerIdAndStoreId/{consumerId}/{storeId}")
    public Double totalPriceByConsumerIdAndStoreId(
        @PathVariable(value = "consumerId") String consumerId,
        @PathVariable(value = "storeId") Long storeId
    ) {
        List<CartDTO> cartDTO = cartService.findAll();
        System.out.println(" CartDTO :" + cartDTO);
        System.out.println("consumerId" + consumerId);
        double sum = 0;
        for (int i = 0; i < cartDTO.size(); i++) {
            if ((cartDTO.get(i).getConsumerId().equals(consumerId)) && (cartDTO.get(i).getStore().getId().equals(storeId))) {
                int quan = cartDTO.get(i).getQuantity();
                double price = Double.parseDouble(cartDTO.get(i).getPrice());
                sum += quan * price;
            }
        }
        return sum;
    }

    @Operation(description = "Get List of products using consumerId")
    @GetMapping("/showCartByConsumerId/{consumerId}")
    public List<Map<String, String>> showCartByConsumerID(@PathVariable String consumerId) {
        List<CartDTO> cartDTO = cartService.findAll();
        List<Map<String, String>> cartRef = new ArrayList<>();
        System.out.println(" CartDTO :" + cartDTO);
        System.out.println("consumerId  :" + consumerId);

        for (int i = 0; i < cartDTO.size(); i++) {
            Map<String, String> map = new HashMap<>();
            if (cartDTO.get(i).getConsumerId().equals(consumerId)) {
                map.put("ProductName", cartDTO.get(i).getProductName());
                map.put("Price", cartDTO.get(i).getPrice());
                map.put("Quant", cartDTO.get(i).getQuantity().toString());
                map.put("StoreId", cartDTO.get(i).getStore().getId().toString());
                cartRef.add(map);
            }
        }
        System.out.println(cartRef);
        return cartRef;
    }

    // TODO 1st API

    @Operation(description = "Delete a product from cart using productName")
    @DeleteMapping(value = "/delProductName/{productName}")
    public ResponseEntity<String> deleteByProductName(@PathVariable String productName) {
        List<CartDTO> cartDTO = cartService.findAll();
        List<Map<String, String>> cartRef = new ArrayList<>();
        System.out.println("CartDTO :" + cartDTO);
        System.out.println("productName" + productName);

        for (int i = 0; i < cartDTO.size(); i++) {
            if (cartDTO.get(i).getProductName().equals(productName)) {
                cartService.deleteByProductName(productName);
            }
        }
        //   System.out.println("deleted using reference id and product name in the cart,");
        return ResponseEntity.ok("Items with given productName is successfully deleted from the cart!!!");
    }

    // TODO 2nd API

    @Operation(description = "Delete a product from cart using consumerId and productName")
    @DeleteMapping(value = "/delConsumerProd/{consumerId}")
    public ResponseEntity<String> deleteByConsumerIdAndProductName(@PathVariable String consumerId, @RequestBody String productName) {
        List<CartDTO> cartDTO = cartService.findAll();
        System.out.println("CartDTO :" + cartDTO);
        System.out.println("productName" + productName);
        int flag = 0;
        for (int i = 0; i < cartDTO.size(); i++) {
            if ((cartDTO.get(i).getProductName().equals(productName)) && (cartDTO.get(i).getConsumerId().equals(consumerId))) {
                cartService.deleteByConsumerIdAndProductName(consumerId, productName);
                flag = 1;
            }
        }

        if (flag == 1) {
            return ResponseEntity.ok("Items with given Consumer Id id & productName is present and successfully deleted from the cart!!!");
        } else {
            return ResponseEntity.ok("Items with given Consumer Id & productName is not present in cart!!");
        }
    }

    //TODO 3rd API

    @Operation(description = "Get List of products using consumerId and productName and storeId")
    @GetMapping(value = "showByConsumerProductStoreId/{consumerId}")
    public List<Map<String, String>> showByConsumerProductStoreId(
        @PathVariable(value = "consumerId") String consumerId,
        @RequestBody String productName,
        @RequestBody Long storeId
    ) {
        List<Cart> cart = cartService.findByConsumerIdAndProductNameAndStoreId(consumerId, productName, storeId);
        List<Map<String, String>> cartRef = new ArrayList<>();
        System.out.println("CartDTO :" + cart);
        System.out.println("productName" + productName);

        for (int i = 0; i < cart.size(); i++) {
            Map<String, String> map = new HashMap<>();
            if (
                (cart.get(i).getProductName().equals(productName)) &&
                (cart.get(i).getConsumerId().equals(consumerId)) &&
                (cart.get(i).getStore().getId().equals(storeId))
            ) {
                map.put("ProductName", cart.get(i).getProductName());
                map.put("Price", cart.get(i).getPrice());
                map.put("Quant", cart.get(i).getQuantity().toString());

                cartRef.add(map);
            }
        }
        System.out.println(cartRef);
        return cartRef;
    }
}
