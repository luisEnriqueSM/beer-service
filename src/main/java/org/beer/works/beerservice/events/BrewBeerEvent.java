package org.beer.works.beerservice.events;

import lombok.NoArgsConstructor;
import org.beer.works.beerservice.web.model.BeerDto;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
