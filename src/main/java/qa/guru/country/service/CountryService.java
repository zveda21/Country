package qa.guru.country.service;

import qa.guru.country.domain.Country;

import java.util.List;

public interface CountryService {

    List<Country> getAllCountries();

    Country addCountry(Country country);

    Country updateCountryName(String countryCode, Country country);
}
