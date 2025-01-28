package qa.guru.country;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import qa.guru.country.domain.Country;
import qa.guru.country.service.CountryService;

import java.util.Date;

@SpringBootApplication
public class CountryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountryApplication.class, args);
    }

    @Bean
    CommandLineRunner testCountries(CountryService countryService) {
        return args -> {
            // Step 1: Add a new country
            Country country = new Country("Test Country", "TC", new Date());
            System.out.println("Adding new country...");
            Country addedCountry = countryService.addCountry(country);
            System.out.println("Added Country: " + addedCountry.countryName() + ", " + addedCountry.countryCode());

            // Step 2: Update the country name
            Country newCountry = new Country("Zi Yan","ZY" ,new Date());
            System.out.println("Updating country...");
            Country updatedCountry = countryService.updateCountryName("TC", newCountry);
            System.out.println("Updated Country: " + updatedCountry.countryName());

            // Step 3: Retrieve all countries
            System.out.println("Fetching all countries...");
            countryService.getAllCountries().forEach(c ->
                    System.out.println("Country: " + c.countryName() + ", " + c.countryCode()));
        };
    }
}
