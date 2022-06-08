package com.capgemini.bakery.taxonomy.shop.service;


import com.capgemini.bakery.exception.ResourceNotFoundException;
import com.capgemini.bakery.taxonomy.shop.model.dto.ShopDto;
import com.capgemini.bakery.taxonomy.shop.model.mapper.ShopMapper;
import com.capgemini.bakery.taxonomy.shop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public ShopDto add(ShopDto shopDto) {
        return ShopMapper.shopToShopDto(shopRepository.save(ShopMapper.shopDtoToShop(shopDto)));
    }

    public List<ShopDto> getAll() {
        return shopRepository.findAll().stream().map(ShopMapper::shopToShopDto).collect(Collectors.toList());
    }

    public ShopDto getById(Long id) {

        return ShopMapper.shopToShopDto(shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Shop with id = " + id)));
    }

    public ShopDto deleteById(Long id) {
        ShopDto shopDtoToBeDeleted = ShopMapper.shopToShopDto(shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Shop with id = " + id)));
        shopRepository.deleteById(id);
        return shopDtoToBeDeleted;
    }

    public ShopDto updateById(Long id, ShopDto shopDto) {

        if (shopRepository.findById(id).isPresent()) {
            return add(ShopDto.builder()
                    .id(id)
                    .division(shopDto.getDivision())
                    .region(shopDto.getRegion())
                    .area(shopDto.getArea())
                    .build());
        } else {
            throw new ResourceNotFoundException("Not found Shop with id = " + id);
        }
    }
}