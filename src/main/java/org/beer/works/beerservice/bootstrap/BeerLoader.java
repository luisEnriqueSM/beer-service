package org.beer.works.beerservice.bootstrap;

import org.beer.works.beerservice.domain.Beer;
import org.beer.works.beerservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

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
                    .upc(33000001L)
                    .price(new BigDecimal("12.95")).build());

            beerRepository.save(Beer.builder()
                    .beerName("Corona")
                    .beerStyle("Clara")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(33000002L)
                    .price(new BigDecimal("10.40")).build());
        }
    }
}
