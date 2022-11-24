package org.springframework.data.jdbc.repository.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.data.relational.core.dialect.ClickHouseDialect;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.lang.Nullable;

public class ClickHouseDialectProvider implements DialectResolver.JdbcDialectProvider {
    @Override
    public Optional<Dialect> getDialect(JdbcOperations operations) {
        return Optional.ofNullable(operations.execute((ConnectionCallback<Dialect>) this::getDialect));
    }

    @Nullable
    private Dialect getDialect(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        String name = metaData.getDatabaseProductName().toLowerCase();
        if (name.contains("clickhouse")) {
            return ClickHouseDialect.INSTANCE;
        }
        return null;
    }
}
