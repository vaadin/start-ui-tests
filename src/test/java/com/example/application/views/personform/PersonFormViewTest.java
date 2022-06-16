package com.example.application.views.personform;

import com.example.application.data.service.SamplePersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.testbench.unit.ComponentTesterPackages;
import com.vaadin.testbench.unit.SpringUIUnitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ComponentTesterPackages("com.example.application.views.personform")
class PersonFormViewTest extends SpringUIUnitTest {

    PersonFormView view;

    @Autowired
    SamplePersonService personService;

    @BeforeEach
    void init() {
        view = navigate(PersonFormView.class);
    }

    @Test
    void emptyForm_canSavePerson() {
        test(view.save).click();

        $(Notification.class).first();
        assertTrue(personService.count() > 0);
    }

    @Test
    void phoneNumberField_fillSelections_returnsCombinedValue() {
        final PhoneNumberTester number_ = test(view.phone);
        number_.setCountryCode("+44");
        assertThrows(IllegalArgumentException.class,
                () -> number_.setNumber("locust"),
                "TextField only accepts numbers");

        number_.setNumber("452324561");

        assertEquals("+44 452324561", number_.getValue());
    }

    @Test
    void clearButton_emptiesFilledFields() {
        test(view.firstName).setValue("John");
        test(view.lastName).setValue("Doe");

        test(view.cancel).click();

        assertTrue(view.firstName.isEmpty());
        assertTrue(view.lastName.isEmpty());
    }
}