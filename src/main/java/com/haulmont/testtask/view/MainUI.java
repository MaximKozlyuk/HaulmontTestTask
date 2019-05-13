package com.haulmont.testtask.view;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
@StyleSheet("vaadin://style.css")
public class MainUI extends UI {

    private static final long serialVersionUID = 1L;

    private VerticalLayout layout = new VerticalLayout();

    private PatientView patientView = new PatientView();
    private DoctorView doctorView;
    private RecipeView recipeView;

    private Layout currentView = patientView;

    @Override
    protected void init(VaadinRequest request) {

        addHeader(layout);

        layout.addComponent(patientView);
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);

    }

    private void addHeader(AbstractOrderedLayout outerLayout) {
        Header header = new Header();
        header.getPatients().addClickListener(event -> {
            currentView.setVisible(false);
            currentView = patientView;
            currentView.setVisible(true);
        });
        header.getRecipes().addClickListener(event -> {
            currentView.setVisible(false);
            if (recipeView == null) {
                recipeView = new RecipeView();
                layout.addComponent(recipeView);
            }
            currentView = recipeView;
            currentView.setVisible(true);
        });
        header.getDocs().addClickListener(event -> {
            currentView.setVisible(false);
            if (doctorView == null) {
                doctorView = new DoctorView();
                layout.addComponent(doctorView);
            }
            currentView = doctorView;
            currentView.setVisible(true);
        });
        outerLayout.addComponent(header);
    }

}