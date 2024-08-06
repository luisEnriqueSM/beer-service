package org.beer.works.beerservice.services;

import org.springframework.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.beer.works.beerservice.domain.Beer;
import org.beer.works.beerservice.repositories.BeerRepository;
import org.beer.works.beerservice.web.controller.exceptions.NotFoundException;
import org.beer.works.beerservice.web.mappers.BeerMapper;
import org.beer.works.beerservice.web.model.BeerDto;
import org.beer.works.beerservice.web.model.BeerPagedList;
import org.beer.works.beerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDto getById(UUID beerId) {
        return beerMapper.beerToBeerDto(
                beerRepository.findById(beerId)
                        .orElseThrow(() -> new NotFoundException()));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(() -> new NotFoundException());

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest) {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if(!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle.toString())){
            // search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if(!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle.toString())){
            // search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if(StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
            // search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        beerPagedList = new BeerPagedList(beerPage
                .getContent()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(beerPage.getPageable().getPageNumber(),
                                beerPage.getPageable().getPageSize()),
                                beerPage.getTotalPages());

        return beerPagedList;
    }
}
