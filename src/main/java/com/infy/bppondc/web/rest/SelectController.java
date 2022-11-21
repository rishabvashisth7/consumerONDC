package com.infy.bppondc.web.rest;

import com.infy.bppondc.repository.ProductRepository;
import com.infy.bppondc.repository.StoreRepository;
import com.infy.bppondc.service.CartService;
import com.infy.bppondc.service.ProductService;
import com.infy.bppondc.service.StoreService;
import com.infy.bppondc.service.dto.CartDTO;
import com.infy.bppondc.service.dto.ProductDTO;
import com.infy.bppondc.service.dto.StoreDTO;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SelectController {

    Map<String, Map<String, String>> cartBPP1 = new LinkedHashMap<>();
    Map<String, Map<String, String>> cartBPP2 = new LinkedHashMap<>();

    private final StoreService storeService;

    private final CartService cartService;

    private final ProductService productService;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    ProductRepository productRepository;

    public SelectController(StoreService storeService, CartService cartService, ProductService productService) {
        this.storeService = storeService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping("/1001")
    public String bpp1(@RequestBody String product) {
        CartDTO cartDTO = new CartDTO();

        System.out.println("FINDALLL  ::" + storeRepository.findAll());

        Optional<StoreDTO> storeDTO = storeService.findOne(Long.parseLong("1001"));

        System.out.println("STOREDTO ))))))--------" + storeDTO);

        System.out.println("******BPP id 1 is selected for product " + product);

        List l = List.of(product.substring(1, product.length() - 1).split(","));

        List<ProductDTO> productDTOS = productService.findAll();

        int flag = 0;

        for (int i = 0; i < productDTOS.size(); ++i) {
            ProductDTO prod = productDTOS.get(i);
            String name = l.get(2).toString().substring(1, l.get(2).toString().length() - 1);
            if (prod.getTitle().equals(name) && prod.getStore().getId() == 1001) {
                cartDTO.setReferenceId(l.get(0).toString().substring(1, l.get(0).toString().length() - 1));
                cartDTO.setQuantity(Integer.parseInt(l.get(1).toString().substring(1, l.get(1).toString().length() - 1)));
                cartDTO.setProductName(l.get(2).toString().substring(1, l.get(2).toString().length() - 1));
                cartDTO.setStore(storeDTO.get());
                cartDTO.setPrice(prod.getPrice().toString());
                cartService.save(cartDTO);
                flag = 1;
                break;
            }
        }

        System.out.println("CART BPP1 Details :" + cartBPP1);

        if (flag == 1) {
            return "Items are selected in Store 1";
        } else {
            return "Item is not present in Store 1";
        }
    }

    @PostMapping("/1002")
    public String bpp2(@RequestBody String product) {
        CartDTO cartDTO = new CartDTO();

        // storeRepository.findById(1002L);
        System.out.println("FINDALLL  ::" + storeRepository.findAll());
        // storeDTO.setId(Long.parseLong("1002"));

        Optional<StoreDTO> storeDTO = storeService.findOne(Long.parseLong("1002"));
        //  storeDTO.get();

        System.out.println("STOREDTO --------" + storeDTO);

        System.out.println("****** BPP id 1 is selected for product " + product);

        List l = List.of(product.substring(1, product.length() - 1).split(","));

        //cartDTO.setPrice("50");

        List<ProductDTO> productDTOS = productService.findAll();

        int flag = 0;

        for (int i = 0; i < productDTOS.size(); ++i) {
            ProductDTO prod = productDTOS.get(i);
            String name = l.get(2).toString().substring(1, l.get(2).toString().length() - 1);
            if (prod.getTitle().equals(name) && prod.getStore().getId() == 1002) {
                cartDTO.setReferenceId(l.get(0).toString().substring(1, l.get(0).toString().length() - 1));

                cartDTO.setQuantity(Integer.parseInt(l.get(1).toString().substring(1, l.get(1).toString().length() - 1)));
                cartDTO.setProductName(l.get(2).toString().substring(1, l.get(2).toString().length() - 1));
                cartDTO.setStore(storeDTO.get());
                cartDTO.setPrice(prod.getPrice().toString());
                cartService.save(cartDTO);
                flag = 1;
                break;
            }
        }

        System.out.println("CART BPP1 Details :" + cartBPP2);

        if (flag == 1) {
            return "Items are selected in Store 2";
        } else {
            return "Item is not present in Store 2";
        }
    }

    @RequestMapping("/total/{referenceid}")
    public Double totalPrice(@PathVariable String referenceid) {
        List<CartDTO> cartDTO = cartService.findAll();
        System.out.println(" CartDTO :" + cartDTO);
        System.out.println("reference" + referenceid);
        double sum = 0;
        for (int i = 0; i < cartDTO.size(); i++) {
            if (cartDTO.get(i).getReferenceId().equals(referenceid)) {
                int quan = cartDTO.get(i).getQuantity();
                double price = Double.parseDouble(cartDTO.get(i).getPrice());
                sum += quan * price;
            }
        }
        return sum;
    }

    @RequestMapping("/showCart/{referenceid}")
    public List<Map<String, String>> showCart(@PathVariable String referenceid) {
        List<CartDTO> cartDTO = cartService.findAll();
        List<Map<String, String>> cartRef = new ArrayList<>();
        System.out.println(" CartDTO :" + cartDTO);
        System.out.println("reference" + referenceid);

        for (int i = 0; i < cartDTO.size(); i++) {
            Map<String, String> map = new HashMap<>();
            if (cartDTO.get(i).getReferenceId().equals(referenceid)) {
                map.put("ProductName", cartDTO.get(i).getProductName());
                map.put("Price", cartDTO.get(i).getPrice());
                map.put("Quant", cartDTO.get(i).getQuantity().toString());
                cartRef.add(map);
            }
        }
        System.out.println(cartRef);
        return cartRef;
    }

    //  @DeleteMapping("/delete/{referenceid")
    @RequestMapping(value = "/delbyref/{referenceid}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteByReferenceId(@PathVariable String referenceid) {
        List<CartDTO> cartDTO = cartService.findAll();
        List<Map<String, String>> cartRef = new ArrayList<>();
        System.out.println(" CartDTO :" + cartDTO);
        System.out.println("reference" + referenceid);

        for (int i = 0; i < cartDTO.size(); i++) {
            if (cartDTO.get(i).getReferenceId().equals(referenceid)) {
                cartService.deleteByReferenceId(referenceid);
            }
        }
        System.out.println("deleted");
        return ResponseEntity.ok("Items with given reference id is successfully deleted from the cart!!!");
    }

    @RequestMapping(value = "/delproductName/{productName}", method = RequestMethod.DELETE)
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

    @RequestMapping(value = "/delRefProd/{referenceid}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteByReferenceIdAndProductName(@PathVariable String referenceid, @RequestBody String productName) {
        List<CartDTO> cartDTO = cartService.findAll();
        //List<Map<String, String>> cartRef = new ArrayList<>();
        System.out.println("CartDTO :" + cartDTO);
        System.out.println("productName" + productName);
        int flag = 0;
        for (int i = 0; i < cartDTO.size(); i++) {
            if ((cartDTO.get(i).getProductName().equals(productName)) && (cartDTO.get(i).getReferenceId().equals(referenceid))) {
                cartService.deleteByReferenceIdAndProductName(referenceid, productName);
                flag = 1;
            }
        }

        if (flag == 1) {
            return ResponseEntity.ok("Items with given reference id & productName is present and successfully deleted from the cart!!!");
        } else {
            return ResponseEntity.ok("Items with given reference id & productName is not present in cart!!");
        }
    }
}
