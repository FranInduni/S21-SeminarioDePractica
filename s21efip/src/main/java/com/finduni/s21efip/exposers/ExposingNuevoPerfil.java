package com.finduni.s21efip.exposers;

import com.finduni.s21efip.App;
import com.finduni.s21efip.entities.Perfil;
import com.finduni.s21efip.providers.ProvidingPerfil;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class: ExposingNuevoPerfil: Controlador de la vista NuevoPerfil
 *
 * @author Francisco Induni
 */
public class ExposingNuevoPerfil {
    
    private ProvidingPerfil providingPerfil;

    @FXML
    private TextField inputNombre;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    /*
    * Inicializador
    */
    public void initialize() {
        this.providingPerfil = App.getProvidingPerfil();
    }

    /*
    * Crea un perfil nuevo en la DB dado un nombre
    */
    @FXML
    private void crearPerfil(ActionEvent event) {
        try {
            String nombre = this.inputNombre.getText();
            
            ArrayList<Perfil> perfilesExistentes = this.providingPerfil.obtenerPerfiles();
            for (Perfil perfil : perfilesExistentes) {
                if (perfil.getNombre().equalsIgnoreCase(nombre)) {
                    Alert errorAlert = new Alert(Alert.AlertType.WARNING);
                    errorAlert.setHeaderText(null);
                    errorAlert.setTitle("Perfil duplicado");
                    errorAlert.setContentText("Ya existe un perfil con el nombre:\n" + nombre);
                    errorAlert.showAndWait();
                    return;
                }
            }
            
            this.providingPerfil.crearPerfil(nombre);
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setHeaderText(null);
            errorAlert.setTitle("Perfil creado");
            errorAlert.setContentText("Se creó correctamente el perfil");
            errorAlert.showAndWait();
            Stage stage = (Stage) this.btnGuardar.getScene().getWindow();
            // Cierra el PopUp y recarga la vista home para forzar la recarga de perfiles desde la DB
            stage.close();
            App.goHome();
        } catch (Exception e) {
            System.out.println("Error al crear un perfil: " + e);
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(null);
            errorAlert.setTitle("Error");
            errorAlert.setContentText("Ocurrió un error inesperado al crear el perfil");
            errorAlert.showAndWait();
        }
    }
    
    /*
    * Cierra el PopUp sin realizar cambios persistentes
    */
    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) this.btnGuardar.getScene().getWindow();
        stage.close();
        App.goHome();
    }
    
}
