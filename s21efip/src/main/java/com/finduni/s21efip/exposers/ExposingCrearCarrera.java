/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.finduni.s21efip.exposers;

import com.finduni.s21efip.App;
import com.finduni.s21efip.entities.carrera.Modalidad;
import com.finduni.s21efip.entities.instancia.Instancia;
import com.finduni.s21efip.entities.instancia.Tipologia;
import com.finduni.s21efip.exceptions.CareerConditionException;
import com.finduni.s21efip.providers.ProvidingCarrera;
import com.finduni.s21efip.views.AdministrarCarrerasInfoCarreraView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class: ExposingCrearCarrera: Controlador de la vista Crear Carrera
 *
 * @author Francisco Induni
 */
public class ExposingCrearCarrera {
    
    private ProvidingCarrera providingCarrera;

    @FXML
    private TextField inputNombre;
    @FXML
    private TextField inputCreditos;
    @FXML
    private ComboBox<Modalidad> inputModalidad;
    @FXML
    private TextField inputCuposNormal;
    @FXML
    private TextField inputCuposSubperiodoB;
    @FXML
    private TextField inputCuposIngresantes;
    @FXML
    private TextField inputCuposVerano;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    /**
     * Inicializador
     */
    public void initialize() {
        try {
            this.providingCarrera = App.getProvidingCarrera();
            inputModalidad.setItems(FXCollections.observableArrayList(Modalidad.PH, Modalidad.ED, Modalidad.EDH));
        } catch (Exception e) {
            System.out.println("Error al ejecutar ExposingCrearCarrera: " + e);
            App.fatalError();
        }
    }    

    @FXML
    /*
    * Método para crear la carrera y regresar al administrador de carreras
    */
    private void guardarCarrera(ActionEvent event) {
        try {
            
            String nombre = this.inputNombre.getText();
            if (nombre.equals("")) {
                throw new CareerConditionException("El nombre de la carrera no puede ser nulo");
            }
            if (nombre.length() > 256) {
                throw new CareerConditionException("El nombre de la carrera no puede contener más de 256 caracteres");
            }
            
            Modalidad modalidad = inputModalidad.getValue();
            if (modalidad == null) {
                throw new CareerConditionException("Debes elegir una modalidad para la carrera");
            }
            
            Integer creditos;
            try {
                creditos = Integer.valueOf(this.inputCreditos.getText());
            } catch (NumberFormatException e) {
                throw new CareerConditionException("Créditos debe ser un valor numérico entero");
            }
            if (creditos < 0 || creditos > 10) {
                throw new CareerConditionException("Créditos debe ser un valor numérico entre 0 y 10");
            }
            
            Integer cuposNormal;
            try {
                cuposNormal = Integer.valueOf(this.inputCuposNormal.getText());
            } catch (NumberFormatException e) {
                throw new CareerConditionException("Cupos normal debe ser un valor numérico entero");
            }
            if (cuposNormal < 1 || cuposNormal > 10) {
                throw new CareerConditionException("Cupos normal debe ser un valor numérico entre 1 y 10");
            }
            
            Integer cuposSubperiodoB;
            try {
                cuposSubperiodoB = Integer.valueOf(this.inputCuposSubperiodoB.getText());
            } catch (NumberFormatException e) {
                throw new CareerConditionException("Cupos Subperiodo 'B' debe ser un valor numérico entero");
            }
            if (cuposSubperiodoB < 1 || cuposSubperiodoB > cuposNormal) {
                throw new CareerConditionException("Cupos Subperiodo 'B' debe ser un valor numérico entre 1 y el valor ingresado en cupos normal (" + cuposNormal.toString() + ")");
            }
            
            Integer cuposIngresantes;
            try {
                cuposIngresantes = Integer.valueOf(this.inputCuposIngresantes.getText());
            } catch (NumberFormatException e) {
                throw new CareerConditionException("Cupos para ingresante debe ser un valor numérico entero");
            }
            if (cuposIngresantes < 1 || cuposIngresantes > cuposNormal) {
                throw new CareerConditionException("Cupos para ingresante debe ser un valor numérico entre 1 y el valor ingresado en cupos normal (" + cuposNormal.toString() + ")");
            }
            
            Integer cuposVerano;
            try {
                cuposVerano = Integer.valueOf(this.inputCuposVerano.getText());
            } catch (NumberFormatException e) {
                throw new CareerConditionException("Cupos para el verano debe ser un valor numérico entero");
            }
            if (cuposVerano < 0 || cuposVerano > cuposNormal) {
                throw new CareerConditionException("Cupos para el verano debe ser un valor numérico entre 0 y el valor ingresado en cupos normal (" + cuposNormal.toString() + ")");
            }
            
            ArrayList<Instancia> instancias = new ArrayList<>();
            instancias.add(App.getProvidingInstancia().crearMateria("Test Materia", true, true, true, true, true, 1, new ArrayList<>(), Tipologia.MIA, 1, true, null));
            instancias.add(App.getProvidingInstancia().crearInstanciaEvaluativa("Test EV", true, true, true, true, true, 1, new ArrayList<>()));
            this.providingCarrera.crearYGuardarCarrera(nombre, modalidad, creditos, cuposVerano, cuposNormal, cuposSubperiodoB, cuposIngresantes, instancias);
            App.goToAdministrarCarreras();
        } catch (CareerConditionException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(null);
            errorAlert.setTitle("Error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        } catch (Exception e) {
            System.out.println("Error inesperado al crear carrera: " + e);
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(null);
            errorAlert.setTitle("Error");
            errorAlert.setContentText("Ocurrió un error inesperado al crear la carrera");
            errorAlert.showAndWait();
            App.goToAdministrarCarreras();
        }
    }

    @FXML
    /*
    * Método para regresar al administrador de carreras descartando los cambios
    */
    private void cancelar(ActionEvent event) {
        App.goToAdministrarCarreras();
    }
    
}
