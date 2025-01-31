package qa.guru.country.service.soap;

import guru.qa.xml.country.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import qa.guru.country.domain.graphql.CountryGql;
import qa.guru.country.domain.graphql.CountryInputGql;
import qa.guru.country.service.CountryService;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import static qa.guru.country.config.AppConfig.SOAP_NAMESPACE_URI;

@Endpoint
public class CountryEndpoint {

    private final CountryService countryService;

    @Autowired
    public CountryEndpoint(CountryService countryService) {
        this.countryService = countryService;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "idRequest")
    @ResponsePayload
    public CountryResponse updateUserRq(@RequestPayload IdRequest request) {
        return createCountryResponse(countryService.findGqlCountryById(UUID.fromString(request.getId())));
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "allRequest")
    @ResponsePayload
    public CountriesResponse getCountries() {
        List<CountryGql> countryGql = countryService.getAllGqlCountries();
        CountriesResponse countriesResponse = new CountriesResponse();
        countriesResponse.getCountries().addAll(
                countryGql.stream()
                        .map(this::mapToCountry)
                        .toList()
        );
        return countriesResponse;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "addRequest")
    @ResponsePayload
    public CountryResponse addCountry(@RequestPayload AddRequest addRequest) {
        CountryInputRequestType countryInputRequest = addRequest.getCountryInputRequest();
        CountryInputGql inputGql = new CountryInputGql(
                countryInputRequest.getCountryName(),
                countryInputRequest.getCountryCode()
        );
        CountryGql countryGql = countryService.addGqlCountry(inputGql);

        return createCountryResponse(countryGql);
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "updateRequest")
    @ResponsePayload
    public CountryResponse updateCountry(@RequestPayload UpdateRequest updateRequest) {
        CountryInputRequestType countryInputRequest = updateRequest.getCountryInputRequest();
        CountryInputGql inputGql = new CountryInputGql(
                countryInputRequest.getCountryName(),
                countryInputRequest.getCountryCode()
        );
        CountryGql countryGql = countryService.updateGqlCountryName(inputGql.countryCode(),inputGql.countryName());
        return createCountryResponse(countryGql);
    }

    private Country mapToCountry(CountryGql countryGql) {
        Country country = new Country();
        country.setId(countryGql.id().toString());
        country.setCountryName(countryGql.countryName());
        country.setCountryCode(countryGql.countryCode());
        XMLGregorianCalendar xmlDate = convertToXMLGregorianCalendar(countryGql.independenceDate());
        country.setIndependenceDate(xmlDate);
        return country;
    }

    private CountryResponse createCountryResponse(CountryGql countryGql) {
        CountryResponse countryResponse = new CountryResponse();
        Country xmlCountry = new Country();

        xmlCountry.setId(String.valueOf(countryGql.id()));
        xmlCountry.setCountryName(countryGql.countryName());
        xmlCountry.setCountryCode(countryGql.countryCode());
        XMLGregorianCalendar xmlDate = convertToXMLGregorianCalendar(countryGql.independenceDate());
        xmlCountry.setIndependenceDate(xmlDate);
        countryResponse.setCountry(xmlCountry);
        return countryResponse;
    }

    private XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {
        try {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);

            DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
            return datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        } catch (Exception e) {
            throw new RuntimeException("Error converting Date to XMLGregorianCalendar", e);
        }
    }
}
