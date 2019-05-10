package com.haulmont.testtask.frontend;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import lombok.Getter;

@Getter
class Header extends HorizontalLayout {

    private Button patients = new Button("Patients");
    private Button recipes = new Button("Recipes");
    private Button docs = new Button("Doctors");

    Header () {
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        addStyleName("header");
        setWidth(100, Unit.PERCENTAGE);

        Label title = new Label("<h2>Hospital viewer</h2>");
        title.addStyleName("site-title");
        title.setContentMode(ContentMode.HTML);
        addComponents(title, patients, recipes, docs);
    }

}
