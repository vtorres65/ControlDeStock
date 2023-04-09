package com.alura.tests;

import com.alura.jdbc.factory2.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {
    public static void main(String[] args) throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();

        System.out.println("Cerrando la conexión ;X");
        con.close();
    }
}
