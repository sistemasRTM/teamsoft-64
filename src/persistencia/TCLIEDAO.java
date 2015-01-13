package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Conexion;
import bean.TCLIE;

public class TCLIEDAO {
	
	public TCLIE getCliente(String codigo) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select clicve,clinom,clidir,clidis,clipro,clidpt from tclie where clicve=?");
		pstm.setString(1, codigo);
		ResultSet rs = pstm.executeQuery();
		TCLIE tclie = new TCLIE();
		while(rs.next()){
			tclie.setClicve(rs.getString("clicve"));
			tclie.setClinom(rs.getString("clinom"));
			tclie.setClidir(rs.getString("clidir"));
			tclie.setClidis(rs.getString("clidis"));
			tclie.setClidpt(rs.getString("clidpt"));
			tclie.setClipro(rs.getString("clipro"));
		}
		rs.close();
		pstm.close();
		return tclie;
	}
	
	public TCLIE getClienteGrupo(String codigo) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select gclcod,gcldes from tgclh where gclcod=?");
		pstm.setString(1, codigo);
		ResultSet rs = pstm.executeQuery();
		TCLIE tclie = new TCLIE();
		while(rs.next()){
			tclie.setClicve(rs.getString("gclcod"));
			tclie.setClinom(rs.getString("gcldes"));
		}
		rs.close();
		pstm.close();
		return tclie;
	}

	public List<TCLIE> getClientes() throws SQLException{
		List<TCLIE> lista = new ArrayList<TCLIE>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select clicve from tclie order by clinom");
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TCLIE nuevo = new TCLIE();
			nuevo.setClicve(rs.getString("clicve"));
			lista.add(nuevo);
		}
		rs.close();
		pstm.close();
		return lista;
	}
}
