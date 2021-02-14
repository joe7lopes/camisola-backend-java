package com.camisola10.camisolabackend.domain.facebook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import static java.util.Collections.emptyList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class FacebookPageReviews {
    public static final FacebookPageReviews EMPTY = new FacebookPageReviews(emptyList());
    List<Review> data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Review {
        @JsonProperty("created_time")
        private Date createdTime;

        @JsonProperty("review_text")
        private String reviewText;
    }
}
