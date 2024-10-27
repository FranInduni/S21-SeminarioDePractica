package com.finduni.s21efip.databaseinterface;

import com.finduni.s21efip.exceptions.InvalidSQLParam;

/**
 * SQLParam: Usado para enviar a DatabaseInterface al momento de ejecutar un stored procedure. Permite indicar el tipo de parámetro (String, Integer, Bool, NULL) y su valor.
 * 
 * @author Francisco Induni
 */
public class SQLParam {
    private SQLParamType type;
    private String stringValue;
    private Integer intValue;
    private Boolean boolValue;
    
    /*
    * Inicializador. Se debe enviar obligatoriamente el tipo de parámetro y un string, integer o boolean si corresponde
    */
    public SQLParam(SQLParamType type, String stringValue, Integer intValue, Boolean boolValue) throws InvalidSQLParam {
        if (type == null) {
            throw new InvalidSQLParam();
        }
        this.type = type;
        switch (type) {
            case STRING:
                this.stringValue = stringValue;
                break;
            case INT:
                this.intValue = intValue;
                break;
            case BOOL:
                this.boolValue = boolValue;
                break;
            default:
                break;
        }
    }
    
    /*
    * Getters y Setters
    */
    
    public SQLParamType getType() {
        return type;
    }
    
    public void setType(SQLParamType type) {
        this.type = type;
    }
    
    public String getStringValue() {
        return stringValue;
    }
    
    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
    
    public Integer getIntValue() {
        return intValue;
    }
    
    public void setIntValue(Integer intValue) {
        this.intValue = intValue;
    }
    
    public Boolean getBoolValue() {
        return boolValue;
    }
    
    public void setBoolValue(Boolean boolValue) {
        this.boolValue = boolValue;
    }
}
