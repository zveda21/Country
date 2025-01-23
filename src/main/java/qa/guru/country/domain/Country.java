package qa.guru.country.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import qa.guru.country.data.CountryEntity;

import java.util.Date;

public record Country(@JsonProperty("country_name")
                      String countryName,
                      @JsonProperty("country_code")
                      String countryCode,
                      Date independencyDate) {

    public static Country fromEntity(CountryEntity countryEntity) {
       return new Country(
               countryEntity.getCountryName(),
               countryEntity.getCountryCode(),
               new Date()
       );
    }
}
