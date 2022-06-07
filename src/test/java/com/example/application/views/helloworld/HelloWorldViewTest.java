package com.example.application.views.helloworld;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonWrap;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationWrap;
import com.vaadin.flow.component.textfield.TextFieldWrap;
import com.vaadin.testbench.unit.UIUnitTest;
import com.vaadin.testbench.unit.ViewPackages;

@ViewPackages
class HelloWorldViewTest extends UIUnitTest {

    private HelloWorldView view;
    private TextFieldWrap name_;

    @BeforeEach
    void setUp() {
        view = navigate(HelloWorldView.class);
        name_ = wrap(TextFieldWrap.class, view.name);
    }

    @Test
    void keyboardShortuct_cleanFormData_showsNotification() {
        name_.setValue("Peter");

        fireShortcut(Key.KEY_D, KeyModifier.ALT, KeyModifier.SHIFT);

        Assertions.assertTrue(view.name.isEmpty());
        NotificationWrap notification_ = $(Notification.class).single();
        Assertions.assertEquals("Data cleaned", notification_.getText());
    }

    @Test
    void enter_showsNotification() {
        name_.setValue("Peter");

        fireShortcut(Key.ENTER);
        NotificationWrap notification_ = $(Notification.class).single();
        Assertions.assertEquals("Hello Peter", notification_.getText());
    }

    @Test
    void shortcutFromModal_showsNotification() {
        ButtonWrap open_ = $(Button.class).withCaption("Open Modal").first();
        open_.click();
        roundTrip();

        fireShortcut(Key.ENTER);
        NotificationWrap notification_ = $(Notification.class).single();
        Assertions.assertEquals("Enter from modal dialog", notification_.getText());
    }

}