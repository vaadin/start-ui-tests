package com.example.application.views.anonymous;

import com.example.application.views.MainLayout;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Public")
@Route(value = "public", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class PublicView extends VerticalLayout {

    public PublicView() {
        setSpacing(false);

        add(new H2("This is a public page"));
        add(new Paragraph("Anonymous and logged user can access this content"));
    }

}
