package com.infy.bppondc.web.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SelectController {

    @PostMapping("/1001")
    public void bpp1(@RequestBody String product) {
        System.out.println("******BPP id 1 is selected for product " + product);
    }

    @PostMapping("/1002")
    public void bpp2(@RequestBody String product) {
        System.out.println("******BPP 2 is selected for product " + product);
    }
}
