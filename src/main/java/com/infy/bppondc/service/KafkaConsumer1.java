package com.infy.bppondc.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.infy.bppondc.service.dto.CartDTO;
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
    KafkaTemplate<String, Catalog> kafkaTemplate;

    private static final String TOPIC = "newtopicresponse";

    public KafkaConsumer1(ProductService productService) {
        this.productService = productService;
    }

    @KafkaListener(topics = "publishtopic", groupId = "group_id1")
    public void consumer1(String message) {
        JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
        String name = String.valueOf(jsonObject.get("productName"));
        String id = jsonObject.get("productId").toString();

        System.out.println("Message from Producer to consumer1 !!!!!= " + message);

        String resultProduct = name.substring(1, name.length() - 1);

        List<ProductDTO> productDTO = productService.findAll();

        for (int i = 0; i < productDTO.size(); i++) {
            Map<String, String> map = new HashMap<>();
            if (productDTO.get(i).getTitle().equals(resultProduct) && (productDTO.get(i).getStore().getId() == 1001)) {
                System.out.println(
                    "The item requested by BAP : " +
                    resultProduct +
                    " is present here in BPP 1. Sending the Items back to the BAP in catalog."
                );
                map.put("ProductName", productDTO.get(i).getTitle());

                Item itm = new Item("1001", "Consumer 1 Replied :" + resultProduct);

                List lst = new ArrayList<>();
                lst.add(itm);
                Catalog obj = new Catalog(id, lst);
                kafkaTemplate.send(TOPIC, obj);
            } else {
                // System.out.println("The item requested by BAP :" + resultProduct + " is not present here in BPP 1");
            }
        }
        //
        //        Map<String, String> map = Map.of("100", "Milk", "102", "Eggs", "101", "Bread");
        //
        //
        //        if (map.containsValue(resultProduct)) {
        //            System.out.println(
        //                "The item requested by BAP : " + resultProduct + " is present here in BPP 1. Sending the Items back to the BAP in catalog."
        //            );
        //
        //            Item itm = new Item("1001", "Consumer 1 Replied :" + resultProduct);
        //
        //            List lst = new ArrayList<>();
        //            lst.add(itm);
        //            Catalog obj = new Catalog(id, lst);
        //            kafkaTemplate.send(TOPIC, obj);
        //        } else {
        //            System.out.println("The item requested by BAP :" + resultProduct + " is not present here in BPP 1");
        //        }
        //

    }
}
