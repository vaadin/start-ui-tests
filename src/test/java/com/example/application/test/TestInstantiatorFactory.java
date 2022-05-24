package com.example.application.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.vaadin.flow.di.Instantiator;
import com.vaadin.flow.di.InstantiatorFactory;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.spring.SpringInstantiator;

public class TestInstantiatorFactory /* extends SpringLookupInitializer */
        implements InstantiatorFactory {
    private static ApplicationContext appCtx;

    @Autowired
    public TestInstantiatorFactory(ApplicationContext appCtx) {
        TestInstantiatorFactory.appCtx = appCtx;
    }

    // For Lookup
    public TestInstantiatorFactory() {
    }

    @Override
    public Instantiator createInstantitor(VaadinService service) {
        return new SpringInstantiator(service, appCtx);
    }
}
