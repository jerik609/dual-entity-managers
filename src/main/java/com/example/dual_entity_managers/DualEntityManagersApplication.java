package com.example.dual_entity_managers;

import com.example.dual_entity_managers.database.DataSourceProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties({DataSourceProperties.FirstProperties.class, DataSourceProperties.SecondProperties.class})
public class DualEntityManagersApplication {

    private static final Log log = LogFactory.getLog(DualEntityManagersApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DualEntityManagersApplication.class, args);
	}

    @Bean
    ApplicationRunner applicationRunner(ApplicationContext applicationContext) {
        return __ -> Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(name -> log.info("BEAN -> " + name));
    }

}
