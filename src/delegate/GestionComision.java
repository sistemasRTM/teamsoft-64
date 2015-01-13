package delegate;

import servicio.ComisionService;
import servicio.ComisionServiceImpl;
import servicio.MantenimientoCriteriosComisionService;
import servicio.MantenimientoCriteriosComisionServiceImpl;
import servicio.MantenimientoExcepcionCriterioComisionService;
import servicio.MantenimientoExcepcionCriterioComisionServiceImpl;

public class GestionComision {

	public static ComisionService getComisionService(){
		return new ComisionServiceImpl();
	}
	
	public static MantenimientoCriteriosComisionService getMantenimientoCriteriosComisionService(){
		return new MantenimientoCriteriosComisionServiceImpl();
	}
	
	public static MantenimientoExcepcionCriterioComisionService getMantenimientoExcepcionCriterioComisionService(){
		return new MantenimientoExcepcionCriterioComisionServiceImpl();
	}
}
