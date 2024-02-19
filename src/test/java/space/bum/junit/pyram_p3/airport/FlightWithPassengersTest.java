package space.bum.junit.pyram_p3.airport;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

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
  Flight flight;

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
