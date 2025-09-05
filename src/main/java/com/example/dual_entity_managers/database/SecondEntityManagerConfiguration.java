package com.example.dual_entity_managers.database;

import com.example.dual_entity_managers.repos.second.SecondRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = SecondRepository.class,
        entityManagerFactoryRef = "secondEntityManagerFactory",
        transactionManagerRef = "secondTransactionManager"
)
public class SecondEntityManagerConfiguration {

    @Bean("secondDataSource")
    DataSource secondDataSource(DataSourceProperties.SecondProperties dataSourceProperties) {
        return DataSourceBuilder
                .create()
                .url(dataSourceProperties.url())
                .username(dataSourceProperties.username())
                .password(dataSourceProperties.password())
                .build();
    }

    @Bean("secondEntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder secondEntityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), dataSource -> Map.of(), null);
    }

    @Bean("secondEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean secondEntityManagerFactory(
            @Qualifier("secondEntityManagerFactoryBuilder") EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Qualifier("secondDataSource") DataSource dataSource) {

        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.example.dual_entity_managers.model")
                .persistenceUnit("secondPU")
                .build();
    }

    @Bean("secondTransactionManager")
    PlatformTransactionManager secondTransactionManager(@Qualifier("secondEntityManagerFactory")EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
