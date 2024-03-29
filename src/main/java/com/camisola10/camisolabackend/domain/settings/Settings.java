package com.camisola10.camisolabackend.domain.settings;

import com.camisola10.camisolabackend.domain.product.Badge;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Settings {

    @Id
    @JsonIgnore
    private Long id = 1L;
    private HomePageLayout homePageLayout;
    private ProductSettings productSettings;
    @LastModifiedDate
    private LocalDateTime lastModified;

    @Data
    @AllArgsConstructor
    public static class HomePageLayout {
        List<String> benficaProductsOrder;
        List<String> sportingProductsOrder;
        List<String> portoProductsOrder;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductSettings {
        List<Badge> badges;
    }
}