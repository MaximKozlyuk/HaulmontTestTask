package com.haulmont.testtask.frontend;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.dao.RecipeDao;
import com.haulmont.testtask.domain.Doctor;
import com.haulmont.testtask.domain.Patient;
import com.haulmont.testtask.domain.Recipe;
import com.haulmont.testtask.frontend.editforms.RecipeForm;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
import java.util.stream.Collectors;

class RecipeView extends VerticalLayout implements Layout {

    private Grid grid = new Grid();

    private Grid docSelectorGrid = new Grid();
    private DoctorDao doctorDao = DoctorDao.get();

    private Grid petientSelectGrid = new Grid();
    private PatientDao patientDao = PatientDao.get();

    private TextField search = new TextField();

    private RecipeForm recipeForm = new RecipeForm(this::updateRecipeList, this::selectDoctor, this::selectPatient);
    private RecipeDao recipeDao = RecipeDao.get();
    private List<Recipe> recipes;

    RecipeView () {
        grid.setColumns("id", "description", "creation", "expired", "priority");
        updateRecipeList(null);

        search.setInputPrompt("Search");

        search.addTextChangeListener(event -> grid.setContainerDataSource(
                new BeanItemContainer<>(Recipe.class,
                        recipes.stream()
                                .filter(patient -> patient.toString().contains(event.getText()))
                                .collect(Collectors.toList()))
        ));

        Button clearFilter = new Button(FontAwesome.TIMES);
        clearFilter.addClickListener(click -> {
            search.clear();
            updateRecipeList(null);
        });

        Button addBtn = new Button("Add");
        addBtn.addClickListener(event -> {
            grid.select(null);
            Recipe newRecipe = new Recipe();
            recipeForm.setPojo(newRecipe);
        });

        CssLayout search = new CssLayout();
        search.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        search.addComponents(this.search, clearFilter);

        HorizontalLayout tools = new HorizontalLayout(search,addBtn);
        tools.setSpacing(true);

        HorizontalLayout body = new HorizontalLayout(grid, recipeForm);
        recipeForm.setVisible(false);
        body.setSpacing(true);
        body.setSizeFull();
        grid.setSizeFull();
        body.setExpandRatio(grid,1);

        grid.addSelectionListener(event -> {
            if (event.getSelected().isEmpty()) {
                recipeForm.setVisible(false);
            } else {
                System.out.println("SELECTION");
                Recipe recipe = (Recipe) event.getSelected().iterator().next();
                recipeForm.setPojo(recipe);
            }
        });

        docSelectorGrid.setSizeFull();
        docSelectorGrid.setCaption("Choose doctor:");
        docSelectorGrid.setColumns("id", "name", "surname", "middleName", "specialization");
        docSelectorGrid.setVisible(false);
        docSelectorGrid.setContainerDataSource(new BeanItemContainer<>(Doctor.class, doctorDao.findAll()));
        docSelectorGrid.addSelectionListener(event -> {
            recipeForm.setDoctor((Doctor) event.getSelected().iterator().next());
            docSelectorGrid.setVisible(false);
        });

        petientSelectGrid.setSizeFull();
        petientSelectGrid.setCaption("Choose patient:");
        petientSelectGrid.setColumns("id", "name", "surname", "middleName", "phoneNum");
        petientSelectGrid.setVisible(false);
        petientSelectGrid.setContainerDataSource(new BeanItemContainer<>(Patient.class, patientDao.findAll()));
        petientSelectGrid.addSelectionListener(event -> {
            recipeForm.setPatient((Patient) event.getSelected().iterator().next());
            petientSelectGrid.setVisible(false);
        });

        setSizeFull();
        setSpacing(true);
        addComponents(tools, body, docSelectorGrid, petientSelectGrid);
    }

    private void updateRecipeList(Object o) {
        recipes = recipeDao.findAll();
        grid.setContainerDataSource(new BeanItemContainer<>(Recipe.class, recipes));
    }

    private void selectDoctor (Object o) {
        docSelectorGrid.setVisible(true);
    }

    private void selectPatient (Object o) {
        petientSelectGrid.setVisible(true);
    }

}
