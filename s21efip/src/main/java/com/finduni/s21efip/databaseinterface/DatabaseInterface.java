package com.finduni.s21efip.databaseinterface;

import static com.finduni.s21efip.databaseinterface.SQLParamType.BOOL;
import static com.finduni.s21efip.databaseinterface.SQLParamType.INT;
import static com.finduni.s21efip.databaseinterface.SQLParamType.NULL;
import static com.finduni.s21efip.databaseinterface.SQLParamType.STRING;
import com.finduni.s21efip.exceptions.DBConnError;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * DatabaseInterface: Interfaz que permite al resto del programa interactuar con la DB. Todas las interacciones son exclusivamente por medio de procedimientos almacenados
 * 
 * @author Francisco Induni
 */
public final class DatabaseInterface {
    static final String DB_URL = "jdbc:mysql://localhost:3306/gestion_carrera_universitaria";
    static final String USER = "root";
    static final String PASS = "adminadmin";
    
    private Connection conn;
    
    public DatabaseInterface() throws DBConnError {
        this.openConn();
    }
    
    /*
    *******************
    * Métodos públicos
    *******************
    */
    
    /*
    * Cierra la conexión abierta con la DB
    */
    public void closeConn() {
        try {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
                System.out.println("Conexión a DB cerrada");
            }
        } catch (SQLException e) { 
        }
    }
    
    /*
    * Inicia una nueva transacción en la DB
    */
    public void startTransaction() throws DBConnError {
        try {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.setAutoCommit(false);
            } else {
                throw new DBConnError();
            }
        } catch (SQLException e) {
            throw new DBConnError();
        }
    }
    
    /*
    * Hace un rollback de la transacción iniciada en la DB
    */
    public void rollbackTransaction() throws DBConnError {
        try {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.rollback();
                this.conn.setAutoCommit(true);
            } else {
                throw new DBConnError();
            }
        } catch (SQLException e) {
            throw new DBConnError();
        }
    }
    
    /*
    * Hace el commit de la transacción iniciada en la DB
    */
    public void commitTransaction() throws DBConnError {
        try {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.commit();
                this.conn.setAutoCommit(true);
            } else {
                throw new DBConnError();
            }
        } catch (SQLException e) {
            throw new DBConnError();
        }
    }
    
    /*
    * Llama a un procedimiento almacenado en la DB dado el nombre del procedimiento y una lista de parámetros.
    * Si se espera que el procedimiento devuelva un entero (usado en los procedimientos de INSERT que devuelven el ID del registro insertado) se debe enviar expectIntegerResult en true
    */
    public Integer callStoredProcedure(String procedureName, ArrayList<SQLParam> params, boolean expectIntegerResult) throws DBConnError {
        try {
            Integer result = null;
            if (this.conn != null && !this.conn.isClosed()) {
                // Arma el string de parámetros dependiendo de la cantidad de parámetros de entrada
                String paramsInSQL = "";
                for (int i = 0; i < params.size(); i++){
                    paramsInSQL += "?";
                    if (i < params.size() - 1) {
                        paramsInSQL += ", ";
                    }
                }
                // Si se espera un parámetro de salida lo agrega
                if (expectIntegerResult) {
                    if (!paramsInSQL.equals("")) {
                        paramsInSQL += ", ";
                    }
                    paramsInSQL += "?"; // Para el parámetro de salida
                }
                
                // Prepara la sentencia SQL para realizar el llamado
                String sql = "{call " + procedureName + "(" + paramsInSQL + ")}";
                CallableStatement stmt = conn.prepareCall(sql);
                
                // Inserta los parámetros de entrada requeridos para el procedimiento
                for (int i = 0; i < params.size(); i++){
                    if (null != params.get(i).getType()) switch (params.get(i).getType()) {
                        case STRING:
                            if (params.get(i).getStringValue() != null) {
                                stmt.setString(i+1, params.get(i).getStringValue());
                                break;
                            }
                        case INT:
                            if (params.get(i).getIntValue() != null) {
                                stmt.setInt(i+1, params.get(i).getIntValue());
                                break;
                            }
                        case BOOL:
                            if (params.get(i).getBoolValue() != null) {
                                stmt.setBoolean(i+1, params.get(i).getBoolValue());
                                break;
                            }
                        case NULL:
                            stmt.setNull(i+1, java.sql.Types.NULL);
                            break;
                        default:
                            stmt.setNull(i+1, java.sql.Types.NULL);
                            break;
                    }
                }
                
                if (expectIntegerResult) { // Si se espera un parámetro de salida lo inserta
                    stmt.registerOutParameter(params.size() + 1, java.sql.Types.INTEGER);
                }
                
                // Ejecuta la llamada al procedimiento
                stmt.execute();
                if (expectIntegerResult) {
                    result = stmt.getInt(params.size() + 1);
                }
                stmt.close(); // Cierra el statment
            } else {
                throw new DBConnError();
            }
            return result;
        } catch (SQLException e) {
            System.out.println("Error en callStoredProcedure - " + procedureName + ": " + e.toString());
            throw new DBConnError();
        }
    }
    
    /*
    * Llama a un procedimiento almacenado en la DB dado el nombre del procedimiento y una lista de parámetros.
    * Este método es exclusivo para procedimientos que realizar un Select.
    * Se devuelve una lista (cada elemento es una row obtenida en el SELECT) de diccionario <String, Objeto> siendo el string el nombre de la columna y el objeto su valor
    */
    public ArrayList<Map<String, Object>> callStoredProcedureForSelect(String procedureName, ArrayList<SQLParam> params) throws DBConnError {
        try {
            ArrayList<Map<String, Object>> results = new ArrayList<>();
            if (this.conn != null && !this.conn.isClosed()) {
                // Arma el string de parámetros dependiendo de la cantidad de parámetros de entrada
                String paramsInSQL = "";
                for (int i = 0; i < params.size(); i++){
                    paramsInSQL += "?";
                    if (i < params.size() - 1) {
                        paramsInSQL += ", ";
                    }
                }
                
                // Prepara la sentencia SQL para realizar el llamado
                String sql = "{call " + procedureName + "(" + paramsInSQL + ")}";
                CallableStatement stmt = conn.prepareCall(sql);
                
                // Inserta los parámetros de entrada requeridos para el procedimiento
                for (int i = 0; i < params.size(); i++){
                    if (null != params.get(i).getType()) switch (params.get(i).getType()) {
                        case STRING:
                            if (params.get(i).getStringValue() != null) {
                                stmt.setString(i+1, params.get(i).getStringValue());
                                break;
                            }
                        case INT:
                            if (params.get(i).getIntValue() != null) {
                                stmt.setInt(i+1, params.get(i).getIntValue());
                                break;
                            }
                        case BOOL:
                            if (params.get(i).getBoolValue() != null) {
                                stmt.setBoolean(i+1, params.get(i).getBoolValue());
                                break;
                            }
                        case NULL:
                            stmt.setNull(i+1, java.sql.Types.NULL);
                            break;
                        default:
                            stmt.setNull(i+1, java.sql.Types.NULL);
                            break;
                    }
                }
                
                // Ejecuta la llamada al procedimiento
                ResultSet rs = stmt.executeQuery();
                
                // Almacena el resultado en la lista results
                ResultSetMetaData rsMetaData = rs.getMetaData();
                int columnCount = rsMetaData.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(rsMetaData.getColumnLabel(i), rs.getObject(i));
                    }
                    results.add(row);
                }
                stmt.close(); // Cierra el statment
            } else {
                throw new DBConnError();
            }
            return results;
        } catch (SQLException e) {
            System.out.println("Error en callStoredProcedure - " + procedureName + ": " + e.toString());
            throw new DBConnError();
        }
    }
    
    /*
    *******************
    * Métodos privados
    *******************
    */
    
    /*
    * Inicializa una conexión con la base de datos
    */
    private void openConn() throws DBConnError {
        try {
            if (this.conn == null || (this.conn != null && !this.conn.isClosed())) {
                this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
            }
        } catch (SQLException e) {
            this.closeConn();
            System.out.println(e.toString());
            throw new DBConnError();
        }
    }
    
}
