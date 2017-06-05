package com.github.demo.cassandra;

import com.datastax.driver.core.Session;
import com.github.demo.cassandra.CassandraSchemaInit;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CassandraSchemaInitTest {

    @Mock
    private Session session;

    private CassandraSchemaInit victim;

    @BeforeClass
    public void setupScenario() {
        initMocks(this);

        victim = new CassandraSchemaInit(session);
    }

    @Test
    public void shouldInitConfigurations() {
        victim.init();

        verify(session).execute("CREATE KEYSPACE IF NOT EXISTS sample " +
                "WITH REPLICATION = { 'class': 'SimpleStrategy', 'replication_factor': '2' }");

        verify(session).execute("CREATE TABLE IF NOT EXISTS sample.users(id int, data blob, " +
                "PRIMARY KEY(id, data))WITH CLUSTERING ORDER BY (data DESC);");
    }

}
