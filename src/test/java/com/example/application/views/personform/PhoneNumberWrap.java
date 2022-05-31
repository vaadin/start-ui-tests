package com.example.application.views.personform;

import java.util.List;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBoxWrap;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldWrap;
import com.vaadin.testbench.unit.ComponentWrap;
import com.vaadin.testbench.unit.Wraps;

@Wraps(PersonFormView.PhoneNumberField.class)
public class PhoneNumberWrap extends ComponentWrap<PersonFormView.PhoneNumberField> {
    final ComboBoxWrap<ComboBox<String>, String> combo_;
    final TextFieldWrap<TextField, String> number_;

    public PhoneNumberWrap(PersonFormView.PhoneNumberField component) {
        super(component);
        combo_ = new ComboBoxWrap<>(
                getComponent().countryCode);
        number_ = new TextFieldWrap<>(getComponent().number);
    }

    public List<String> getCountryCodes() {
        return combo_.getSuggestionItems();
    }

    public void setCountryCode(String code) {
        ensureComponentIsUsable();
        if(!getCountryCodes().contains(code)) {
            throw new IllegalArgumentException("Given code is not available for selection");
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
