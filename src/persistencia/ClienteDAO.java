package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;
import bean.Cliente;

public class ClienteDAO {

	public List<Cliente> buscraByPk(String codigo) throws SQLException {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Cliente cliente = null;
		List<Cliente> clientes = new ArrayList<Cliente>();
		pstm = Conexion.obtenerConexion().prepareStatement(
				"Select * from tclie where clicve like ?");
		pstm.setString(1, codigo + "%");
		rs = pstm.executeQuery();
		while (rs.next()) {
			cliente = new Cliente();
			cliente.setCodigo(rs.getString("clicve"));
			cliente.setNombre(rs.getString("clinom"));
			cliente.setDireccion(rs.getString("clidir"));
			clientes.add(cliente);
		}
		rs.close();
		pstm.close();
		return clientes;
	}

	public int registrarCliente(Cliente cliente) throws SQLException {
		int resultado;
		PreparedStatement pstm = null;
		pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"insert into tclie(clicve, clinom, cliabr,cliruc,clnide,cltide,clidir,clidis,clipro,"
								+ "clidpt,clipai,clite1,clite2,clite3,clirf1,clirf3,clirf2,cliven,clicob,clizon,clisit,clilcr,clisal,cliscr,cpacve,cldsct,clprec,"
								+ "clcnom,clcpue,clmail)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		pstm.setString(1, cliente.getCodigo());
		pstm.setString(2, cliente.getNombre());
		pstm.setString(3, cliente.getAbreviatura());
		pstm.setString(4, cliente.getRuc());
		pstm.setString(5, cliente.getDni());
		pstm.setString(6, cliente.getTipoIde());
		pstm.setString(7, cliente.getDireccion());
		pstm.setString(8, cliente.getDistrito());
		pstm.setString(9, cliente.getProvincia());
		pstm.setString(10, cliente.getDepartamento());
		pstm.setString(11, cliente.getPais());
		pstm.setString(12, cliente.getTelef1());
		pstm.setString(13, cliente.getTelef2());
		pstm.setString(14, cliente.getTelef3());
		pstm.setString(15, cliente.getAxd());
		pstm.setString(16, cliente.getTipo());
		pstm.setString(17, cliente.getRefComercial());
		pstm.setString(18, cliente.getVendedor());
		pstm.setString(19, cliente.getCobrador());
		pstm.setString(20, cliente.getZona());
		pstm.setString(21, cliente.getSituacion());
		pstm.setDouble(22, cliente.getLimiteCredito());
		pstm.setDouble(23, cliente.getSaldoCtaCorriente());
		pstm.setString(24, cliente.getSituacionCredito());
		pstm.setString(25, cliente.getCondicionPago());
		pstm.setString(26, cliente.getListaPrecios());
		pstm.setString(27, cliente.getContactoNombre());
		pstm.setString(28, cliente.getContactoPuesto());
		pstm.setString(29, cliente.getContactoEmail());
		resultado = pstm.executeUpdate();
		pstm.close();

		return resultado;
	}

	public int modificarCliente(Cliente cliente) throws SQLException {
		int resultado;
		PreparedStatement pstm = null;
		pstm = Conexion.obtenerConexion().prepareStatement(
				"update tclie  set clinom = ? ,cliabr= ? ,"
						+ "cliruc= ? ,clnide= ? ,cltide= ? ," + "clidir= ? ,"
						+ "clidis= ? ,clipro= ? ," + "clidpt=? ,clipai= ? ,"
						+ "clite1= ? ,clite2= ? ,"
						+ "clite3= ? ,clirf2= ? ,clirf3= ? ,"
						+ "clirf1= ? ,cliven= ? ," + "clicob= ? ,clizon= ? ,"
						+ "clisit= ? ,clilcr= ? ," + "clisal= ? ,cliscr= ? ,"
						+ "cpacve= ? ,cldsct= ? ," + "clprec= ? ,clcnom= ? ,"
						+ "clcpue= ? ," + "clmail= ?  where clicve = ?  ");

		pstm.setString(1, cliente.getNombre());
		pstm.setString(2, cliente.getRuc());
		pstm.setString(3, cliente.getDni());
		pstm.setString(4, cliente.getTipoIde());
		pstm.setString(5, cliente.getDireccion());
		pstm.setString(6, cliente.getDistrito());
		pstm.setString(7, cliente.getProvincia());
		pstm.setString(8, cliente.getDepartamento());
		pstm.setString(9, cliente.getPais());
		pstm.setString(10, cliente.getTelef1());
		pstm.setString(11, cliente.getTelef2());
		pstm.setString(12, cliente.getTelef3());
		pstm.setString(13, cliente.getAxd());
		pstm.setString(14, cliente.getTipo());
		pstm.setString(15, cliente.getRefComercial());
		pstm.setString(16, cliente.getVendedor());
		pstm.setString(17, cliente.getCobrador());
		pstm.setString(18, cliente.getZona());
		pstm.setString(19, cliente.getSituacion());
		pstm.setDouble(20, cliente.getLimiteCredito());
		pstm.setDouble(21, cliente.getSaldoCtaCorriente());
		pstm.setString(22, cliente.getSituacionCredito());
		pstm.setString(23, cliente.getCondicionPago());
		pstm.setDouble(24, cliente.getPorcentajeDsc());
		pstm.setString(25, cliente.getListaPrecios());
		pstm.setString(26, cliente.getContactoNombre());
		pstm.setString(27, cliente.getContactoPuesto());
		pstm.setString(28, cliente.getContactoEmail());
		pstm.setString(29, cliente.getCodigo());

		resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
		
	}
	
	public Cliente verificaCliente(Cliente listacliente) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement("SELECT clicve FROM tclie WHERE clicve = ? ");
		pstm.setString(1, listacliente.getCodigo());
		
		Cliente listaCliente = null;
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			listaCliente = new Cliente();
			listaCliente.setCodigo(rs.getString(1));
		}			
		rs.close();
		pstm.close();

		return listaCliente;
	}
	
	
	
}


