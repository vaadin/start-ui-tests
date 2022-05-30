package com.example.application.views.authenticated;

import javax.annotation.security.PermitAll;

import com.example.application.views.MainLayout;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Protected")
@Route(value = "protected", layout = MainLayout.class)
@PermitAll
public class ProtectedView extends VerticalLayout {

    public ProtectedView() {
        setSpacing(false);

        add(new H2("This is a protected page"));
        add(new Paragraph("Only logged user can access this content"));
    }

}
