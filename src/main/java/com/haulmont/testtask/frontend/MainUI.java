package com.haulmont.testtask.frontend;

import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.domain.Patient;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
import java.util.stream.Collectors;

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

    private Grid grid = new Grid();
    private TextField searchPatient = new TextField();
    private PatientForm patientForm = new PatientForm(this::updateList);

    private PatientDao patientDao = PatientDao.get();
    private List<Patient> patients;

    @Override
    protected void init(VaadinRequest request) {

        VerticalLayout layout = new VerticalLayout();

        addHeader(layout);

        grid.setColumns("id", "name", "surname", "middleName", "phoneNum");
        updateList(null);

        // search functionality and create button
        searchPatient.setInputPrompt("Search patient");
        searchPatient.addTextChangeListener(event -> grid.setContainerDataSource(
                new BeanItemContainer<>(Patient.class,
                        patients.stream()
                                .filter(patient -> patient.toString().contains(event.getText()))
                                .collect(Collectors.toList()))
        ));
        Button clearFilter = new Button(FontAwesome.TIMES);
        clearFilter.addClickListener(click -> {
            searchPatient.clear();
            updateList(null);
        });

        Button addBtn = new Button("Add");
        addBtn.addClickListener(event -> {
            grid.select(null);
            Patient newPatient = new Patient();
            newPatient.setId(-1);
            patientForm.setPatient(newPatient);
        });

        CssLayout search = new CssLayout();
        search.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        search.addComponents(searchPatient, clearFilter);

        HorizontalLayout tools = new HorizontalLayout(search,addBtn);
        tools.setSpacing(true);

        //
        HorizontalLayout body = new HorizontalLayout(grid,patientForm);
        patientForm.setVisible(false);
        body.setSpacing(true);
        body.setSizeFull();
        grid.setSizeFull();
        body.setExpandRatio(grid,1);

        grid.addSelectionListener(event -> {
            if (event.getSelected().isEmpty()) {
                patientForm.setVisible(false);
            } else {
                Patient patient = (Patient) event.getSelected().iterator().next();
                patientForm.setPatient(patient);
            }
        });

        layout.addComponents(tools, body);
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);

    }

    private void changeForm () {

    }

    public void updateList (Object o) {
        patients = patientDao.findAll();
        grid.setContainerDataSource(new BeanItemContainer<>(Patient.class, patients));
    }

    private void addHeader (AbstractOrderedLayout outerLayout) {

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        headerLayout.addStyleName("header");
        //headerLayout.setHeight(4, Unit.EM);
        headerLayout.setWidth(100, Unit.PERCENTAGE);

        Label title = new Label("<h2>Hospital viewer</h2>");
        title.addStyleName("site-title");
        title.setContentMode(ContentMode.HTML);
        headerLayout.addComponent(title);

        Button patients = new Button("Patients");
        Button recipes = new Button("Recipes");
        Button docs = new Button("Doctors");

        headerLayout.addComponent(patients);
        headerLayout.addComponent(recipes);
        headerLayout.addComponent(docs);

        outerLayout.addComponent(headerLayout);

    }

    /*
    Page.getCurrent().setTitle("Hospital home page");

        VerticalLayout content = new VerticalLayout();
        setContent(content);
        addHeader(content);

        PatientCreator patientCreator = new PatientCreator();
        content.addComponent(patientCreator);

        for (int i = 0; i < patients.size(); i++) {
            content.addComponent(new PatientView(patients.get(i),patients.get(i).getId()));
        }
     */

//    private void addItemsGrid (AbstractLayout content) {
//        grid.setHeightMode(HeightMode.ROW);
//        grid.setWidth(100, Unit.PERCENTAGE);
//        grid.setHeightUndefined();
//        grid.setStyleName("items-grid");
//        grid.setColumns("name", "surname", "middleName", "phoneNum");
//
//        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
//        //grid.setEditorBuffered(true);
//        grid.setEditorEnabled(true);
//        //grid.addListener(event -> Notification.show("!!!"));
//
//        BeanItemContainer<Patient> beanItemContainer = new BeanItemContainer<>(Patient.class, patients);
//        grid.setContainerDataSource(beanItemContainer);
//
//        BeanFieldGroup beanFieldGroup = new BeanFieldGroup<>(Patient.class);
//        //beanFieldGroup.setItemDataSource(data.get(0));
//        beanFieldGroup.addCommitHandler(new FieldGroup.CommitHandler() {
//            @Override
//            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
//                System.out.println("PRE COMMIT");
//            }
//
//            @Override
//            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
//                System.out.println("POST COMMIT");
//                commitEvent.getFieldBinder().commit();
//            }
//        });
//        grid.setEditorFieldGroup(beanFieldGroup);
//
//        content.addComponent(grid);
//        updateGrid();
//    }
//
//    private void updateGrid () {
//        BeanItem<Patient> patientBean = new BeanItem<Patient>(PatientDaoImitation.getNPatients(1).get(0));
//
//        BeanItemContainer beanItemContainer = new BeanItemContainer<>(Patient.class, patients);
//
//        beanItemContainer.addPropertySetChangeListener((
//                event -> {
//                    System.out.println("addPropertySetChangeListener");
//                    System.out.println(event);
//                }
//        ));
//
//        beanItemContainer.addItemSetChangeListener(event -> {
//            System.out.println("addItemSetChangeListener:");
//            Container container = event.getContainer();
//            System.out.println(container.getItem(patients.get(0)));
//        });
//
//        grid.setContainerDataSource(beanItemContainer);
//    }
//
//    private void addTextField (AbstractLayout outerContent) {
//        // text field example
//        TextField tf = new TextField("Age");
//        tf.setInputPrompt("Please enter age");
//        tf.setImmediate(true);
//        tf.setConverter(new StringToIntegerConverter());
//        IntegerRangeValidator validator =
//                new IntegerRangeValidator("Age must be below 100", 1, 99);
//        tf.addValidator(validator);
//        outerContent.addComponent(tf);
//    }

}