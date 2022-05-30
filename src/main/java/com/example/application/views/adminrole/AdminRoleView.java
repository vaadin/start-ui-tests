package com.example.application.views.adminrole;

import javax.annotation.security.RolesAllowed;

import com.example.application.views.MainLayout;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Admin Role")
@Route(value = "admin-role", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class AdminRoleView extends VerticalLayout {

    public AdminRoleView() {
        setSpacing(false);

        add(new H2("This is a protected page"));
        add(new Paragraph("Permission grant only to users with ADMIN role"));
    }

}
