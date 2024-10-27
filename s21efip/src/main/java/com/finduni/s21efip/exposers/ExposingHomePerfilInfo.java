package com.finduni.s21efip.exposers;

import com.finduni.s21efip.App;
import com.finduni.s21efip.entities.Perfil;
import com.finduni.s21efip.exceptions.InvalidOrNullParam;
import com.finduni.s21efip.providers.ProvidingPerfil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;

/**
 * FXML Controller class: ExposingHomePerfilInfo: Controlador de la vista Home Perfil Info (Vista generada para cada elemento de la lista de perfiles en la vista Home)
 *
 * @author Francisco Induni
 */
public class ExposingHomePerfilInfo {
    
    private ProvidingPerfil providingPerfil;
    private Perfil perfil;

    @FXML
    private Text txtNombre;
    @FXML
    private Button btnSeleccionar;
    @FXML
    private Button btnBorrar;

    /*
    * Inicializador manual
    */
    public void initAttributes(Perfil perfil) {
        this.providingPerfil = App.getProvidingPerfil();
        this.perfil = perfil;
        this.txtNombre.setText(perfil.getNombre());
    }
    
    /*
    * Permite seleccionar un perfil y cambiar la vista de la pantalla principal del programa hacia el gestor de ese perfil
    * NO DESARROLLADO EN ESTE PROTOTIPO
    */
    @FXML
    private void seleccionarPerfil(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText("Este prototipo no incluye el flujo tras la selección de un perfil");
        alert.showAndWait();
    }

    /*
    * Permite borrar un perfil de la base de datos
    */
    @FXML
    private void borrarPerfil(ActionEvent event) {
        ButtonType btnAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Desea borrar el perfil?", btnAceptar, btnCancelar);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Borrar perfil");
        confirmationAlert.setContentText("Desea borrar el perfil '" + this.perfil.getNombre() + "'?\nEsta acción es irrecuperable.");
        confirmationAlert.showAndWait().ifPresent(response -> {
        if (response == btnAceptar) {
            try {
                this.providingPerfil.borrarPerfil(this.perfil);
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText(null);
                errorAlert.setTitle("Perfil borrado");
                errorAlert.setContentText("Se borró correctamente el perfil\n" + this.perfil.getNombre());
                errorAlert.showAndWait();
            } catch (InvalidOrNullParam e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText(null);
                errorAlert.setTitle("Error");
                errorAlert.setContentText("Ocurrió un error inesperado al borrar el perfil");
                errorAlert.showAndWait();
            }
        }});
        
        App.goHome(); // Recarga la misma vista Home en la pantalla para forzar que se recargue la lista de perfiles desde la DB
    }
    
}
