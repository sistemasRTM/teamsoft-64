package delegate;

import servicio.ClienteService;
import servicio.ClienteServiceImpl;

public class GestionCliente {

	public static ClienteService getClienteService(){
		return new ClienteServiceImpl();
	}
	
	
}
