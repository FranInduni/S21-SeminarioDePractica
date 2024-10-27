package com.finduni.s21efip.exceptions;

/**
 * InvalidOrNullParam: Excepción para indicar que un parámetro recibido es inválido o no puede ser nulo
 * 
 * @author Francisco Induni
 */
public class InvalidOrNullParam extends Exception {
    
    public InvalidOrNullParam() {
        super();
    }
    
    public InvalidOrNullParam(String mensaje) {
        super(mensaje);
    }
    
    public InvalidOrNullParam(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
}
