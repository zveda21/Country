package qa.guru.country.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {

    CountryEntity findByCountryName(String countryName);

    CountryEntity findByCountryCode(String countryCode);

}
