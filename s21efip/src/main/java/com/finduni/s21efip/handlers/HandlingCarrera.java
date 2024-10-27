package com.finduni.s21efip.handlers;

import com.finduni.s21efip.databaseinterface.DatabaseInterface;
import com.finduni.s21efip.databaseinterface.SQLParam;
import com.finduni.s21efip.databaseinterface.SQLParamType;
import com.finduni.s21efip.entities.carrera.Carrera;
import com.finduni.s21efip.entities.carrera.Modalidad;
import com.finduni.s21efip.entities.instancia.Instancia;
import com.finduni.s21efip.entities.instancia.InstanciaEvaluativa;
import com.finduni.s21efip.entities.instancia.Materia;
import com.finduni.s21efip.exceptions.DBConnError;
import com.finduni.s21efip.exceptions.InvalidOrNullParam;
import com.finduni.s21efip.exceptions.InvalidSQLParam;
import com.finduni.s21efip.exceptions.NoResultsError;
import java.util.ArrayList;
import java.util.Map;

/**
 * HandlingCarrera: Es la interfaz que maneja la persistencia de carreras en la DB.
 * 
 * @author Francisco Induni
 */
public class HandlingCarrera {
    private DatabaseInterface databaseInterface;
    private HandlingInstancia handlingInstancia;
    
    /*
    * Inicializador
    */
    public HandlingCarrera(DatabaseInterface databaseInterface, HandlingInstancia handlingInstancia) throws InvalidOrNullParam {
        if (databaseInterface != null) {
            this.databaseInterface = databaseInterface;
        } else {
            throw new InvalidOrNullParam("databaseInterface no puede ser nulo");
        }
        if (handlingInstancia != null) {
            this.handlingInstancia = handlingInstancia;
        } else {
            throw new InvalidOrNullParam("handlingInstancia no puede ser nulo");
        }
    }
    
    /*
    * Guarda una entidad carrera en la DB
    */
    public void guardarCarrera(Carrera carrera) throws DBConnError {
        try {
            // Prepara el llamado al procedimiento de guardar carrera
            ArrayList<SQLParam> params = new ArrayList<>();
            params.add(new SQLParam(SQLParamType.STRING, carrera.getNombre(), null, null));
            params.add(new SQLParam(SQLParamType.STRING, carrera.getModalidad().toString(), null, null));
            params.add(new SQLParam(SQLParamType.INT, null, carrera.getCreditosElectivas(), null));
            params.add(new SQLParam(SQLParamType.INT, null, carrera.getCuposVerano(), null));
            params.add(new SQLParam(SQLParamType.INT, null, carrera.getCuposNormal(), null));
            params.add(new SQLParam(SQLParamType.INT, null, carrera.getCuposSoloEnB(), null));
            params.add(new SQLParam(SQLParamType.INT, null, carrera.getCuposIngresantes(), null));
            
            this.databaseInterface.startTransaction(); // Inicializa una transacción ya que el insertado de materias e instancias evaluativas va por separado del insertado de la carrera en la DB. Si cualquier query falla se hará un rollback
            Integer carreraId = this.databaseInterface.callStoredProcedure("PROCEDURE_CARRERA_CREAR", params, true); // Inserta la carrera en la DB (aún no sus materias e instancias evaluativas)
            carrera.setCarreraId(carreraId); // Actualiza la entidad recibida con el ID correspondiente en DB.
            
            // Prepara la lista de instancias a guardar
            ArrayList<Instancia> instanciasAGuardar = new ArrayList<>();
            for (Instancia instancia : carrera.getInstancias()) {
                instanciasAGuardar.add(instancia);
            }
            
            while (!instanciasAGuardar.isEmpty()) { // Va insertando cada instancia (materia o instancia evaluativa) en DB a medida que puede (cuando sus correlativas ya fueron insertadas)
                boolean guardeUnaInstancia = false;
                for (Instancia instancia : instanciasAGuardar) {
                    boolean correlativasYaGuardadas = true;
                    for (Instancia instanciaCorrelativa : instancia.getCorrelativas()) {
                        if (instanciaCorrelativa.getInstanciaId() == null) {
                            correlativasYaGuardadas = false;
                            break;
                        }
                    }
                    if (correlativasYaGuardadas) { // Si la instancia revisada ya cuenta con sus correlativas insertadas en DB procede a insertarla a través del handler de Instancias.
                        if (instancia instanceof Materia) {
                            this.handlingInstancia.guardarMateria((Materia) instancia, carrera);
                            instanciasAGuardar.remove(instancia);
                            guardeUnaInstancia = true;
                            break;
                        } else if (instancia instanceof InstanciaEvaluativa) {
                            this.handlingInstancia.guardarInstanciaEvaluativa((InstanciaEvaluativa) instancia, carrera);
                            instanciasAGuardar.remove(instancia);
                            guardeUnaInstancia = true;
                            break;
                        }
                    }
                }
                if (!guardeUnaInstancia) { // Si termina el bucle de las instancias que faltan guardar sin haber guardado ninguna significa que hay un bucle infinito
                    throw new DBConnError("Se detectó un bucle infinito al guardar las instancias de la carrera");
                }
            }
            
            this.databaseInterface.commitTransaction(); // Si finalizo el insertado de instancias correctamente se realiza el commit de la transacción
        } catch (DBConnError | InvalidOrNullParam | InvalidSQLParam e) {
            System.out.println("Error al guardar carrera: " + e);
            this.databaseInterface.rollbackTransaction(); // Rollback de la transacción dado el error
            throw new DBConnError();
        }
    }
    
    /*
    * Dado un ID devuelve la entidad de la carrera obtenida desde la DB
    */
    public Carrera obtenerCarreraPorId(int carreraIdToGet) throws DBConnError, NoResultsError {
        try {
            // Prepara el llamado al procedimiento de obtener carrera por ID
            ArrayList<SQLParam> params = new ArrayList<>();
            params.add(new SQLParam(SQLParamType.INT, null, carreraIdToGet, null));
            
            ArrayList<Map<String, Object>> results = this.databaseInterface.callStoredProcedureForSelect("PROCEDURE_CARRERA_OBTENER", params);
            if (results.size() < 1) {
                throw new NoResultsError("Carrera no encontrada dado el ID");
            }
            
            // Crea la entidad carrera con los datos obtenidos desde la DB
            Map<String, Object> carreraFromDB = results.get(0);
            int carreraId = ((Long) carreraFromDB.get("carreraId")).intValue();
            String carreraNombre = (String) carreraFromDB.get("carreraNombre");
            Modalidad carreraModalidad = Modalidad.valueOf(carreraFromDB.get("carreraModalidad").toString());
            Integer carreraCreditosElectivas = ((Long) carreraFromDB.get("carreraCreditosElectivas")).intValue();
            Integer carreraCuposVerano = ((Long) carreraFromDB.get("carreraCuposVerano")).intValue();
            Integer carreraCuposNormal = ((Long) carreraFromDB.get("carreraCuposNormal")).intValue();
            Integer carreraCuposSoloEnB = ((Long) carreraFromDB.get("carreraCuposSoloEnB")).intValue();
            Integer carrerasCuposIngresantes = ((Long) carreraFromDB.get("carrerasCuposIngresantes")).intValue();
            
            
            Carrera carrera = new Carrera(carreraId, carreraNombre, carreraModalidad, carreraCreditosElectivas, carreraCuposVerano, carreraCuposNormal, carreraCuposSoloEnB, carrerasCuposIngresantes, new ArrayList<>());
            this.handlingInstancia.obtenerInstanciasDadoCarrera(carrera); // Rellena la carrera con sus instancias a través del handler de Instancias
            return carrera;
            
        } catch (DBConnError | InvalidOrNullParam | InvalidSQLParam | NoResultsError e) {
            System.out.println("Error al obtener carrera por Id: " + e);
            throw new DBConnError();
        }
    }
    
    /*
    * Devuelve una lista con todas las carreras guardadas en la DB
    */
    public ArrayList<Carrera> obtenerCarreras() throws DBConnError {
        try {
            ArrayList<Carrera> carreras = new ArrayList<>();
            
            ArrayList<Map<String, Object>> results = this.databaseInterface.callStoredProcedureForSelect("PROCEDURE_CARRERA_LISTAR", new ArrayList<>());
            
            // Por cada ID de carrera obtenido del procedimiento carreras listas llama a obtenerCarreraPorId para reutilizar el código de transcripción de los datos en DB a la entidad carrera del programa
            for (Map<String, Object> carreraFromDB : results) {
                int carreraId = ((Long) carreraFromDB.get("carreraId")).intValue();
                carreras.add(this.obtenerCarreraPorId(carreraId));
            }
            
            return carreras;
            
        } catch (DBConnError | NoResultsError e) {
            System.out.println("Error al obtener carreras: " + e);
            throw new DBConnError();
        }
    }
    
    /*
    * Borra una carrera de la DB
    */
    public void borrarCarrera(Carrera carrera) throws DBConnError, InvalidOrNullParam {
        if (carrera.getCarreraId() == null) {
            throw new InvalidOrNullParam("La carrera no puede tener ID nulo");
        }
        try {
            ArrayList<SQLParam> params = new ArrayList<>();
            params.add(new SQLParam(SQLParamType.INT, null, carrera.getCarreraId(), null));
            
            this.databaseInterface.callStoredProcedure("PROCEDURE_CARRERA_BORRAR", params, false);
            
        } catch (DBConnError | InvalidSQLParam e) {
            System.out.println("Error al borrar carrera: " + e);
            throw new DBConnError();
        }
    }
}
