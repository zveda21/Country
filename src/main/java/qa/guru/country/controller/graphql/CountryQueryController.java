package qa.guru.country.controller.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qa.guru.country.domain.Country;
import qa.guru.country.domain.graphql.CountryGql;
import qa.guru.country.service.CountryService;

import java.util.List;
import java.util.UUID;

@Controller
public class CountryQueryController {

    private final CountryService countryService;

    @Autowired
    public CountryQueryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @QueryMapping
    public List<CountryGql> countries() {
        return countryService.getAllGqlCountries();
    }

    @QueryMapping
    public CountryGql country(@Argument(name = "id") UUID id) {
        return countryService.findGqlCountryById(id);
    }

    @QueryMapping
    public CountryGql countryByCode(@Argument(name = "countryCode") String countryCode) {
        return countryService.findGqlCountryByCountryCode(countryCode);
    }

    @QueryMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Country addCountry(@RequestBody Country country) {
        return countryService.addCountry(country);
    }

    @PatchMapping("/update/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Country updateCountryName(@PathVariable String countryCode, @RequestBody Country country) {
        return countryService.updateCountryName(countryCode, country);
    }
}
