package com.example.application.views.about;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.testbench.unit.UIUnitTest;

class AboutViewTest extends UIUnitTest {

    @Override
    protected String scanPackage() {
        return "com.example.application";
    }

    @Test
    public void navigateByStringInLink() {
        String aboutString = getMenuItemLink();

        AboutView about = navigate(aboutString, AboutView.class);

        Assertions.assertTrue(
                select(Image.class).from(about).first().getComponent()
                        .isVisible(),
                "Image should be visible on the AboutView");
    }

    @Nullable
    private String getMenuItemLink() {
        final List<RouterLink> menuItems = select(
                RouterLink.class).withCondition(
                        link -> link.getClassName().contains("menu-item-link"))
                .allComponents();
        String aboutString = null;
        for (RouterLink link : menuItems) {
            // Missing the withClass query here.
            if (select(Span.class).from(link).withCondition(
                            s -> s.getClassName().contains("menu-item-text")).first()
                    .getComponent().getText().equalsIgnoreCase("about")) {
                aboutString = link.getHref();
                break;
            }
        }
        return aboutString;
    }

    @Test
    public void navigateByClass() {
        AboutView about = navigate(AboutView.class);

        Assertions.assertTrue(
                select(Image.class).from(about).first().getComponent()
                        .isVisible(),
                "Image should be visible on the AboutView");
    }
}
