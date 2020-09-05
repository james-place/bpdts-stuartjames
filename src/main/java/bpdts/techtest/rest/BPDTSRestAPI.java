package bpdts.techtest.rest;

import bpdts.techtest.datamodel.GeoLocation;
import bpdts.techtest.datamodel.SearchResult;
import bpdts.techtest.service.UserDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BPDTSRestAPI {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private UserDistance userDistance;

  @GetMapping(value = "/api/bpdts/techtest/users-in-london-or-within-sixty-miles-of-london")
  public SearchResult getUsersWithinSixtyMilesOfLondon() {
    return userDistance.getUsersWithinDistanceOfLocation(GeoLocation.City.LONDON.getName(), 60.0,
        GeoLocation.City.LONDON.getLatitude(),
        GeoLocation.City.LONDON.getLongitude());
  }
}
