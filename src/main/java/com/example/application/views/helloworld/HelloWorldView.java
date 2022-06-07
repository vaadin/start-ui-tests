package com.example.application.views.helloworld;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Hello World")
@Route(value = "hello")
@RouteAlias(value = "")
public class HelloWorldView extends HorizontalLayout {

    TextField name;
    Button sayHello;

    public HelloWorldView() {
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(
                e -> Notification.show("Hello " + name.getValue()));

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        Button modalButton = new Button("modal",
                ev -> Notification.show("Enter from modal dialog"));
        modalButton.addClickShortcut(Key.ENTER);
        Div div = new Div(modalButton);
        add(name, sayHello,
                new Button("Open Modal", ev -> new Dialog(div).open()));

        sayHello.addClickShortcut(Key.ENTER);

        Shortcuts.addShortcutListener(name, event -> name.setValue("Anonymous"),
                Key.KEY_F, KeyModifier.ALT, KeyModifier.SHIFT);
        Shortcuts.addShortcutListener(name,
                event -> name.setValue(name.getEmptyValue()), Key.KEY_D,
                KeyModifier.ALT, KeyModifier.SHIFT);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        attachEvent.getUI().addShortcutListener(
                () -> Notification.show("Data cleaned"), Key.KEY_D,
                KeyModifier.ALT, KeyModifier.SHIFT);
    }
}
