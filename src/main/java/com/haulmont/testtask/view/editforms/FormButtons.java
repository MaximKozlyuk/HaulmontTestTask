package com.haulmont.testtask.view.editforms;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;
import lombok.Getter;

/**
 * Configured save and delete buttons for all forms
 */
@Getter
class FormButtons extends FormLayout {

    private Button save = new Button("Save");
    private Button delete = new Button("Delete ");

    private HorizontalLayout buttons = new HorizontalLayout();

    FormButtons() {
        setSizeUndefined();
        buttons.setSpacing(true);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        buttons.addComponents(save, delete);
    }

}
