package persistencia;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import recursos.Sesion;
import bean.TDFCPE;
import util.Conexion;

public class TDFCPEDAO {
	
	public int insertarDFCPE(TDFCPE tdfcpe) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("insert into "+Sesion.bdProd+"docvtapercepcion(tipodocumento,seriedocumento,numerodocumento,codarticulo,valorvtaart,valorpercep,valorfinalart,situacion) values(?,?,?,?,?,?,?,?)");
		pstm.setString(1, tdfcpe.getPdtdoc());
		pstm.setInt(2, tdfcpe.getPdpvta());
		pstm.setInt(3, tdfcpe.getPdfabo());
		pstm.setString(4, tdfcpe.getPdarti());
		pstm.setDouble(5, tdfcpe.getPdnpvt());
		pstm.setDouble(6, tdfcpe.getVperc());
		pstm.setDouble(7, tdfcpe.getVtotartperc());
		pstm.setInt(8, tdfcpe.getSituacion());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}

}
