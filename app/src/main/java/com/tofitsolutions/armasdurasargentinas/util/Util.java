package com.tofitsolutions.armasdurasargentinas.util;

public class Util {
    //static String host;
    static String dbLocal;
    //LOCAL
    static String host = "192.168.1.43:8080";

    //PRODUCCION
    //static String host = "armadurasargentinas.herokuapp.com";


    //static String dbLocal = "192.168.1.35:3306";
    public static String getHost(){
        return host;
    }
    /*
    public static String getDbLocal(){
        return dbLocal;
    }
*/
}


