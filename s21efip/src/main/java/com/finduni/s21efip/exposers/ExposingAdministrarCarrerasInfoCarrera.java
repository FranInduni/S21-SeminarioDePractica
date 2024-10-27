package com.finduni.s21efip.exposers;

import com.finduni.s21efip.App;
import com.finduni.s21efip.entities.carrera.Carrera;
import com.finduni.s21efip.exceptions.InvalidOrNullParam;
import com.finduni.s21efip.providers.ProvidingCarrera;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 * FXML Controller class: ExposingAdministrarCarrerasInfoCarrera: Controlador para la vista de Administrar Carreras Info Carrera (Cada row de la lista de carreras en la vista de Administrar Carreras)
 *
 * @author Francisco Induni
 */
public class ExposingAdministrarCarrerasInfoCarrera {
    
    private ProvidingCarrera providingCarrera;
    private Carrera carrera;

    @FXML
    private Text txtNombre;
    @FXML
    private Label labelModalidad;
    @FXML
    private Button btnBorrar;
    
    /*
    * Inicializador manual
    */
    public void initAttributes(Carrera carrera) {
        this.providingCarrera = App.getProvidingCarrera();
        this.carrera = carrera;
        this.txtNombre.setText(carrera.getNombre());
        this.labelModalidad.setText(carrera.getModalidad().toString());
    }
    
    /*
    * Borra la carrera indicada de la base de datos.
    */
    @FXML
    private void borrarCarrera(ActionEvent event) {
        ButtonType btnAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Desea borrar la carrera?", btnAceptar, btnCancelar);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setTitle("Borrar carrera");
        confirmationAlert.setContentText("Desea borrar la carrera '" + this.carrera.getNombre() + "'?\nEsta acción es irrecuperable.");
        confirmationAlert.showAndWait().ifPresent(response -> {
        if (response == btnAceptar) {
            try {
                this.providingCarrera.borrarCarrera(this.carrera);
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText(null);
                errorAlert.setTitle("Carrera borrada");
                errorAlert.setContentText("Se borró correctamente la carrera\n" + this.carrera.getNombre());
                errorAlert.showAndWait();
            } catch (InvalidOrNullParam e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText(null);
                errorAlert.setTitle("Error");
                errorAlert.setContentText("Ocurrió un error inesperado al borrar la carrera");
                errorAlert.showAndWait();
            }
        }});
        
        App.goToAdministrarCarreras(); // Recarga la misma vista para forzar que se recarguen la lista de carreras desde la DB
    }
    
}
