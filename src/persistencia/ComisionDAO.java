package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import recursos.Sesion;
import util.Conexion;
import bean.CalculoComision;
import bean.Comision;
import bean.EjerPer;
import bean.Vendedor;

public class ComisionDAO {

	public List<Comision> buscar(String nombre) throws SQLException {
		Comision comision = null;
		Vendedor vendedor = null;
		List<Vendedor> vendedores = new ArrayList<Vendedor>();
		List<Comision> comisiones = new ArrayList<Comision>();

		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"Select agecve,agenom from tagen where upper(agenom) like ? ");
		pstm.setString(1, nombre.toUpperCase() + "%");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			vendedor = new Vendedor();
			vendedor.setCodigo(rs.getString(1));
			vendedor.setNombre(rs.getString(2));
			vendedores.add(vendedor);
		}

		for (int i = 0; i < vendedores.size(); i++) {
			pstm = Conexion.obtenerConexion().prepareStatement(
					"Select * from " + Sesion.bdProd
							+ "comision where codigovendedor=?");
			pstm.setString(1, vendedores.get(i).getCodigo());
			rs = pstm.executeQuery();
			while (rs.next()) {
				comision = new Comision();
				comision.setVendedor(vendedores.get(i));
				comision.setFecha_inicial(rs.getDate("fechainicial"));
				comision.setFecha_final(rs.getDate("fechafinal"));
				comision.setComision_mayor(rs.getDouble("comisionmayor"));
				comision.setComision_menor(rs.getDouble("comisionmenor"));
				comision.setAcumula(rs.getString("Acumula"));
				comision.setSituacion(rs.getInt("Situacion"));
				comision.setMaestro(rs.getInt("maestro"));
				comision.setComision_mayor_m(rs.getDouble("comisionmayormaestro"));
				comision.setComision_menor_m(rs.getDouble("comisionmenormaestro"));
				comisiones.add(comision);
			}
		}

		rs.close();
		pstm.close();
		return comisiones;
	}

	public boolean validarInsert(Comision comision) throws SQLException {
		boolean existe = false;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		java.sql.Date fechaIni = new java.sql.Date(comision.getFecha_inicial()
				.getTime());
		java.sql.Date fechaFin = new java.sql.Date(comision.getFecha_final()
				.getTime());
		pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"select * from "
								+ Sesion.bdProd
								+ "comision where codigovendedor =?  and (?  between fechainicial and fechafinal or ? between fechainicial and fechafinal)");
		pstm.setString(1, comision.getVendedor().getCodigo());
		pstm.setDate(2, fechaIni);
		pstm.setDate(3, fechaFin);
		rs = pstm.executeQuery();
		while (rs.next()) {
			existe = true;
		}
		rs.close();
		pstm.close();
		return existe;
	}

	public boolean validarUpdate(Comision comision, Comision antiguo)
			throws SQLException {
		boolean existe = false;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		java.sql.Date fechaIni = new java.sql.Date(comision.getFecha_inicial()
				.getTime());
		java.sql.Date fechaFin = new java.sql.Date(comision.getFecha_final()
				.getTime());
		pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"select fechainicial,fechafinal from "
								+ Sesion.bdProd
								+ "comision where codigovendedor =?  and (?  between fechainicial and fechafinal or ? between fechainicial and fechafinal)");
		pstm.setString(1, comision.getVendedor().getCodigo());
		pstm.setDate(2, fechaIni);
		pstm.setDate(3, fechaFin);
		rs = pstm.executeQuery();
		while (rs.next()) {
			String fechaIniFormateada = Sesion.formateaFechaVista(antiguo
					.getFecha_inicial());
			String fechaFinFormateada = Sesion.formateaFechaVista(antiguo
					.getFecha_final());
			String fechaIniBDFormateada = Sesion.formateaFechaVista(rs
					.getDate(1));
			String fechaFinBDFormateada = Sesion.formateaFechaVista(rs
					.getDate(2));

			if (!fechaIniBDFormateada.equals(fechaIniFormateada)
					|| !fechaFinBDFormateada.equals(fechaFinFormateada)) {
				existe = true;
				break;
			}

		}
		rs.close();
		pstm.close();
		System.out.println(existe);
		return existe;
	}

	public int registrarComision(Comision comision) throws SQLException {
		int resultado = -1;

		java.sql.Date fechaIni = new java.sql.Date(comision.getFecha_inicial()
				.getTime());
		java.sql.Date fechaFin = new java.sql.Date(comision.getFecha_final()
				.getTime());

		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"insert into "
								+ Sesion.bdProd
								+ "comision(codigovendedor,fechainicial,fechafinal,comisionmayor,comisionmenor,situacion,acumula,maestro,comisionmayormaestro,comisionmenormaestro) values(?,?,?,?,?,?,?,?,?,?)");
		pstm.setString(1, comision.getVendedor().getCodigo());
		pstm.setDate(2, fechaIni);
		pstm.setDate(3, fechaFin);
		pstm.setDouble(4, comision.getComision_mayor());
		pstm.setDouble(5, comision.getComision_menor());
		pstm.setDouble(6, comision.getSituacion());
		pstm.setString(7, comision.getAcumula());
		pstm.setInt(8, comision.getMaestro());
		pstm.setDouble(9, comision.getComision_mayor_m());
		pstm.setDouble(10, comision.getComision_menor_m());
		resultado = pstm.executeUpdate();

		pstm.close();

		return resultado;
	}

	public int modificarComision(Comision comision, Comision antigua)
			throws SQLException {
		int resultado = -1;

		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"update "
								+ Sesion.bdProd
								+ "comision set fechainicial = ?,fechafinal = ?,comisionmayor= ?,comisionmenor = ?,situacion = ?, acumula = ?,maestro =?,comisionmayormaestro=?,comisionmenormaestro=? where codigovendedor = ? and fechainicial = ? ");

		java.sql.Date fechaIni = new java.sql.Date(comision.getFecha_inicial()
				.getTime());
		java.sql.Date fechaFin = new java.sql.Date(comision.getFecha_final()
				.getTime());
		java.sql.Date fechaIniAntigua = new java.sql.Date(antigua
				.getFecha_inicial().getTime());

		pstm.setDate(1, fechaIni);
		pstm.setDate(2, fechaFin);
		pstm.setDouble(3, comision.getComision_mayor());
		pstm.setDouble(4, comision.getComision_menor());
		pstm.setInt(5, comision.getSituacion());
		pstm.setString(6, comision.getAcumula());
		pstm.setInt(7, comision.getMaestro());
		pstm.setDouble(8, comision.getComision_mayor_m());
		pstm.setDouble(9, comision.getComision_menor_m());
		pstm.setString(10, comision.getVendedor().getCodigo().trim());
		pstm.setDate(11, fechaIniAntigua);
		resultado = pstm.executeUpdate();

		pstm.close();

		return resultado;

	}

	public List<Comision> listarActivos(Date desde, Date hasta)
			throws SQLException {
		List<Comision> comisiones = new ArrayList<Comision>();
		Comision comision = null;
		Vendedor vendedor = null;
		java.sql.Date fechaIni = new java.sql.Date(desde.getTime());
		java.sql.Date fechaFin = new java.sql.Date(hasta.getTime());
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"Select * from "
								+ Sesion.bdProd
								+ "comision where situacion = 1 and (?  between fechainicial and fechafinal or ? between fechainicial and fechafinal)");
		pstm.setDate(1, fechaIni);
		pstm.setDate(2, fechaFin);
		ResultSet rs = pstm.executeQuery();

		while (rs.next()) {
			comision = new Comision();
			vendedor = new Vendedor();
			vendedor.setCodigo(rs.getString("codigovendedor"));
			comision.setVendedor(vendedor);
			comision.setFecha_inicial(rs.getDate("fechainicial"));
			comision.setFecha_final(rs.getDate("fechafinal"));
			comision.setComision_mayor(rs.getDouble("comisionmayor"));
			comision.setComision_menor(rs.getDouble("comisionmenor"));
			comision.setAcumula(rs.getString("Acumula"));
			comisiones.add(comision);
		}

		rs.close();
		pstm.close();

		return comisiones;
	}

	public int grabarHistorico(List<CalculoComision> pedidosCalculados)
			throws SQLException {

		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"insert into "
								+ Sesion.bdProd
								+ "comisionhistorica(codigovendedor,periodo,fechainicial,fechafinal,montomayor,montomenor,comisionmayor,comisionmenor,situacion,fproc,uproc,ciaproc) values(?,?,?,?,?,?,?,?,?,?,?,?)");
		int resultado = 0;
		//Conexion.obtenerConexion().setAutoCommit(false);
		for (CalculoComision calculado : pedidosCalculados) {
			java.sql.Date fechaIni = new java.sql.Date(calculado
					.getFechaInicial().getTime());
			java.sql.Date fechaFin = new java.sql.Date(calculado
					.getFechaFinal().getTime());
			java.sql.Date fecha = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			pstm.setString(1, calculado.getPhusap());
			pstm.setString(2, calculado.getPeriodo());
			pstm.setDate(3, fechaIni);
			pstm.setDate(4, fechaFin);
			pstm.setDouble(5, calculado.getVentaMayor());
			pstm.setDouble(6, calculado.getVentaMenor());
			pstm.setDouble(7, calculado.getComisionMayor());
			pstm.setDouble(8, calculado.getComisionMenor());
			pstm.setInt(9, 1);
			pstm.setDate(10, fecha);
			pstm.setString(11, Sesion.usuario);
			pstm.setString(12, Sesion.cia);
			resultado = pstm.executeUpdate();
		}
		//Conexion.obtenerConexion().setAutoCommit(true);
		pstm.close();
		return resultado;
	}

	public int actualizarHistorico(Date desde, Date hasta) throws SQLException {
		int resultado = 0;
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"update "
								+ Sesion.bdProd
								+ "comisionhistorica set situacion=0 where situacion=1 and fechainicial=? and fechafinal=? ");
		java.sql.Date fechaIni = new java.sql.Date(desde.getTime());
		java.sql.Date fechaFin = new java.sql.Date(hasta.getTime());
		pstm.setDate(1, fechaIni);
		pstm.setDate(2, fechaFin);
		resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}

	public int cerrarPeriodo(Date desde, Date hasta,String proceso) throws SQLException {
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"insert into "
								+ Sesion.bdProd
								+ "tperiodo(idperiodo,fechainicial,fechafinal,situacion,proceso)values(NEXT VALUE FOR prodtecni.CODPERIODO,?,?,?,?)");
		int resultado = 0;
		java.sql.Date fechaIni = new java.sql.Date(desde.getTime());
		java.sql.Date fechaFin = new java.sql.Date(hasta.getTime());
		pstm.setDate(1, fechaIni);
		pstm.setDate(2, fechaFin);
		pstm.setInt(3, 1);
		pstm.setString(4, proceso);
		resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}

	public int actualizarPeriodo(Date desde, Date hasta, int situacion)
			throws SQLException {
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"update "
								+ Sesion.bdProd
								+ "tperiodo set situacion=? where fechainicial=? and fechafinal=?");
		int resultado = 0;
		java.sql.Date fechaIni = new java.sql.Date(desde.getTime());
		java.sql.Date fechaFin = new java.sql.Date(hasta.getTime());
		pstm.setInt(1, situacion);
		pstm.setDate(2, fechaIni);
		pstm.setDate(3, fechaFin);
		resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}

	public int situacionPeriodo(Date desde, Date hasta) throws SQLException {
		int resultado = -1;
		java.sql.Date fechaIni = new java.sql.Date(desde.getTime());
		java.sql.Date fechaFin = new java.sql.Date(hasta.getTime());
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("select situacion from "
								+ Sesion.bdProd
								+ "tperiodo where ?  between fechainicial and fechafinal or ? between fechainicial and fechafinal");

		pstm.setDate(1, fechaIni);
		pstm.setDate(2, fechaFin);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			resultado = rs.getInt("situacion");
		}
		rs.close();
		pstm.close();
		return resultado;
	}

	public List<EjerPer> listarPeriodos() throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select * from " + Sesion.bdProd + "tperiodo");
		ResultSet rs = pstm.executeQuery();
		List<EjerPer> periodos = new ArrayList<EjerPer>();
		EjerPer periodo = null;
		while (rs.next()) {
			periodo = new EjerPer();
			periodo.setFechaInicial(rs.getDate("fechainicial"));
			periodo.setFechaFinal(rs.getDate("fechafinal"));
			periodo.setSituacion(rs.getInt("situacion"));
			periodos.add(periodo);
		}
		rs.close();
		pstm.close();
		return periodos;
	}
	
	public boolean verificaMaestro() throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("Select * from "+Sesion.bdProd+"comision where maestro=1");
		ResultSet rs = pstm.executeQuery();
		boolean resultado = false;
		while(rs.next()){
			 resultado = true;
		}
		rs.close();
		pstm.close();
		return resultado;
	}
	
	public boolean verificaMaestro(Comision comision) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("Select * from "+Sesion.bdProd+"comision where maestro=1 and codigovendedor=?");
		pstm.setString(1, comision.getVendedor().getCodigo().trim());
		ResultSet rs = pstm.executeQuery();
		boolean resultado = false;
		while(rs.next()){
			 resultado = true;
		}
		rs.close();
		pstm.close();
		return resultado;
	}
	
	public Comision buscarMaestro() throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("Select * from "+Sesion.bdProd+"comision where maestro=1");
		ResultSet rs = pstm.executeQuery();
		Comision comision = null;
		Vendedor vendedor = null;
		while(rs.next()){
			comision = new Comision();
			vendedor = new Vendedor();
			vendedor.setCodigo(rs.getString("codigovendedor"));
			comision.setVendedor(vendedor);
			comision.setComision_mayor_m(rs.getDouble("comisionmayormaestro"));
			comision.setComision_menor_m(rs.getDouble("comisionmenormaestro"));
		}
		rs.close();
		pstm.close();
		return comision;
	}
	
}
