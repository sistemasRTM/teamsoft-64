package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.ListaPrecio;
import bean.TALIAS;

public interface ListaPrecioService {

	public List<ListaPrecio>  listar() throws SQLException;
	public void registrarPrecios(ListaPrecio listaprecio) throws SQLException;
	public void modificarPrecios(ListaPrecio listaprecio) throws SQLException;
	public ListaPrecio verificaPrecio(ListaPrecio listaprecio) throws SQLException;
	public int getSecuencia() throws SQLException;
	public String getARTMED(String artcod) throws SQLException;
	public TALIAS listarTALIAS() throws SQLException;
}
