package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;
import bean.Empresa;

public class EmpresaDAO {
	
	public List<Empresa> listar() throws SQLException {
		
		Empresa empresa = null;
		List<Empresa> empresas = new ArrayList<Empresa>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select * from SPEED407.TCIAS");
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			empresa = new Empresa();
			empresa.setCodigo(rs.getString(1));
			empresa.setNombre(rs.getString(2));
			empresa.setRuc(rs.getString(9));
			empresas.add(empresa);
		}
		rs.close();
		pstm.close();
		return empresas;
	}
	
	public Empresa buscarByPK(String codigo) throws SQLException {
		
		Empresa empresa = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select * from SPEED407.TCIAS where CIACVE=?");
		pstm.setString(1, codigo);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		empresa = new Empresa();
		empresa.setNombre(rs.getString(2));
		
		rs.close();
		pstm.close();
		return empresa;
	}

	public Connection verifica(String cia) {
		return Conexion.obtenerConexion(cia);
	}

}
