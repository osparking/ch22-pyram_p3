package space.bum.junit.pyram_p3.airport.producers;

import java.io.IOException;

import javax.enterprise.inject.Produces;

import space.bum.junit.pyram_p3.airport.Flight;
import space.bum.junit.pyram_p3.airport.FlightBuilderUtil;

public class FlightProducer {
  @Produces
  public Flight createFlight() throws IOException {
    return FlightBuilderUtil.buildFlightFromCsv();
  }
}
