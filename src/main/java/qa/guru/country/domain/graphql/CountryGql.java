package qa.guru.country.domain.graphql;

import qa.guru.country.data.CountryEntity;

import java.util.Date;
import java.util.UUID;

public record CountryGql(
        UUID id,
        String countryName,
        String countryCode,
        Date independenceDate) {

    public static CountryGql fromGqlEntity(CountryEntity countryEntity) {
        if (countryEntity == null) {
            throw new IllegalStateException("Country ID cannot be null");
        }

        return new CountryGql(
                countryEntity.getId(),
                countryEntity.getCountryName(),
                countryEntity.getCountryCode(),
                new Date()
        );
    }
}
