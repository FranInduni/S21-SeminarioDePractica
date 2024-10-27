package com.finduni.s21efip.exceptions;

/**
 * InvalidSQLParam: Excepción para indicar que uno de los parámetros SQL recibidos es inválido
 * 
 * @author Francisco Induni
 */
public class InvalidSQLParam extends Exception {
    
    public InvalidSQLParam() {
        super();
    }
    
    public InvalidSQLParam(String mensaje) {
        super(mensaje);
    }
    
    public InvalidSQLParam(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
}
