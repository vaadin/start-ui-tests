package com.example.application.views.helloworld;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.testbench.unit.ButtonWrap;
import com.vaadin.testbench.unit.NotificationWrap;
import com.vaadin.testbench.unit.TextFieldWrap;
import com.vaadin.testbench.unit.UIUnitTest;

class HelloWorldViewTest extends UIUnitTest {

    @Override
    protected String scanPackage() {
        return "com.example.application";
    }

    @Test
    public void validateDefaultNotification() {
        final HelloWorldView helloView = navigate(HelloWorldView.class);
        $(ButtonWrap.class, helloView.sayHello).click();

        NotificationWrap notification_ = select(Notification.class).first();
        Assertions.assertEquals("Hello ", notification_.getText());
    }

    @Test
    public void validateTextFieldUsage() {
        final HelloWorldView helloView = navigate(HelloWorldView.class);

        $(TextFieldWrap.class, helloView.name).setValue("Test");
        $(ButtonWrap.class, helloView.sayHello).click();

        NotificationWrap notification_ = select(Notification.class).first();
        Assertions.assertEquals("Hello Test", notification_.getText());
    }
}
