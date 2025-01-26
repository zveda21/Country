package guru.qa.country.controller;

import guru.qa.country.model.Country;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class CountryController {

    @GetMapping("/")
    public List<Country> getAll() {
        return List.of(
                new Country("Armenia", "AM", new Date()),
                new Country("Russia", "RU", new Date())
        );
    }
}
