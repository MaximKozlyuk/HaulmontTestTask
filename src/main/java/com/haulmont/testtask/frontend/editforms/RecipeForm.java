package com.haulmont.testtask.frontend.editforms;

import com.haulmont.testtask.dao.RecipeDao;
import com.haulmont.testtask.domain.GenericObject;
import com.haulmont.testtask.domain.Priority;
import com.haulmont.testtask.domain.Recipe;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

import java.util.function.Consumer;

public class RecipeForm extends FormButtons {

    private TextField description = new TextField("Description");
    private PopupDateField creation = new PopupDateField("Creation");
    private PopupDateField expired = new PopupDateField("Expired");
    private NativeSelect priority = new NativeSelect("Priority");

    private RecipeDao dao = RecipeDao.get();
    private Recipe pojo;

    private Consumer<Object> updateList;

    public RecipeForm (Consumer<Object> updateList) {
        super();
        this.updateList = updateList;

        getSave().addClickListener(e -> save());
        getDelete().addClickListener(e -> delete());

        priority.addItems(Priority.values());
        priority.setNullSelectionAllowed(false);

        addComponents(description, creation, expired, priority, getButtons());

    }

    public void setPojo(GenericObject pojo) {
        this.pojo = (Recipe) pojo;
        BeanFieldGroup.bindFieldsUnbuffered(pojo,this);

        getDelete().setVisible(dao.existsById(pojo.getId()));
        setVisible(true);
        //getName().selectAll();
    }

    private void save () {
        dao.save(pojo);
        updateList.accept(null);
        setVisible(false);
        pojo = null;    // todo clear all fields
    }

    private void delete () {
        dao.delete(pojo);
        updateList.accept(null);
        setVisible(false);
        pojo = null;
    }

}
