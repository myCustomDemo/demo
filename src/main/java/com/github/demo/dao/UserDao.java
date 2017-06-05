package com.github.demo.dao;

import com.github.demo.domain.User;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Provides access to see User in the underlying storage.
 */
public interface UserDao {

    List<User> findAll();

    void insert(User user);

    void delete(User user);

    void update(User user, int id, String data);

    String convertByteBufferToString(ByteBuffer data);
}
