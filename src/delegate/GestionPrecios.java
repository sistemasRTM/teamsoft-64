package delegate;

import servicio.ListaPrecioService;
import servicio.ListaPrecioServiceImpl;

public class GestionPrecios {

	public static ListaPrecioService getPrecioService(){
		return new ListaPrecioServiceImpl();
	}
	
}
