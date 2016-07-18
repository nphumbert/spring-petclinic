package org.springframework.samples.petclinic;

import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.web.PetTypeFormatter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Collections;
import java.util.HashSet;

@Configuration
@Import(MvcViewConfig.class)
@ComponentScan("org.springframework.samples.petclinic.web")
@EnableWebMvc
public class MvcCoreConfig extends WebMvcConfigurerAdapter {

    @Bean
    public FormattingConversionServiceFactoryBean conversionService(ClinicService clinicService) {
        FormattingConversionServiceFactoryBean factoryBean = new FormattingConversionServiceFactoryBean();
        factoryBean.setFormatters(new HashSet<>(Collections.singletonList(new PetTypeFormatter(clinicService))));
        return factoryBean;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/messages");
        return messageSource;
    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        exceptionResolver.setDefaultErrorView("exception");
        exceptionResolver.setWarnLogCategory("warn");
        return exceptionResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/vendors/**").addResourceLocations("/vendors/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("welcome");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
