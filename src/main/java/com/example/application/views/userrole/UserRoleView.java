package com.example.application.views.userrole;

import javax.annotation.security.RolesAllowed;

import com.example.application.views.MainLayout;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("User Role")
@Route(value = "user-role", layout = MainLayout.class)
@RolesAllowed("USER")
public class UserRoleView extends VerticalLayout {

    public UserRoleView() {
        setSpacing(false);

        add(new H2("This is a protected page"));
        add(new Paragraph("Permission grant only to users with USER role"));
    }

}
