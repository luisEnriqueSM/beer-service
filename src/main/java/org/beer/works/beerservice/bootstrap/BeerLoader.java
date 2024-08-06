package org.beer.works.beerservice.bootstrap;

import org.beer.works.beerservice.domain.Beer;
import org.beer.works.beerservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

//@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    public static final UUID BEER_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if(beerRepository.count() == 0){
            beerRepository.save(Beer.builder()
                    .beerName("Dos XX")
                    .beerStyle("Lager")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal("12.95")).build());

            beerRepository.save(Beer.builder()
                    .beerName("Corona")
                    .beerStyle("Clara")
                    .quantityToBrew(350)
                    .minOnHand(12)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("10.40")).build());

            beerRepository.save(Beer.builder()
                    .beerName("Modelo")
                    .beerStyle("Clara")
                    .quantityToBrew(400)
                    .minOnHand(12)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal("17.90")).build());
        }
    }
}
