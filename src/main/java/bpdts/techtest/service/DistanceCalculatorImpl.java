package bpdts.techtest.service;

import bpdts.techtest.datamodel.GeoLocation;
import org.springframework.stereotype.Service;

@Service
public class DistanceCalculatorImpl implements DistanceCalculator {

  private static final double EARTH_RADIUS = 3958.8;

  @Override
  public double calculateDistance(GeoLocation from, GeoLocation to) {

    if ((from.getLatitude() == to.getLatitude()) && (from.getLongitude() == to.getLongitude())) {
      return 0;
    } else {

      double fromLongitude = Math.toRadians(from.getLongitude());
      double fromLatitude = Math.toRadians(from.getLatitude());

      double toLongitude = Math.toRadians(to.getLongitude());
      double toLatitude = Math.toRadians(to.getLatitude());

      // Haversine formula
      double lonDist = toLongitude - fromLongitude;
      double latDist = toLatitude - fromLatitude;
      double a = Math.pow(Math.sin(latDist / 2), 2)
          + Math.cos(fromLatitude) * Math.cos(toLatitude)
          * Math.pow(Math.sin(lonDist / 2), 2);
      double c = 2 * Math.asin(Math.sqrt(a));
      return (c * EARTH_RADIUS);
    }
  }
}
