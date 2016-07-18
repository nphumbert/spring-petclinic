package org.springframework.samples.petclinic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@Import({JpaRepositoryConfig.class, JdbcRepositoryConfig.class, SpringDataJpaRepositoryConfig.class, DataSourceConfig.class})
@EnableTransactionManagement
@PropertySource("classpath:spring/data-access.properties")
@ComponentScan("org.springframework.samples.petclinic.service")
public class BusinessConfig {

    @Bean
    @Profile({"jpa", "spring-data-jpa"})
    public AbstractEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, @Value("${jpa.database}") Database database, @Value("${jpa.showSql}") boolean showSql) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter(database, showSql));
        entityManagerFactory.setPersistenceUnitName("petclinic");
        entityManagerFactory.setPackagesToScan("org.springframework.samples.petclinic");
        return entityManagerFactory;
    }

    private JpaVendorAdapter jpaVendorAdapter(Database database, boolean showSql) {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(database);
        jpaVendorAdapter.setShowSql(showSql);
        return jpaVendorAdapter;
    }

    @Bean(name = "transactionManager")
    @Profile({"jpa", "spring-data-jpa"})
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    /**
     * Post-processor to perform exception translation on @Repository classes (from native
     * exceptions such as JPA PersistenceExceptions to Spring's DataAccessException hierarchy).
     **/
    @Bean
    @Profile({"jpa", "spring-data-jpa"})
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean(name = "transactionManager")
    @Profile("jdbc")
    public PlatformTransactionManager jdbcTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean()
    @Profile("jdbc")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean()
    @Profile("jdbc")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
