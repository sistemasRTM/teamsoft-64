package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;
import bean.ListaPrecio;

public class ListaPrecioDAO {

	public List<ListaPrecio> listar() throws SQLException{
		ListaPrecio listaPrecio = null;
		List<ListaPrecio> listaPrecios = new ArrayList<ListaPrecio>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select distinct lpcodi from tlprh");
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			listaPrecio = new ListaPrecio();
			listaPrecio.setLpcodi(rs.getString(1));
			listaPrecios.add(listaPrecio);
		}
		rs.close();
		pstm.close();
		return listaPrecios;
	}
	
	public void registrarPrecios(ListaPrecio listaprecio) throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement("INSERT INTO TLPRH (lpcodi,lpsecu,lpdesc,lparti,lpfvta,lpunit,lpmone,lppdsc,lptigv,LPTIM2,LPTIM3,lpdeta,lpcomi,lpmult,lpunvt,lpprom,lpfinp,lpinip) " +
						"values (?,?,?,?,1,?,?,?,?,0,0,'N','S',1,?,0,0,0)");
		pstm.setString(1, listaprecio.getLpcodi());
		pstm.setInt(2, listaprecio.getLpsecu());
		pstm.setString(3, listaprecio.getDescripcion());
		pstm.setString(4, listaprecio.getCodigolp());
		pstm.setDouble(5, listaprecio.getPrecio());
		pstm.setInt(6, listaprecio.getMoneda());
		pstm.setDouble(7, listaprecio.getDescuento());
		pstm.setDouble(8, listaprecio.getLptigv());
		pstm.setString(9,listaprecio.getLpunvt());
		pstm.executeUpdate();
		pstm.close();
	}
	
	public void modificarPrecios(ListaPrecio listaprecio)
			throws SQLException {
	
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement("UPDATE TLPRH SET LPUNIT = ? WHERE LPARTI = ? AND LPCODI = ?");
		
		/*UPDATE TLPRH SET LPUNIT =?PUNIT WHERE LPARTI = ?CODI AND LPCODI = '03'*/
		
		pstm.setDouble(1, listaprecio.getPrecio());
		pstm.setString(2, listaprecio.getCodigolp());
		pstm.setString(3, listaprecio.getLpcodi());
	
		pstm.executeUpdate();
		pstm.close();
		
	}
	
	public ListaPrecio verificaPrecio(ListaPrecio listaprecio) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement("SELECT lpunit FROM TLPRH WHERE LPARTI=? AND LPCODI= ? ");
		pstm.setString(1, listaprecio.getCodigolp());
		pstm.setString(2, listaprecio.getLpcodi());
		ListaPrecio listaPrecio = null;
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			listaPrecio = new ListaPrecio();
			listaPrecio.setPrecio(rs.getDouble(1));
		}			
		rs.close();
		pstm.close();

		return listaPrecio;
	}
	
	public int getSecuencia() throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("SELECT max(lpsecu) FROM TLPRH");
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int secuencia = rs.getInt(1);
		rs.close();
		pstm.close();
		return secuencia;
	}
	
	public String getARTMED(String artcod) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("SELECT ARTMED FROM TARTI WHERE ARTCOD=?");
		pstm.setString(1, artcod);
		ResultSet rs = pstm.executeQuery();
		String artmed="";
		while(rs.next()){
			artmed = rs.getString(1);
		}
		rs.close();
		pstm.close();
		return artmed;
	}
	
}
