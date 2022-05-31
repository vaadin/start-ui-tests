package com.example.application.views.addressform;

import com.example.application.data.service.SampleAddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonWrap;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldWrap;
import com.vaadin.testbench.unit.SpringUIUnitTest;

import static org.junit.jupiter.api.Assertions.*;

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
    void emptyForm_canSavePerson(){
        final ButtonWrap<Button> button_ = wrap(view.save);
        button_.click();

        $(Notification.class).first();
        assertTrue(service.count() >0);
    }

    @Test
    void postalCode_validatesInput() {
        final TextFieldWrap<TextField, String> postalCode_ = wrap(view.postalCode);

        postalCode_.setValue("20100");

        assertThrows(IllegalArgumentException.class, () -> postalCode_.setValue("20P-11"), "Only numbers should be accepted as postal code");
    }

    @Test
    void clearButton_emptiesFilledFields() {
        final TextFieldWrap<TextField, String> city_ = wrap(view.city);
        final TextFieldWrap<TextField, String> postalCode_ = wrap(view.postalCode);
        city_.setValue("John");
        postalCode_.setValue("20100");

        final ButtonWrap<Button> cancel_ = wrap(view.cancel);
        cancel_.click();

        assertTrue(view.city.isEmpty());
        assertTrue(view.postalCode.isEmpty());
    }
}