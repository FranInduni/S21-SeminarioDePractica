package com.finduni.s21efip.handlers;

import com.finduni.s21efip.databaseinterface.DatabaseInterface;
import com.finduni.s21efip.databaseinterface.SQLParam;
import com.finduni.s21efip.databaseinterface.SQLParamType;
import com.finduni.s21efip.entities.Perfil;
import com.finduni.s21efip.exceptions.DBConnError;
import com.finduni.s21efip.exceptions.InvalidOrNullParam;
import com.finduni.s21efip.exceptions.InvalidSQLParam;
import java.util.ArrayList;
import java.util.Map;

/**
 * HandlingPerfil: Es la interfaz que maneja la persistencia de perfiles del programa en la DB.
 * 
 * @author Francisco Induni
 */
public class HandlingPerfil {
    private DatabaseInterface databaseInterface;
    
    /*
    * Inicializador
    */
    public HandlingPerfil(DatabaseInterface databaseInterface) throws InvalidOrNullParam {
        if (databaseInterface != null) {
            this.databaseInterface = databaseInterface;
        } else {
            throw new InvalidOrNullParam("databaseInterface no puede ser nulo");
        }
    }
    
    /*
    * Guarda una entidad perfil recibida en la DB
    */
    public void guardarPerfil(Perfil perfil) throws DBConnError {
        try {
            ArrayList<SQLParam> params = new ArrayList<>();
            params.add(new SQLParam(SQLParamType.STRING, perfil.getNombre(), null, null));
            
            Integer perfilId = this.databaseInterface.callStoredProcedure("PROCEDURE_PERFIL_CREAR", params, true);
            perfil.setPerfilId(perfilId);
        } catch (DBConnError | InvalidSQLParam e) {
            System.out.println("Error al guardar perfil: " + e);
            throw new DBConnError();
        }
    }
    
    /*
    * Borra un perfil recibido de la DB
    */
    public void borrarPerfil(Perfil perfil) throws DBConnError {
        try {
            ArrayList<SQLParam> params = new ArrayList<>();
            params.add(new SQLParam(SQLParamType.INT, null, perfil.getPerfilId(), null));
            
            this.databaseInterface.callStoredProcedure("PROCEDURE_PERFIL_BORRAR", params, false);
        } catch (DBConnError | InvalidSQLParam e) {
            System.out.println("Error al guardar perfil: " + e);
            throw new DBConnError();
        }
    }
    
    /*
    * Obtiene y devuelve la lista de perfiles persistidos en la DB
    */
    public ArrayList<Perfil> obtenerPerfiles() throws DBConnError {
        try {
            ArrayList<Perfil> perfiles = new ArrayList<>();
            
            ArrayList<Map<String, Object>> results = this.databaseInterface.callStoredProcedureForSelect("PROCEDURE_PERFIL_LISTAR", new ArrayList<>());
            
            for (int i = 0; i < results.size(); i++) {
                Map<String, Object> perfilFromDB = results.get(i);
                int perfilId = ((Long) perfilFromDB.get("perfilId")).intValue();
                String perfilNombre = perfilFromDB.get("perfilNombre").toString();
                perfiles.add(new Perfil(perfilId, perfilNombre, new ArrayList()));
            }
            
            return perfiles;
        } catch (DBConnError e) {
            System.out.println("Error al obtener perfiles: " + e);
            throw new DBConnError();
        }
    }
    
}
