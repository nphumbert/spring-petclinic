package org.springframework.samples.petclinic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.xml.MarshallingView;

import java.util.Collections;

@Configuration
public class MvcViewConfig {

    @Bean
    public ViewResolver contentNegotiatingViewResolver(){
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setUseNotAcceptableStatusCode(true);
        JstlView view = new JstlView();
        view.setUrl("");
        resolver.setDefaultViews(Collections.<View>singletonList(view));
        return resolver;
    }

    @Bean
    public BeanNameViewResolver beanNameViewResolver() {
        return new BeanNameViewResolver();
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean(name = "vets/vetList.xml")
    public MarshallingView marshallingView() {
        MarshallingView marshallingView = new MarshallingView();
        marshallingView.setMarshaller(marshaller());
        return marshallingView;
    }

    @Bean()
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Vets.class);
        return marshaller;
    }
}
