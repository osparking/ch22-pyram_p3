package space.bum.junit.pyram_p3.airport.producers;

import java.io.IOException;

import javax.enterprise.inject.Produces;

import space.bum.junit.pyram_p3.airport.Flight;
import space.bum.junit.pyram_p3.airport.FlightBuilderUtil;
import space.bum.junit.pyram_p3.airport.FlightNumber;

public class FlightProducer {
  @Produces
  @FlightNumber(number = "AB5422")
  public Flight createFlight() throws IOException {
    return FlightBuilderUtil.buildFlightFromCsv("AB5422", 50,
        "src/test/resources/flights_information.csv");
  }
}
