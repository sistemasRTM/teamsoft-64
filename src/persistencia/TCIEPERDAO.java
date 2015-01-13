package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.TCIEPER;
import util.Conexion;

public class TCIEPERDAO {

	public List<TCIEPER> listar() throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select * from prodtecni.tcieper");
		ResultSet rs = pstm.executeQuery();
		List<TCIEPER> listado = new ArrayList<TCIEPER>();
		while(rs.next()){
			TCIEPER tcieper = new TCIEPER();
			tcieper.setEjercicio(rs.getString("ejercicio"));
			tcieper.setPeriodo(rs.getString("periodo"));
			tcieper.setSituacion(rs.getString("situacion"));
			tcieper.setProceso(rs.getString("proceso"));
			tcieper.setUsucre(rs.getString("usucre"));
			tcieper.setFechacre(rs.getString("fechacre"));
			tcieper.setUsumod(rs.getString("usumod"));
			tcieper.setFechamos(rs.getString("fechamos"));
			listado.add(tcieper);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TCIEPER> obtener(String ejercicio,String periodo) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select * from prodtecni.tcieper where ejercicio=? and periodo=?");
		pstm.setString(1, ejercicio);
		pstm.setString(2, periodo);
		ResultSet rs = pstm.executeQuery();
		List<TCIEPER> listado = new ArrayList<TCIEPER>();
		while(rs.next()){
			TCIEPER tcieper = new TCIEPER();
			tcieper.setEjercicio(rs.getString("ejercicio"));
			tcieper.setPeriodo(rs.getString("periodo"));
			tcieper.setSituacion(rs.getString("situacion"));
			tcieper.setProceso(rs.getString("proceso"));
			listado.add(tcieper);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public TCIEPER obtenerUltimoPeriodo(String proceso) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select ejercicio,periodo,situacion,proceso,integer(ejercicio||periodo) as ejerperi from prodtecni.tcieper where proceso=? order by ejerperi desc  fetch first 1 rows only ");
		pstm.setString(1, proceso);
		ResultSet rs = pstm.executeQuery();
		TCIEPER tcieper = null;
		while(rs.next()){
			tcieper = new TCIEPER();
			tcieper.setEjercicio(rs.getString("ejercicio"));
			tcieper.setPeriodo(rs.getString("periodo"));
			tcieper.setSituacion(rs.getString("situacion"));
			tcieper.setProceso(rs.getString("proceso"));
		}
		rs.close();
		pstm.close();
		return tcieper;
	}
	
	public int insertar(TCIEPER tcieper) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"insert into prodtecni.tcieper (ejercicio,periodo,situacion,proceso,usucre,fechacre) " +
				"values (?,?,?,?,?,?)");
		pstm.setString(1, tcieper.getEjercicio());
		pstm.setString(2, tcieper.getPeriodo());
		pstm.setString(3, tcieper.getSituacion());
		pstm.setString(4, tcieper.getProceso());
		pstm.setString(5, tcieper.getUsucre());
		pstm.setString(6, tcieper.getFechacre());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
	
}
