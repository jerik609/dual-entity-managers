# Demonstrates the usage of two entity managers for two distinct JPA repositories

## Solution
Basically this: 
- https://www.javatodev.com/multiple-datasources-with-spring-boot-data-jpa/
- https://medium.com/@AlexanderObregon/how-spring-boot-handles-multiple-datasources-internally-fb2dcc3dae57

JPA repository autoconfiguration takes case or many things, even in a partially manual mode.

It creates JPA repositories automatically if a default (named in a standard way) data source, entity manager (factory) and JPA transaction manager are present.

If a more distinctive setup is used, then we need to prepare all the beans manually and instruct the JPA scanning mechanism how to assemble the individual repositories - this is what this example does.

The Spring Data JPA will still wrap the repositories in transactional proxies using the provided platform transaction managers (they must be of JPA type!).

## Database
`
CREATE TABLE example.first (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  data VARCHAR(256) NOT NULL,
  extended_data VARCHAR(256) NOT NULL,
  modified timestamp not null
);
`
`
CREATE TABLE example.first (
id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
data VARCHAR(256) NOT NULL,
extended_data VARCHAR(256) NOT NULL,
modified timestamp not null
);
`

## Bits and snippets
1. `@EnableTransactionManagement` is not needed in spring boot, see: https://stackoverflow.com/questions/40724100/enabletransactionmanagement-in-spring-boot
2. `@Transactional` is not needed when working with JPA repositories, as they are automatically wrapped in proxies
3. Constructor binding cannot be used with certain bean creation mechanisms, e.g. `@Component`, see: https://docs.spring.io/spring-boot/docs/3.0.13-SNAPSHOT/api/org/springframework/boot/context/properties/ConstructorBinding.html

## Links
### General guides to dual entity manager setup
- https://stackoverflow.com/questions/43509145/spring-boot-multiple-data-sources-using-entitymanager
- https://javanexus.com/blog/mastering-spring-data-dual-entity-managers
- https://www.javatodev.com/multiple-datasources-with-spring-boot-data-jpa/
- https://stackoverflow.com/questions/46574686/spring-boot-2-0-0-m4-hibernate-5-2-11-final-could-not-find-bean-of-type-entity/51305724#51305724
- https://stackoverflow.com/questions/45663025/spring-data-jpa-multiple-enablejparepositories
- https://stackoverflow.com/questions/40724100/enabletransactionmanagement-in-spring-boot
- https://stackoverflow.com/questions/53934077/spring-boot-jpa-set-custom-datasource
- https://docs.spring.io/spring-boot/how-to/data-access.html#howto.data-access.configure-two-datasources

### Links from debugging
- https://stackoverflow.com/questions/46574686/spring-boot-2-0-0-m4-hibernate-5-2-11-final-could-not-find-bean-of-type-entity/51305724#51305724
- https://stackoverflow.com/questions/1965454/showing-a-spring-transaction-in-log
