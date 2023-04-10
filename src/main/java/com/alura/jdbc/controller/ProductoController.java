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

        prepareStatement statement = con.prepareStatement("UPDATE PRODUCTO SET "
                + " NOMBRE = ?"
                + ", DESCRIPCION = ?"
                + ", CANTIDAD = ?"
                + "WHERE ID = ?");
        statement.setString(1, nombre);
        statement.setString(2, descripcion);
        statement.setInt(3, cantidad);
        statement.setInt(4, id);

        statement.execute();

        int updateCount = statement.getUpdateCount();

        con.close();

        return updateCount;
    }

    public int eliminar(Integer id) throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();

        PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
        statement.setInt(1, id);

        statement.execute();

        int updateCount = statement.getUpdateCount();

        con.close():;

        return updateCount;
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
        String nombre = producto.get("NOMBRE")
        String descripcion = producto.get("DESCRIPCION");
        Integer cantidad = Integer.valueOf(producto.get("CANTIDAD"));
        Integer maximoCantidad = 50;

        ConnectionFactory factory = new ConnectionFactory();
        Connection con = factory.recuperaConexion();
        con.setAutoCommit(false);

        PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTO(nombre, descripcion, cantidad)"
        + " VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

        do {
            int cantidadParaGuardar = Math.min(cantidad, maximoCantidad);

            ejecutaRegistro(nombre, descripcion, cantidadParaGuardar, statement);
            cantidad -= maximoCantidad;
        }while (cantidad > 0);


        con.close();
    }

    private static void ejecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement statement)
            throw SQLException {
        statement.setString(1, nombre);
        statement.setString(2, descripcion);
        statement.setInt(3, cantidad);

        statement.execute();

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
