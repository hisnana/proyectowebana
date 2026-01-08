package es.cursojava.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneradorLog {
	
	public Logger generarLog (Object objeto) {
		Logger log = LoggerFactory.getLogger(objeto.getClass().toString());
		
		return log;
	}
	
	

}
