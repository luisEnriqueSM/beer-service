package org.beer.works.beerservice.web.mappers;

import org.beer.works.beerservice.domain.Beer;
import org.beer.works.beerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto dto);
}
