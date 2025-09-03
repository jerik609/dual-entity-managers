package com.example.dual_entity_managers.configurations;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "barEntityManagerFactory",
//        transactionManagerRef = "barTransactionManager",
//        basePackages = { "com.sctrcd.multidsdemo.bar.repo" })

public class FirstEntityManagerConfiguration {

    // ============================
    // playing with entity managers

    // https://stackoverflow.com/questions/43509145/spring-boot-multiple-data-sources-using-entitymanager
    // https://javanexus.com/blog/mastering-spring-data-dual-entity-managers

    // aha! -> https://stackoverflow.com/questions/46574686/spring-boot-2-0-0-m4-hibernate-5-2-11-final-could-not-find-bean-of-type-entity/51305724#51305724

    // ========================
    // ------------------------
    // THE FIRST ENTITY MANAGER

    // the data source

    @Bean("firstDataSource")
    DataSource firstDataSource(DataSourceProperties.FirstProperties dataSourceProperties) {
        return DataSourceBuilder
                .create()
                .url(dataSourceProperties.url())
                .username(dataSourceProperties.username())
                .password(dataSourceProperties.password())
                .build();
    }

    // the entity manager factory

    @Bean("firstEntityManagerFactory")
    EntityManagerFactory firstEntityManagerFactory(@Qualifier("firstDataSource") DataSource dataSource) {
        final var localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.example.spring_databases_playground.entities");

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        //** Set the JPA provider explicitly (Hibernate)
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(jpaProperties);

//        final var x = new DefaultPersistenceUnitManager();
//        x.setDefaultPersistenceUnitName("first");
//        x.afterPropertiesSet();
//
//        localContainerEntityManagerFactoryBean.setPersistenceUnitManager(x);
//        localContainerEntityManagerFactoryBean.setPersistenceUnitName("first");
        localContainerEntityManagerFactoryBean.afterPropertiesSet();
        return localContainerEntityManagerFactoryBean.getObject();
    }

    // transaction manager (because we want to handle transactions on this datasource separately)
    // ... but maybe we don't want to? it depends on the use case

    @Bean("firstTransactionManager")
    PlatformTransactionManager firstTransactionManager(@Qualifier("firstEntityManagerFactory")EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    // ========================
    // ------------------------
    // THE SECOND ENTITY MANAGER

    // @Bean
    //public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
    //    return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
    //}

}
