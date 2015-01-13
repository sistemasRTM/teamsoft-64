package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recursos.MaestroBean;
import recursos.Sesion;
import util.Conexion;
import bean.TCCM;

public class TCCMDAO {

	public List<TCCM> listar() throws SQLException {
		List<TCCM> listado = new ArrayList<TCCM>();
		TCCM objTCCM = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select * from " + Sesion.bdProd + "tccm order by CODFAM");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objTCCM = new TCCM();
			objTCCM.setId(rs.getInt("ID"));
			objTCCM.setCodfam(rs.getString("CODFAM"));
			objTCCM.setDesfam(rs.getString("DESFAM"));
			objTCCM.setCodsfa(rs.getString("CODSFA"));
			objTCCM.setDessfa(rs.getString("DESSFA"));
			objTCCM.setCodori(rs.getString("CODORI"));
			objTCCM.setDesori(rs.getString("DESORI"));
			objTCCM.setValor(rs.getDouble("VALXMA"));
			objTCCM.setCodigo(rs.getString("CODIGO"));
			listado.add(objTCCM);
		}
		rs.close();
		pstm.close();
		return listado;
	}

	public List<TCCM> buscarPorFamilia(TCCM tccm) throws SQLException {
		List<TCCM> listado = new ArrayList<TCCM>();
		TCCM objTCCM = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select * from " + Sesion.bdProd + "tccm where CODFAM=?");
		pstm.setString(1, tccm.getCodfam());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objTCCM = new TCCM();
			objTCCM.setId(rs.getInt("ID"));
			objTCCM.setCodfam(rs.getString("CODFAM"));
			objTCCM.setDesfam(rs.getString("DESFAM"));
			objTCCM.setCodsfa(rs.getString("CODSFA"));
			objTCCM.setDessfa(rs.getString("DESSFA"));
			objTCCM.setCodori(rs.getString("CODORI"));
			objTCCM.setDesori(rs.getString("DESORI"));
			objTCCM.setValor(rs.getDouble("VALXMA"));
			objTCCM.setCodigo(rs.getString("CODIGO"));
			listado.add(objTCCM);
		}
		rs.close();
		pstm.close();
		return listado;
	}

	public List<TCCM> buscarPorFamYSubFam(TCCM tccm) throws SQLException {
		List<TCCM> listado = new ArrayList<TCCM>();
		TCCM objTCCM = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select * from " + Sesion.bdProd
						+ "tccm where CODFAM=? and CODSFA=?");
		pstm.setString(1, tccm.getCodfam());
		pstm.setString(2, tccm.getCodsfa());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objTCCM = new TCCM();
			objTCCM.setId(rs.getInt("ID"));
			objTCCM.setCodfam(rs.getString("CODFAM"));
			objTCCM.setDesfam(rs.getString("DESFAM"));
			objTCCM.setCodsfa(rs.getString("CODSFA"));
			objTCCM.setDessfa(rs.getString("DESSFA"));
			objTCCM.setCodori(rs.getString("CODORI"));
			objTCCM.setDesori(rs.getString("DESORI"));
			objTCCM.setValor(rs.getDouble("VALXMA"));
			objTCCM.setCodigo(rs.getString("CODIGO"));
			listado.add(objTCCM);
		}
		rs.close();
		pstm.close();
		return listado;
	}

	public List<TCCM> buscarPorFamYSubFamyOri(TCCM tccm) throws SQLException {
		List<TCCM> listado = new ArrayList<TCCM>();
		TCCM objTCCM = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select * from " + Sesion.bdProd
						+ "tccm where DESFAM=? and DESSFA=? and DESORI=?");
		pstm.setString(1, tccm.getDesfam());
		pstm.setString(2, tccm.getDessfa());
		pstm.setString(3, tccm.getDesori());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objTCCM = new TCCM();
			objTCCM.setId(rs.getInt("ID"));
			objTCCM.setCodfam(rs.getString("CODFAM"));
			objTCCM.setDesfam(rs.getString("DESFAM"));
			objTCCM.setCodsfa(rs.getString("CODSFA"));
			objTCCM.setDessfa(rs.getString("DESSFA"));
			objTCCM.setCodori(rs.getString("CODORI"));
			objTCCM.setDesori(rs.getString("DESORI"));
			objTCCM.setValor(rs.getDouble("VALXMA"));
			objTCCM.setCodigo(rs.getString("CODIGO"));
			listado.add(objTCCM);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public int insertar(TCCM tccm) throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"SELECT NEXTVAL FOR " + Sesion.bdProd
						+ "CODTCCM FROM SYSIBM.SYSDUMMY1");
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int codigo = rs.getInt(1);
		rs.close();
		pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"insert into "
								+ Sesion.bdProd
								+ "tccm (ID,CODFAM,DESFAM,CODSFA,DESSFA,CODORI,DESORI,VALXMA,CODIGO) values(?,?,?,?,?,?,?,?,?)");
		pstm.setInt(1, codigo);
		pstm.setString(2, tccm.getCodfam());
		pstm.setString(3, tccm.getDesfam());
		pstm.setString(4, tccm.getCodsfa());
		pstm.setString(5, tccm.getDessfa());
		pstm.setString(6, tccm.getCodori());
		pstm.setString(7, tccm.getDesori());
		pstm.setDouble(8, tccm.getValor());
		pstm.setString(9, tccm.getCodigo());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
	
	public int modificar(TCCM tccm) throws SQLException {
	
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"update "
								+ Sesion.bdProd
								+ "tccm set CODFAM=?,DESFAM=?,CODSFA=?,DESSFA=?,CODORI=?,DESORI=?,VALXMA=? where CODIGO=?");
		
		pstm.setString(1, tccm.getCodfam());
		pstm.setString(2, tccm.getDesfam());
		pstm.setString(3, tccm.getCodsfa());
		pstm.setString(4, tccm.getDessfa());
		pstm.setString(5, tccm.getCodori());
		pstm.setString(6, tccm.getDesori());
		pstm.setDouble(7, tccm.getValor());
		pstm.setString(8, tccm.getCodigo());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
	
	public int eliminar(String codigo) throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"delete from " + Sesion.bdProd + "tccm where CODIGO=? ");
		pstm.setString(1,codigo);
		return pstm.executeUpdate();
	}

	public TCCM verificar(TCCM tccm) throws SQLException{
		TCCM resultado = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select CODIGO from " + Sesion.bdProd + "tccm where CODIGO=? ");
		pstm.setString(1, tccm.getCodigo());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			resultado = new TCCM();
			resultado.setCodigo(rs.getString("CODIGO"));
		}
		
		return resultado;
	}
	
	public List<MaestroBean> listarSubFamilias(TCCM tccm) throws SQLException {
		List<MaestroBean> listado = new ArrayList<MaestroBean>();
		MaestroBean objTCCM = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select substring(tbespe,4,6) codigo,desesp from ttabd where tbiden='INSFA' and substring(tbespe,1,3)=?");
		pstm.setString(1, tccm.getCodfam());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objTCCM = new MaestroBean();
			objTCCM.setCodigo(rs.getString("codigo"));
			objTCCM.setDescripcion(rs.getString("desesp"));
			listado.add(objTCCM);
		}
		return listado;
	}
	
	public List<MaestroBean> listarOrigenes(TCCM tccm) throws SQLException {
		List<MaestroBean> listado = new ArrayList<MaestroBean>();
		MaestroBean objTCCM = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select distinct substring(artcod,1,3) codigo,substring(artcod,1,3) descripcion from tarti where artfam=? and artsfa=?");
		pstm.setString(1, tccm.getCodfam());
		pstm.setString(2, tccm.getCodsfa());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objTCCM = new MaestroBean();
			objTCCM.setCodigo(rs.getString("codigo"));
			objTCCM.setDescripcion(rs.getString("descripcion"));
			listado.add(objTCCM);
		}
		return listado;
	}
	
	public List<TCCM> listarCriteriosParaExcepcion() throws SQLException {
		List<TCCM> listado = new ArrayList<TCCM>();
		TCCM objTCCM = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select * from " + Sesion.bdProd + "tccm where CODSFA='000' or CODORI='000' order by CODFAM");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objTCCM = new TCCM();
			objTCCM.setId(rs.getInt("ID"));
			objTCCM.setCodfam(rs.getString("CODFAM"));
			objTCCM.setDesfam(rs.getString("DESFAM"));
			objTCCM.setCodsfa(rs.getString("CODSFA"));
			objTCCM.setDessfa(rs.getString("DESSFA"));
			objTCCM.setCodori(rs.getString("CODORI"));
			objTCCM.setDesori(rs.getString("DESORI"));
			objTCCM.setValor(rs.getDouble("VALXMA"));
			objTCCM.setCodigo(rs.getString("CODIGO"));
			listado.add(objTCCM);
		}
		return listado;
	}
	
	public List<TCCM> buscarCriteriosParaExcepcionPorFamilia(TCCM tccm) throws SQLException {
		List<TCCM> listado = new ArrayList<TCCM>();
		TCCM objTCCM = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select * from " + Sesion.bdProd + "tccm where CODFAM=? and (CODSFA='000' or CODORI='000') order by CODFAM");
		pstm.setString(1, tccm.getCodfam());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objTCCM = new TCCM();
			objTCCM.setId(rs.getInt("ID"));
			objTCCM.setCodfam(rs.getString("CODFAM"));
			objTCCM.setDesfam(rs.getString("DESFAM"));
			objTCCM.setCodsfa(rs.getString("CODSFA"));
			objTCCM.setDessfa(rs.getString("DESSFA"));
			objTCCM.setCodori(rs.getString("CODORI"));
			objTCCM.setDesori(rs.getString("DESORI"));
			objTCCM.setValor(rs.getDouble("VALXMA"));
			objTCCM.setCodigo(rs.getString("CODIGO"));
			listado.add(objTCCM);
		}
		return listado;
	}
}
