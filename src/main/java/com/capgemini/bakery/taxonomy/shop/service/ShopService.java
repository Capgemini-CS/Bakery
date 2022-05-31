package com.capgemini.bakery.taxonomy.shop.service;


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
        return ShopMapper.shopToShopDto(shopRepository.getReferenceById(id));
    }

    public ShopDto deleteById(Long id) {
        ShopDto shopDtoToBeDeleted = getById(id);
        shopRepository.deleteById(id);
        return shopDtoToBeDeleted;
    }

    public ShopDto updateById(Long id, ShopDto shopDto) {
        ShopDto shopDtoToBeUpdated = new ShopDto();

        if (shopRepository.findById(id).isPresent()) {
            shopDtoToBeUpdated = ShopDto.builder()
                    .id(id)
                    .division(shopDto.getDivision())
                    .region(shopDto.getRegion())
                    .area(shopDto.getArea())
                    .build();
            shopRepository.save(ShopMapper.shopDtoToShop(shopDtoToBeUpdated));
            return shopDtoToBeUpdated;
        }
        return shopDtoToBeUpdated;
    }
}