package delegate;

import servicio.ComprasService;
import servicio.ComprasServiceImpl;

public class GestionCompras {

	public static ComprasService getComprasService(){
		return new ComprasServiceImpl();
	}
	
}
