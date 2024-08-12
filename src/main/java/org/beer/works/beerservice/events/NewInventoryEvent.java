package org.beer.works.beerservice.events;

import lombok.NoArgsConstructor;
import org.beer.works.beerservice.web.model.BeerDto;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent{

    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
