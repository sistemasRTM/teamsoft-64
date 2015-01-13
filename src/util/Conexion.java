package util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import recursos.Sesion;
import bean.Empleado;

public class Conexion {

	private static String url = "";
	private static String usuario = "";
	private static String password = "";
	private static Connection cn = null;
	
	static {
		try {
			Class.forName("com.ibm.as400.access.AS400JDBCDriver");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} 
	}

	// conexion para login
	public static String obtenerConexion(Empleado objEmpleado) {
		url = "jdbc:as400://"+Sesion.ip+";prompt=false";
		usuario = objEmpleado.getStrUsuario();
		password = objEmpleado.getStrClave();
		String estado = "";
		try {
			cn = DriverManager.getConnection(url, usuario, password);
			estado = "exito";
		} catch (SQLException ex) {
			estado = ex.getMessage();
		}
		return estado;
	}
	// conexion para cia
	public static Connection obtenerConexion(String cia) {
		url = "jdbc:as400://"+Sesion.ip+"/SPEED400"+cia;//+"; libraries=SPEEDOBJTC";
		//url = "jdbc:as400://"+Sesion.ip+"/PRODTECNI";
		try {
			cn = DriverManager.getConnection(url, usuario, password);
			CallableStatement cst = cn.prepareCall("{CALL SPEED407.MA1004('"+cia+"')}");
			cst.execute();
			cst.close();
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return cn;
	}
	
	// conexion para cia
	public static void reconectar() throws SQLException {
		url = "jdbc:as400://"+Sesion.ip+"/SPEED400"+Sesion.cia;
		cn = DriverManager.getConnection(url, usuario, password);
		CallableStatement cst = cn.prepareCall("{CALL SPEED407.MA1004('"+Sesion.cia+"')}");
		cst.execute();
		cst.close();
	}

	public static Connection obtenerConexion() {
		return cn;
	}

	public static void desconectar() throws SQLException {
		cn.close();
		
	}
		
}