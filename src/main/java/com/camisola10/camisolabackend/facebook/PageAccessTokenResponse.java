package com.camisola10.camisolabackend.facebook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class PageAccessTokenResponse {

    List<TokenResponse> data;
}
