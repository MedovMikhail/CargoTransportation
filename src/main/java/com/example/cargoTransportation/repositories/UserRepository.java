package com.example.cargoTransportation.repositories;

import com.example.cargoTransportation.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    List<UserEntity> findAll();
    UserEntity findById(long id);
    Optional<UserEntity> findByLogin(String login);
}
