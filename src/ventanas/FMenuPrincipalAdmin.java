package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;
import bean.Operacion;
import recursos.MaestroFrame;
import recursos.Sesion;
import servicio.LoginService;
import util.Conexion;
import controlador.PermisosController;
import controlador.SeguridadController;
import delegate.GestionSeguridad;

public class FMenuPrincipalAdmin extends MaestroFrame {

	private static final long serialVersionUID = 1L;
	private static FMenuPrincipalAdmin gui = null;
	private JMenu mnSeguridad;
	private JMenuItem mntmModulos;
	private JMenuItem mntmMenu;
	private JMenuItem mntmOpciones;
	private JMenuItem mntmPerfil;
	private JMenuItem mntmSubopcionesoperaciones;
	private JMenuItem mntmOpcionessubopciones;
	private JMenuItem mntmMenusopciones;
	private JMenuItem mntmPerfilesmodulos;
	private JMenuItem mntmSubopciones;
	private JMenuItem mntmOperaciones;
	private JMenuItem mntmPermisos;
	private JMenuItem mntmAuditoria;
	private JMenuItem mntmConsultasBD;
	private JButton btnSalir;
	private JButton btnConectar;
	private PermisosController controlador = new PermisosController();
	private LoginService LoginServicio = GestionSeguridad.getLoginService();
	
	public static FMenuPrincipalAdmin createInstance() {
		if (gui == null) {
			gui = new FMenuPrincipalAdmin();
			
		}
		return gui;

	}

	public static FMenuPrincipalAdmin getInstance() {
		return gui;
	}
	
	public FMenuPrincipalAdmin() {
		controlador.setVista(this);
		
		setTitle(getTitle() + "-" + Sesion.tfADM);

		mnSeguridad = new JMenu("Seguridad");
		mnSeguridad.setVisible(false);
		mnMenuBar.add(mnSeguridad);

		mntmPerfil = new JMenuItem("Perfiles");
		mntmPerfil.setVisible(false);
		mntmPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIMantenimientoPerfil mPerfil = new FIMantenimientoPerfil();
				try {
					Sesion.operaciones = LoginServicio.listarOperaciones(3, 4,
							8);
					for (Operacion operacion : Sesion.operaciones) {
						switch (operacion.getCodigo()) {
						case 1:
							mPerfil.getBtnNuevo().setVisible(true);
							break;
						case 2:
							mPerfil.getBtnModificar().setVisible(true);
							break;
						case 3:
							mPerfil.getBtnEliminar().setVisible(true);
							break;
						case 4:
							mPerfil.getBtnGuardar().setVisible(true);
							break;
						case 5:
							mPerfil.getBtnCancelar().setVisible(true);
							break;
						case 11:
							mPerfil.getBtnSalir().setVisible(true);
							break;
						}
					}
				} catch (SQLException e1) {
				}
				controlador.setVista(mPerfil);
				mPerfil.setControlador(controlador);
				dpEscritorio.add(mPerfil,Sesion.show);
			}
		});
		mnSeguridad.add(mntmPerfil);

		mntmModulos = new JMenuItem("Modulos");
		mntmModulos.setVisible(false);
		mntmModulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIMantenimientoModulo mModulos = new FIMantenimientoModulo();
				try {
					Sesion.operaciones = LoginServicio.listarOperaciones(3, 4,
							10);
					for (Operacion operacion : Sesion.operaciones) {
						switch (operacion.getCodigo()) {
						case 1:
							mModulos.getBtnNuevo().setVisible(true);
							break;
						case 2:
							mModulos.getBtnModificar().setVisible(true);
							break;
						case 3:
							mModulos.getBtnEliminar().setVisible(true);
							break;
						case 4:
							mModulos.getBtnGuardar().setVisible(true);
							break;
						case 5:
							mModulos.getBtnCancelar().setVisible(true);
							break;
						case 11:
							mModulos.getBtnSalir().setVisible(true);
							break;
						}
					}
				} catch (SQLException e1) {
				}
				mModulos.setControlador(controlador);
				controlador.setVista(mModulos);
				dpEscritorio.add(mModulos,Sesion.show);
			}
		});
		
		mntmPerfilesmodulos = new JMenuItem("Perfiles-Modulos");
		mntmPerfilesmodulos.setVisible(false);
		mntmPerfilesmodulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIPerfilModulo mPerfilModulo = new FIPerfilModulo();
				try {
					Sesion.operaciones = LoginServicio.listarOperaciones(3, 4,
							9);
					for (Operacion operacion : Sesion.operaciones) {
						switch (operacion.getCodigo()) {
						case 4:
							mPerfilModulo.getBtnGuardar().setVisible(true);
							break;
						case 5:
							mPerfilModulo.getBtnCancelar().setVisible(true);
							break;
						case 11:
							mPerfilModulo.getBtnSalir().setVisible(true);
							break;
						}
					}
				} catch (SQLException e1) {
				}
				mPerfilModulo.setControlador(controlador);
				controlador.setVista(mPerfilModulo);
				dpEscritorio.add(mPerfilModulo,Sesion.show);
			}
		});
		mnSeguridad.add(mntmPerfilesmodulos);
		mnSeguridad.add(mntmModulos);

		mntmMenu = new JMenuItem("Menus");
		mntmMenu.setVisible(false);
		mntmMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIMantenimientoMenu mMenu = new FIMantenimientoMenu();
				try {
					Sesion.operaciones = LoginServicio.listarOperaciones(3, 4,
							11);
					for (Operacion operacion : Sesion.operaciones) {
						switch (operacion.getCodigo()) {
						case 1:
							mMenu.getBtnNuevo().setVisible(true);
							break;
						case 2:
							mMenu.getBtnModificar().setVisible(true);
			
							break;
						case 3:
							mMenu.getBtnEliminar().setVisible(true);
							break;
						case 4:
							mMenu.getBtnGuardar().setVisible(true);
							break;
						case 5:
							mMenu.getBtnCancelar().setVisible(true);
							break;
						case 11:
							mMenu.getBtnSalir().setVisible(true);
							break;
						}
					}
				} catch (SQLException e1) {
				}
				mMenu.setControlador(controlador);
				controlador.setVista(mMenu);
				dpEscritorio.add(mMenu,Sesion.show);
			}
		});
		mnSeguridad.add(mntmMenu);

		mntmOpciones = new JMenuItem("Opciones");
		mntmOpciones.setVisible(false);
		mntmOpciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIMantenimientoOpcion mOpciones = new FIMantenimientoOpcion();
				try {
					Sesion.operaciones = LoginServicio.listarOperaciones(3, 4,
							13);
					for (Operacion operacion : Sesion.operaciones) {
						switch (operacion.getCodigo()) {
						case 1:
							mOpciones.getBtnNuevo().setVisible(true);
							break;
						case 2:
							mOpciones.getBtnModificar().setVisible(true);
							break;
						case 3:
							mOpciones.getBtnEliminar().setVisible(true);
							break;
						case 4:
							mOpciones.getBtnGuardar().setVisible(true);
							break;
						case 5:
							mOpciones.getBtnCancelar().setVisible(true);
							break;
						case 11:
							mOpciones.getBtnSalir().setVisible(true);
							break;
						}
					}
				} catch (SQLException e1) {
				}
				mOpciones.setControlador(controlador);
				controlador.setVista(mOpciones);
				dpEscritorio.add(mOpciones,Sesion.show);
			}
		});
		
		mntmMenusopciones = new JMenuItem("Menus-Opciones");
		mntmMenusopciones.setVisible(false);
		mntmMenusopciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIMenuOpcion mMenuOpcion = new FIMenuOpcion();
				try {
					Sesion.operaciones = LoginServicio.listarOperaciones(3, 4,
							12);
					for (Operacion operacion : Sesion.operaciones) {
						switch (operacion.getCodigo()) {
						case 4:
							mMenuOpcion.getBtnGuardar().setVisible(true);
							break;
						case 5:
							mMenuOpcion.getBtnCancelar().setVisible(true);
							break;
						case 11:
							mMenuOpcion.getBtnSalir().setVisible(true);
							break;
						}
					}
				} catch (SQLException e1) {
				}
				mMenuOpcion.setControlador(controlador);
				controlador.setVista(mMenuOpcion);
				dpEscritorio.add(mMenuOpcion,Sesion.show);
			}
		});
		mnSeguridad.add(mntmMenusopciones);
		mnSeguridad.add(mntmOpciones);

		mntmPermisos = new JMenuItem("Permisos");
		mntmPermisos.setVisible(false);
		mntmPermisos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FIPermisos.getInstance() == null){
					FIPermisos permisos = FIPermisos.createInstance();
					permisos.initialize();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(3, 4,
								18);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 4:
								permisos.getBtnGuardar().setVisible(true);
								break;
							case 5:
								permisos.getBtnCancelar().setVisible(true);
								break;
							case 11:
								permisos.getBtnSalir().setVisible(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}
					PermisosController controlador = new PermisosController();
					controlador.setVista(permisos);
					permisos.setControlador(controlador);
					dpEscritorio.add(permisos,Sesion.show);
					}else{
						FIPermisos.getInstance().toFront();
					}
			}
		});

		mntmSubopciones = new JMenuItem("SubOpciones");
		mntmSubopciones.setVisible(false);
		mntmSubopciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIMantenimientoSubOpcion mSubOpcion = new FIMantenimientoSubOpcion();
				try {
					Sesion.operaciones = LoginServicio.listarOperaciones(3, 4,
							15);
					for (Operacion operacion : Sesion.operaciones) {
						switch (operacion.getCodigo()) {
						case 1:
							mSubOpcion.getBtnNuevo().setVisible(true);
							break;
						case 2:
							mSubOpcion.getBtnModificar().setVisible(true);
							break;
						case 3:
							mSubOpcion.getBtnEliminar().setVisible(true);
							break;
						case 4:
							mSubOpcion.getBtnGuardar().setVisible(true);
							break;
						case 5:
							mSubOpcion.getBtnCancelar().setVisible(true);
							break;
						case 11:
							mSubOpcion.getBtnSalir().setVisible(true);
							break;
						}
					}
				} catch (SQLException e1) {
				}
				mSubOpcion.setControlador(controlador);
				controlador.setVista(mSubOpcion);
				dpEscritorio.add(mSubOpcion,Sesion.show);
			}
		});
		
		mntmOpcionessubopciones = new JMenuItem("Opciones-SubOpciones");
		mntmOpcionessubopciones.setVisible(false);
		mntmOpcionessubopciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIOpcionSubOpcion mOpcionSubOpcion = new FIOpcionSubOpcion();
				try {
					Sesion.operaciones = LoginServicio.listarOperaciones(3, 4,
							14);
					for (Operacion operacion : Sesion.operaciones) {
						switch (operacion.getCodigo()) {
						case 4:
							mOpcionSubOpcion.getBtnGuardar().setVisible(true);
							break;
						case 5:
							mOpcionSubOpcion.getBtnCancelar().setVisible(true);
							break;
						case 11:
							mOpcionSubOpcion.getBtnSalir().setVisible(true);
							break;
						}
					}
				} catch (SQLException e1) {
				}
				mOpcionSubOpcion.setControlador(controlador);
				controlador.setVista(mOpcionSubOpcion);
				dpEscritorio.add(mOpcionSubOpcion,Sesion.show);
			}
		});
		mnSeguridad.add(mntmOpcionessubopciones);
		mnSeguridad.add(mntmSubopciones);

		mntmOperaciones = new JMenuItem("Operaciones");
		mntmOperaciones.setVisible(false);
		mntmOperaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIMantenimientoOperacion mOperacion = new FIMantenimientoOperacion();
				try {
					Sesion.operaciones = LoginServicio.listarOperaciones(3, 4,
							17);
					for (Operacion operacion : Sesion.operaciones) {
						switch (operacion.getCodigo()) {
						case 1:
							mOperacion.getBtnNuevo().setVisible(true);
							break;
						case 2:
							mOperacion.getBtnModificar().setVisible(true);
							break;
						case 3:
							mOperacion.getBtnEliminar().setVisible(true);
							break;
						case 4:
							mOperacion.getBtnGuardar().setVisible(true);
							break;
						case 5:
							mOperacion.getBtnCancelar().setVisible(true);
							break;
						case 11:
							mOperacion.getBtnSalir().setVisible(true);
							break;
						}
					}
				} catch (SQLException e1) {
				}
				mOperacion.setControlador(controlador);
				controlador.setVista(mOperacion);
				dpEscritorio.add(mOperacion,Sesion.show);
			}
		});
		
		mntmSubopcionesoperaciones = new JMenuItem("SubOpciones-Operaciones");
		mntmSubopcionesoperaciones.setVisible(false);
		mntmSubopcionesoperaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FISubOpcionOperacion mSubOpcionOperacion = new FISubOpcionOperacion();
				try {
					Sesion.operaciones = LoginServicio.listarOperaciones(3, 4,
							16);
					for (Operacion operacion : Sesion.operaciones) {
						switch (operacion.getCodigo()) {
						case 4:
							mSubOpcionOperacion.getBtnGuardar().setVisible(true);
							break;
						case 5:
							mSubOpcionOperacion.getBtnCancelar().setVisible(true);
							break;
						case 11:
							mSubOpcionOperacion.getBtnSalir().setVisible(true);
							break;
						}
					}
				} catch (SQLException e1) {
				}
				mSubOpcionOperacion.setControlador(controlador);
				controlador.setVista(mSubOpcionOperacion);
				dpEscritorio.add(mSubOpcionOperacion,Sesion.show);
			}
		});
		mnSeguridad.add(mntmSubopcionesoperaciones);
		mnSeguridad.add(mntmOperaciones);
		mnSeguridad.add(mntmPermisos);
		
		mntmAuditoria = new JMenuItem("Auditoria");
		mntmAuditoria.setVisible(true);
		mntmAuditoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FIAuditoria.getInstance() == null){
					FIAuditoria mAuditoria = FIAuditoria.createInstance();
					mAuditoria.initialize();
					SeguridadController controlador = new SeguridadController(mAuditoria);
					mAuditoria.setControlador(controlador);
					dpEscritorio.add(mAuditoria,Sesion.show);
				}
			}
		});
		mnSeguridad.add(mntmAuditoria);
		
		mntmConsultasBD = new JMenuItem("Consultas BD");
		mntmConsultasBD.setVisible(true);
		mntmConsultasBD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dpEscritorio.add(new FIConsultasBD(),Sesion.show);
			}
		});
		mnSeguridad.add(mntmConsultasBD);
		
		btnConectar = new JButton("",Sesion.cargarIcono(Sesion.imgConectar));
		btnConectar.setToolTipText("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(Conexion.obtenerConexion().isClosed()){
						Conexion.reconectar();
					}
				} catch (SQLException e1) {
					Sesion.mensajeError(null, "Excepción de Motor de BD. Mensaje del sistema: \n"+e1.getMessage()+" " + e1.getCause());
				}
			}
		});
		mnMenuBar.add(btnConectar);
		
		btnSalir = new JButton("",Sesion.cargarIcono(Sesion.imgSalir));
		btnSalir.setToolTipText("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIPermisos.close();
				FIAuditoria.close();
				FIPermisos.close();
				gui = null;
				dispose();
			}
		});
		mnMenuBar.add(btnSalir);
	}
	
	public JMenu getOpcSeguridad() {
		return mnSeguridad;
	}
	
	public JMenuItem getSubOpcConsultasBD() {
		return mntmConsultasBD;
	}
	
	public JMenuItem getSubOpcPerfil() {
		return mntmPerfil;
	}
	
	public JMenuItem getSubOpcPerfilModulo() {
		return mntmPerfilesmodulos;
	}
	
	public JMenuItem getSubOpcModulo() {
		return mntmModulos;
	}
	
	public JMenuItem getSubOpcMenu() {
		return mntmMenu;
	}
	
	public JMenuItem getSubOpcMenuOpcion() {
		return mntmMenusopciones;
	}
	
	public JMenuItem getSubOpcOpcion() {
		return mntmOpciones;
	}
	
	public JMenuItem getSubOpcOpcionSubOpc() {
		return mntmOpcionessubopciones;
	}
	
	public JMenuItem getSubOpcSubOpcion() {
		return mntmSubopciones;
	}
	
	public JMenuItem getSubOpcSubOpcionOperacion() {
		return mntmSubopcionesoperaciones;
	}
	
	public JMenuItem getSubOpcOperacion() {
		return mntmOperaciones;
	}
	
	public JMenuItem getSubOpcPermiso() {
		return mntmPermisos;
	}
	
	public JMenuItem getSubOpcAuditoria() {
		return mntmAuditoria;
	}
	
}
