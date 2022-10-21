package com.infy.bppondc.web.rest;

import com.infy.bppondc.repository.StoreRepository;
import com.infy.bppondc.service.CartService;
import com.infy.bppondc.service.StoreService;
import com.infy.bppondc.service.dto.CartDTO;
import com.infy.bppondc.service.dto.StoreDTO;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SelectController {

    Map<String, Map<String, String>> cartBPP1 = new LinkedHashMap<>();
    Map<String, Map<String, String>> cartBPP2 = new LinkedHashMap<>();

    private final StoreService storeService;

    private final CartService cartService;

    @Autowired
    StoreRepository storeRepository;

    public SelectController(StoreService storeService, CartService cartService) {
        this.storeService = storeService;
        this.cartService = cartService;
    }

    @PostMapping("/1001")
    public void bpp1(@RequestBody String product) {
        CartDTO cartDTO = new CartDTO();

        // storeRepository.findById(1001L);
        System.out.println("FINDALLL  ::" + storeRepository.findAll());
        // storeDTO.setId(Long.parseLong("1001"));

        Optional<StoreDTO> storeDTO = storeService.findOne(Long.parseLong("1001"));
        //  storeDTO.get();

        System.out.println("STOREDTO ))))))--------" + storeDTO);
        //  System.out.println("StoreDTO (((((())))))))))))))" + storeRepository.findById(Long.parseLong("1001")));

        System.out.println("******BPP id 1 is selected for product " + product);

        List l = List.of(product.substring(1, product.length() - 1).split(","));

        cartDTO.setReferenceId(l.get(0).toString());
        //cartDTO.setQuantity(Integer.parseInt(l.get(1).toString()));
        System.out.println("l.get(1).toString()  --------------->>" + l.get(1).toString().substring(1, l.get(1).toString().length() - 1));
        cartDTO.setQuantity(Integer.parseInt(l.get(1).toString().substring(1, l.get(1).toString().length() - 1)));
        cartDTO.setProductName(l.get(2).toString().substring(1, l.get(1).toString().length() - 1));
        cartDTO.setStore(storeDTO.get());
        cartDTO.setPrice("50");

        cartService.save(cartDTO);
        System.out.println("CARTDTO :----------->>" + cartDTO);
        //        System.out.println(" referenceid, quantity, prodname" + l.get(0));
        //        System.out.println(" LIST Of product details -- referenceid, quantity, prodname" + l);
        //
        //        String productName = l.get(2).toString().substring(1,l.get(2).toString().length() - 1);
        //
        //        Map<String, String> prodDetails  = new LinkedHashMap<>();
        //        prodDetails.put("productName", l.get(2).toString());
        //        prodDetails.put("quantity", l.get(1).toString());
        //
        //        Map <String , String> priceList = Map.of("Milk", "50", "Bread", "40", "Eggs" ,"90");
        //
        //        System.out.println("productName  : " + productName);
        //        if(priceList.containsKey(productName)){
        //            prodDetails.put("price", priceList.get(productName));
        //        }
        //
        //        cartBPP1.put(l.get(0).toString(), prodDetails);

        //  System.out.println("CART BPP1 Details :" + cartBPP1);
    }

    @PostMapping("/1002")
    public void bpp2(@RequestBody String product) {
        System.out.println("******BPP 2 is selected for product " + product);
        List l = List.of(product.substring(1, product.length() - 1).split(","));
        System.out.println(" referenceid, quantity, prodname" + l.get(0));
        System.out.println(" LIST Of product details -- referenceid, quantity, prodname" + l);

        String productName = l.get(2).toString().substring(1, l.get(2).toString().length() - 1);

        Map<String, String> prodDetails = new LinkedHashMap<>();
        prodDetails.put("productName", l.get(2).toString());
        prodDetails.put("quantity", l.get(1).toString());

        Map<String, String> priceList = Map.of("Milk", "50", "Bread", "40", "Eggs", "90", "Yogurt", "60");

        System.out.println("productName  : " + productName);
        if (priceList.containsKey(productName)) {
            prodDetails.put("price", priceList.get(productName));
        }

        cartBPP2.put(l.get(0).toString(), prodDetails);

        System.out.println("CART BPP1 Details :" + cartBPP2);
    }
}
