package org.beer.works.beerservice.web.mappers;

import org.beer.works.beerservice.domain.Beer;
import org.beer.works.beerservice.services.inventory.BeerInventoryService;
import org.beer.works.beerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper{

    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    /*@Autowired
    public BeerMapperDecorator(BeerInventoryService beerInventoryService, BeerMapper mapper) {
        this.beerInventoryService = beerInventoryService;
        this.mapper = mapper;
    }*/

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        return mapper.beerToBeerDto(beer);
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beer) {
        BeerDto dto = mapper.beerToBeerDto(beer);
        dto.setQualityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
        return dto;
    }

    @Override
    public Beer beerDtoToBeer(BeerDto dto) {
        return mapper.beerDtoToBeer(dto);
    }
}
