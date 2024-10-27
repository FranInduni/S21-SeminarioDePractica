package com.finduni.s21efip.exposers;

import com.finduni.s21efip.views.HomePerfilInfoView;
import com.finduni.s21efip.App;
import com.finduni.s21efip.entities.Perfil;
import com.finduni.s21efip.providers.ProvidingPerfil;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class: ExposingHome: Controlador de la vista Home.
 *
 * @author Francisco Induni
 */
public class ExposingHome {
    
    private ProvidingPerfil providingPerfil;
    ArrayList<Perfil> perfiles;
    
    @FXML
    private Button btnNuevoPerfil;
    @FXML
    private Button btnAdministrarCarreras;
    @FXML
    private ListView<Perfil> listPerfiles;
    
    /*
    * Inicializador
    */
    public void initialize() {
        try {
            this.providingPerfil = App.getProvidingPerfil();
            perfiles = this.providingPerfil.obtenerPerfiles();
            this.listPerfiles.getItems().addAll(perfiles);
            listPerfiles.setCellFactory(clbck -> new HomePerfilInfoView());
        } catch (Exception e) {
            System.out.println("Error al ejecutar ExposingHome: " + e);
            App.fatalError();
        }
        
    }    

    /*
    * Abre una vista en PopUp para permitir la creación de un nuevo perfil
    */
    @FXML
    private void nuevoPerfil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/finduni/s21efip/views/NuevoPerfilView.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println("Error en ExposingHome - Nuevo Perfil: " + e);
            App.fatalError();
        }
    }

    /*
    * Cambia la vista de la pantalla principal del programa a la Administración de Carreras
    */
    @FXML
    private void administrarCarreras(ActionEvent event) {
        App.goToAdministrarCarreras();
    }
    
}
