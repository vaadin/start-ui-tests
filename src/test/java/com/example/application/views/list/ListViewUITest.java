package com.example.application.views.list;

import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.example.application.security.SecurityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonTester;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridTester;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldTester;
import com.vaadin.testbench.unit.SpringUIUnitTest;
import com.vaadin.testbench.unit.TreeOnFailureExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings({ "unchecked", "rawtypes" })
@ExtendWith(TreeOnFailureExtension.class)
@WithMockUser
@ContextConfiguration(classes = ListViewUITest.Config.class)
class ListViewUITest extends SpringUIUnitTest {

    @Autowired
    CrmService crmService;

    ListView view;
    GridTester<Grid<Contact>, Contact> grid_;
    TextFieldTester<TextField, String> filter_;
    ButtonTester addContact_;

    @BeforeEach
    void setupReferences() {
        view = navigate(ListView.class);
        grid_ = test(view.grid);
        filter_ = test(view.filterText);
        addContact_ = test($(Button.class).withCaption("Add contact").single());
    }

    @Test
    void enterView_editFormNotVisible() {
        var x = test($(Grid.class).first());
        assertFalse($(ContactForm.class).exists(),
                "Contact form should not be visible when entering list view");
    }

    @Test
    void enterView_gridPopulated() {
        assertTrue(grid_.size() > 0, "Contacts should be visible on grid");
    }

    @Test
    void filterByName_gridContentIsFiltered() {
        int allItems = grid_.size();
        filter_.setValue("ar");
        assertTrue(allItems > grid_.size(),
                "Expecting filtered grid to show less than " + allItems
                        + " rows but got " + grid_.size());

        // clear filter
        filter_.setValue(filter_.getComponent().getEmptyValue());
        assertEquals(allItems, grid_.size(),
                "Filter removed, expecting all items to be shown " + allItems
                        + " and now " + grid_.size());
    }

    @Test
    void sortByFirstName_gridSorted() {
        TODO();
    }

    @Test
    void sortByLastName_gridSorted() {
        TODO();
    }

    @Test
    void sortByEmail_gridSorted() {
        TODO();
    }

    @Test
    void selectGridRow_editFormShown() {
        grid_.select(2);

        Contact selected = grid_.getSelected().iterator().next();
        assertNotNull(selected);

        assertTrue($(ContactForm.class).exists(),
                "Contact form should be visible when selecting a contact from the list");

        assertEquals(selected.getFirstName(), view.form.firstName.getValue(),
                "firstname");
        assertEquals(selected.getLastName(), view.form.lastName.getValue(),
                "lastname");
        assertEquals(selected.getEmail(), view.form.email.getValue(), "email");
        assertEquals(selected.getCompany(), view.form.company.getValue(),
                "company");
        assertEquals(selected.getStatus(), view.form.status.getValue(),
                "status");

        assertTrue(test(view.form.save).isUsable(),
                "save button should be usable");
        assertTrue(test(view.form.delete).isUsable(),
                "delete button should be usable");
        assertTrue(test(view.form.close).isUsable(),
                "cancel button should be usable");
    }

    @Test
    void addContact_editFormShown() {
        addContact_.click();

        assertTrue($(ContactForm.class).exists(),
                "Contact form should be visible when adding a new contact");

        assertTrue(view.form.firstName.isEmpty(), "firstname should be empty");
        assertTrue(view.form.lastName.isEmpty(), "lastName should be empty");
        assertTrue(view.form.email.isEmpty(), "email should be empty");
        assertTrue(view.form.company.isEmpty(), "company should be empty");
        assertTrue(view.form.status.isEmpty(), "status should be empty");

        assertFalse(test(view.form.save).isUsable(),
                "save button should not be usable");
        assertFalse(test(view.form.delete).isUsable(),
                "delete button should not be usable");
        assertTrue(test(view.form.close).isUsable(),
                "cancel button should be usable");
    }

    @Test
    void contactForm_invalidValues_errorsShownAndFormCannotBeSubmitted() {
        var firstname_ = test(view.form.firstName);
        var lastName_ = test(view.form.lastName);
        var email_ = test(view.form.email);
        var company_ = test(view.form.company);
        var status_ = test(view.form.status);

        grid_.select(2);
        assertTrue(test(view.form.save).isUsable(),
                "save button should be usable");

        firstname_.setValue("");
        assertTrue(firstname_.getComponent().isInvalid());
        assertFalse(test(view.form.save).isUsable(),
                "invalid firstname, save button should not be usable");
        firstname_.setValue("some value");
        assertFalse(firstname_.getComponent().isInvalid());
        assertTrue(test(view.form.save).isUsable(),
                "invalid firstname, save button should not be usable");

        lastName_.setValue("");
        assertTrue(lastName_.getComponent().isInvalid());
        assertFalse(test(view.form.save).isUsable(),
                "invalid lastname, save button should not be usable");
        lastName_.setValue("some value");
        assertFalse(lastName_.getComponent().isInvalid());
        assertTrue(test(view.form.save).isUsable(),
                "valid lastname, save button should not be usable");

        email_.setValue("xxxxxxx");
        assertTrue(email_.getComponent().isInvalid());
        assertFalse(test(view.form.save).isUsable(),
                "invalid email, save button should not be usable");
        email_.setValue("a@b.com");
        assertFalse(email_.getComponent().isInvalid());
        assertTrue(test(view.form.save).isUsable(),
                "valid email, save button should not be usable");

        company_.selectItem(null);
        assertTrue(company_.getComponent().isInvalid());
        assertFalse(test(view.form.save).isUsable(),
                "invalid company, save button should not be usable");
        company_.selectItem(company_.getSuggestions().get(1));
        assertFalse(company_.getComponent().isInvalid());
        assertTrue(test(view.form.save).isUsable(),
                "valid company, save button should not be usable");

        status_.selectItem(null);
        assertTrue(status_.getComponent().isInvalid());
        assertFalse(test(view.form.save).isUsable(),
                "invalid status, save button should not be usable");
        status_.selectItem(status_.getSuggestions().get(1));
        assertFalse(status_.getComponent().isInvalid());
        assertTrue(test(view.form.save).isUsable(),
                "valid status, save button should not be usable");
    }

    @Test
    void contactForm_cancel_formClosed() {
        addContact_.click();

        assertTrue($(ContactForm.class).exists(),
                "Contact form should be visible when adding a new contact");

        test(view.form.close).click();
        assertFalse($(ContactForm.class).exists(),
                "Contact form should be close after pressing cancel button");

        grid_.select(3);
        assertTrue($(ContactForm.class).exists(),
                "Contact form should be visible when contact is selected");

        test(view.form.close).click();
        assertFalse($(ContactForm.class).exists(),
                "Contact form should be close after pressing cancel button");

    }

    @Test
    void delete_contactDeletedAndGridRefreshed() {
        grid_.select(3);
        int originalSize = grid_.size();
        Contact selected = grid_.getSelected().iterator().next();

        assertTrue($(ContactForm.class).exists(),
                "Contact form should be visible when adding a new contact");

        ButtonTester<Button> delete_ = test(view.form.delete);
        assertTrue(delete_.isUsable(), "Delete button should be usable");

        delete_.click();

        assertEquals(originalSize - 1, grid_.size());
        assertFalse(crmService.findAllContacts("").contains(selected),
                "Contact should have been deleted but was not");

        assertFalse($(ContactForm.class).exists(),
                "Contact form should be closed after deleting a contact");
    }

    @Test
    void save_addedContactPresentOnGrid() {
        // Ensure contact does not exist
        filter_.setValue("XXFirstnameXX");
        assertEquals(0, grid_.size());

        // Remove filter to fill grid
        filter_.setValue(filter_.getComponent().getEmptyValue());

        addContact_.click();
        int originalSize = grid_.size();

        assertTrue($(ContactForm.class).exists(),
                "Contact form should be visible when adding a new contact");

        var company_ = test(view.form.company);
        var status_ = test(view.form.status);
        var save_ = test(view.form.save);

        test(view.form.firstName).setValue("XXFirstnameXX");
        test(view.form.lastName).setValue("Lastname");
        test(view.form.email).setValue("first@last.org");
        company_.selectItem(company_.getSuggestions().get(2));
        status_.selectItem(status_.getSuggestions().get(3));

        assertTrue(save_.isUsable(), "Save button should be usable");

        save_.click();

        assertEquals(originalSize + 1, grid_.size());

        filter_.setValue("XXFirstnameXX");
        assertEquals(1, grid_.size());
    }

    private void TODO() {
        Assertions.fail("TODO: test not yet implemented");
    }

    @Configuration
    @Import(SecurityService.class)
    static class Config {

        @Bean
        CrmService crmService() {
            return new MockCrmService();
        }
    }
}
