package qa.guru.country.controller.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import qa.guru.country.domain.graphql.CountryGql;
import qa.guru.country.domain.graphql.CountryInputGql;
import qa.guru.country.service.CountryService;

@Controller
public class CountryMutationController {

    private final CountryService countryService;

    @Autowired
    public CountryMutationController(CountryService countryService) {
        this.countryService = countryService;
    }

    @MutationMapping
    public CountryGql addCountry(@Argument("input") CountryInputGql input) {
        return countryService.addGqlCountry(input);
    }

    @MutationMapping
    public CountryGql updateCountryName(@Argument(name = "countryCode") String countryCode,
                                        @Argument(name = "newCountryName") String newCountryName) {
        return countryService.updateGqlCountryName(countryCode, newCountryName);
    }
}
