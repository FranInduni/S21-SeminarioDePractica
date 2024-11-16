package com.finduni.s21efip.exposers;

import com.finduni.s21efip.views.AdministrarCarrerasInfoCarreraView;
import com.finduni.s21efip.App;
import com.finduni.s21efip.entities.carrera.Carrera;
import com.finduni.s21efip.providers.ProvidingCarrera;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    */
    @FXML
    private void nuevaCarrera(ActionEvent event) {
        App.goToCrearCarrera();
    }
    
}
