package com.onlyonegames.eternalfantasia.domain.model.dto.RequestDto;

import lombok.Data;

import java.util.List;

@Data
public class StrengthenRequestDto {
    ItemRequestDto originalItem;
    List<ItemRequestDto> materialItemList;
}
