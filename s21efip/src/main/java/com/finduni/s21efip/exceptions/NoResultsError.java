package com.finduni.s21efip.exceptions;

/**
 * NoResultsError: Excepción para indicar que no se encontraron resultados cuando se esperaba encontrar.
 * 
 * @author Francisco Induni
 */
public class NoResultsError extends Exception {
    
    public NoResultsError() {
        super();
    }
    
    public NoResultsError(String mensaje) {
        super(mensaje);
    }
    
    public NoResultsError(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
}
