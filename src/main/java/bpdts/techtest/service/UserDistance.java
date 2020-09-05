package bpdts.techtest.service;

import bpdts.techtest.datamodel.SearchResult;

public interface UserDistance {

  SearchResult getUsersWithinDistanceOfLocation(String locationName,
                                                Double distance,
                                                Double locationLatitude,
                                                Double locationLongitude);
}
