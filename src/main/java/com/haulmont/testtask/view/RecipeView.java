package com.haulmont.testtask.view;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.dao.RecipeDao;
import com.haulmont.testtask.domain.Doctor;
import com.haulmont.testtask.domain.Patient;
import com.haulmont.testtask.domain.Recipe;
import com.haulmont.testtask.view.editforms.RecipeForm;
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

    private Grid patientSelectGrid = new Grid();
    private PatientDao patientDao = PatientDao.get();

    private TextField search = new TextField();

    /**
     * Reference of {@link #updateRecipeList(Object)} gives form ability to
     * update views grid
     */
    private RecipeForm recipeForm = new RecipeForm(
            this::updateRecipeList, this::selectDoctor, this::selectPatient);
    private RecipeDao recipeDao = RecipeDao.get();
    private List<Recipe> recipes;

    RecipeView() {
        grid.setColumns("id", "description", "creation", "expired", "priority");
        updateRecipeList(null);

        search.setInputPrompt("Search");

        search.addTextChangeListener(event -> {
            BeanItemContainer container = new BeanItemContainer<>(Recipe.class,
                    recipes.stream()
                            .filter(recipe -> recipe.toString().
                                    contains(event.getText()))
                            .collect(Collectors.toList()));
            grid.setContainerDataSource(container);
        });

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

        HorizontalLayout tools = new HorizontalLayout(search, addBtn);
        tools.setSpacing(true);

        HorizontalLayout body = new HorizontalLayout(grid, recipeForm);
        recipeForm.setVisible(false);
        body.setSpacing(true);
        body.setSizeFull();
        grid.setSizeFull();
        body.setExpandRatio(grid, 1);

        grid.addSelectionListener(event -> {
            if (event.getSelected().isEmpty()) {
                recipeForm.setVisible(false);
            } else {
                Recipe recipe = (Recipe) event.getSelected().iterator().next();
                recipeForm.setPojo(recipe);
            }
        });

        docSelectorGrid.setSizeFull();
        docSelectorGrid.setCaption("Choose doctor:");
        docSelectorGrid.setColumns(
                "id", "name", "surname", "middleName", "specialization");
        docSelectorGrid.setVisible(false);
        docSelectorGrid.setContainerDataSource(
                new BeanItemContainer<>(Doctor.class, doctorDao.findAll()));
        docSelectorGrid.addSelectionListener(event -> {
            recipeForm.setDoctor((Doctor) event.getSelected()
                    .iterator().next());
            docSelectorGrid.setVisible(false);
        });

        patientSelectGrid.setSizeFull();
        patientSelectGrid.setCaption("Choose patient:");
        patientSelectGrid.setColumns(
                "id", "name", "surname", "middleName", "phoneNum");
        patientSelectGrid.setVisible(false);
        patientSelectGrid.setContainerDataSource(
                new BeanItemContainer<>(Patient.class, patientDao.findAll()));
        patientSelectGrid.addSelectionListener(event -> {
            recipeForm.setPatient((Patient) event.getSelected()
                    .iterator().next());
            patientSelectGrid.setVisible(false);
        });

        setSizeFull();
        setSpacing(true);
        addComponents(tools, body, docSelectorGrid, patientSelectGrid);
    }

    private void updateRecipeList(Object o) {
        recipes = recipeDao.findAll();
        grid.setContainerDataSource(
                new BeanItemContainer<>(Recipe.class, recipes));
        // hiding selection grid if
        // user saved or deleted something with opened grid
        patientSelectGrid.setVisible(false);
        docSelectorGrid.setVisible(false);
    }

    private void selectDoctor(Object o) {
        patientSelectGrid.setVisible(false);
        docSelectorGrid.setVisible(true);
    }

    private void selectPatient(Object o) {
        docSelectorGrid.setVisible(false);
        patientSelectGrid.setVisible(true);
    }

}
