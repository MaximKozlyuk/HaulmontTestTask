package com.haulmont.testtask.frontend;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * + Отображение списка пациентов
 * + Добавление нового пациента, редактирование и удаление существующего
 * Отображение списка врачей
 * Отображение статистической информации по количеству рецептов, выписанных врачами
 * Добавление нового врача, редактирование и удаление существующего
 * Отображения списка рецептов
 * Фильтрация списка рецептов по описанию, приоритету и пациенту
 * Добавление нового рецепта, редактирование и удаление существующего
 *
 * Система должна иметь защиту на уровне БД от удаления пациента и врача, для которых существуют рецепты
 * Все формы ввода должны валидировать данные в соответствии с их типом и допустимыми значениями
 */

//@StyleSheet("frontend://styles/styles.css")
//@PreserveOnRefresh
@Theme(ValoTheme.THEME_NAME)
@StyleSheet("vaadin://style.css")
public class MainUI extends  UI {

    private static final long serialVersionUID = 1L;

    private VerticalLayout layout = new VerticalLayout();

    private PatientView patientView = new PatientView();
    private DoctorView doctorView;

    private ObjectView currentView = patientView;

    @Override
    protected void init(VaadinRequest request) {

        addHeader(layout);

        layout.addComponent(patientView);
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);

    }

    private void addHeader (AbstractOrderedLayout outerLayout) {
        Header header = new Header();
        header.getPatients().addClickListener(event -> {
            currentView.setVisible(false);
            currentView = patientView;
            currentView.setVisible(true);
        });
        header.getRecipes().addClickListener(event -> {

        });
        header.getDocs().addClickListener(event -> {
            currentView.setVisible(false);
            if (doctorView == null ) {
                doctorView = new DoctorView();
                layout.addComponent(doctorView);
            }
            currentView = doctorView;
            currentView.setVisible(true);
        });
        outerLayout.addComponent(header);
    }

}


//    class InterfaceGeneralizer<T extends GenericObject> {
//
//        private Class pojoType;
//
//        public InterfaceGeneralizer(T pojo) {
//            pojoType = pojo.getClass();
//             //pojoType.getDeclaredFields()[0].getName()
//            grid.setColumns();
//
//        }
//
//    }