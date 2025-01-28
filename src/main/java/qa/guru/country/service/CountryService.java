package qa.guru.country.service;

import qa.guru.country.domain.Country;
import qa.guru.country.domain.graphql.CountryGql;
import qa.guru.country.domain.graphql.CountryInputGql;

import java.util.List;
import java.util.UUID;

public interface CountryService {

    List<Country> getAllCountries();

    List<CountryGql> getAllGqlCountries();

    Country findCountryById(UUID uuid);

    CountryGql findGqlCountryById(UUID id);

    Country findCountryByCountryCode(String countryCode);

    CountryGql findGqlCountryByCountryCode(String countryCode);

    Country addCountry(Country country);

    CountryGql addGqlCountry(CountryInputGql input);

    CountryGql updateGqlCountryName(String countryCode, String newCountryName);

    Country updateCountryName(String countryCode, Country country);
}
