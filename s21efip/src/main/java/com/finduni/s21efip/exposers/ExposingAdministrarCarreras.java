package com.finduni.s21efip.exposers;

import com.finduni.s21efip.views.AdministrarCarrerasInfoCarreraView;
import com.finduni.s21efip.App;
import com.finduni.s21efip.entities.carrera.Carrera;
import com.finduni.s21efip.entities.carrera.Modalidad;
import com.finduni.s21efip.entities.instancia.Instancia;
import com.finduni.s21efip.entities.instancia.Tipologia;
import com.finduni.s21efip.providers.ProvidingCarrera;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


/**
 * FXML Controller class: ExposingAdministrarCarreras: Controlador para la vista de administrar carreras
 *
 * @author Francisco Induni
 */
public class ExposingAdministrarCarreras {
    
    private ProvidingCarrera providingCarrera;
    ArrayList<Carrera> carreras;

    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnNuevaCarrera;
    @FXML
    private ListView<Carrera> listCarreras;

    /*
    * Inicializador
    */
    public void initialize() {
        try {
            this.providingCarrera = App.getProvidingCarrera();
            carreras = this.providingCarrera.obtenerTodasLasCarreras();
            this.listCarreras.getItems().addAll(carreras);
            listCarreras.setCellFactory(clbck -> new AdministrarCarrerasInfoCarreraView());
        } catch (Exception e) {
            System.out.println("Error al ejecutar ExposingAdministrarCarreras: " + e);
            App.fatalError();
        }
    }    
    
    /*
    * Método para regresar la pantalla principal del programa a la vista Home
    */
    @FXML
    private void regresar(ActionEvent event) {
        App.goHome();
    }
    
    /*
    * Método para inicializar el flujo de nueva carrera
    * ESTE FLUJO NO ESTA DESARROLLADO AUN DEL LADO DE LAS VISTAS. SI SE LLAMA SE CREA UNA CARRERA POR DEFECTO
    */
    @FXML
    private void nuevaCarrera(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText("El flujo de crear Carrera será incluído en la 4ta entrega de este TP\nSerá creada una carrera automáticamente a modo de ejemplo");
        alert.showAndWait();
        
        try {
            ArrayList<Instancia> instancias = new ArrayList<>();
            instancias.add(App.getProvidingInstancia().crearMateria("Test Materia", true, true, true, true, true, 1, new ArrayList<>(), Tipologia.MIA, 1, true, null));
            instancias.add(App.getProvidingInstancia().crearInstanciaEvaluativa("Test EV", true, true, true, true, true, 1, new ArrayList<>()));
            this.providingCarrera.crearYGuardarCarrera("Carrera creada automáticamente", Modalidad.EDH, 5, 3, 4, 3, 2, instancias);
            App.goToAdministrarCarreras();
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(null);
            errorAlert.setTitle("Error");
            errorAlert.setContentText("Ocurrió un error inesperado al crear la carrera");
            errorAlert.showAndWait();
        }
    }
    
}
