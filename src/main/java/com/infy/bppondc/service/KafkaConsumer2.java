package com.infy.bppondc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
public class KafkaConsumer2 {

    private final ProductService productService;

    @Autowired
    KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    private static final String TOPIC = "newtopicresponse";

    public KafkaConsumer2(ProductService productService) {
        this.productService = productService;
    }

    @KafkaListener(topics = "publishtopic", groupId = "group_id2")
    public void consumer1(Map<String, Object> message) {
        System.out.println("Message from Producer to consumer2 @@@@@= " + message);

        //        JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
        //        String name = String.valueOf(jsonObject.get("productName"));
        //        String id = jsonObject.get("productId").toString();

        System.out.println("message :" + message);
        String name = message.get("productName").toString();
        String id = message.get("productId").toString();

        System.out.println(" name :" + name);
        System.out.println("id :" + id);
        String resultProduct = name;

        List<ProductDTO> productDTO = productService.findAll();

        int flag = 0;
        for (ProductDTO dto : productDTO) {
            Map<String, String> map = new HashMap<>();
            if (dto.getTitle().equals(resultProduct) && (dto.getStore().getId() == 1002)) {
                //                System.out.println(
                //                    "The item requested by BAP : " +
                //                        resultProduct +
                //                        " is present here in BPP 2. Sending the Items back to the BAP in catalog."
                //                );
                map.put("ProductName", dto.getTitle());

                Item itm = new Item("1002", "Consumer 2 Replied :" + resultProduct);

                List lst = new ArrayList<>();
                lst.add(itm);
                Catalog catalog = new Catalog(id, lst);

                ObjectMapper obj1 = new ObjectMapper();
                Map<String, Object> productMap = obj1.convertValue(catalog, Map.class);
                System.out.println("obj :" + productMap);
                kafkaTemplate.send(TOPIC, productMap);
            }
        }

        if (flag == 0) {
            System.out.println(
                "The item requested by BAP :" + name + "is not present here in BPP 2. Sending the Items back to the BAP in catalog."
            );
        }
    }
}
