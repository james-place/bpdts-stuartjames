package bpdts.techtest.datamodel;


import java.util.Objects;

public class GeoLocation {

  private final double latitude;
  private final double longitude;

  public GeoLocation(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public static boolean isValidLatitude(double latitude) {
    boolean isValid = true;
    if (latitude > 90 || latitude < -90) {
      isValid = false;
    }
    return isValid;
  }

  public static boolean isValidLongitude(double longitude) {
    boolean isValid = true;
    if (longitude > 180 || longitude < -180) {
      isValid = false;
    }
    return isValid;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GeoLocation)) return false;
    GeoLocation that = (GeoLocation) o;
    return Double.compare(that.latitude, latitude) == 0 &&
        Double.compare(that.longitude, longitude) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(latitude, longitude);
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("latitude" + latitude)
        .append("longitude" + longitude)
        .toString();
  }

  public enum City {
    LONDON("London", new GeoLocation(51.50853, -0.12574));
    private final String name;
    private final GeoLocation geoLocation;

    City(String name, GeoLocation geoLocation) {
      this.name = name;
      this.geoLocation = geoLocation;
    }

    public double getLatitude() {
      return geoLocation.getLatitude();
    }

    public double getLongitude() {
      return geoLocation.getLongitude();
    }

    public String getName() {
      return name;
    }


  }
}
