package com.tofitsolutions.armasdurasargentinas.util;

public class Util {
    //static String host;
    static String dbLocal;
    //LOCAL
    //static String host = "192.168.0.108:8080";

    //PRODUCCION
    static String host = "armadurasargentinas.herokuapp.com";


    //static String dbLocal = "192.168.5.109:3306";
    public static String getHost(){
        return host;
    }
    /*
    public static String getDbLocal(){
        return dbLocal;
    }
*/

	public static double setearDosDecimales(double numero) {

		double response = Math.round(numero* 10d) / 10d;
		return response;
	}
}


