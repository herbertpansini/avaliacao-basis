package br.com.basis.prova.util;

import java.time.LocalDate;
import java.time.Period;

public class CalculadorIdade {
	
	public static int calcularIdade(LocalDate birthDate) {
        if (birthDate != null) {
            return Period.between(birthDate, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }
}
