package qa.guru.country.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qa.guru.country.data.CountryEntity;
import qa.guru.country.data.CountryRepository;
import qa.guru.country.domain.Country;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;

    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll()
                .stream()
                .map(Country::fromEntity)
                .toList();
    }

    @Override
    public Country addCountry(Country country) {
        CountryEntity entity = new CountryEntity();
        entity.setCountryName(country.countryName());
        entity.setCountryCode(country.countryCode());
        entity = countryRepository.save(entity);
        return Country.fromEntity(entity);
    }

    @Override
    public Country updateCountryName(String countryCode, Country country) {
        CountryEntity countryEntity = countryRepository.findByCountryCode(countryCode);
        if (countryEntity == null) {
            throw new IllegalArgumentException("Country with code " + countryCode + " does not exist");
        }
        countryEntity.setCountryName(country.countryName());
        countryRepository.save(countryEntity);
        return Country.fromEntity(countryEntity);
    }
}
