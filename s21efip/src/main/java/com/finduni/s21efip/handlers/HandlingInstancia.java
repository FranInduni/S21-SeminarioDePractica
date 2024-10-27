package com.finduni.s21efip.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finduni.s21efip.databaseinterface.DatabaseInterface;
import com.finduni.s21efip.databaseinterface.SQLParam;
import com.finduni.s21efip.databaseinterface.SQLParamType;
import com.finduni.s21efip.entities.carrera.Carrera;
import com.finduni.s21efip.entities.instancia.Instancia;
import com.finduni.s21efip.entities.instancia.InstanciaEvaluativa;
import com.finduni.s21efip.entities.instancia.Materia;
import com.finduni.s21efip.entities.instancia.Tipologia;
import com.finduni.s21efip.exceptions.DBConnError;
import com.finduni.s21efip.exceptions.InvalidOrNullParam;
import com.finduni.s21efip.exceptions.InvalidSQLParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * HandlingInstancia: Es la interfaz que maneja la persistencia de instancias de carreras en la DB.
 * 
 * @author Francisco Induni
 */
public class HandlingInstancia {
    private DatabaseInterface databaseInterface;
    
    /*
    * Inicializador
    */    
    public HandlingInstancia(DatabaseInterface databaseInterface) throws InvalidOrNullParam {
        if (databaseInterface != null) {
            this.databaseInterface = databaseInterface;
        } else {
            throw new InvalidOrNullParam("databaseInterface no puede ser nulo");
        }
    }
    
    /*
    * Guarda una materia de una carrera en la DB
    */
    public void guardarMateria(Materia materia, Carrera carrera) throws DBConnError, InvalidOrNullParam {
        try {
            if (carrera.getCarreraId() == null) {
                throw new InvalidOrNullParam("La carrera no puede tener ID nulo");
            }
            
            // Prepara el JSON de instancias correlativas
            String correlativas = "[";
            for (int i = 0; i < materia.getCorrelativas().size(); i++) {
                Instancia instancia = materia.getCorrelativas().get(i);
                if (instancia.getInstanciaId() == null) {
                    throw new InvalidOrNullParam("La instancia correlativa no puede tener el ID nulo");
                } else {
                    correlativas += instancia.getInstanciaId().toString();
                    if (i + 1 < materia.getCorrelativas().size()) {
                        correlativas += ", ";
                    }
                }
            }
            correlativas += "]";
            
            // Prepara los parámetros para el llamado al procedimiento
            ArrayList<SQLParam> params = new ArrayList<>();
            params.add(new SQLParam(SQLParamType.INT, null, carrera.getCarreraId(), null));
            params.add(new SQLParam(SQLParamType.STRING, materia.getNombre(), null, null));
            params.add(new SQLParam(SQLParamType.BOOL, null, null, materia.getInscripcionEnV()));
            params.add(new SQLParam(SQLParamType.BOOL, null, null, materia.getInscripcionEn1A()));
            params.add(new SQLParam(SQLParamType.BOOL, null, null, materia.getInscripcionEn1B()));
            params.add(new SQLParam(SQLParamType.BOOL, null, null, materia.getInscripcionEn2A()));
            params.add(new SQLParam(SQLParamType.BOOL, null, null, materia.getInscripcionEn2B()));
            params.add(new SQLParam(SQLParamType.INT, null, materia.getInscripcionCuatrimestreMinimo(), null));
            params.add(new SQLParam(SQLParamType.STRING, materia.getTipologia().toString(), null, null));
            params.add(new SQLParam(SQLParamType.INT, null, materia.getCuatrimestreEnCarrera(), null));
            params.add(new SQLParam(SQLParamType.BOOL, null, null, materia.getGastaCupos()));
            params.add(new SQLParam(SQLParamType.INT, null, materia.getCreditos(), null));
            params.add(new SQLParam(SQLParamType.STRING, correlativas, null, null));
            
            // Realiza el llamado para guardar la materia y modifica la entidad materia con el ID obtenido de la DB
            Integer instanciaId = this.databaseInterface.callStoredProcedure("PROCEDURE_CARRERA_MATERIA_CREAR", params, true);
            materia.setInstanciaId(instanciaId);
        } catch (DBConnError | InvalidOrNullParam | InvalidSQLParam e) {
            System.out.println("Error al guardar materia: " + e);
            throw new DBConnError();
        }
    }
    
    /*
    * Guarda una instancia evaluativa de una carrera en la DB
    */
    public void guardarInstanciaEvaluativa(InstanciaEvaluativa instanciaEvaluativa, Carrera carrera) throws DBConnError, InvalidOrNullParam {
        try {
            if (carrera.getCarreraId() == null) {
                throw new InvalidOrNullParam("La carrera no puede tener ID nulo");
            }
            
            // Prepara el JSON de instancias correlativas
            String correlativas = "[";
            for (int i = 0; i < instanciaEvaluativa.getCorrelativas().size(); i++) {
                Instancia instancia = instanciaEvaluativa.getCorrelativas().get(i);
                if (instancia.getInstanciaId() == null) {
                    throw new InvalidOrNullParam("La instancia correlativa no puede tener el ID nulo");
                } else {
                    correlativas += instancia.getInstanciaId().toString();
                    if (i + 1 < instanciaEvaluativa.getCorrelativas().size()) {
                        correlativas += ", ";
                    }
                }
            }
            correlativas += "]";
            
            // Prepara los parámetros para el llamado al procedimiento
            ArrayList<SQLParam> params = new ArrayList<>();
            params.add(new SQLParam(SQLParamType.INT, null, carrera.getCarreraId(), null));
            params.add(new SQLParam(SQLParamType.STRING, instanciaEvaluativa.getNombre(), null, null));
            params.add(new SQLParam(SQLParamType.BOOL, null, null, instanciaEvaluativa.getInscripcionEnV()));
            params.add(new SQLParam(SQLParamType.BOOL, null, null, instanciaEvaluativa.getInscripcionEn1A()));
            params.add(new SQLParam(SQLParamType.BOOL, null, null, instanciaEvaluativa.getInscripcionEn1B()));
            params.add(new SQLParam(SQLParamType.BOOL, null, null, instanciaEvaluativa.getInscripcionEn2A()));
            params.add(new SQLParam(SQLParamType.BOOL, null, null, instanciaEvaluativa.getInscripcionEn2B()));
            params.add(new SQLParam(SQLParamType.INT, null, instanciaEvaluativa.getInscripcionCuatrimestreMinimo(), null));
            params.add(new SQLParam(SQLParamType.STRING, correlativas, null, null));
            
            // Realiza el llamado para guardar la instancia evaluativa y modifica la entidad instancia evaluativa con el ID obtenido de la DB
            Integer instanciaId = this.databaseInterface.callStoredProcedure("PROCEDURE_CARRERA_INSTANCIA_EVALUATIVA_CREAR", params, true);
            instanciaEvaluativa.setInstanciaId(instanciaId);
        } catch (DBConnError | InvalidOrNullParam | InvalidSQLParam e) {
            System.out.println("Error al guardar instancia evaluativa: " + e);
            throw new DBConnError();
        }
    }
    
    /*
    * Dado una entidad carrera recibida le setea su lista de instancias con las obtenidas en la DB
    */
    public void obtenerInstanciasDadoCarrera(Carrera carrera) throws InvalidOrNullParam, DBConnError {
        try {
            if (carrera.getCarreraId() == null) {
                throw new InvalidOrNullParam("La carrera no puede tener ID nulo");
            }
            ArrayList<SQLParam> params = new ArrayList<>();
            params.add(new SQLParam(SQLParamType.INT, null, carrera.getCarreraId(), null));
            
            ArrayList<Map<String, Object>> results = this.databaseInterface.callStoredProcedureForSelect("PROCEDURE_CARRERA_INSTANCIA_LISTAR", params);
            
            Map<Integer, Instancia> instancias = new HashMap<>();
            
            for (int i = 0; i < results.size(); i++) { // Para cada row recibido crea la entidad instancia evaluativa o materia correspondiente y la inserta en la lista de instancias de la carrera
                Map<String, Object> instanciaFromDB = results.get(i);
                int instanciaId = ((Long) instanciaFromDB.get("instanciaId")).intValue();
                String instanciaNombre = instanciaFromDB.get("instanciaNombre").toString();
                Boolean instanciaInscripcionEnV = (Boolean) instanciaFromDB.get("instanciaInscripcionEnV");
                Boolean instanciaInscripcionEn1A = (Boolean) instanciaFromDB.get("instanciaInscripcionEn1A");
                Boolean instanciaInscripcionEn1B = (Boolean) instanciaFromDB.get("instanciaInscripcionEn1B");
                Boolean instanciaInscripcionEn2A = (Boolean) instanciaFromDB.get("instanciaInscripcionEn2A");
                Boolean instanciaInscripcionEn2B = (Boolean) instanciaFromDB.get("instanciaInscripcionEn2B");
                Integer instanciaCuatrimestreMinimo = ((Long) instanciaFromDB.get("instanciaCuatrimestreMinimo")).intValue();
                if (instanciaFromDB.get("materiaTipologia") != null) { // Es materia
                    Tipologia materiaTipologia = Tipologia.valueOf(instanciaFromDB.get("materiaTipologia").toString());
                    Integer materiaCuatrimestreEnCarrera = ((Long) instanciaFromDB.get("materiaCuatrimestreEnCarrera")).intValue();
                    Boolean materiaGastaCupo = (Boolean) instanciaFromDB.get("materiaGastaCupo");
                    Integer materiaCreditos = (instanciaFromDB.get("materiaCreditos") != null ? ((Long) instanciaFromDB.get("materiaCreditos")).intValue() : null);
                    instancias.put(instanciaId, new Materia(instanciaId, instanciaNombre, instanciaInscripcionEnV, instanciaInscripcionEn1A, instanciaInscripcionEn1B, instanciaInscripcionEn2A, instanciaInscripcionEn2B,
                            instanciaCuatrimestreMinimo, new ArrayList<>(), materiaTipologia, materiaCuatrimestreEnCarrera, materiaGastaCupo, materiaCreditos));
                } else { // Es instancia evaluativa
                    instancias.put(instanciaId, new InstanciaEvaluativa(instanciaId, instanciaNombre, instanciaInscripcionEnV, instanciaInscripcionEn1A, instanciaInscripcionEn1B, instanciaInscripcionEn2A, instanciaInscripcionEn2B,
                            instanciaCuatrimestreMinimo, new ArrayList<>()));
                }
            }
            
            // Tras haber insertado las instancias en la lista de instancias, se las insertó con la lista de correlativas vacía en todos los casos (ya que aún no estaban creadas todas las entidades necesarias)
            // Ahora se actualizará la lista de correlativas para cada instancia previamente insertada
            ObjectMapper objectMapper = new ObjectMapper(); // Usado para transformar el JSON de array de enteros que devuelve la DB en un array de JAVA
            for (int i = 0; i < results.size(); i ++) {
                Map<String, Object> instanciaFromDB = results.get(i);
                int instanciaId = ((Long) instanciaFromDB.get("instanciaId")).intValue();
                ArrayList<Integer> correlativasIds = objectMapper.readValue(instanciaFromDB.get("instanciaCorrelativas").toString(), new TypeReference<ArrayList<Integer>>(){});
                for (Integer instanciaCorrelativaId : correlativasIds) {
                    if (instancias.containsKey(instanciaCorrelativaId)) {
                        instancias.get(instanciaId).getCorrelativas().add(instancias.get(instanciaCorrelativaId));
                    } else {
                        throw new DBConnError("El ID de instancia correlativa de una instancia no fue encontrada en el resultado de la DB");
                    }
                }
            }
            
            for (Instancia instancia : instancias.values()) { // Inserta en la carrera la lista de instancias generadas
                carrera.getInstancias().add(instancia);
            }
        } catch (JsonProcessingException | DBConnError | InvalidOrNullParam | InvalidSQLParam e) {
            System.out.println("Error al obtener instancias dado una carrera: " + e);
            throw new DBConnError();
        }
    }
}
