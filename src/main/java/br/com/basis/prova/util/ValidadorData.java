package br.com.basis.prova.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ValidadorData {
	
	public static boolean validarData(LocalDate data) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	df.setLenient(false);
    	try {
    	    df.parse(data.toString());
    	    return true;
    	} catch (ParseException e) {
    		return false;
    	}
	}
}
