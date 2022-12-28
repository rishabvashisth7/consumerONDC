package com.infy.bppondc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infy.bppondc.service.dto.Catalog;
import com.infy.bppondc.service.dto.Item;
import com.infy.bppondc.service.dto.ProductDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer1 {

    private final ProductService productService;

    @Autowired
    KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    private static final String TOPIC = "newtopicresponse";

    public KafkaConsumer1(ProductService productService) {
        this.productService = productService;
    }

    @KafkaListener(topics = "publishtopic", groupId = "group_id1")
    public void consumer1(Map<String, Object> message) {
        System.out.println("message :" + message);
        String name = message.get("productName").toString();
        String id = message.get("productId").toString();

        System.out.println("Message from Producer to consumer1 !!!!!= " + message);

        List<ProductDTO> productDTO = productService.findAll();
        int flag = 0;
        for (ProductDTO dto : productDTO) {
            //            System.out.println(" inside for loop :");
            Map<String, String> map = new HashMap<>();
            if (dto.getTitle().equals(name) && (dto.getStore().getId() == 1001)) {
                //                System.out.println(
                //                    "The item requested by BAP : " +
                //                        name +
                //                        " is present here in BPP 1. Sending the Items back to the BAP in catalog."
                //                );
                map.put("ProductName", dto.getTitle());

                Item itm = new Item("1001", "Consumer 1 Replied :" + name);

                List lst = new ArrayList<>();
                lst.add(itm);
                Catalog catalog = new Catalog(id, lst);

                ObjectMapper obj1 = new ObjectMapper();
                Map<String, Object> productMap = obj1.convertValue(catalog, Map.class);
                System.out.println("obj :" + productMap);
                kafkaTemplate.send(TOPIC, productMap);

                flag = 1;
            }
        }
        if (flag == 0) {
            System.out.println(
                "The item requested by BAP :" + name + "is not present here in BPP 1. Sending the Items back to the BAP in catalog."
            );
        }
    }
}
