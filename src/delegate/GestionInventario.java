package delegate;

import servicio.TMOVDService;
import servicio.TMOVDServiceImpl;

public class GestionInventario {

	public static TMOVDService getTMOVDService(){
		return new TMOVDServiceImpl();
	}
	
}
