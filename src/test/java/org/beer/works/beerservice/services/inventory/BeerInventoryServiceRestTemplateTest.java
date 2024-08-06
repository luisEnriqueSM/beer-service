package org.beer.works.beerservice.services.inventory;

import org.beer.works.beerservice.bootstrap.BeerLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BeerInventoryServiceRestTemplateTest {

    @Autowired
    BeerInventoryService beerInventoryService;

    @BeforeEach
    void setUp(){

    }

    @Test
    void getOnHandInventory(){
        Integer quantityOnHand = beerInventoryService.getOnHandInventory(BeerLoader.BEER_1_UUID);

        System.out.println(quantityOnHand);
    }
}
