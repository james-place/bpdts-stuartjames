package bpdts.techtest.service;

import bpdts.techtest.datamodel.GeoLocation;
import bpdts.techtest.datamodel.SearchResult;
import bpdts.techtest.datamodel.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserDistanceImpl implements UserDistance {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private BPDTSRestClient BPDTSRestClient;

  @Autowired
  private DistanceCalculator distanceCalculator;

  @Override
  public SearchResult getUsersWithinDistanceOfLocation(String cityName, Double distance, Double latitude, Double longitude) {

    SearchResult results = new SearchResult();
    Set<User> users = new HashSet<>();

    // First Search by city name
    if (!(cityName == null) && !cityName.isEmpty()) {
      log.debug("Getting users from {}", cityName);
      users.addAll(BPDTSRestClient.getAllUsersFromCity(cityName));
    } else {
      log.debug("No city name provided");
    }

    // If coordinates and distance are provided find all users with in range
    if (distance != null && latitude != null && longitude != null) {
      log.debug("Getting users within [{}] miles of [{} , {}]", distance, latitude, longitude);

      boolean validParams = isValidParams(distance, latitude, longitude, results);

      if (validParams) {
        GeoLocation geoLocation = new GeoLocation(latitude, longitude);
        users.addAll(getUsersWithinDistance(distance, geoLocation));
      }

    } else {
      log.debug("No coordinates provided");
    }

    log.debug("[{}] users found", users.size());
    results.setUsers(users);

    return results;
  }

  private boolean isValidParams(Double distance, Double locationLatitude, Double locationLongitude, SearchResult results) {
    boolean validParams = true;

    if (distance < 0) {
      //invalid distance
      results.getErrorMessages().add("Distance must be greater than 0");
      validParams = false;
    }


    if (!GeoLocation.isValidLatitude(locationLatitude)) {
      results.getErrorMessages().add("Valid Latitude range is  between 90 and -90");
      validParams = false;
    }

    if (!GeoLocation.isValidLongitude(locationLongitude)) {
      results.getErrorMessages().add("Valid Longitude range is  between 180 and -180");
      validParams = false;
    }
    return validParams;
  }

  private List<User> getUsersWithinDistance(double distance, GeoLocation location) {
    List<User> usersWithinDistance = new ArrayList<>();
    Collection<User> allUsers = BPDTSRestClient.getAllUsers();

    for (User user : allUsers) {
      GeoLocation geoLocation = new GeoLocation(user.getLatitude(), user.getLongitude());
      double calculatedDistance = distanceCalculator.calculateDistance(geoLocation, location);
      if (calculatedDistance <= distance) {
        user.setDistanceFromCoords(calculatedDistance);
        usersWithinDistance.add(user);
      }
    }
    return usersWithinDistance;
  }
}
