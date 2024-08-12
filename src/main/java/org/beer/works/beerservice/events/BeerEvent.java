package org.beer.works.beerservice.events;

import lombok.*;
import org.beer.works.beerservice.web.model.BeerDto;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 2313637709394826515L;

    private BeerDto beerDto;
}
