package org.springframework.samples.petclinic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;


@Configuration
@PropertySource(value = "classpath:spring/data-access.properties")
public class DataSourceConfig {

    @Bean(name = "dataSource")
    public DataSource dataSource(@Value("${jdbc.driverClassName}") String driverClassName, @Value("${jdbc.url}") String url, @Value("${jdbc.username}") String username, @Value("${jdbc.password}") String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource, @Value("${jdbc.initLocation}") Resource initLocation, @Value("${jdbc.dataLocation}") Resource dataLocation) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator(initLocation, dataLocation));
        return initializer;
    }

    private DatabasePopulator databasePopulator(Resource initLocation, Resource dataLocation) {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(initLocation);
        populator.addScript(dataLocation);
        return populator;
    }

}
