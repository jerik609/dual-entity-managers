package com.example.dual_entity_managers;

import com.example.dual_entity_managers.configurations.DataSourceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(
        //exclude = {JpaRepositoriesAutoConfiguration.class}
)
@EnableConfigurationProperties({DataSourceProperties.FirstProperties.class, DataSourceProperties.SecondProperties.class})
public class DualEntityManagersApplication {

	public static void main(String[] args) {
		SpringApplication.run(DualEntityManagersApplication.class, args);
	}

}
