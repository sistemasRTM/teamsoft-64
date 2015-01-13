package delegate;

import servicio.VendedorService;
import servicio.VendedorServiceImpl;

public class GestionVendedores {
	
	public static VendedorService getVendedorService(){
		return new VendedorServiceImpl();
	}

}
