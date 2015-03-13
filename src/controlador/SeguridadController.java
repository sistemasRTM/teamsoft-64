package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bean.Auditoria;
import bean.Empleado;
import bean.Modulo;
import bean.Opcion;
import bean.SubOpcion;
import delegate.GestionSeguridad;
import recursos.MaestroBean;
import recursos.Sesion;
import servicio.AuditoriaService;
import servicio.EmpresaService;
import servicio.LoginService;
import ventanas.FEmpresa;
import ventanas.FIAuditoria;
import ventanas.FLogin;
import ventanas.FMenuPrincipalAdmin;
import ventanas.FMenuPrincipalContabilidad;
import ventanas.FMenuPrincipalCuentasXCobrar;
import ventanas.FMenuPrincipalFacturacion;
import ventanas.FMenuPrincipalInventario;
import ventanas.FMenuPrincipalRRHH;
import ventanas.FModulosGeneral;

public class SeguridadController implements ActionListener {

	private FLogin login;
	private FEmpresa empresa;
	private FModulosGeneral modulos;
	private FIAuditoria auditoria = new FIAuditoria();

	private LoginService LoginServicio = GestionSeguridad.getLoginService();
	private EmpresaService EmpresaServicio = GestionSeguridad
			.getEmpresaService();
	private AuditoriaService auditoriaService = GestionSeguridad
			.getAuditoriaService();

	public SeguridadController(FLogin login) {
		this.login = login;
	}

	public SeguridadController(FEmpresa empresa) {
		this.empresa = empresa;
	}

	public SeguridadController(FModulosGeneral modulos) {
		this.modulos = modulos;
	}

	public SeguridadController(FIAuditoria auditoria) {
		this.auditoria = auditoria;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == auditoria.getBtnConsultar()) {
			String error = "";
			Auditoria objAuditoria = new Auditoria();
			MaestroBean beanCIA = auditoria.getCIA();
			MaestroBean beanModulo = auditoria.getModulo();
			MaestroBean beanMenu = auditoria.getMenu();
			MaestroBean beanOpcion = auditoria.getOpcion();
			MaestroBean beanOperacion = auditoria.getOperacion();
			MaestroBean beanUsuario = auditoria.getUsuario();
			Date desde = auditoria.getDesde();
			Date hasta = auditoria.getHasta();

			if (beanCIA == null) {
				error += "Seleccione CIA\n";
			}
			if (beanModulo == null) {
				error += "Seleccione Modulo\n";
			}
			if (beanMenu == null) {
				error += "Seleccione Opción\n";
			}
			if (beanOpcion == null) {
				error += "Seleccione Sub Opción\n";
			}
			if (beanOperacion == null) {
				error += "Seleccione Operación\n";
			}
			if (beanUsuario == null) {
				error += "Seleccione Usuario\n";
			}

			if (!error.equals("")) {
				Sesion.mensajeError(auditoria, error);
			} else {
				try {
					objAuditoria.setUsu(beanUsuario.getDescripcion());
					objAuditoria.setCIA(beanCIA.getDescripcion());
					objAuditoria.setModulo(beanModulo.getDescripcion());
					objAuditoria.setMenu(beanMenu.getDescripcion());
					objAuditoria.setOpcion(beanOpcion.getDescripcion());
					objAuditoria.setTipo_ope(beanOperacion.getDescripcion());
					boolean logIn = auditoria.getLogIn();
					boolean logOut = auditoria.getLogOut();
					List<Auditoria> auditorias = auditoriaService.buscar(
							objAuditoria, desde, hasta, logIn, logOut);
					if (auditorias.size() > 0) {
						auditoria.cargarTabla(auditorias);
					} else {
						Sesion.mensajeInformacion(auditoria,
								"No se encontrarón resultados para los criterios seleccionados");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

		} else if (e.getSource() == auditoria.getBtnSalir()) {
			FIAuditoria.close();
			auditoria.salir();
		} else if (e.getSource() == login.getTxtUsuario()) {
			login.getTxtContraseña().requestFocus();
		} else if (e.getSource() == login.getIngresar()
				|| e.getSource() == login.getTxtContraseña()) {

			if (!(login.getContraseña().equals("") || login.getUsuario()
					.equals(""))) {
				Empleado objEmpleado = new Empleado();
				objEmpleado.setStrUsuario(login.getUsuario());
				objEmpleado.setStrClave(login.getContraseña());
				String estado = LoginServicio.verifica(objEmpleado);
				if (estado.equalsIgnoreCase("exito")) {
					Sesion.usuario = objEmpleado.getStrUsuario();
					Sesion.contraseña = objEmpleado.getStrClave();

					empresa = new FEmpresa();
					try {
						// registrando auditoria - proceso login
						Auditoria auditoria = new Auditoria();
						auditoria.setUsu(Sesion.usuario);
						auditoria.setFecha(new Timestamp(Calendar.getInstance()
								.getTimeInMillis()));
						auditoria.setTipo_ope("Log In");
						auditoriaService.insertarAuditoria(auditoria);
						login.dispose();
						empresa.setVisible(true);
						empresa.setLocationRelativeTo(null);
						empresa.setControlador(this);

					} catch (SQLException e1) {
						Sesion.mensajeError(empresa, e1.getMessage());
					}

				} else if (estado
						.equalsIgnoreCase("The application server rejected the connection. (User ID is not known.)")) {
					Sesion.mensajeError(login, "Usuario incorrecto");
				} else {
					Sesion.mensajeError(login, "Contraseña incorrecta");
				}

			} else {
				Sesion.mensajeError(login, "ingrese usuario y/o contraseña");
			}
		}

		else if (e.getSource() == empresa.getBtnAceptar()) {
			MaestroBean cia = (MaestroBean) empresa.getCboCIA()
					.getSelectedItem();
			if (EmpresaServicio.verifica(cia.getDescripcion().trim()) != null) {
				Sesion.cia = cia.getDescripcion().trim();
				Sesion.nombreCIA = cia.getCodigo();
			}
			// registrando auditoria - proceso login, eleccion de CIA
			/*
			Auditoria auditoria = new Auditoria();
			auditoria.setUsu(Sesion.usuario);
			auditoria.setFecha(new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));
			auditoria.setTipo_ope("Log In");
			auditoria.setRef1(Sesion.cia);
			try {
				auditoriaService.insertarAuditoria(auditoria);
				// cargando los permisos
				//Sesion.modulos = LoginServicio.listarModulos();
			} catch (SQLException e1) {

			}
			//
			 */
			
			
			try {
				Sesion.modulos = LoginServicio.listarModulos();
			} catch (SQLException e1) {

			}
			
			modulos = new FModulosGeneral();

			if (Sesion.modulos.size() > 0) {
				for (Modulo modulo : Sesion.modulos) {
					switch (modulo.getCodigo()) {
					case 1:
						modulos.getBtnFacturacion().setEnabled(true);
						break;
					case 2:
						modulos.getBtnRRHH().setEnabled(true);
						break;
					case 3:
						modulos.getBtnAdmin().setEnabled(true);
						break;
					case 4:
						modulos.getBtnContabilidad().setEnabled(true);
						break;
					case 5:
						modulos.getBtnCuentasXCobrar().setEnabled(true);
						break;
					case 6:
						modulos.getBtnInventario().setEnabled(true);
						break;	
					}
				}
			} else {
				Sesion.mensajeInformacion(empresa, "Usuario " + Sesion.usuario
						+ " no tiene permisos para la compañia seleccionada");
			}

			modulos.setVisible(true);
			modulos.setLocationRelativeTo(null);
			modulos.setControlador(this);
			empresa.dispose();
		} else if (e.getSource() == empresa.getBtnSalir()) {
			// registrando auditoria - proceso log out, eleccion de CIA
			/*
			Auditoria auditoria = new Auditoria();
			auditoria.setUsu(Sesion.usuario);
			auditoria.setFecha(new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));
			auditoria.setTipo_ope("Log Out");
			try {
				auditoriaService.insertarAuditoria(auditoria);
			} catch (SQLException e1) {
			}
			*/
			System.exit(0);
		} else if (e.getSource() == modulos.getBtnAdmin()) {
			if (FMenuPrincipalAdmin.getInstance() == null) {
				FMenuPrincipalAdmin gui = FMenuPrincipalAdmin.createInstance();
				
				try {
					// codigo de modulo de facturacion
					Sesion.opciones = LoginServicio.listarOpciones(3);
					Sesion.subOpciones = LoginServicio.listarSubOpciones(3);
				} catch (SQLException e1) {
				}
				for (Opcion opcion : Sesion.opciones) {
					switch (opcion.getCodigo()) {
					case 4:
						gui.getOpcSeguridad().setVisible(true);
						break;
					}
				}

				for (SubOpcion subOpcion : Sesion.subOpciones) {
					switch (subOpcion.getCodigo()) {
					case 3:
						gui.getSubOpcConsultasBD().setVisible(true);
						break;
					case 8:
						gui.getSubOpcPerfil().setVisible(true);
						break;
					case 9:
						gui.getSubOpcPerfilModulo().setVisible(true);
						break;
					case 10:
						gui.getSubOpcModulo().setVisible(true);
						break;
					case 11:
						gui.getSubOpcMenu().setVisible(true);
						break;
					case 12:
						gui.getSubOpcMenuOpcion().setVisible(true);
						break;
					case 13:
						gui.getSubOpcOpcion().setVisible(true);
						break;
					case 14:
						gui.getSubOpcOpcionSubOpc().setVisible(true);
						break;
					case 15:
						gui.getSubOpcSubOpcion().setVisible(true);
						break;
					case 16:
						gui.getSubOpcSubOpcionOperacion().setVisible(true);
						break;
					case 17:
						gui.getSubOpcOperacion().setVisible(true);
						break;
					case 18:
						gui.getSubOpcPermiso().setVisible(true);
						break;
					case 19:
						gui.getSubOpcAuditoria().setVisible(true);
						break;
					}
				}
				gui.setVisible(true);
				gui.setLocationRelativeTo(null);
				gui.requestFocusInWindow();
			} else {
				FMenuPrincipalAdmin.getInstance().toFront();
			}
		} else if (e.getSource() == modulos.getBtnContabilidad()) {
			if (FMenuPrincipalContabilidad.getInstance() == null) {
				FMenuPrincipalContabilidad gui = FMenuPrincipalContabilidad
						.createInstance();
				try {
					// codigo de modulo de facturacion
					Sesion.opciones = LoginServicio.listarOpciones(4);
					Sesion.subOpciones = LoginServicio.listarSubOpciones(4);
				} catch (SQLException e1) {
				}
				for (Opcion opcion : Sesion.opciones) {
					switch (opcion.getCodigo()) {
					case 1:
						gui.getOpcReporte().setVisible(true);
						break;
					case 8:
						gui.getOpcMantenimiento().setVisible(true);
						break;
					case 12:
						gui.getOpcProceso().setVisible(true);
						break;
					}
				}

				for (SubOpcion subOpcion : Sesion.subOpciones) {
					switch (subOpcion.getCodigo()) {
					case 2:
						gui.getSubOpcRegistroCompras().setVisible(true);
						break;
					case 20:
						gui.getSubOpcRegistroVentas().setVisible(true);
						break;
					case 25:
						gui.getSubOpcCertificadoPercepcion().setVisible(true);
						break;
					case 26:
						gui.getSubOpcRegistroOperacionDiaria().setVisible(true);
						break;
					case 27:
						gui.getSubOpcRegistroOperacion().setVisible(true);
						break;
					case 28:
						gui.getSubOpcRegistroPresentacionComercial().setVisible(true);
						break;
					case 29:
						gui.getSubOpcEstablecimientoComercial().setVisible(true);
						break;
					case 30:
						gui.getSubOpcTipoTransanccion().setVisible(true);
						break;
					case 31:
						gui.getSubOpcAsociacionPresentacionComercial().setVisible(true);
						break;
					case 32:
						gui.getSubOpcAsociacionDocumentoTransaccion().setVisible(true);
						break;
					case 33:
						gui.getSubOpcAsociacionDocumentoCliente().setVisible(true);
						break;
					case 38:
						gui.getSubOpcReImprimirDocuemntos().setVisible(true);
						break;
					case 40:
						gui.getSubOpcRegistrarMovimientos().setVisible(true);
						break;
					case 41:
						gui.getSubOpcReporteLibroKardex().setVisible(true);
						break;
					case 48:
						gui.getSubOpcReporteLibroDiario().setVisible(true);
						break;
					case 49:
						gui.getSubOpcReporteLibroMayor().setVisible(true);
						break;
					case 50:
						gui.getSubOpcReporteClientesSunat().setVisible(true);
						break;
					}
				}

				gui.setVisible(true);
				gui.setLocationRelativeTo(null);
				gui.requestFocusInWindow();
			} else {
				FMenuPrincipalContabilidad.getInstance().toFront();
			}

		} else if (e.getSource() == modulos.getBtnFacturacion()) {
			if (FMenuPrincipalFacturacion.getInstance() == null) {
				FMenuPrincipalFacturacion gui = FMenuPrincipalFacturacion
						.createInstance();
				
				try {
					// codigo de modulo de facturacion
					Sesion.opciones = LoginServicio.listarOpciones(1);
					Sesion.subOpciones = LoginServicio.listarSubOpciones(1);
				} catch (SQLException e1) {
				}

				// *********************************************************************************
				for (Opcion opcion : Sesion.opciones) {
					switch (opcion.getCodigo()) {
					case 5:
						gui.getOpcMantenimiento().setVisible(true);
						break;
					case 7:
						gui.getOpcFacturacion().setVisible(true);
						break;
					}

				}

				for (SubOpcion subOpcion : Sesion.subOpciones) {
					switch (subOpcion.getCodigo()) {
					case 1:
						gui.getSubOpcCliente().setVisible(true);
						break;
					case 7:
						gui.getSubOpcListaPrecio().setVisible(true);
						break;
					case 23:
						gui.getSubOpcGenerarCertificado().setVisible(true);
						break;
					case 24:
						gui.getSubOpcVerificarPercepcion().setVisible(true);
						break;
					case 34:
						gui.getSubOpcImprimirPedidos().setVisible(true);
						break;
					case 44:
						gui.getSubOpcNotaCredito().setVisible(true);
						break;
						
						
					}
				}
				
				gui.setVisible(true);
				gui.setLocationRelativeTo(null);
				gui.requestFocusInWindow();
				// *********************************************************************************
			} else {
				FMenuPrincipalFacturacion.getInstance().toFront();
			}
		} else if (e.getSource() == modulos.getBtnRRHH()) {
			if (FMenuPrincipalRRHH.getInstance() == null) {
				FMenuPrincipalRRHH guiRRHH = FMenuPrincipalRRHH
						.createInstance();
				
				try {
					// codigo de modulo de facturacion
					Sesion.opciones = LoginServicio.listarOpciones(2);
					Sesion.subOpciones = LoginServicio.listarSubOpciones(2);
				} catch (SQLException e1) {
				}
				for (Opcion opcion : Sesion.opciones) {
					switch (opcion.getCodigo()) {
					case 2:
						guiRRHH.getOpcMantenimiento().setVisible(true);
						break;

					case 3:
						guiRRHH.getOpcPlanilla().setVisible(true);
						break;
					}
				}

				for (SubOpcion subOpcion : Sesion.subOpciones) {
					switch (subOpcion.getCodigo()) {
					case 4:
						guiRRHH.getSubOpcComision().setVisible(true);
						break;
					case 5:
						guiRRHH.getSubOpcCalculoComision().setVisible(true);
						break;
					case 6:
						guiRRHH.getSubOpcAbrirPeriodo().setVisible(true);
						break;
					case 21:
						guiRRHH.getSubOpcCriteriosCalculo().setVisible(true);
						break;

					case 22:
						guiRRHH.getSubOpcCriterioExcepcion().setVisible(true);
						break;

					}
				}
				
				guiRRHH.setVisible(true);
				guiRRHH.setLocationRelativeTo(null);
				guiRRHH.requestFocusInWindow();
			} else {
				FMenuPrincipalRRHH.getInstance().toFront();
			}

		} else if (e.getSource() == modulos.getBtnCuentasXCobrar()) {
			if (FMenuPrincipalCuentasXCobrar.getInstance() == null) {
				FMenuPrincipalCuentasXCobrar gui = FMenuPrincipalCuentasXCobrar
						.createInstance();
				
				try {
					Sesion.opciones = LoginServicio.listarOpciones(5);
					Sesion.subOpciones = LoginServicio.listarSubOpciones(5);
				} catch (SQLException e1) {
				}
				
				for (Opcion opcion : Sesion.opciones) {
					switch (opcion.getCodigo()) {
					case 10:
						gui.getOpcProcesos().setVisible(true);
						break;
					}
				}
				
				for (SubOpcion subOpcion : Sesion.subOpciones) {
					switch (subOpcion.getCodigo()) {
					case 36:
						gui.getSubOpcLetras().setVisible(true);
						break;
					case 37:
						gui.getSubOpcEstadoCuentas().setVisible(true);
						break;
					case 39:
						gui.getSubOpcHistorialComprasClientes().setVisible(true);
						break;	
					}
				}
				
				
				gui.setVisible(true);
				gui.setLocationRelativeTo(null);
				gui.requestFocusInWindow();

			} else {
				FMenuPrincipalCuentasXCobrar.getInstance().toFront();
			}
		}else if (e.getSource() == modulos.getBtnInventario()) {
			if (FMenuPrincipalInventario.getInstance() == null) {
				FMenuPrincipalInventario gui = FMenuPrincipalInventario
						.createInstance();
				
				try {
					Sesion.opciones = LoginServicio.listarOpciones(6);
					Sesion.subOpciones = LoginServicio.listarSubOpciones(6);
				} catch (SQLException e1) {
				}
				
				for (Opcion opcion : Sesion.opciones) {
					switch (opcion.getCodigo()) {
					case 9:
						gui.getOpcConsultas().setVisible(true);
						break;
					}
				}
				
				for (SubOpcion subOpcion : Sesion.subOpciones) {
					switch (subOpcion.getCodigo()) {
					case 35:
						gui.getSubOpcMovimientoInventario().setVisible(true);
						break;
					}
				}
				
				gui.setVisible(true);
				gui.setLocationRelativeTo(null);
				gui.requestFocusInWindow();
			} else {
				FMenuPrincipalInventario.getInstance().toFront();
			}
		} else if (e.getSource() == modulos.getBtnSalir()) {
			/*
			Auditoria auditoria = new Auditoria();
			auditoria.setUsu(Sesion.usuario);
			auditoria.setFecha(new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));
			auditoria.setTipo_ope("Log Out");
			auditoria.setRef1(Sesion.cia);
			try {
				auditoriaService.insertarAuditoria(auditoria);
				// Conexion.desconectar();
			} catch (SQLException e1) {
				System.exit(0);
			}
			*/
			System.exit(0);
		}

	}

}
