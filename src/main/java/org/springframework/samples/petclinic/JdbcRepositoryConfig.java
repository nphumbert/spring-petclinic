package org.springframework.samples.petclinic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jdbc")
@ComponentScan("org.springframework.samples.petclinic.repository.jdbc")
public class JdbcRepositoryConfig {
}
