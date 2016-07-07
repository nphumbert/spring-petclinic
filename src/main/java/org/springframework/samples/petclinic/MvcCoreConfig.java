package org.springframework.samples.petclinic;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:spring/mvc-core-config.xml")
@Import(MvcViewConfig.class)
public class MvcCoreConfig {
}
