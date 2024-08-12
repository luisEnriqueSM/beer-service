package org.beer.works.beerservice.services.brewing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.beer.works.beerservice.config.JmsConfig;
import org.beer.works.beerservice.domain.Beer;
import org.beer.works.beerservice.events.BrewBeerEvent;
import org.beer.works.beerservice.events.NewInventoryEvent;
import org.beer.works.beerservice.repositories.BeerRepository;
import org.beer.works.beerservice.web.model.BeerDto;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWERING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event){
        BeerDto beerDto = event.getBeerDto();

        Beer beer = beerRepository.getReferenceById(beerDto.getId());

        beerDto.setQualityOnHand(beer.getQuantityToBrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        log.info("Brewed beer " + beer.getMinOnHand() + " : QOH: " + beerDto.getQualityOnHand());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
