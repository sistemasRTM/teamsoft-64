package persistencia;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import recursos.Sesion;
import bean.THCEPE;
import util.Conexion;

public class THCEPEDAO {
	
	public int insertarHCEPE(THCEPE thcepe) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("insert into "+ Sesion.bdProd+"certificadopercepcioncab(seriecertificado,numerocertificado,fechacertificado,codigocliente,porcentaper,montototper,situacion) values(?,?,?,?,?,?,?)");
		java.sql.Date fecha = new java.sql.Date(thcepe.getFecha().getTime());
		pstm.setInt(1, thcepe.getSerie());
		pstm.setInt(2, thcepe.getNumero());
		pstm.setDate(3, fecha);
		pstm.setString(4, thcepe.getCliente());
		pstm.setDouble(5, thcepe.getPperc());
		pstm.setDouble(6, thcepe.getItperc());
		pstm.setInt(7, thcepe.getSituacion());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}

}
