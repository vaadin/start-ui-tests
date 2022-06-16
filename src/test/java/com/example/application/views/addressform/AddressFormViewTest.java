package com.example.application.views.addressform;

import com.example.application.data.service.SampleAddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.testbench.unit.SpringUIUnitTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AddressFormViewTest extends SpringUIUnitTest {

    @Autowired
    private SampleAddressService service;

    private AddressFormView view;

    @BeforeEach
    void init() {
        view = navigate(AddressFormView.class);
    }

    @Test
    void emptyForm_canSavePerson() {
        test(view.save).click();

        $(Notification.class).first();
        assertTrue(service.count() > 0);
    }

    @Test
    void postalCode_validatesInput() {
        test(view.postalCode).setValue("20100");

        assertThrows(IllegalArgumentException.class,
                () -> test(view.postalCode).setValue("20P-11"),
                "Only numbers should be accepted as postal code");
    }

    @Test
    void clearButton_emptiesFilledFields() {
        test(view.city).setValue("John");
        test(view.postalCode).setValue("20100");

        test(view.cancel).click();

        assertTrue(view.city.isEmpty());
        assertTrue(view.postalCode.isEmpty());
    }
}