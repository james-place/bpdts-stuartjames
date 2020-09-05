package bpdts.techtest.service;

import bpdts.techtest.datamodel.User;

import java.util.Collection;
import java.util.Optional;

public interface BPDTSRestClient {

  Collection<User> getAllUsers();

  Collection<User> getAllUsersFromCity(String city);

  Optional<User> getUserById(int id);
}
