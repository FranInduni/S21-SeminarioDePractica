package com.finduni.s21efip.exceptions;

/**
 * NoResultsError: Excepción para indicar que alguna condición/regla para crear una carrera no se cumple
 * 
 * @author Francisco Induni
 */
public class CareerConditionException extends Exception {
    
    public CareerConditionException() {
        super();
    }
    
    public CareerConditionException(String mensaje) {
        super(mensaje);
    }
    
    public CareerConditionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
}
