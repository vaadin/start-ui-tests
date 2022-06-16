package com.example.application.views.personform;

import java.util.List;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBoxTester;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldTester;
import com.vaadin.testbench.unit.ComponentTester;
import com.vaadin.testbench.unit.Tests;

@Tests(PersonFormView.PhoneNumberField.class)
public class PhoneNumberTester
        extends ComponentTester<PersonFormView.PhoneNumberField> {
    final ComboBoxTester<ComboBox<String>, String> combo_;
    final TextFieldTester<TextField, String> number_;

    public PhoneNumberTester(PersonFormView.PhoneNumberField component) {
        super(component);
        combo_ = new ComboBoxTester<>(getComponent().countryCode);
        number_ = new TextFieldTester<>(getComponent().number);
    }

    public List<String> getCountryCodes() {
        return combo_.getSuggestionItems();
    }

    public void setCountryCode(String code) {
        ensureComponentIsUsable();
        if (!getCountryCodes().contains(code)) {
            throw new IllegalArgumentException(
                    "Given code is not available for selection");
        }
        combo_.selectItem(code);
    }

    public void setNumber(String number) {
        ensureComponentIsUsable();
        number_.setValue(number);
    }

    public String getValue() {
        return getComponent().generateModelValue();
    }

}
