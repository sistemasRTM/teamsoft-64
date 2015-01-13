package servicio;

import java.sql.SQLException;
import java.util.List;

import persistencia.ListaPrecioDAO;
import persistencia.TALIASDAO;

import bean.ListaPrecio;
import bean.TALIAS;

public class ListaPrecioServiceImpl implements ListaPrecioService{

	ListaPrecioDAO listaPrecioDAO = new ListaPrecioDAO();
	TALIASDAO taliasDAO = new TALIASDAO();
	@Override
	public List<ListaPrecio> listar() throws SQLException {
	
		return listaPrecioDAO.listar();
	}

	@Override
	public void registrarPrecios(ListaPrecio listaprecio) throws SQLException {

		listaPrecioDAO.registrarPrecios(listaprecio);
	}

	@Override
	public void modificarPrecios(ListaPrecio listaprecio) throws SQLException {
	
		listaPrecioDAO.modificarPrecios(listaprecio);
	}

	@Override
	public ListaPrecio verificaPrecio(ListaPrecio listaprecio) throws SQLException {

		return listaPrecioDAO.verificaPrecio(listaprecio);
	}

	@Override
	public int getSecuencia() throws SQLException {
		return listaPrecioDAO.getSecuencia();
	}

	@Override
	public String getARTMED(String artcod) throws SQLException {
		return listaPrecioDAO.getARTMED(artcod);
	}

	@Override
	public TALIAS listarTALIAS() throws SQLException {
		return taliasDAO.listarTALIAS();
	}
}
