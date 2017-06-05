package com.github.demo.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.github.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;

import static com.github.demo.cassandra.CassandraConfig.*;
import static com.google.common.collect.Lists.newArrayList;

/**
 * Provides access to see User in the underlying Cassandra cluster.
 */
@Repository
public class UserDaoImpl implements UserDao {

    private final Session session;

    @Autowired
    public UserDaoImpl(Session session) {
        this.session = session;
    }

    public String convertByteBufferToString(ByteBuffer data){
        try {
            return new String(data.array(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> findAll() {
        Statement select = QueryBuilder
                .select().all()
                .from(KEYSPACE, TABLE_USERS);

        ResultSet resultSet = session.execute(select);

        List<User> userList = newArrayList();
        for (Row row : resultSet) {
            User foundUser = new User(
                    row.getInt(TABLE_USER_ID),
                    convertByteBufferToString(row.getBytes(TABLE_USER_DATA))
            );
            userList.add(foundUser);
        }

        return userList;
    }

    public void insert(User user) {
        Statement insert = QueryBuilder
                .insertInto(KEYSPACE, TABLE_USERS)
                .value(TABLE_USER_ID, user.getId())
                .value(TABLE_USER_DATA, user.getName());

        session.execute(insert);
    }


    public void delete(User user){
        Statement delete = QueryBuilder
                .delete()
                .from(KEYSPACE, TABLE_USERS)
                .where(QueryBuilder.eq(TABLE_USER_ID, user.getId()));

        session.execute(delete);
    }


    public void update(User user, int id, String data){
        Statement update = QueryBuilder
                .update(KEYSPACE, TABLE_USERS)
                .with(QueryBuilder.set(TABLE_USER_DATA, user.setData(data)))
                .where(QueryBuilder.eq(TABLE_USER_ID, user.getId()));

        session.execute(update);
    }

}