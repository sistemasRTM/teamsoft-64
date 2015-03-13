package servicio;

import java.util.ArrayList;

import persistencia.NotaCreditoDAO;

public class NotaCreditoServiceImpl implements NotaCreditoService{

	NotaCreditoDAO ncDAO = new NotaCreditoDAO();
	
	@Override
	public ArrayList<String[]> listarnc(String ejer, String peri) {
		return ncDAO.listarnc(ejer, peri);
	}

}
