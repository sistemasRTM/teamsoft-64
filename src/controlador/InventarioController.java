package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import bean.TMOVD;
import recursos.Sesion;
import servicio.TMOVDService;
import ventanas.FIMovimientoInventario;
import delegate.GestionInventario;

public class InventarioController implements ActionListener{

	TMOVDService servicio = GestionInventario.getTMOVDService();
	FIMovimientoInventario mMovimientoInventario;
	
	public InventarioController(FIMovimientoInventario mMovimientoInventario){
		this.mMovimientoInventario = mMovimientoInventario;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mMovimientoInventario.getBtnProcesar() || e.getSource() == mMovimientoInventario.getTxtMotor() ){
			procesar();
		}else if (mMovimientoInventario.getBtnSalir() == e.getSource()) {
			FIMovimientoInventario.close();
			mMovimientoInventario.salir();
		}
	}


	private void procesar() {
		String motor = mMovimientoInventario.getMotor();
		if(!motor.equals("")){
		try {
			List<TMOVD> listado = servicio.listar(motor, mMovimientoInventario.getSituacion());
			if(listado.size()>0){
				mMovimientoInventario.cargaTabla(listado);
			}else{
				Sesion.mensajeInformacion(mMovimientoInventario, "No se encontrarón resultados para los criterios ingresados");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}else{
			Sesion.mensajeError(mMovimientoInventario, "Ingrese Nro. de Motor");
		}
	}

}
