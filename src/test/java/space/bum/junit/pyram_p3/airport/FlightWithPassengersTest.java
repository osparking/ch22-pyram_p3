package space.bum.junit.pyram_p3.airport;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import space.bum.junit.pyram_p3.airport.producers.FlightProducer;

@RunWith(Arquillian.class)
public class FlightWithPassengersTest {

  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
        .addClasses(Passenger.class, Flight.class, FlightProducer.class)
        .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @Inject
  @FlightNumber(number = "AB5422")
  Flight flight;

  @Mock
  DistanceManager distanceManager;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  private static Map<Passenger, Integer> passengersPointsMap = new HashMap<>();

  @BeforeClass
  public static void setUp() {
    passengersPointsMap.put(new Passenger("940207-6459423", "Susan Todd", "GB"),
        210);
    passengersPointsMap
        .put(new Passenger("860602-2749821", "Harry Christensen", "GB"), 420);
    passengersPointsMap.put(new Passenger("850205-3917188", "정성민", "KR"),
        630);
  }

  @Test
  public void testFlightsDistances() {
    when(distanceManager.getPassengersPointsMap())
        .thenReturn(passengersPointsMap);

    assertEquals(210, distanceManager.getPassengersPointsMap()
        .get(new Passenger("940207-6459423", "Susan Todd", "GB")).longValue());
    assertEquals(420,
        distanceManager.getPassengersPointsMap()
            .get(new Passenger("860602-2749821", "Harry Christensen", "GB"))
            .longValue());
    assertEquals(630, distanceManager.getPassengersPointsMap()
        .get(new Passenger("850205-3917188", "정성민", "KR")).longValue());
  }

  @Test(expected = RuntimeException.class)
  public void testNumberOfSeatsCannotBeExceeded() throws IOException {
    assertEquals(50, flight.getPassengersNumber());
    flight.addPassenger(new Passenger("850205-3917188", "정성민", "KR"));
  }

  @Test
  public void testAddRemovePassenters() throws IOException {
    flight.setSeats(51);
    Passenger newPassenger = new Passenger("560205-2917188", "정성숙", "KR");
    flight.addPassenger(newPassenger);
    assertEquals(51, flight.getPassengersNumber());
    flight.removePassenger(newPassenger);
    assertEquals(50, flight.getPassengersNumber());
    assertEquals(51, flight.getSeats());
  }
}
