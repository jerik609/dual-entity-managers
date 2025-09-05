package com.example.dual_entity_managers.database;

import com.example.dual_entity_managers.repos.first.FirstRepository;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = FirstRepository.class,
        entityManagerFactoryRef = "firstEntityManagerFactory",
        transactionManagerRef = "firstTransactionManager"
)
public class FirstEntityManagerConfiguration {

    @Bean("firstDataSource")
    DataSource firstDataSource(DataSourceProperties.FirstProperties dataSourceProperties) {
        return DataSourceBuilder
                .create()
                .url(dataSourceProperties.url())
                .username(dataSourceProperties.username())
                .password(dataSourceProperties.password())
                .build();
    }

    @Bean("firstEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean firstEntityManagerFactory(
            @Qualifier("firstDataSource") DataSource dataSource) {
        final var localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); // instantiates the persistence provider
        //localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class); // also works, but who knows which default were not initialized if used instead of jpa vendor adapter
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.example.dual_entity_managers.model"); // searches for manager entities, instead of using persistence.xml
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("firstPU"); // optional
        return localContainerEntityManagerFactoryBean;
    }

    @Bean("firstTransactionManager")
    PlatformTransactionManager firstTransactionManager(
            @Qualifier("firstEntityManagerFactory")EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
