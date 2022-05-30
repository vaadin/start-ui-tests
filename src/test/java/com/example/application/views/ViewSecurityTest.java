package com.example.application.views;

import java.util.List;
import java.util.stream.Collectors;

import com.example.application.views.adminrole.AdminRoleView;
import com.example.application.views.anonymous.PublicView;
import com.example.application.views.authenticated.ProtectedView;
import com.example.application.views.login.LoginView;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.router.RouteNotFoundError;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.testbench.unit.SpringUIUnitTest;

@SpringBootTest
public class ViewSecurityTest extends SpringUIUnitTest {

    @Test
    @WithAnonymousUser
    void anonymousUser_publicView_signInLinkPresent() {
        // public view is default page
        Assertions.assertInstanceOf(PublicView.class, getCurrentView());

        Assertions.assertTrue(
                $(Anchor.class).withText("Sign in").first().isUsable(),
                "Sign in link should be available for anonymous user");
    }

    @Test
    @WithAnonymousUser
    void anonymousUser_protectedView_redirectToLogin() {
        navigate("protected", LoginView.class);
    }

    @Test
    @WithMockUser(username = "user")
    void user_protectedView_userInfoVisible() {
        navigate(ProtectedView.class);

        Assertions.assertTrue(
                $(Avatar.class).first().getComponent().isVisible(),
                "Avatar should be visible for logged users");
        String username = $(Footer.class).thenOnFirst(Span.class)
                .withClassName("font-medium", "text-s", "text-secondary")
                .first().getComponent().getText();
        Assertions.assertEquals("John Normal", username);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminUser_adminView_viewShown() {
        navigate(AdminRoleView.class);

        Assertions.assertTrue(
                $(Avatar.class).first().getComponent().isVisible(),
                "Avatar should be visible for logged users");
        String username = $(Footer.class).thenOnFirst(Span.class)
                .withClassName("font-medium", "text-s", "text-secondary")
                .first().getComponent().getText();
        Assertions.assertEquals("Emma Powerful", username);
    }

    @Test
    @WithMockUser(username = "user")
    void user_adminView_accessDenied() {
        RouteNotFoundError errorView = navigate("admin-role",
                RouteNotFoundError.class);
        Assertions.assertTrue(
                errorView.getElement().getChild(0).getOuterHTML()
                        .contains("Reason: Access denied"),
                "Admin view should not be accessible to users with USER role");
    }

    @Test
    @WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
    void adminUser_fullMenu() {
        List<String> menuLinks = getMenuLinks();
        Assertions.assertIterableEquals(
                List.of("public", "protected", "user-role", "admin-role"),
                menuLinks);
    }

    @Test
    @WithMockUser(username = "user")
    void user_restrictedMenu() {
        List<String> menuLinks = getMenuLinks();
        Assertions.assertIterableEquals(
                List.of("public", "protected", "user-role"), menuLinks);
    }

    @Test
    @WithAnonymousUser
    void anonymousUser_onlyPublicPageLink() {
        List<String> menuLinks = getMenuLinks();
        Assertions.assertIterableEquals(List.of("public"), menuLinks);
    }

    private List<String> getMenuLinks() {
        List<String> menuLinks = $(UnorderedList.class)
                .withClassName("navigation-list").thenOnFirst(RouterLink.class)
                .withClassName("menu-item-link").allComponents().stream()
                .map(RouterLink::getHref).collect(Collectors.toList());
        return menuLinks;
    }

}
