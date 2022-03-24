package com.aplicacion.ejercicio24.configuraciones;

public class Transacciones {

    public static final String NAME_DATABASE = "DBfirmas";

    public static final String NAME_TABLE = "firmas";

    public static final String KEY_ID = "id";
    public static final String KEY_DESCRIPCION = "nombre";
    public static final String KEY_IMAGEN = "imagen";


    public static final String CREATE_TABLE_SIGNATORESS = "CREATE TABLE "+ NAME_TABLE + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_DESCRIPCION + " TEXT, " +
            KEY_IMAGEN + " BLOB)";

    public static final String DROP_TABLE_SIGNATORESS = "DROP TABLE IF EXISTS "+ NAME_TABLE;
}
