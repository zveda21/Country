package qa.guru.country.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import qa.guru.country.domain.Country;
import qa.guru.country.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("api/country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/all")
    public List<Country> getAll() {
        return countryService.getAllCountries();
    }

    @PostMapping("/add")
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
