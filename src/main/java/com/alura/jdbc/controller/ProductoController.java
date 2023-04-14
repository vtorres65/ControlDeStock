package com.alura.jdbc.controller;

import java.sql.*;
import java.util.List;

import com.alura.jdbc.factory2.ConnectionFactory;
import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {

    private ProductoDAO productoDao;
    
    public ProductoController() throws SQLException {
        this.productoDao = new ProductoDAO(new ConnectionFactory().recuperaConexion());
    }

    public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
        return productoDao.modificar(nombre, descripcion, cantidad, id);
    }

    public int eliminar(Integer id) {
        return productoDao.eliminar(id);
    }

    public List<Producto> listar() {
        return productoDao.listar();
    }
    public void guardar(Producto producto, ProductoController productoDAO) {
        producto.setCategoriaId(producto.getCategoriaId());
        productoDAO.guardar(producto);
    }
    private void guardar(Producto producto) {
    }
    private static void ejecutaRegistro(Producto producto, PreparedStatement statement)
            throws SQLException {
        statement.setString(1, producto.getNombre());
        statement.setString(2, producto.getDescripcion());
        statement.setInt(3, producto.getCantidad());

        statement.execute();

        final ResultSet resultSet = statement.getGeneratedKeys();

        try(resultSet) {
            while (resultSet.next()){
                producto.setId(resultSet.getInt(1));
                System.out.println(String.format(
                        "Fue insertado el producto %s", producto));
            }
        }
    }

    public List<Producto> listar(Categoria categoria) {
        return productoDao.listar(categoria.getId());
    }

}
