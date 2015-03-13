package delegate;

import servicio.NotaCreditoService;
import servicio.NotaCreditoServiceImpl;

public class GestionFacturacion {

	public static NotaCreditoService getNotaCreditoService(){
		return new NotaCreditoServiceImpl();
	}
	
}
