package com.example.dual_entity_managers.configurations;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class SecondEntityManagerConfiguration {

    // ============================
    // playing with entity managers

    // https://stackoverflow.com/questions/43509145/spring-boot-multiple-data-sources-using-entitymanager
    // https://javanexus.com/blog/mastering-spring-data-dual-entity-managers

    // aha! -> https://stackoverflow.com/questions/46574686/spring-boot-2-0-0-m4-hibernate-5-2-11-final-could-not-find-bean-of-type-entity/51305724#51305724

    // ========================
    // ------------------------
    // THE SECOND ENTITY MANAGER

    // the data source

    @Bean("secondDataSource")
    DataSource secondDataSource(DataSourceProperties.SecondProperties dataSourceProperties) {
        return DataSourceBuilder
                .create()
                .url(dataSourceProperties.url())
                .username(dataSourceProperties.username())
                .password(dataSourceProperties.password())
                .build();
    }

    // entity manager factory builder

    @Bean("secondEntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder secondEntityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), dataSource -> Map.of(), null);
    }

    // the entity manager factory

    @Bean("secondEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean secondEntityManagerFactory(
            @Qualifier("secondEntityManagerFactoryBuilder") EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Qualifier("secondDataSource") DataSource dataSource) {

        final var builder = entityManagerFactoryBuilder.dataSource(dataSource);

        // damn, it shuts up when scanning is enforced :-)
        builder.packages("com.example.dual_entity_managers.model");

        //.build();

        System.out.println("before!!!");

        final var theThing = builder.build();

        System.out.println("after!!!");


//        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
//        localContainerEntityManagerFactoryBean.afterPropertiesSet();

        return theThing;
    }

    // transaction manager (because we want to handle transactions on this datasource separately)
    // ... but maybe we don't want to? it depends on the use case

    @Bean("secondTransactionManager")
    PlatformTransactionManager secondTransactionManager(@Qualifier("secondEntityManagerFactory")EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
