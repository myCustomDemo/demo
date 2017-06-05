package com.github.demo.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.ConstantReconnectionPolicy;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class CassandraConfig {

    public static final String KEYSPACE = "sample";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_USER_ID = "id";
    public static final String TABLE_USER_DATA = "data";

    private String cassandraHost = "localhost";

    @VisibleForTesting
    Cluster.Builder clusterBuilder() {
        return Cluster.builder();
    }

    @Bean
    public Cluster cluster() {
        return clusterBuilder()
                .addContactPoint(cassandraHost)
                .withReconnectionPolicy(new ConstantReconnectionPolicy(100L))
                .build();
    }

    @Bean
    public Session session() throws Exception {
        return cluster().newSession();
    }

    @Value("${cassandra.host}")
    public void setCassandraHost(String cassandraHost) {
        this.cassandraHost = cassandraHost;
    }

}