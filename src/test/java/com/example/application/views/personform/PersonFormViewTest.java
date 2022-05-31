package com.example.application.views.personform;

import com.example.application.data.service.SamplePersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonWrap;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldWrap;
import com.vaadin.testbench.unit.ComponentWrapPackages;
import com.vaadin.testbench.unit.SpringUIUnitTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ComponentWrapPackages("com.example.application.views.personform")
class PersonFormViewTest extends SpringUIUnitTest {

    PersonFormView view;

    @Autowired
    SamplePersonService personService;

    @BeforeEach
    void init() {
        view = navigate(PersonFormView.class);
    }

    @Test
    void emptyForm_canSavePerson(){
        final ButtonWrap<Button> button_ = wrap(view.save);
        button_.click();

        $(Notification.class).first();
        assertTrue(personService.count() >0);
    }

    @Test
    void phoneNumberField_fillSelections_returnsCombinedValue(){
        final PhoneNumberWrap number_ = wrap(view.phone);
        number_.setCountryCode("+44");
        assertThrows(IllegalArgumentException.class, ()->number_.setNumber("locust"), "TextField only accepts numbers");

        number_.setNumber("452324561");

        assertEquals("+44 452324561", number_.getValue());
    }

    @Test
    void clearButton_emptiesFilledFields() {
        final TextFieldWrap<TextField, String> fname_ = wrap(view.firstName);
        final TextFieldWrap<TextField, String> lname_ = wrap(view.lastName);
        fname_.setValue("John");
        lname_.setValue("Doe");

        final ButtonWrap<Button> cancel_ = wrap(view.cancel);
        cancel_.click();

        assertTrue(view.firstName.isEmpty());
        assertTrue(view.lastName.isEmpty());
    }
}