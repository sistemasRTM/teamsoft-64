package recursos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import util.Conexion;

public class MaestroComboBox extends JComboBox {

	private static final long serialVersionUID = 1L;
		
	public MaestroComboBox(){
		
	}
	
	public MaestroComboBox(String tabla, String codigo, String descripcion,
			String filtro,boolean activar) {
		try {
			MaestroBean bean = null;
			PreparedStatement pstm = Conexion.obtenerConexion()
					.prepareStatement(
							"select " + codigo + "," + descripcion + " from "
									+ tabla + " where " + filtro);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new MaestroBean();
				bean.setCodigo(rs.getString(1).trim());
				bean.setDescripcion(rs.getString(2).trim());
				addItem(bean);
			}
			rs.close();
			pstm.close();
			if(activar){
			AutoCompleteDecorator.decorate(this);
			}
		} catch (SQLException e) {
		}
	}
	
	public MaestroComboBox(String tabla, String codigo, String descripcion,
			String filtro) {
		try {
			MaestroBean bean = null;
			PreparedStatement pstm = Conexion.obtenerConexion()
					.prepareStatement(
							"select " + codigo + "," + descripcion + " from "
									+ tabla + " where " + filtro);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new MaestroBean();
				bean.setCodigo(rs.getString(1).trim());
				bean.setDescripcion(rs.getString(2).trim());
				addItem(bean);
			}
			rs.close();
			pstm.close();
			AutoCompleteDecorator.decorate(this);
		} catch (SQLException e) {
		}
	}

	public MaestroComboBox(String tabla, String codigo, String descripcion) {
		try {
			MaestroBean bean = null;
			PreparedStatement pstm = Conexion.obtenerConexion()
					.prepareStatement(
							"select " + codigo + "," + descripcion + " from "
									+ tabla);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new MaestroBean();
				bean.setCodigo(rs.getString(1).trim());
				bean.setDescripcion(rs.getString(2).trim());
				addItem(bean);
			}
			rs.close();
			pstm.close();
			AutoCompleteDecorator.decorate(this);
		} catch (SQLException e) {
		}
	}
	
	public void executeQuery (String tabla, String codigo, String descripcion,String filtro){
		try {
			MaestroBean bean = null;
			PreparedStatement pstm = Conexion.obtenerConexion()
					.prepareStatement(
							"select " + codigo + "," + descripcion + " from "
									+ tabla + " where " + filtro);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new MaestroBean();
				bean.setCodigo(rs.getString(1).trim());
				bean.setDescripcion(rs.getString(2).trim());
				addItem(bean);
			}
			rs.close();
			pstm.close();
			AutoCompleteDecorator.decorate(this);
		} catch (SQLException e) {
		}
	}
	
	public void executeQuery (String tabla, String codigo, String descripcion){
		try {
			MaestroBean bean = null;
			PreparedStatement pstm = Conexion.obtenerConexion()
					.prepareStatement(
							"select " + codigo + "," + descripcion + " from "
									+ tabla);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new MaestroBean();
				bean.setCodigo(rs.getString(1).trim());
				bean.setDescripcion(rs.getString(2).trim());
				addItem(bean);
			}
			rs.close();
			pstm.close();
			AutoCompleteDecorator.decorate(this);
		} catch (SQLException e) {
		}
	}

}
