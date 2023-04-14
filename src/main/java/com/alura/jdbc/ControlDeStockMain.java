package com.alura.jdbc;

import javax.swing.JFrame;

import com.alura.jdbc.controller.ProductoController;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.view.ControlDeStockFrame;

import java.sql.SQLException;

public class ControlDeStockMain {

	public static void main(String[] args, Producto Producto, ProductoController productoDAO) throws SQLException {
		ControlDeStockFrame produtoCategoriaFrame = new ControlDeStockFrame(Producto, productoDAO);
		produtoCategoriaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
