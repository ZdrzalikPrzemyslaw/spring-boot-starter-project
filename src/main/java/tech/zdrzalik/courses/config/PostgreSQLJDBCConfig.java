package tech.zdrzalik.courses.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

/**
 * This class configures the datasource used in the application based on the data provided in the application.properties file.
 */

// Prawdopodobnie jesli stworzymy druga taka klase mozemy miec drugie zrodlo danych w naszej apce.
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "postgresql.1.entityManagerFactory",
        transactionManagerRef = "postgresql.1.transactionManager",
        basePackages = {
                "tech.zdrzalik.courses.model"
        },
        enableDefaultTransactions = true
)
public class PostgreSQLJDBCConfig {

    @Value("${db.driver}")
    private String driver;
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    @Value("${db.hibernate.dialect}")
    private String dialect;

    @Bean("postgresql.1.datasource")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driver)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Bean("postgresql.1.entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("postgresql.1.datasource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .properties(new HashMap<>() {
                    {
                        put("hibernate.dialect", dialect);
                    }
                })
                .packages("tech.zdrzalik.courses.model")
                .persistenceUnit("pu")
                .build();
    }

    @Bean(name = "postgresql.1.transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("postgresql.1.entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }


}