package com.alura.jdbc.controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory2.ConnectionFactory;
import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {

    private ProductoDAO productoDao;
    
    public ProductoController() {
        var factory = new com.alura.jdbc.factory.ConnectionFactory();
        this.productoDao = new ProductoDAO(factory.recuperaConexion());
    }

    public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection con = new ConnectionFactory().recuperaConexion();

        Statement statement = con.createStatement();
        statement.execute("UPDATE PRODUCTO SET "
                + " NOMBRE = '" + nombre + "'"
                + ", DESCRIPCION = '" + descripcion + "'"
                + ", CANTIDAD = " + cantidad
                + "WHERE ID = " + id);
        int updateCount = statement.getUpdateCount();

        con.close();

        return updateCount;
    }

    public int eliminar(Integer id) throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();

        Statement statement = con.createStatement();

        statement.execute("DELETE FROM PRODUCTO WHERE ID = " + id);

        return statement.getUpdateCount();
    }

    public List<Map<String, String>> listar() throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();

        Statement statement = con.createStatement();

        statement.execute("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");

        ResultSet resultSet = statement.getResultSet();

        List<Map<String, String>> resultado = new ArrayList<>();
        while (resultSet.next()){
            Map<String, String> fila = new HashMap<>();
            fila.put("ID", String.valueOf(resultSet.getInt("ID")));
            fila.put("NOMBRE", resultSet.getString("NOMBRE"));
            fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
            fila.put("CANTIDAD", String.valueOf(resultSet.getString("CANTIDAD")));

            resultado.add(fila);
        }

        con.close();

        return resultado;
    }

    public void guardar(Map<String, String> producto) throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();

        Statement statement = con.createStatement();

        statement.execute("INSERT INTO PRODUCTO(nombre, descripcion, cantidad)"
        + " VALUES('" + producto.get("NOMBRE") + "','"
        + producto.get("DESCRIPCION") + "',"
        + producto.get("CANTIDAD") + ")", Statement.RETURN_GENERATED_KEYS);
        
        ResultSet resultSet = statement.getGeneratedKeys();
        
        while (resultSet.next()){
            System.out.println(String.format(
                    "Fue insertado el producto de ID %d",resultSet.getInt(1)));
        }
    }

    public List<Producto> listar(Categoria categoria) {
        return productoDao.listar(categoria);
    }

}
