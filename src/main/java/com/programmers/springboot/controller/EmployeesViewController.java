/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.programmers.springboot.controller;

import com.programmers.springboot.exception.ResourceNotFoundException;
import com.programmers.springboot.model.Employee;
import com.programmers.springboot.repository.EmployeeRepository;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author e-emgarza
 */
@Component
public class EmployeesViewController implements Initializable {

    Employee employee, employeeUpd;
    ObservableList<Employee> data = FXCollections.observableArrayList();

    @Autowired
    EmployeeRepository employeeRepository;

    @FXML
    private Button btnAgregar;
    @FXML
    private TableView<Employee> table;

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnModificar;

    long idMod;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createTable();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        btnModificar.setVisible(false);
        TableColumn<Employee, String> colId = new TableColumn<>("ID");
        TableColumn<Employee, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Employee, String> colApellido = new TableColumn<>("Apellido");
        TableColumn<Employee, String> colEmail = new TableColumn<>("Email");

        //relaciona el valor para cada columna       
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory("firstName"));
        colApellido.setCellValueFactory(new PropertyValueFactory("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory("emailId"));

        table.getColumns().addAll(colId);
        table.getColumns().addAll(colNombre);
        table.getColumns().addAll(colApellido);
        table.getColumns().addAll(colEmail);
    }

    public TableView<Employee> createTable() {
        //nombre de las columnas
        table.getItems().removeAll(data);

        //    data = table.getItems();
        long total = employeeRepository.findAll().size();
//        System.out.println(FXCollections.observableArrayList(employeeRepository.findAll()));
//        System.out.println(getAllEmployees().toString());
        for (int i = 0; i < getAllEmployees().size(); i++) {
            data.add(getAllEmployees().get(i));
            System.out.println(data.get(i));
        }
        table.setItems(data);
        return table;
    }

    @FXML
    private void onSave(ActionEvent event) {
        Employee employee = new Employee();
        table.getItems().removeAll(data);
        employee.setFirstName(txtNombre.getText());
        employee.setLastName(txtApellido.getText());
        employee.setEmailId(txtEmail.getText());
        employeeRepository.save(employee);
        doRefresh(event);
        cleanTxt();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @FXML
    private void doRefresh(ActionEvent event) {
        table.getItems().removeAll(data);
        table.getColumns().removeAll();
        createTable();
        cleanTxt();
    }

    @FXML
    private void handleModificar(ActionEvent event) {
        idMod = table.getSelectionModel().getSelectedItem().getId();
        employeeUpd = employeeRepository.findById(idMod)
                .orElseThrow(() -> new ResourceNotFoundException("No existe empleado con id:" + idMod));
        txtNombre.setText(employeeUpd.getFirstName());
        txtApellido.setText(employeeUpd.getLastName());
        txtEmail.setText(employeeUpd.getEmailId());
        btnAgregar.setVisible(false);
        btnModificar.setVisible(true);

    }

    @FXML
    private void handleEliminar(ActionEvent event) {
        long id = table.getSelectionModel().getSelectedItem().getId();
        Employee employeeDel = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe empleado con id:" + id));
        employeeRepository.delete(employeeDel);
        createTable();
    }

    @FXML
    private void handleRecargar(ActionEvent event) {
        table.getItems().removeAll(data);
        table.getColumns().removeAll();
        createTable();
    }

    @FXML
    private void onUpdate(ActionEvent event) {
        Employee employeemod = employeeRepository.findById(idMod)
                .orElseThrow(() -> new ResourceNotFoundException("No existe empleado con id:" + idMod));
        employeemod.setFirstName(txtNombre.getText());
        employeemod.setLastName(txtApellido.getText());
        employeemod.setEmailId(txtEmail.getText());
        employeeRepository.save(employeemod);
        doRefresh(event);
        btnModificar.setVisible(false);
        btnAgregar.setVisible(true);
        cleanTxt();
    }

    private void cleanTxt() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
    }
}
