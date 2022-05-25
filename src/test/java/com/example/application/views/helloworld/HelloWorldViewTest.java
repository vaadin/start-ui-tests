package com.example.application.views.helloworld;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonWrap;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationWrap;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldWrap;
import com.vaadin.testbench.unit.TreeOnFailureExtension;
import com.vaadin.testbench.unit.UIUnitTest;

@ExtendWith(TreeOnFailureExtension.class)
class HelloWorldViewTest extends UIUnitTest {

    @Override
    protected String scanPackage() {
        return "com.example.application";
    }

    @Test
    public void validateDefaultNotification() {
        final HelloWorldView helloView = navigate(HelloWorldView.class);
        ButtonWrap<Button> button_ = wrap(helloView.sayHello);
        button_.click();

        NotificationWrap notification_ = $(Notification.class).first();
        Assertions.assertEquals("Hello ", notification_.getText());
    }

    @Test
    public void validateTextFieldUsage() {
        final HelloWorldView helloView = navigate(HelloWorldView.class);

        final TextFieldWrap<TextField, String> text_ = wrap(helloView.name);
        text_.setValue("Test");
        ButtonWrap<Button> button_ = wrap(helloView.sayHello);
        button_.click();

        NotificationWrap notification_ = $(Notification.class).first();
        Assertions.assertEquals("Hello Test", notification_.getText());
    }
}
