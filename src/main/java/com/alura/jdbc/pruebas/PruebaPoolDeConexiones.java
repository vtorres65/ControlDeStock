package com.alura.jdbc.pruebas;

import java.sql.SQLException;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.factory2.ConnectionFactory;

public class PruebaPoolDeConexiones {

    public static void main(String[] args) throws SQLException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        
        for (int i = 0; i < 20; i++) {
            ConnectionFactory conexion = (ConnectionFactory) connectionFactory.recuperaConexion();
            
            System.out.println("Abriendo conexión #" + i);
        }
    }
    
}
