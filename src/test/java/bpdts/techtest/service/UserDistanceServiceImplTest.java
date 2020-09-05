package bpdts.techtest.service;

import bpdts.techtest.datamodel.GeoLocation;
import bpdts.techtest.datamodel.SearchResult;
import bpdts.techtest.datamodel.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class UserDistanceServiceImplTest {

  @Mock
  BPDTSRestClient restClient;

  @Mock
  DistanceCalculator distanceCalculator;

  @InjectMocks
  UserDistanceImpl userDistance;

  private GeoLocation close, far, london;
  private User londonUser, closeUser, farUser;
  private Collection<User> londonUsers;
  private Collection<User> allUsers;

  @BeforeEach
  void setUp() throws Exception {

    MockitoAnnotations.initMocks(this);

    close = new GeoLocation(5.0, 5.0);
    far = new GeoLocation(20.0, 20.0);
    london = new GeoLocation(GeoLocation.City.LONDON.getLatitude(), GeoLocation.City.LONDON.getLongitude());

    londonUser = new User();
    londonUser.setId(1);
    londonUser.setLatitude(far.getLatitude());
    londonUser.setLongitude(far.getLongitude());

    closeUser = new User();
    closeUser.setId(2);
    closeUser.setLatitude(close.getLatitude());
    closeUser.setLongitude(close.getLongitude());

    farUser = new User();
    farUser.setId(3);
    farUser.setLatitude(far.getLatitude());
    farUser.setLongitude(far.getLongitude());

    londonUsers = new ArrayList<>();
    londonUsers.add(londonUser);

    allUsers = new ArrayList<>();
    allUsers.add(londonUser);
    allUsers.add(closeUser);
    allUsers.add(farUser);

    Mockito.when(restClient.getAllUsersFromCity(GeoLocation.City.LONDON.getName())).thenReturn(londonUsers);
    Mockito.when(restClient.getAllUsers()).thenReturn(allUsers);
    Mockito.when(distanceCalculator.calculateDistance(close, london)).thenReturn(25.0);
    Mockito.when(distanceCalculator.calculateDistance(far, london)).thenReturn(100.0);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void testUserWithinProximity() {
    SearchResult withinDistanceOfLocation = userDistance.getUsersWithinDistanceOfLocation(GeoLocation.City.LONDON.getName(), 50.0, london.getLatitude(), london.getLongitude());
    assertThat(withinDistanceOfLocation.getUsers()).hasSize(2);
    assertThat(withinDistanceOfLocation.getUsers()).contains(londonUser, closeUser);
  }

  @Test
  void testUserWithinProximityWithCityNameOnly() {
    SearchResult withinDistanceOfLocation = userDistance.getUsersWithinDistanceOfLocation(GeoLocation.City.LONDON.getName(), null, null, null);
    assertThat(withinDistanceOfLocation.getUsers()).hasSize(1);
    assertThat(withinDistanceOfLocation.getUsers()).contains(londonUser);
  }

  @Test
  void testUserWithinProximityWithGeoPointsOnly() {
    SearchResult withinDistanceOfLocation = userDistance.getUsersWithinDistanceOfLocation(null, 50.0, london.getLatitude(), london.getLongitude());
    assertThat(withinDistanceOfLocation.getUsers()).hasSize(1);
    assertThat(withinDistanceOfLocation.getUsers()).contains(closeUser);
  }

  @Test
  void testUserWithinProximityWithCityNameInvalidCoords() {
    SearchResult withinDistanceOfLocation = userDistance.getUsersWithinDistanceOfLocation(GeoLocation.City.LONDON.getName(), -50.0, -91.0, -181.0);
    assertThat(withinDistanceOfLocation.getUsers()).hasSize(1);
    assertThat(withinDistanceOfLocation.getUsers()).contains(londonUser);
    assertThat(withinDistanceOfLocation.getErrorMessages()).hasSize(3);
  }

  @Test
  void testValidParams() {
    SearchResult result = userDistance.getUsersWithinDistanceOfLocation(null, null, null, null);
    assertThat(result.getUsers()).isEmpty();
    result = userDistance.getUsersWithinDistanceOfLocation(null, -50.0, london.getLatitude(), london.getLongitude());
    assertThat(result.getUsers()).hasSize(0);
    assertThat(result.getErrorMessages()).hasSize(1);
    result = userDistance.getUsersWithinDistanceOfLocation(null, 50.0, -91.0, london.getLongitude());
    assertThat(result.getUsers()).hasSize(0);
    assertThat(result.getErrorMessages()).hasSize(1);
    result = userDistance.getUsersWithinDistanceOfLocation(null, 50.0, 91.0, london.getLongitude());
    assertThat(result.getUsers()).hasSize(0);
    assertThat(result.getErrorMessages()).hasSize(1);
    result = userDistance.getUsersWithinDistanceOfLocation(null, 50.0, london.getLatitude(), 181.0);
    assertThat(result.getUsers()).hasSize(0);
    assertThat(result.getErrorMessages()).hasSize(1);
    result = userDistance.getUsersWithinDistanceOfLocation(null, 50.0, london.getLatitude(), -181.0);
    assertThat(result.getUsers()).hasSize(0);
    assertThat(result.getErrorMessages()).hasSize(1);
  }
}