package com.haulmont.testtask.frontend;

import com.haulmont.testtask.dao.RecipeDao;
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

    private TextField search = new TextField();
    private RecipeForm recipeForm = new RecipeForm(this::updateRecipeList);

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
                Recipe recipe = (Recipe) event.getSelected().iterator().next();
                recipeForm.setPojo(recipe);
            }
        });

        setSizeFull();
        setSpacing(true);
        addComponents(tools, body);
    }

    private void updateRecipeList(Object o) {
        recipes = recipeDao.findAll();
        grid.setContainerDataSource(new BeanItemContainer<>(Recipe.class, recipes));
    }

}
