package com.example.application.views.masterdetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import com.example.application.data.entity.SamplePerson;
import com.example.application.data.generator.DataGenerator;
import com.example.application.data.service.SamplePersonRepository;
import com.example.application.test.InMemorySamplePersonRepository;
import com.example.application.test.TestInstantiatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonWrap;
import com.vaadin.flow.component.checkbox.CheckboxWrap;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridWrap;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationWrap;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldWrap;
import com.vaadin.testbench.unit.ComponentQuery;
import com.vaadin.testbench.unit.UIUnitTest;

@ExtendWith({ SpringExtension.class })
@Import(MasterDetailViewTest.TestConfig.class)
class MasterDetailViewTest extends UIUnitTest {

    @Autowired
    SamplePersonRepository repository;

    List<SamplePerson> people;
    private GridWrap<Grid<SamplePerson>, SamplePerson> grid_;
    private MasterDetailView view;

    @Override
    protected Set<Class<?>> lookupServices() {
        return Set.of(TestInstantiatorFactory.class);
    }

    @BeforeEach
    void setupTestData() {
        view = (MasterDetailView) getCurrentView();
        grid_ = $(Grid.class).first();
        people = repository.findAll(Pageable.ofSize(10)).toList();
    }

    @Test
    void gridContainsData() {
        Assertions.assertTrue(grid_.size() > 20,
                "With the generated data, there should be at least twenty rows in the grid, but found "
                        + grid_.size());

        SamplePerson person = people.get(0);
        Assertions.assertEquals(person.getFirstName(), grid_.getCellText(0, 0));
        Assertions.assertEquals(person.getLastName(), grid_.getCellText(0, 1));
        Assertions.assertEquals(person.getEmail(), grid_.getCellText(0, 2));

        Assertions.assertTrue(grid_.getSelected().isEmpty(),
                "Expecting no rows to be selected when entering the view without parameters");
    }

    @Test
    void selectPersonOnGrid_editFormIsFilled() {
        int selectedRow = ThreadLocalRandom.current().nextInt(0, people.size());
        SamplePerson person = people.get(selectedRow);

        grid_.clickRow(selectedRow);

        List<SamplePerson> selectedRows = new ArrayList<>(grid_.getSelected());
        Assertions.assertEquals(1, selectedRows.size(),
                "Expecting a single row to be selected");
        Assertions.assertSame(person, selectedRows.get(0));

        assertEditFormIsFilled(person);

        Assertions.assertTrue(
                $view(Button.class).withTheme("primary").first().isUsable(),
                "Expecting Save button to be enabled");
    }

    @Test
    void selectPersonOnGrid_singleSelectionIsAllowed() {
        grid_.clickRow(2);
        Assertions.assertEquals(1, grid_.getSelected().size(),
                "Expecting a single row to be selected");
        Assertions.assertSame(people.get(2),
                grid_.getSelected().iterator().next());

        grid_.clickRow(4);
        Assertions.assertEquals(1, grid_.getSelected().size(),
                "Expecting a single row to be selected");
        Assertions.assertSame(people.get(4),
                grid_.getSelected().iterator().next());
    }

    @Test
    void deselectPerson_editFormIsCleared() {
        TextField textField = $textField(q -> q.withCaption("First Name"))
                .getComponent();
        Assertions.assertTrue(textField.isEmpty(),
                "Expecting field to be null when entering the form");

        grid_.clickRow(2);
        Assertions.assertFalse(textField.isEmpty(),
                "Expecting field to be filled when selecting row");

        grid_.clickRow(2);
        Assertions.assertTrue(textField.isEmpty(),
                "Expecting field to be cleared when deselecting row");
    }

    @Test
    void enterViewWithPersonIdParameter_rowSelectedAndEditFormFilled() {
        SamplePerson person = people
                .get(ThreadLocalRandom.current().nextInt(0, people.size()));
        navigate(String.format("master-detail/%s/edit", person.getId()),
                MasterDetailView.class);
        assertEditFormIsFilled(person);
    }

    @Test
    void enterViewWithWrongPersonIdParameter_notificationIsShown() {
        UUID wrongId = UUID.randomUUID();
        navigate(String.format("master-detail/%s/edit", wrongId),
                MasterDetailView.class);
        NotificationWrap<?> notification_ = $(Notification.class)
                .withResultsSize(1).first();
        String notificationText = notification_.getText();
        Assertions.assertTrue(
                notificationText.contains("samplePerson was not found"));
        Assertions.assertTrue(notificationText.contains("ID = " + wrongId));
    }

    @Test
    void cancel_clearsEditFormAndGridSelection() {
        grid_.select(1);
        assertEditFormIsFilled(people.get(1));

        ButtonWrap<Button> cancel_ = wrap(view.cancel);
        cancel_.click();

        Assertions.assertTrue(grid_.getSelected().isEmpty());
        Assertions.assertTrue(
                $(AbstractField.class).allComponents().stream()
                        .allMatch(AbstractField::isEmpty),
                "Expecting all form fields to be empty");
    }

    @Test
    void save_personAddedToGrid() {
        $textField(view.firstName).setValue("Attilio");
        $textField(view.lastName).setValue("Regolo");
        $textField(view.email).setValue("aregolo@spqr.org");
        wrap(CheckboxWrap.class, view.important).click();

        wrap(ButtonWrap.class, view.save).click();

        Assertions.assertTrue(
                $(AbstractField.class).allComponents().stream()
                        .allMatch(AbstractField::isEmpty),
                "Expecting form to be cleared, but was not");

        NotificationWrap<?> notification_ = $(Notification.class)
                .withResultsSize(1).first();
        String notificationText = notification_.getText();
        Assertions.assertTrue(
                notificationText.contains("SamplePerson details stored"));

        Assertions.assertEquals(1L, repository.findAll().stream()
                .filter(p -> "aregolo@spqr.org".equals(p.getEmail())).count());

    }

    private void assertEditFormIsFilled(SamplePerson person) {
        Assertions.assertEquals(person.getFirstName(),
                $textField(q -> q.withCaption("First Name")).getComponent()
                        .getValue());
        Assertions.assertEquals(person.getLastName(),
                $textField(q -> q.withCaption("Last Name")).getComponent()
                        .getValue());
        Assertions.assertEquals(person.getEmail(),
                $textField(q -> q.withCaption("Email")).getComponent()
                        .getValue());
        Assertions.assertEquals(person.getPhone(),
                $textField(q -> q.withCaption("Phone")).getComponent()
                        .getValue());
        Assertions.assertEquals(person.getOccupation(),
                $textField(q -> q.withCaption("Occupation")).getComponent()
                        .getValue());
    }

    @SuppressWarnings("unchecked")
    private TextFieldWrap<TextField, String> $textField(TextField textField) {
        return wrap(TextFieldWrap.class, textField);
    }

    private TextFieldWrap<TextField, String> $textField(
            Consumer<ComponentQuery<TextField>> statement) {
        ComponentQuery<TextField> query = $(TextField.class);
        statement.accept(query);
        return query.first();
    }

    @Configuration
    @ComponentScan("com.example.application.data")
    @Import(TestInstantiatorFactory.class)
    static class TestConfig {

        @Bean
        SamplePersonRepository samplePersonRepository(
                DataGenerator dataGenerator) throws Exception {
            InMemorySamplePersonRepository repository = new InMemorySamplePersonRepository();
            dataGenerator.loadData(repository).run();
            return repository;
        }
    }
}