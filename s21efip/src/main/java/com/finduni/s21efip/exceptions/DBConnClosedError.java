package com.finduni.s21efip.exceptions;

/**
 * DBConnClosedError: Excepción para indicar que la conexión a la base de datos está cerrada
 * 
 * @author Francisco Induni
 */
public class DBConnClosedError extends Exception {
    
    public DBConnClosedError() {
        super();
    }
    
    public DBConnClosedError(String mensaje) {
        super(mensaje);
    }
    
    public DBConnClosedError(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
}
