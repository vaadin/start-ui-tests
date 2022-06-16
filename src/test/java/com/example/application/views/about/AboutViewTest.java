package com.example.application.views.about;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.testbench.unit.UIUnitTest;
import com.vaadin.testbench.unit.ViewPackages;

@ViewPackages(packages = "com.example.application")
class AboutViewTest extends UIUnitTest {

    @Test
    public void navigateByStringInLink() {
        String aboutString = getMenuItemLink();

        AboutView about = navigate(aboutString, AboutView.class);

        Assertions.assertTrue($(Image.class).from(about).first().isVisible(),
                "Image should be visible on the AboutView");
    }

    @Nullable
    private String getMenuItemLink() {
        final List<RouterLink> menuItems = $(RouterLink.class)
                .withClassName("menu-item-link").all();
        String aboutString = null;
        for (RouterLink link : menuItems) {
            // Missing the withClass query here.
            if ($(Span.class).from(link).withClassName("menu-item-text").first()
                    .getText().equalsIgnoreCase("about")) {
                aboutString = link.getHref();
                break;
            }
        }
        return aboutString;
    }

    @Test
    public void navigateByClass() {
        AboutView about = navigate(AboutView.class);

        Assertions.assertTrue($(Image.class).from(about).first().isVisible(),
                "Image should be visible on the AboutView");
    }
}
