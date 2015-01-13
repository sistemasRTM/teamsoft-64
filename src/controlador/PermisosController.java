package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.Menu;
import bean.Modulo;
import bean.Opcion;
import bean.Operacion;
import bean.Perfil;
import bean.SubOpcion;
import delegate.GestionPermisos;
import delegate.GestionSeguridad;
import recursos.MaestroBean;
import recursos.Sesion;
import servicio.AuditoriaService;
import servicio.MenuService;
import servicio.ModuloService;
import servicio.OpcionService;
import servicio.OperacionService;
import servicio.PerfilService;
import servicio.SubOpcionService;
import ventanas.FIMantenimientoMenu;
import ventanas.FIMantenimientoModulo;
import ventanas.FIMantenimientoOpcion;
import ventanas.FIMantenimientoOperacion;
import ventanas.FIMantenimientoPerfil;
import ventanas.FIMantenimientoSubOpcion;
import ventanas.FIMenuOpcion;
import ventanas.FIOpcionSubOpcion;
import ventanas.FIPerfilModulo;
import ventanas.FIPermisos;
import ventanas.FISubOpcionOperacion;
import ventanas.FMenuPrincipalAdmin;

public class PermisosController implements ActionListener, KeyListener {

	FIMantenimientoOperacion mOperacion = new FIMantenimientoOperacion();
	FISubOpcionOperacion mSubOpcionOperacion = new FISubOpcionOperacion();
	FIMantenimientoSubOpcion mSubOpcion = new FIMantenimientoSubOpcion();
	FIOpcionSubOpcion mOpcionSubOpcion = new FIOpcionSubOpcion();
	FIMantenimientoOpcion mOpcion = new FIMantenimientoOpcion();
	FIMenuOpcion mMenuOpcion = new FIMenuOpcion();
	FIMantenimientoMenu mMenu = new FIMantenimientoMenu();
	FIMantenimientoModulo mModulo = new FIMantenimientoModulo();
	FIPerfilModulo mPerfilModulo = new FIPerfilModulo();
	FIMantenimientoPerfil mPerfil = new FIMantenimientoPerfil();
	FMenuPrincipalAdmin mMenuPrincAdm;
	FIPermisos mPermisos = new FIPermisos();

	OperacionService operacionService = GestionPermisos.getOperacionService();
	SubOpcionService subOpcionService = GestionPermisos.getSubOpcionService();
	OpcionService opcionService = GestionPermisos.getOpcionService();
	MenuService menuService = GestionPermisos.getMenuService();
	ModuloService moduloService = GestionPermisos.getModuloService();
	PerfilService perfilService = GestionPermisos.getPerfilService();

	List<Opcion> opciones = new ArrayList<Opcion>();
	AuditoriaService auditoriaService = GestionSeguridad.getAuditoriaService();

	public void setVista(FIMantenimientoOperacion mOperacion) {
		this.mOperacion = mOperacion;
	}

	public void setVista(FISubOpcionOperacion mSubOpcionOperacion) {
		this.mSubOpcionOperacion = mSubOpcionOperacion;
	}

	public void setVista(FIMantenimientoSubOpcion mSubOpcion) {
		this.mSubOpcion = mSubOpcion;
	}

	public void setVista(FIOpcionSubOpcion mOpcionSubOpcion) {
		this.mOpcionSubOpcion = mOpcionSubOpcion;
	}

	public void setVista(FIMantenimientoOpcion mOpcion) {
		this.mOpcion = mOpcion;
	}

	public void setVista(FIMenuOpcion mMenuOpcion) {
		this.mMenuOpcion = mMenuOpcion;
	}

	public void setVista(FIMantenimientoMenu mMenu) {
		this.mMenu = mMenu;
	}

	public void setVista(FIMantenimientoModulo mModulo) {
		this.mModulo = mModulo;
	}

	public void setVista(FIPerfilModulo mPerfilModulo) {
		this.mPerfilModulo = mPerfilModulo;
	}

	public void setVista(FIMantenimientoPerfil mPerfil) {
		this.mPerfil = mPerfil;
	}
	
	public void setVista(FMenuPrincipalAdmin mMenuPrincAdm) {
		this.mMenuPrincAdm = mMenuPrincAdm;
	}
	
	public void setVista(FIPermisos mPermisos) {
		this.mPermisos = mPermisos;
	}

	//
	@Override
	public void keyPressed(KeyEvent arg0) {
		System.out.println("entro");
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		System.out.println("entro");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("entro tecla");
		if (e.getKeyCode() == KeyEvent.VK_F1) {
			System.out.println("f1");
		} else if (e.getKeyCode() == KeyEvent.VK_F2) {
			System.out.println("f2");
		}

	}

	//
	@Override
	public void actionPerformed(ActionEvent e) {
		// operacion
		if (e.getSource() == mOperacion.getBtnNuevo()) {
			nuevaOperacion();
		} else if (e.getSource() == mOperacion.getBtnModificar()) {
			modificarOperacion();
		} else if (e.getSource() == mOperacion.getBtnEliminar()) {
			eliminarOperacion();
		} else if (e.getSource() == mOperacion.getBtnBuscar()
				|| e.getSource() == mOperacion.getTxtBuscar()) {
			buscarOperacion();
		} else if (e.getSource() == mOperacion.getBtnGuardar()
				|| e.getSource() == mOperacion.getTxtDescripcion()) {
			guardarOperacion();
		} else if (e.getSource() == mOperacion.getBtnCancelar()) {
			cancelarOperacion();
		} else if (e.getSource() == mOperacion.getBtnSalir()) {
			mOperacion.dispose();
		}
		// Sub Opcion - Operacion
		else if (e.getSource() == mSubOpcionOperacion.getBtnGuardar()) {
			guardarSubOpcionOperacion();
		} else if (e.getSource() == mSubOpcionOperacion.getBtnCancelar()) {
			cancelarSubOpcionOperacion();
		} else if (e.getSource() == mSubOpcionOperacion.getBtnSalir()) {
			mSubOpcionOperacion.dispose();
		}
		// Sub Opcion
		else if (e.getSource() == mSubOpcion.getBtnNuevo()) {
			nuevaSubOpcion();
		} else if (e.getSource() == mSubOpcion.getBtnModificar()) {
			modificarSubOpcion();
		} else if (e.getSource() == mSubOpcion.getBtnEliminar()) {
			eliminarSubOpcion();
		} else if (e.getSource() == mSubOpcion.getBtnBuscar()
				|| e.getSource() == mSubOpcion.getTxtBuscar()) {
			buscarSubOpcion();
		} else if (e.getSource() == mSubOpcion.getBtnGuardar()
				|| e.getSource() == mSubOpcion.getTxtDescripcion()) {
			guardarSubOpcion();
		} else if (e.getSource() == mSubOpcion.getBtnCancelar()) {
			cancelarSubOpcion();
		} else if (e.getSource() == mSubOpcion.getBtnSalir()) {
			mSubOpcion.dispose();
		}
		// Opcion - Sub Opcion
		else if (e.getSource() == mOpcionSubOpcion.getBtnGuardar()) {
			guardarOpcionSubOpcion();
		} else if (e.getSource() == mOpcionSubOpcion.getBtnCancelar()) {
			cancelarOpcionSubOpcion();
		} else if (e.getSource() == mOpcionSubOpcion.getBtnSalir()) {
			mOpcionSubOpcion.dispose();
		}

		// opcion
		else if (e.getSource() == mOpcion.getBtnNuevo()) {
			nuevaOpcion();
		} else if (e.getSource() == mOpcion.getBtnModificar()) {
			modificarOpcion();
		} else if (e.getSource() == mOpcion.getBtnEliminar()) {
			eliminarOpcion();
		} else if (e.getSource() == mOpcion.getBtnBuscar()
				|| e.getSource() == mOpcion.getTxtBuscar()) {
			buscarOpcion();
		} else if (e.getSource() == mOpcion.getBtnGuardar()
				|| e.getSource() == mOpcion.getTxtDescripcion()) {
			guardarOpcion();
		} else if (e.getSource() == mOpcion.getBtnCancelar()) {
			cancelarOpcion();
		} else if (e.getSource() == mOpcion.getBtnSalir()) {
			mOpcion.dispose();
		}
		// Menu - Opcion
		else if (e.getSource() == mMenuOpcion.getBtnGuardar()) {
			guardarMenuOpcion();
		} else if (e.getSource() == mMenuOpcion.getBtnCancelar()) {
			cancelarMenuOpcion();
		} else if (e.getSource() == mMenuOpcion.getBtnSalir()) {
			mMenuOpcion.dispose();
		}
		// menu
		else if (e.getSource() == mMenu.getBtnNuevo()) {
			nuevaMenu();
		} else if (e.getSource() == mMenu.getBtnModificar()) {
			modificarMenu();
		} else if (e.getSource() == mMenu.getBtnEliminar()) {
			eliminarMenu();
		} else if (e.getSource() == mMenu.getBtnBuscar()
				|| e.getSource() == mMenu.getTxtBuscar()) {
			buscarMenu();
		} else if (e.getSource() == mMenu.getBtnGuardar()
				|| e.getSource() == mMenu.getTxtDescripcion()) {
			guardarMenu();
		} else if (e.getSource() == mMenu.getBtnCancelar()) {
			cancelarMenu();
		} else if (e.getSource() == mMenu.getBtnSalir()) {
			mMenu.dispose();
		}
		// modulo
		else if (e.getSource() == mModulo.getBtnNuevo()) {
			nuevoModulo();
		} else if (e.getSource() == mModulo.getBtnModificar()) {
			modificarModulo();
		} else if (e.getSource() == mModulo.getBtnEliminar()) {
			eliminarModulo();
		} else if (e.getSource() == mModulo.getBtnBuscar()
				|| e.getSource() == mModulo.getTxtBuscar()) {
			buscarModulo();
		} else if (e.getSource() == mModulo.getBtnGuardar()
				|| e.getSource() == mModulo.getTxtDescripcion()) {
			guardarModulo();
		} else if (e.getSource() == mModulo.getBtnCancelar()) {
			cancelarModulo();
		} else if (e.getSource() == mModulo.getBtnSalir()) {
			mModulo.dispose();
		}else if (e.getSource() == mModulo.getBtnImprimir()) {
		
		}
		// Perfil - Modulo
		else if (e.getSource() == mPerfilModulo.getBtnGuardar()) {
			guardarPerfilModulo();
		} else if (e.getSource() == mPerfilModulo.getBtnCancelar()) {
			cancelarPerfilModulo();
		} else if (e.getSource() == mPerfilModulo.getBtnSalir()) {
			mPerfilModulo.dispose();
		}
		// perfil
		else if (e.getSource() == mPerfil.getBtnNuevo()) {
			nuevaPerfil();
		} else if (e.getSource() == mPerfil.getBtnModificar()) {
			modificarPerfil();
		} else if (e.getSource() == mPerfil.getBtnEliminar()) {
			eliminarPerfil();
		} else if (e.getSource() == mPerfil.getBtnBuscar()
				|| e.getSource() == mPerfil.getTxtBuscar()) {
			buscarPerfil();
		} else if (e.getSource() == mPerfil.getBtnGuardar()
				|| e.getSource() == mPerfil.getTxtDescripcion()) {
			guardarPerfil();
		} else if (e.getSource() == mPerfil.getBtnCancelar()) {
			cancelarPerfil();
		} else if (e.getSource() == mPerfil.getBtnSalir()) {
			mPerfil.dispose();
		} else {
		}
	}

	// operacion ------------------------------------------------------------
	private void nuevaOperacion() {
		mOperacion.desactivaControles();
		mOperacion.limpiar();
		mOperacion.setOperacion(0);
	}

	private void modificarOperacion() {
		if (!mOperacion.getDescripcion().equals("")) {
			mOperacion.desactivaControles();
			mOperacion.setOperacion(1);
		} else {
			Sesion.mensajeError(mOperacion,
					"Seleccione un registro de la tabla.");
		}
	}

	private void eliminarOperacion() {
		if (!mOperacion.getDescripcion().equals("")) {
			try {
				if (Sesion.mensajeConfirmacion(mOperacion,
						"¿Esta seguro que desea eliminar el registro?") == 0) {
					int resultado = operacionService.eliminar(Integer
							.parseInt(mOperacion.getCodigo()));
					if (resultado > 0) {
						mOperacion.limpiar();
						int fila = mOperacion.getTable().getSelectedRow();
						mOperacion.getModeloTable().removeRow(fila);
						Sesion.mensajeInformacion(mOperacion,
								"Eliminación exitosa");
					} else
						System.out.println(resultado);
				}
			} catch (SQLException e1) {
				Sesion.mensajeError(
						mOperacion,
						"Error inesperado de BD, " + e1.getMessage() + " "
								+ e1.getErrorCode() + " " + e1.getCause());
			}

		} else {
			Sesion.mensajeError(mOperacion,
					"Seleccione un registro de la tabla.");
		}
	}

	private void buscarOperacion() {
		Operacion operacion = new Operacion();
		operacion.setDescripcion(mOperacion.getBuscar());
		try {
			List<Operacion> operaciones = operacionService.buscar(operacion);
			mOperacion.cargarTabla(operaciones);
		} catch (SQLException e) {
			Sesion.mensajeError(
					mOperacion,
					"Error inesperado de BD, " + e.getMessage() + " "
							+ e.getErrorCode() + " " + e.getCause());
		}
	}

	private void guardarOperacion() {
		if (!mOperacion.getDescripcion().equals("")) {
			switch (mOperacion.getOperacion()) {
			case 0:
				try {
					Operacion operacion = new Operacion();
					operacion.setDescripcion(mOperacion.getDescripcion());
					int codigo = operacionService.insertar(operacion);
					Sesion.mensajeInformacion(mOperacion,
							"se ha registrado exitosamente con el codigo: "
									+ codigo);
				} catch (SQLException e) {
					Sesion.mensajeError(mOperacion,
							"Error inesperado de BD, " + e.getMessage() + " "
									+ e.getErrorCode() + " " + e.getCause());
				}
				break;
			default:
				try {
					Operacion operacion = new Operacion();
					operacion
							.setCodigo(Integer.parseInt(mOperacion.getCodigo()));
					operacion.setDescripcion(mOperacion.getDescripcion());
					int resultado = operacionService.modificar(operacion);
					if (resultado > 0) {
						mOperacion.cargarTabla(operacion);
						Sesion.mensajeInformacion(mOperacion,
								"Modificación exitosa");
					} else
						System.out.println(resultado);
				} catch (SQLException e) {
					Sesion.mensajeError(mOperacion,
							"Error inesperado de BD, " + e.getMessage() + " "
									+ e.getErrorCode() + " " + e.getCause());
				}
			}
			mOperacion.activaControles();
			mOperacion.limpiar();
		} else
			Sesion.mensajeError(mOperacion, "Ingrese descripción");

	}

	private void cancelarOperacion() {
		mOperacion.activaControles();
		mOperacion.limpiar();
	}

	// Sub Opcion - Operacion
	private void guardarSubOpcionOperacion() {
		MaestroBean mb = (MaestroBean) mSubOpcionOperacion.getCboSubOpcion()
				.getSelectedItem();
		SubOpcion subOpcion = new SubOpcion();
		subOpcion.setCodigo(Integer.parseInt(mb.getCodigo()));
		subOpcion.setDescripcion(mb.getDescripcion());

		List<Operacion> operaciones = new ArrayList<Operacion>();
		Operacion operacion;
		for (int i = 0; i < mSubOpcionOperacion.getTable().getRowCount(); i++) {
			operacion = new Operacion();
			operacion.setCodigo(Integer.parseInt(mSubOpcionOperacion.getTable()
					.getValueAt(i, 0).toString()));
			operaciones.add(operacion);
		}
		try {
			int resultado = subOpcionService.guardarDetalleSubOpcionOperacion(
					subOpcion, operaciones);
			if (resultado > 0) {
				Sesion.mensajeInformacion(
						mSubOpcionOperacion,
						"Se han agregado exitosamente " + resultado
								+ " operacion(es) a la Sub Opción:"
								+ subOpcion.getDescripcion());
			} else
				Sesion.mensajeInformacion(mSubOpcionOperacion,
						"Se han quitado todas las operaciones de la Sub Opción: "
								+ subOpcion.getDescripcion());
		} catch (SQLException e) {
			Sesion.mensajeError(
					mSubOpcionOperacion,
					"Error inesperado de BD, " + e.getMessage() + " "
							+ e.getErrorCode() + " " + e.getCause());
		}
	}

	private void cancelarSubOpcionOperacion() {
		if (Sesion
				.mensajeConfirmacion(mSubOpcionOperacion,
						"No se guardara ningun cambio ralizado,¿Seguro que desea cancelar?") == 0)
			mSubOpcionOperacion.limpiarTabla();
	}

	// Sub Opcion ------------------------------------------------------------
	private void nuevaSubOpcion() {
		mSubOpcion.desactivaControles();
		mSubOpcion.limpiar();
		mSubOpcion.setOperacion(0);
	}

	private void modificarSubOpcion() {
		if (!mSubOpcion.getDescripcion().equals("")) {
			mSubOpcion.desactivaControles();
			mSubOpcion.setOperacion(1);
		} else {
			Sesion.mensajeError(mSubOpcion,
					"Seleccione un registro de la tabla.");
		}
	}

	private void eliminarSubOpcion() {
		if (!mSubOpcion.getDescripcion().equals("")) {
			try {
				if (Sesion.mensajeConfirmacion(mSubOpcion,
						"¿Esta seguro que desea eliminar el registro?") == 0) {
					int resultado = subOpcionService.eliminar(Integer
							.parseInt(mSubOpcion.getCodigo()));
					if (resultado > 0) {
						mSubOpcion.limpiar();
						int fila = mSubOpcion.getTable().getSelectedRow();
						mSubOpcion.getModeloTable().removeRow(fila);
						Sesion.mensajeInformacion(mSubOpcion,
								"Eliminación exitosa");
					}
				}
			} catch (SQLException e1) {
				Sesion.mensajeError(
						mSubOpcion,
						"Error inesperado de BD, " + e1.getMessage() + " "
								+ e1.getErrorCode() + " " + e1.getCause());
			}

		} else {
			Sesion.mensajeError(mSubOpcion,
					"Seleccione un registro de la tabla.");
		}
	}

	private void buscarSubOpcion() {
		SubOpcion subOpcion = new SubOpcion();
		subOpcion.setDescripcion(mSubOpcion.getBuscar());
		try {
			List<SubOpcion> subOpciones = subOpcionService.buscar(subOpcion);
			mSubOpcion.cargarTabla(subOpciones);
		} catch (SQLException e) {
			Sesion.mensajeError(
					mSubOpcion,
					"Error inesperado de BD, " + e.getMessage() + " "
							+ e.getErrorCode() + " " + e.getCause());
		}
	}

	private void guardarSubOpcion() {
		if (!mSubOpcion.getDescripcion().equals("")) {
			switch (mSubOpcion.getOperacion()) {
			case 0:
				try {
					SubOpcion subOpcion = new SubOpcion();
					subOpcion.setDescripcion(mSubOpcion.getDescripcion());
					int codigo = subOpcionService.insertar(subOpcion);
					Sesion.mensajeInformacion(mSubOpcion,
							"se ha registrado exitosamente con el codigo: "
									+ codigo);
				} catch (SQLException e) {
					Sesion.mensajeError(mSubOpcion,
							"Error inesperado de BD, " + e.getMessage() + " "
									+ e.getErrorCode() + " " + e.getCause());
				}
				break;
			default:
				try {
					SubOpcion subOpcion = new SubOpcion();
					subOpcion
							.setCodigo(Integer.parseInt(mSubOpcion.getCodigo()));
					subOpcion.setDescripcion(mSubOpcion.getDescripcion());
					int resultado = subOpcionService.modificar(subOpcion);
					if (resultado > 0) {
						mSubOpcion.cargarTabla(subOpcion);
						Sesion.mensajeInformacion(mSubOpcion,
								"Modificación exitosa");
					} else
						System.out.println(resultado);
				} catch (SQLException e) {
					Sesion.mensajeError(mSubOpcion,
							"Error inesperado de BD, " + e.getMessage() + " "
									+ e.getErrorCode() + " " + e.getCause());
				}
			}
			mSubOpcion.activaControles();
			mSubOpcion.limpiar();
		} else
			Sesion.mensajeError(mSubOpcion, "Ingrese descripción");
	}

	private void cancelarSubOpcion() {
		mSubOpcion.activaControles();
		mSubOpcion.limpiar();
	}

	// Opcion - Sub Opcion ---------------------------------------------------
	private void guardarOpcionSubOpcion() {

		MaestroBean mb = (MaestroBean) mOpcionSubOpcion.getCboOpcion()
				.getSelectedItem();
		Opcion opcion = new Opcion();
		opcion.setCodigo(Integer.parseInt(mb.getCodigo()));
		opcion.setDescripcion(mb.getDescripcion());

		List<SubOpcion> subOpciones = new ArrayList<SubOpcion>();
		SubOpcion subOpcion;
		for (int i = 0; i < mOpcionSubOpcion.getTable().getRowCount(); i++) {
			subOpcion = new SubOpcion();
			subOpcion.setCodigo(Integer.parseInt(mOpcionSubOpcion.getTable()
					.getValueAt(i, 0).toString()));
			subOpciones.add(subOpcion);
		}
		try {
			int resultado = opcionService.guardarDetalleOpcionSubOpcion(opcion,
					subOpciones);
			if (resultado > 0) {
				Sesion.mensajeInformacion(
						mOpcionSubOpcion,
						"Se han agregado exitosamente " + resultado
								+ " sub opcion(es) a la Opción:"
								+ opcion.getDescripcion());
			} else
				Sesion.mensajeInformacion(mOpcionSubOpcion,
						"Se han quitado todas las sub opciones de la Opción: "
								+ opcion.getDescripcion());
		} catch (SQLException e) {
			Sesion.mensajeError(
					mOpcionSubOpcion,
					"Error inesperado de BD, " + e.getMessage() + " "
							+ e.getErrorCode() + " " + e.getCause());
		}

	}

	private void cancelarOpcionSubOpcion() {
		if (Sesion
				.mensajeConfirmacion(mOpcionSubOpcion,
						"No se guardara ningun cambio ralizado,¿Seguro que desea cancelar?") == 0)
			mOpcionSubOpcion.limpiarTabla();

	}

	// Opcion ---------------------------------------------------------------
	private void nuevaOpcion() {
		mOpcion.desactivaControles();
		mOpcion.limpiar();
		mOpcion.setOperacion(0);
		
		

	}

	private void modificarOpcion() {
		if (!mOpcion.getDescripcion().equals("")) {
			mOpcion.desactivaControles();
			mOpcion.setOperacion(1);
		} else {
			Sesion.mensajeError(mOpcion, "Seleccione un registro de la tabla.");
		}
	}

	private void eliminarOpcion() {
		if (!mOpcion.getDescripcion().equals("")) {
			try {
				if (Sesion.mensajeConfirmacion(mOpcion,
						"¿Esta seguro que desea eliminar el registro?") == 0) {
					int resultado = opcionService.eliminar(Integer
							.parseInt(mOpcion.getCodigo()));
					if (resultado > 0) {
						mOpcion.limpiar();
						int fila = mOpcion.getTable().getSelectedRow();
						mOpcion.getModeloTable().removeRow(fila);
						Sesion.mensajeInformacion(mOpcion,
								"Eliminación exitosa");
					}
				}
			} catch (SQLException e1) {
				Sesion.mensajeError(
						mOpcion,
						"Error inesperado de BD, " + e1.getMessage() + " "
								+ e1.getErrorCode() + " " + e1.getCause());
			}

		} else {
			Sesion.mensajeError(mOpcion, "Seleccione un registro de la tabla.");
		}
	}

	private void buscarOpcion() {
		Opcion opcion = new Opcion();
		opcion.setDescripcion(mOpcion.getBuscar());
		try {
			List<Opcion> subOpciones = opcionService.buscar(opcion);
			mOpcion.cargarTabla(subOpciones);
		} catch (SQLException e) {
			Sesion.mensajeError(
					mOpcion,
					"Error inesperado de BD, " + e.getMessage() + " "
							+ e.getErrorCode() + " " + e.getCause());
		}
	}

	private void guardarOpcion() {
		if (!mOpcion.getDescripcion().equals("")) {
			switch (mOpcion.getOperacion()) {
			case 0:
				try {
					Opcion opcion = new Opcion();
					opcion.setDescripcion(mOpcion.getDescripcion());
					int codigo = opcionService.insertar(opcion);
					Sesion.mensajeInformacion(mOpcion,
							"se ha registrado exitosamente con el codigo: "
									+ codigo);
				} catch (SQLException e) {
					Sesion.mensajeError(mOpcion,
							"Error inesperado de BD, " + e.getMessage() + " "
									+ e.getErrorCode() + " " + e.getCause());
				}
				break;
			default:
				try {
					Opcion opcion = new Opcion();
					opcion.setCodigo(Integer.parseInt(mOpcion.getCodigo()));
					opcion.setDescripcion(mOpcion.getDescripcion());
					int resultado = opcionService.modificar(opcion);
					if (resultado > 0) {
						mOpcion.cargarTabla(opcion);
						Sesion.mensajeInformacion(mOpcion,
								"Modificación exitosa");
					}
				} catch (SQLException e) {
					Sesion.mensajeError(mOpcion,
							"Error inesperado de BD, " + e.getMessage() + " "
									+ e.getErrorCode() + " " + e.getCause());
				}
			}
			mOpcion.activaControles();
			mOpcion.limpiar();
		} else
			Sesion.mensajeError(mOpcion, "Ingrese descripción");

	}

	private void cancelarOpcion() {
		mOpcion.activaControles();
		mOpcion.limpiar();
	}

	// menu - opcion
	private void guardarMenuOpcion() {
		MaestroBean mb = (MaestroBean) mMenuOpcion.getCboMenu()
				.getSelectedItem();
		Menu menu = new Menu();
		menu.setCodigo(Integer.parseInt(mb.getCodigo()));
		menu.setDescripcion(mb.getDescripcion());

		List<Opcion> opciones = new ArrayList<Opcion>();
		Opcion opcion;
		for (int i = 0; i < mMenuOpcion.getTable().getRowCount(); i++) {
			opcion = new Opcion();
			opcion.setCodigo(Integer.parseInt(mMenuOpcion.getTable()
					.getValueAt(i, 0).toString()));
			opciones.add(opcion);
		}
		try {
			int resultado = menuService
					.guardarDetalleMenuOpcion(menu, opciones);
			if (resultado > 0) {
				Sesion.mensajeInformacion(
						mMenuOpcion,
						"Se han agregado exitosamente " + resultado
								+ " opcion(es) al Menú:"
								+ menu.getDescripcion());
			} else
				Sesion.mensajeInformacion(
						mMenuOpcion,
						"Se han quitado todas las opciones del Menú: "
								+ menu.getDescripcion());
		} catch (SQLException e) {
			Sesion.mensajeError(
					mMenuOpcion,
					"Error inesperado de BD, " + e.getMessage() + " "
							+ e.getErrorCode() + " " + e.getCause());
		}
	}

	private void cancelarMenuOpcion() {
		if (Sesion
				.mensajeConfirmacion(mMenuOpcion,
						"No se guardara ningun cambio ralizado,¿Seguro que desea cancelar?") == 0)
			mMenuOpcion.limpiarTabla();

	}

	// menu
	private void nuevaMenu() {
		mMenu.desactivaControles();
		mMenu.limpiar();
		mMenu.setOperacion(0);
	}

	private void modificarMenu() {
		if (!mMenu.getDescripcion().equals("")) {
			mMenu.desactivaControles();
			mMenu.setOperacion(1);
		} else {
			Sesion.mensajeError(mMenu, "Seleccione un registro de la tabla.");
		}
	}

	private void eliminarMenu() {
		if (!mMenu.getDescripcion().equals("")) {
			try {
				if (Sesion.mensajeConfirmacion(mMenu,
						"¿Esta seguro que desea eliminar el registro?") == 0) {
					int resultado = menuService.eliminar(Integer.parseInt(mMenu
							.getCodigo()));
					if (resultado > 0) {
						mMenu.limpiar();
						int fila = mMenu.getTable().getSelectedRow();
						mMenu.getModeloTable().removeRow(fila);
						Sesion.mensajeInformacion(mMenu, "Eliminación exitosa");
					}
				}
			} catch (SQLException e1) {
				Sesion.mensajeError(
						mMenu,
						"Error inesperado de BD, " + e1.getMessage() + " "
								+ e1.getErrorCode() + " " + e1.getCause());
			}

		} else {
			Sesion.mensajeError(mMenu, "Seleccione un registro de la tabla.");
		}
	}

	private void buscarMenu() {
		Menu menu = new Menu();
		menu.setDescripcion(mMenu.getBuscar());
		try {
			List<Menu> menus = menuService.buscar(menu);
			mMenu.cargarTabla(menus);
		} catch (SQLException e) {
			Sesion.mensajeError(
					mMenu,
					"Error inesperado de BD, " + e.getMessage() + " "
							+ e.getErrorCode() + " " + e.getCause());
		}
	}

	private void guardarMenu() {
		if (!mMenu.getDescripcion().equals("")) {
			switch (mMenu.getOperacion()) {
			case 0:
				try {
					Menu menu = new Menu();
					menu.setDescripcion(mMenu.getDescripcion());
					int codigo = menuService.insertar(menu);
					Sesion.mensajeInformacion(mMenu,
							"se ha registrado exitosamente con el codigo: "
									+ codigo);
				} catch (SQLException e) {
					Sesion.mensajeError(mMenu,
							"Error inesperado de BD, " + e.getMessage() + " "
									+ e.getErrorCode() + " " + e.getCause());
				}
				break;
			default:
				try {
					Menu menu = new Menu();
					menu.setCodigo(Integer.parseInt(mMenu.getCodigo()));
					menu.setDescripcion(mMenu.getDescripcion());
					int resultado = menuService.modificar(menu);
					if (resultado > 0) {
						mMenu.cargarTabla(menu);
						Sesion.mensajeInformacion(mMenu, "Modificación exitosa");
					}
				} catch (SQLException e) {
					Sesion.mensajeError(mMenu,
							"Error inesperado de BD, " + e.getMessage() + " "
									+ e.getErrorCode() + " " + e.getCause());
				}
			}
			mMenu.activaControles();
			mMenu.limpiar();
		} else
			Sesion.mensajeError(mMenu, "Ingrese descripción");

	}

	private void cancelarMenu() {
		mMenu.activaControles();
		mMenu.limpiar();
	}

	// Modulo
	private void nuevoModulo() {
		mModulo.desactivaControles();
		mModulo.limpiar();
		mModulo.setOperacion(0);
	}

	private void modificarModulo() {
		if (!mModulo.getDescripcion().equals("")) {
			mModulo.desactivaControles();
			mModulo.setOperacion(1);
		} else {
			Sesion.mensajeError(mModulo, "Seleccione un registro de la tabla.");
		}
	}

	private void eliminarModulo() {
		if (!mModulo.getDescripcion().equals("")) {
			try {
				if (Sesion.mensajeConfirmacion(mModulo,
						"¿Esta seguro que desea eliminar el registro?") == 0) {
					int resultado = moduloService.eliminar(Integer
							.parseInt(mModulo.getCodigo()));
					if (resultado > 0) {
						mModulo.limpiar();
						int fila = mModulo.getTable().getSelectedRow();
						mModulo.getModeloTable().removeRow(fila);
						Sesion.mensajeInformacion(mModulo,
								"Eliminación exitosa");
					}
				}
			} catch (SQLException e1) {
				Sesion.mensajeError(
						mModulo,
						"Error inesperado de BD, " + e1.getMessage() + " "
								+ e1.getErrorCode() + " " + e1.getCause());
			}

		} else {
			Sesion.mensajeError(mModulo, "Seleccione un registro de la tabla.");
		}
	}

	private void buscarModulo() {
		Modulo modulo = new Modulo();
		modulo.setDescripcion(mModulo.getBuscar());
		try {
			List<Modulo> modulos = moduloService.buscar(modulo);
			mModulo.cargarTabla(modulos);
		} catch (SQLException e) {
			Sesion.mensajeError(
					mModulo,
					"Error inesperado de BD, " + e.getMessage() + " "
							+ e.getErrorCode() + " " + e.getCause());
		}
	}

	private void guardarModulo() {
		if (!mModulo.getDescripcion().equals("")) {
			switch (mModulo.getOperacion()) {
			case 0:
				try {
					Modulo modulo = new Modulo();
					modulo.setDescripcion(mModulo.getDescripcion());
					Menu menu = mModulo.getMenu();
					modulo.setMenu(menu);
					int codigo = moduloService.insertar(modulo);
					Sesion.mensajeInformacion(mModulo,
							"se ha registrado exitosamente con el codigo: "
									+ codigo);
				} catch (SQLException e) {
					Sesion.mensajeError(mModulo,
							"Error inesperado de BD, " + e.getMessage() + " "
									+ e.getErrorCode() + " " + e.getCause());
				}
				break;
			default:
				try {
					Modulo modulo = new Modulo();
					modulo.setCodigo(Integer.parseInt(mModulo.getCodigo()));
					modulo.setDescripcion(mModulo.getDescripcion());
					Menu menu = mModulo.getMenu();
					modulo.setMenu(menu);
					int resultado = moduloService.modificar(modulo);
					if (resultado > 0) {
						mModulo.cargarTabla(modulo);
						Sesion.mensajeInformacion(mModulo,
								"Modificación exitosa");
					}
				} catch (SQLException e) {
					Sesion.mensajeError(mModulo,
							"Error inesperado de BD, " + e.getMessage() + " "
									+ e.getErrorCode() + " " + e.getCause());
				}
			}
			mModulo.activaControles();
			mModulo.limpiar();
		} else
			Sesion.mensajeError(mModulo, "Ingrese descripción");
	}

	private void cancelarModulo() {
		mModulo.activaControles();
		mModulo.limpiar();
	}

	// perfil - modulo
	private void guardarPerfilModulo() {
		MaestroBean mb = (MaestroBean) mPerfilModulo.getCboPerfil()
				.getSelectedItem();
		Perfil perfil = new Perfil();
		perfil.setCodigo(Integer.parseInt(mb.getCodigo()));
		perfil.setDescripcion(mb.getDescripcion());

		List<Modulo> modulos = new ArrayList<Modulo>();
		Modulo modulo;
		for (int i = 0; i < mPerfilModulo.getTable().getRowCount(); i++) {
			modulo = new Modulo();
			modulo.setCodigo(Integer.parseInt(mPerfilModulo.getTable()
					.getValueAt(i, 0).toString()));
			modulos.add(modulo);
		}
		try {
			int resultado = perfilService.guardarDetallePerfilModulo(perfil,
					modulos);
			if (resultado > 0) {
				Sesion.mensajeInformacion(
						mPerfilModulo,
						"Se han agregado exitosamente " + resultado
								+ " modulo(s) al Perfil: "
								+ perfil.getDescripcion());
			} else
				Sesion.mensajeInformacion(mPerfilModulo,
						"Se han quitado todos los modulos del Perfil: "
								+ perfil.getDescripcion());
		} catch (SQLException e) {
			Sesion.mensajeError(
					mPerfilModulo,
					"Error inesperado de BD, " + e.getMessage() + " "
							+ e.getErrorCode() + " " + e.getCause());
		}
	}

	private void cancelarPerfilModulo() {
		if (Sesion
				.mensajeConfirmacion(mPerfilModulo,
						"No se guardara ningun cambio ralizado,¿Seguro que desea cancelar?") == 0)
			mPerfilModulo.limpiarTabla();
	}

	// perfil
	private void nuevaPerfil() {
		mPerfil.desactivaControles();
		mPerfil.limpiar();
		mPerfil.setOperacion(0);
	}

	private void modificarPerfil() {
		if (!mPerfil.getDescripcion().equals("")) {
			mPerfil.desactivaControles();
			mPerfil.setOperacion(1);
		} else {
			Sesion.mensajeError(mPerfil, "Seleccione un registro de la tabla.");
		}
	}

	private void eliminarPerfil() {
		if (!mPerfil.getDescripcion().equals("")) {
			try {
				if (Sesion.mensajeConfirmacion(mPerfil,
						"¿Esta seguro que desea eliminar el registro?") == 0) {
					int resultado = perfilService.eliminar(Integer
							.parseInt(mPerfil.getCodigo()));
					if (resultado > 0) {
						mPerfil.limpiar();
						int fila = mPerfil.getTable().getSelectedRow();
						mPerfil.getModeloTable().removeRow(fila);
						Sesion.mensajeInformacion(mPerfil,
								"Eliminación exitosa");
					}
				}
			} catch (SQLException e1) {
				Sesion.mensajeError(
						mPerfil,
						"Error inesperado de BD, " + e1.getMessage() + " "
								+ e1.getErrorCode() + " " + e1.getCause());
			}

		} else {
			Sesion.mensajeError(mPerfil, "Seleccione un registro de la tabla.");
		}
	}

	private void buscarPerfil() {
		Perfil perfil = new Perfil();
		perfil.setDescripcion(mPerfil.getBuscar());
		try {
			List<Perfil> perfiles = perfilService.buscar(perfil);
			mPerfil.cargarTabla(perfiles);
		} catch (SQLException e) {
			Sesion.mensajeError(
					mPerfil,
					"Error inesperado de BD, " + e.getMessage() + " "
							+ e.getErrorCode() + " " + e.getCause());
		}
	}

	private void guardarPerfil() {
		if (!mPerfil.getDescripcion().equals("")) {
			switch (mPerfil.getOperacion()) {
			case 0:
				try {
					Perfil perfil = new Perfil();
					perfil.setDescripcion(mPerfil.getDescripcion());
					int codigo = perfilService.insertar(perfil);
					Sesion.mensajeInformacion(mPerfil,
							"se ha registrado exitosamente con el codigo: "
									+ codigo);
				} catch (SQLException e) {
					Sesion.mensajeError(mPerfil,
							"Error inesperado de BD, " + e.getMessage() + " "
									+ e.getErrorCode() + " " + e.getCause());
				}
				break;
			default:
				try {
					Perfil perfil = new Perfil();
					perfil.setCodigo(Integer.parseInt(mPerfil.getCodigo()));
					perfil.setDescripcion(mPerfil.getDescripcion());
					int resultado = perfilService.modificar(perfil);
					if (resultado > 0) {
						mPerfil.cargarTabla(perfil);
						Sesion.mensajeInformacion(mPerfil,
								"Modificación exitosa");
					}
				} catch (SQLException e) {
					Sesion.mensajeError(mPerfil,
							"Error inesperado de BD, " + e.getMessage() + " "
									+ e.getErrorCode() + " " + e.getCause());
				}
			}
			mPerfil.activaControles();
			mPerfil.limpiar();
		} else
			Sesion.mensajeError(mPerfil, "Ingrese descripción");

	}

	private void cancelarPerfil() {
		mPerfil.activaControles();
		mPerfil.limpiar();
	}
}
