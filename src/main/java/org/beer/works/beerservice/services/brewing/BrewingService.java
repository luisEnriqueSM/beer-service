package org.beer.works.beerservice.services.brewing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.beer.works.beerservice.config.JmsConfig;
import org.beer.works.beerservice.domain.Beer;
import org.beer.works.common.events.BrewBeerEvent;
import org.beer.works.beerservice.repositories.BeerRepository;
import org.beer.works.beerservice.services.inventory.BeerInventoryService;
import org.beer.works.beerservice.web.mappers.BeerMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000) // every 5 seconds
    public void checkForLowInventory(){
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer inventoryQOH = beerInventoryService.getOnHandInventory(beer.getId());

            log.debug("Min OnHand is: " + beer.getMinOnHand());
            log.debug("Inventory is: " + inventoryQOH);

            if(beer.getMinOnHand() >= inventoryQOH){
                jmsTemplate.convertAndSend(JmsConfig.BREWERING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }
}
