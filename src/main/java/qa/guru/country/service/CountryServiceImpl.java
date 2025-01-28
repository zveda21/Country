package qa.guru.country.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qa.guru.country.data.CountryEntity;
import qa.guru.country.data.CountryRepository;
import qa.guru.country.domain.Country;
import qa.guru.country.domain.graphql.CountryGql;
import qa.guru.country.domain.graphql.CountryInputGql;

import java.util.List;
import java.util.UUID;

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
    public List<CountryGql> getAllGqlCountries() {
        return countryRepository.findAll()
                .stream()
                .map(CountryGql::fromGqlEntity)
                .toList();
    }

    @Override
    public Country findCountryById(UUID uuid) {
        return countryRepository.findById(uuid)
                .map(Country::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Country with ID " + uuid + " not found"));
    }

    @Override
    public CountryGql findGqlCountryById(UUID id) {
        return countryRepository.findById(id)
                .map(CountryGql::fromGqlEntity)
                .orElseThrow(() -> new IllegalArgumentException("Country with ID " + id + " not found"));
    }

    @Override
    public Country findCountryByCountryCode(String countryCode) {
        CountryEntity countryEntity = countryRepository.findByCountryCode(countryCode);
        if (countryEntity == null) {
            throw new IllegalArgumentException("Country with code " + countryCode + " not found");
        }
        return Country.fromEntity(countryEntity);
    }

    @Override
    public CountryGql findGqlCountryByCountryCode(String countryCode) {
        CountryEntity countryEntity = countryRepository.findByCountryCode(countryCode);
        if (countryEntity == null) {
            throw new IllegalArgumentException("Country with code " + countryCode + " not found");
        }
        System.out.println("dffffdgda--" + CountryGql.fromGqlEntity(countryEntity).countryName());
        return CountryGql.fromGqlEntity(countryEntity);
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
    public CountryGql addGqlCountry(CountryInputGql input) {
        CountryEntity entity = new CountryEntity();
        entity.setCountryName(input.countryName());
        entity.setCountryCode(input.countryCode());
        entity = countryRepository.save(entity);
        return CountryGql.fromGqlEntity(entity);
    }

    @Override
    public CountryGql updateGqlCountryName(String countryCode, String newCountryName) {
        CountryEntity entity = countryRepository.findByCountryCode(countryCode);
        if (entity == null) {
            throw new IllegalArgumentException("Country with code " + countryCode + " not found.");
        }
        entity.setCountryName(newCountryName);
        entity = countryRepository.save(entity);
        return CountryGql.fromGqlEntity(entity);
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
