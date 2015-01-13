package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import bean.Operacion;
import controlador.ComisionController;
import controlador.RRHHController;
import delegate.GestionComision;
import recursos.MaestroFrame;
import recursos.Sesion;
import delegate.GestionSeguridad;
import servicio.ComisionService;
import servicio.LoginService;

public class FMenuPrincipalRRHH extends MaestroFrame {

	private static final long serialVersionUID = 1L;
	private static FMenuPrincipalRRHH gui = null;
	private JMenu mnPlanillas;
	private JMenuItem mntmCalculoComisiones;
	private JMenuItem mntmAbrirPeriodo;
	private JMenu mnMantenimiento;
	private JMenuItem mntmComisiones;
	private JMenuItem mntmCriterioCalculo;
	private JMenuItem mntmCriterioExcepcion;
	private JButton btnSalir;
	private RRHHController controlador = new RRHHController();
	private ComisionService servicio = GestionComision.getComisionService();
	private LoginService LoginServicio = GestionSeguridad.getLoginService();

	public static FMenuPrincipalRRHH createInstance() {
		if (gui == null) {
			gui = new FMenuPrincipalRRHH();
		}
		return gui;

	}

	public static FMenuPrincipalRRHH getInstance() {
		return gui;
	}

	public FMenuPrincipalRRHH() {
		setTitle(getTitle() + "-" + Sesion.tfRRHH);

		mnMantenimiento = new JMenu("Mantenimientos");
		mnMantenimiento.setVisible(false);
		mnMenuBar.add(mnMantenimiento);

		mntmComisiones = new JMenuItem("Comisiones");
		mntmComisiones.setVisible(false);
		mntmComisiones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (FIMantenimientoComision.getInstance() == null) {
					FIMantenimientoComision gui = FIMantenimientoComision
							.createInstance();
					gui.initialize();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(2,
								2, 4);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 1:
								gui.getBtnNuevo().setVisible(true);
								break;
							case 2:
								gui.getBtnModificar().setVisible(true);
								break;
							case 4:
								gui.getBtnGuardar().setVisible(true);
								break;
							case 5:
								gui.getBtnCancelar().setVisible(true);
								break;
							case 11:
								gui.getBtnSalir().setVisible(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}
					
					gui.setVisible(true);
					gui.requestFocus();

					ComisionController controlador = new ComisionController(gui);
					gui.setControlador(controlador);
					dpEscritorio.add(gui,Sesion.show);
				}else{
					FIMantenimientoComision.getInstance().toFront();
				}
			}
		});
		mnMantenimiento.add(mntmComisiones);

		mntmCriterioCalculo = new JMenuItem("Criterios de clasificación");
		mntmCriterioCalculo.setVisible(false);
		mntmCriterioCalculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (FIMantenimientoCriteriosComision.getInstance() == null) {
					FIMantenimientoCriteriosComision gui = FIMantenimientoCriteriosComision
							.createInstance();
					gui.initialize();

					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(2,
								2, 21);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 3:
								gui.getBtnEliminar().setVisible(true);
								break;
							case 4:
								gui.getBtnGuardar().setVisible(true);
								break;
							case 5:
								gui.getBtnCancelar().setVisible(true);
								break;
							case 11:
								gui.getBtnSalir().setVisible(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}

					gui.setVisible(true);
					gui.requestFocus();
					controlador.setVista(gui);
					gui.setControlador(controlador);
					dpEscritorio.add(gui,Sesion.show);
				}else{
					FIMantenimientoCriteriosComision.getInstance().toFront();
				}
			}
		});
		mnMantenimiento.add(mntmCriterioCalculo);

		mntmCriterioExcepcion = new JMenuItem("Excepción de criterios");
		mntmCriterioExcepcion.setVisible(false);
		mntmCriterioExcepcion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (FIMantenimientoExcepcionCriterio.getInstance() == null) {
					FIMantenimientoExcepcionCriterio gui = FIMantenimientoExcepcionCriterio
							.createInstance();
					gui.initialize();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(2,
								2, 22);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 4:
								gui.getBtnGuardar().setVisible(true);
								break;
							case 5:
								gui.getBtnCancelar().setVisible(true);
								break;
							case 11:
								gui.getBtnSalir().setVisible(true);
								break;
							case 15:
								gui.getBtnMantener().setEnabled(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}
					gui.setVisible(true);
					gui.requestFocus();
					controlador.setVista(gui);
					gui.setControlador(controlador);
					dpEscritorio.add(gui,Sesion.show);
				}else{
					FIMantenimientoExcepcionCriterio.getInstance().toFront();
				}
			}
		});
		mnMantenimiento.add(mntmCriterioExcepcion);

		mnPlanillas = new JMenu("Planillas");
		mnPlanillas.setVisible(false);
		mnMenuBar.add(mnPlanillas);

		mntmCalculoComisiones = new JMenuItem("Cálculo Comisiones");
		mntmCalculoComisiones.setVisible(false);
		mntmCalculoComisiones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (FICalculoComisiones.getInstance() == null) {
					FICalculoComisiones gui = FICalculoComisiones
							.createInstance(dpEscritorio);
					gui.initialize();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(2,
								3, 5);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 5:
								gui.getBtnCancelar().setVisible(true);
								break;
							case 6:
								gui.getBtnExcel().setVisible(true);
								break;
							case 11:
								gui.getBtnSalir().setVisible(true);
								break;
							case 12:
								gui.getBtnCalcular().setEnabled(true);
								break;
							case 13:
								gui.getBtnCerrarPeriodo().setEnabled(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}
					gui.setVisible(true);
					gui.requestFocus();
					controlador.setVista(gui);
					gui.setControlador(controlador);
					dpEscritorio.add(gui,Sesion.show);
				}else{
					FICalculoComisiones.getInstance().toFront();
				}
			}
		});
		mnPlanillas.add(mntmCalculoComisiones);

		mntmAbrirPeriodo = new JMenuItem("Abrir Periodo");
		mntmAbrirPeriodo.setVisible(false);
		mntmAbrirPeriodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (FIAbrirPeriodo.getInstance() == null) {
					FIAbrirPeriodo gui = FIAbrirPeriodo.createInstance();
					gui.initialize();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(2,
								3, 6);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 11:
								gui.getBtnSalir().setVisible(true);
								break;
							case 14:
								gui.getBtnAbrirPeriodo().setEnabled(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}
					
					gui.setVisible(true);
					gui.requestFocus();
					controlador.setVista(gui);
					gui.setControlador(controlador);
					try {
						gui.cargarTabla(servicio.listarPeriodos());
					} catch (SQLException e1) {
					}
					dpEscritorio.add(gui,Sesion.show);
				}else{
					FIAbrirPeriodo.getInstance().toFront();
				}
			}
		});
		mnPlanillas.add(mntmAbrirPeriodo);

		btnSalir = new JButton("", Sesion.cargarIcono(Sesion.imgSalir));
		btnSalir.setToolTipText("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIMantenimientoComision.close();
				FIMantenimientoExcepcionCriterio.close();
				FIMantenimientoCriteriosComision.close();
				FICalculoComisiones.close();
				FIDetalleCalculos.close();
				FIAbrirPeriodo.close();
				gui = null;
				dispose();
			}
		});
		mnMenuBar.add(btnSalir);
	}

	public JMenu getOpcPlanilla() {
		return mnPlanillas;
	}

	public JMenuItem getSubOpcCalculoComision() {
		return mntmCalculoComisiones;
	}

	public JMenuItem getSubOpcAbrirPeriodo() {
		return mntmAbrirPeriodo;
	}

	public JMenu getOpcMantenimiento() {
		return mnMantenimiento;
	}

	public JMenuItem getSubOpcComision() {
		return mntmComisiones;
	}

	public JMenuItem getSubOpcCriteriosCalculo() {
		return mntmCriterioCalculo;
	}

	public JMenuItem getSubOpcCriterioExcepcion() {
		return mntmCriterioExcepcion;
	}

}
