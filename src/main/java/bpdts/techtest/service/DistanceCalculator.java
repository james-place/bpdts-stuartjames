package bpdts.techtest.service;

import bpdts.techtest.datamodel.GeoLocation;

public interface DistanceCalculator {

  double calculateDistance(GeoLocation far, GeoLocation wide);

}
