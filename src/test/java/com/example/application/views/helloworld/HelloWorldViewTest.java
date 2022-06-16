package com.example.application.views.helloworld;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.testbench.unit.TreeOnFailureExtension;
import com.vaadin.testbench.unit.UIUnitTest;
import com.vaadin.testbench.unit.ViewPackages;

@ExtendWith(TreeOnFailureExtension.class)
@ViewPackages(packages = "com.example.application")
class HelloWorldViewTest extends UIUnitTest {

    @Test
    public void validateDefaultNotification() {
        final HelloWorldView helloView = navigate(HelloWorldView.class);
        test(helloView.sayHello).click();

        Notification notification = $(Notification.class).first();
        Assertions.assertEquals("Hello ", test(notification).getText());
    }

    @Test
    public void validateTextFieldUsage() {
        final HelloWorldView helloView = navigate(HelloWorldView.class);

        test(helloView.name).setValue("Test");
        test(helloView.sayHello).click();

        Notification notification = $(Notification.class).first();
        Assertions.assertEquals("Hello Test", test(notification).getText());
    }
}
