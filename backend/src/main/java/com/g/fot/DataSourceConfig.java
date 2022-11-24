package com.g.fot;

import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.SimpleTransactionStatus;

import com.clickhouse.jdbc.ClickHouseDataSource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceConfig {
    @Bean
    public DataSource dataSource(DataSourceProperties config) throws SQLException {
        String url = config.getUrl();
        Properties properties = new Properties();
        properties.setProperty("user", config.getUsername());
        properties.setProperty("password", config.getPassword());
        return new ClickHouseDataSource(url, properties);
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new PlatformTransactionManager() {
            @Override
            public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
                return new SimpleTransactionStatus();
            }

            @Override
            public void commit(TransactionStatus status) throws TransactionException {
                // Do nothing
            }

            @Override
            public void rollback(TransactionStatus status) throws TransactionException {
                // Do nothing
            }
        };
    }
}
