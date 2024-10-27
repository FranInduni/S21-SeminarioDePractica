package com.finduni.s21efip.exceptions;

/**
 * DBConnError: Excepción para indicar problema al conectar con la base de datos
 * 
 * @author Francisco Induni
 */
public class DBConnError extends Exception {
    
    public DBConnError() {
        super();
    }
    
    public DBConnError(String mensaje) {
        super(mensaje);
    }
    
    public DBConnError(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
}
