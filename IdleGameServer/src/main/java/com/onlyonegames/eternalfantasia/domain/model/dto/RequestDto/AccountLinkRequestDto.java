package com.onlyonegames.eternalfantasia.domain.model.dto.RequestDto;

import lombok.Data;

@Data
public class AccountLinkRequestDto {
    String socialId;
    String socialProvider;
}
