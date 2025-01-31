package qa.guru.country.service;

import com.google.protobuf.Empty;
import com.google.protobuf.util.Timestamps;
import guru.qa.grpc.country.*;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import qa.guru.country.domain.graphql.CountryGql;
import qa.guru.country.domain.graphql.CountryInputGql;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class GrpcCountryService extends CountryServiceGrpc.CountryServiceImplBase {

    private static final Random random  = new Random();
    private final CountryService countryService;

    public GrpcCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public void countries(Empty request, StreamObserver<CountriesResponse> responseObserver) {
        List<CountryGql> getAllCountries = countryService.getAllGqlCountries();
        CountriesResponse.Builder responseBuilder = CountriesResponse.newBuilder();
        for (CountryGql country : getAllCountries) {
            CountryResponse countryResponse = CountryResponse.newBuilder()
                    .setCountryName(country.countryName())
                    .setCountryCode(country.countryCode())
                    .build();

            responseBuilder.addCountries(countryResponse);
        }
        CountriesResponse response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void country(IdRequest request, StreamObserver<CountryResponse> responseObserver) {
        final CountryGql countryGql = countryService.findGqlCountryById(UUID.fromString(request.getId()));
        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setCountryCode(countryGql.countryCode())
                        .setCountryName(countryGql.countryName())
                        .setIndependenceDate(Timestamps.fromDate(countryGql.independenceDate()))
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void addCountry(CountryRequest request, StreamObserver<CountryResponse> responseObserver) {
        final CountryGql countryGql  = countryService.addGqlCountry(new CountryInputGql(
                request.getCountryName(),
                request.getCountryCode()
        ));
        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setCountryCode(countryGql.countryCode())
                        .setCountryName(countryGql.countryName())
                        .setIndependenceDate(Timestamps.fromDate(countryGql.independenceDate()))
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void randomCountry(CountRequest request, StreamObserver<CountryResponse> responseObserver) {
        List<CountryGql> countryGqls = countryService.getAllGqlCountries();
        for (int i = 0; i <request.getCount() ; i++) {
            CountryGql randomCountry = countryGqls.get(random.nextInt(countryGqls.size()));
            responseObserver.onNext(
                    CountryResponse.newBuilder()
                            .setCountryCode(randomCountry.countryCode())
                            .setCountryName(randomCountry.countryName())
                            .setIndependenceDate(Timestamps.fromDate(randomCountry.independenceDate()))
                            .build()
            );

        }
        responseObserver.onCompleted();
    }
}
