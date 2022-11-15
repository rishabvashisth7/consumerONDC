package com.infy.bppondc.web.rest;

import com.infy.bppondc.repository.ProductRepository;
import com.infy.bppondc.repository.StoreRepository;
import com.infy.bppondc.service.CartService;
import com.infy.bppondc.service.ProductService;
import com.infy.bppondc.service.StoreService;
import com.infy.bppondc.service.dto.CartDTO;
import com.infy.bppondc.service.dto.ProductDTO;
import com.infy.bppondc.service.dto.StoreDTO;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        cartDTO.setReferenceId(l.get(0).toString().substring(1, l.get(0).toString().length() - 1));

        //cartDTO.setQuantity(Integer.parseInt(l.get(1).toString()));
        //        System.out.println(" l____________________________ : " + l);
        //        System.out.println(" productName :" + l.get(2).toString().substring(1, l.get(2).toString().length() - 1));
        //        System.out.println("l.get(1).toString()  --------------->>" + l.get(1).toString().substring(1, l.get(1).toString().length() - 1));

        cartDTO.setQuantity(Integer.parseInt(l.get(1).toString().substring(1, l.get(1).toString().length() - 1)));
        cartDTO.setProductName(l.get(2).toString().substring(1, l.get(2).toString().length() - 1));
        cartDTO.setStore(storeDTO.get());
        //cartDTO.setPrice("50");

        List<ProductDTO> productDTOS = productService.findAll();
        for (int i = 0; i < productDTOS.size(); ++i) {
            ProductDTO prod = productDTOS.get(i);
            String name = l.get(2).toString().substring(1, l.get(2).toString().length() - 1);
            if (prod.getTitle().equals(name) && prod.getStore().getId() == 1001) {
                cartDTO.setPrice(prod.getPrice().toString());
                break;
            }
        }

        String productName = l.get(2).toString().substring(1, l.get(2).toString().length() - 1);
        Long StoreID = storeDTO.get().getId();

        //  productService.findByTitle(productName);

        //System.out.println("   productService.findByTitle(productName)    ::: " + productService.findByTitle(productName));

        cartService.save(cartDTO);
        System.out.println("CARTDTO :----------->>" + cartDTO);

        System.out.println("CART BPP1 Details :" + cartBPP1);
    }

    @PostMapping("/1002")
    public void bpp2(@RequestBody String product) {
        CartDTO cartDTO = new CartDTO();

        // storeRepository.findById(1002L);
        System.out.println("FINDALLL  ::" + storeRepository.findAll());
        // storeDTO.setId(Long.parseLong("1002"));

        Optional<StoreDTO> storeDTO = storeService.findOne(Long.parseLong("1002"));
        //  storeDTO.get();

        System.out.println("STOREDTO ))))))--------" + storeDTO);
        //  System.out.println("StoreDTO (((((())))))))))))))" + storeRepository.findById(Long.parseLong("1002")));

        System.out.println("******BPP id 1 is selected for product " + product);

        List l = List.of(product.substring(1, product.length() - 1).split(","));

        cartDTO.setReferenceId(l.get(0).toString().substring(1, l.get(0).toString().length() - 1));

        cartDTO.setQuantity(Integer.parseInt(l.get(1).toString().substring(1, l.get(1).toString().length() - 1)));
        cartDTO.setProductName(l.get(2).toString().substring(1, l.get(2).toString().length() - 1));
        cartDTO.setStore(storeDTO.get());
        //cartDTO.setPrice("50");

        List<ProductDTO> productDTOS = productService.findAll();
        for (int i = 0; i < productDTOS.size(); ++i) {
            ProductDTO prod = productDTOS.get(i);
            String name = l.get(2).toString().substring(1, l.get(2).toString().length() - 1);
            if (prod.getTitle().equals(name) && prod.getStore().getId() == 1002) {
                cartDTO.setPrice(prod.getPrice().toString());
                break;
            }
        }

        String productName = l.get(2).toString().substring(1, l.get(2).toString().length() - 1);
        Long StoreID = storeDTO.get().getId();

        cartService.save(cartDTO);
        System.out.println("CARTDTO :----------->>" + cartDTO);

        System.out.println("CART BPP1 Details :" + cartBPP2);
    }

    @RequestMapping("/total")
    public Double totalPrice(@RequestBody String refrenceid) {
        List<CartDTO> cartDTO = cartService.findAll();
        System.out.println(" CartDTO :" + cartDTO);
        double sum = 0;
        for (int i = 0; i < cartDTO.size(); i++) {
            if (cartDTO.get(i).getReferenceId().equals(refrenceid)) {
                int quan = cartDTO.get(i).getQuantity();
                double price = Double.parseDouble(cartDTO.get(i).getPrice());
                sum += quan * price;
            }
        }

        return sum;
    }
}
