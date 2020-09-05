package bpdts.techtest.service;

import bpdts.techtest.datamodel.GeoLocation;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

//import org.junit.runner.RunWith;
//import org.mockito.junit.MockitoJUnitRunner;

//@RunWith(MockitoJUnitRunner.class)
class DistanceCalculatorImplTest {

  private static GeoLocation NEWCASTLE = new GeoLocation(54.97328, -1.61396);
  private static GeoLocation PRESTON = new GeoLocation(53.7, -2.7);
  private static GeoLocation MANCHESTER = new GeoLocation(53.4, 2.2);
  private static GeoLocation LONDON = new GeoLocation(GeoLocation.City.LONDON.getLatitude(),
      GeoLocation.City.LONDON.getLongitude());
  private DistanceCalculator service = new DistanceCalculatorImpl();

  static Stream<Arguments> test_GeoLocation() {
    return Stream.of(
        Arguments.of(NEWCASTLE, MANCHESTER, 188),
        Arguments.of(NEWCASTLE, PRESTON, 98),
        Arguments.of(NEWCASTLE, LONDON, 247)
    );
  }

  @Test
  void test_calculateDistanceBetween2Points_SameCoordinates() {
    GeoLocation testLocation = new GeoLocation(50, 100);


    double distance = service.calculateDistance(LONDON, LONDON);
    assertThat(distance).isEqualTo(0);

  }

  @ParameterizedTest(name = "Run: {index} - Point1: {0}, Point2: {1}, Expected Distance= {2}")
  @MethodSource("test_GeoLocation")
  void test_calculateDistance(GeoLocation point1, GeoLocation point2, double expectedDistance) {

    double actualDistance = service.calculateDistance(point1, point2);
    assertThat(actualDistance).isCloseTo(expectedDistance, Percentage.withPercentage(2));
  }
}