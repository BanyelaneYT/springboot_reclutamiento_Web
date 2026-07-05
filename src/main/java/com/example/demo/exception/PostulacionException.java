package com.example.demo.exception;

public class PostulacionException extends RuntimeException {

    public static final String CODIGO_MISMO_PUESTO = "MISMO_PUESTO";
    public static final String CODIGO_PROCESO_ACTIVO = "PROCESO_ACTIVO";

    private final String codigo;

    public PostulacionException(String codigo, String mensaje) {
        super(mensaje);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
