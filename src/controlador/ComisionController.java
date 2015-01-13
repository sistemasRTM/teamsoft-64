package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import bean.Auditoria;
import bean.Comision;
import delegate.GestionComision;
import delegate.GestionSeguridad;
import recursos.Sesion;
import servicio.AuditoriaService;
import servicio.ComisionService;
import ventanas.FIMantenimientoComision;

public class ComisionController implements ActionListener {

	FIMantenimientoComision mantenimientoComision;
	ComisionService servicio = GestionComision.getComisionService();
	AuditoriaService auditoriaService = GestionSeguridad.getAuditoriaService();
	Auditoria auditoria;

	public ComisionController(FIMantenimientoComision mantenimientoComision) {
		this.mantenimientoComision = mantenimientoComision;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == mantenimientoComision.getBtnSalir()) {
			FIMantenimientoComision.close();
			mantenimientoComision.salir();
		}
		if (e.getSource() == mantenimientoComision.getBtnGuardar()) {
			String resultadoOperacion = "";
			int acumula = mantenimientoComision.getCboAcumula()
					.getSelectedIndex();
			if (acumula != 0) {
				boolean existe = false;

				String vAcumula = "F";
				if (acumula == 1) {
					vAcumula = "T";
				}

				Comision comision = new Comision();
				comision.setVendedor(mantenimientoComision.getVendedor());
				comision.setFecha_inicial(mantenimientoComision.getFechaini());
				comision.setFecha_final(mantenimientoComision.getFechafin());
				comision.setComision_mayor(mantenimientoComision
						.getComisionMayor());
				comision.setComision_menor(mantenimientoComision
						.getComisionMenor());
				comision.setComision_mayor_m(mantenimientoComision
						.getComisionMayorM());
				comision.setComision_menor_m(mantenimientoComision
						.getComisionMenorM());
				comision.setSituacion(mantenimientoComision.getChkSituacion());
				comision.setMaestro(mantenimientoComision.getChkMaestro());
				comision.setAcumula(vAcumula);

				String fechaIni = mantenimientoComision.getDtpFechaIni()
						.getEditor().getText();
				String fechaFin = mantenimientoComision.getDtpFechaFin()
						.getEditor().getText();

				int iAnos = Integer.parseInt(fechaIni.substring(6, 10));
				int iMes = Integer.parseInt(fechaIni.substring(3, 5));
				int iDias = Integer.parseInt(fechaIni.substring(0, 2));

				int fAnos = Integer.parseInt(fechaFin.substring(6, 10));
				int fMes = Integer.parseInt(fechaFin.substring(3, 5));
				int fDias = Integer.parseInt(fechaFin.substring(0, 2));

				int iTotalDias = Sesion.convertirFecha(iAnos, iMes, iDias);
				int fTotalDias = Sesion.convertirFecha(fAnos, fMes, fDias);

				if (fTotalDias > iTotalDias) {
					switch (mantenimientoComision.getOperacion()) {
					case 0:
						try {
							boolean existeMaestro = false;
							if (comision.getMaestro() == 1) {
								existeMaestro = servicio.verificaMaestro();
							}
							if (existeMaestro == false) {
								existe = servicio.validarInsert(comision);
								if (existe == false) {
									int r = servicio.registrar(comision);
									if (r > 0) {
										// auditoria
										auditoria = new Auditoria();
										auditoria.setUsu(Sesion.usuario);
										auditoria.setCIA(Sesion.cia);
										auditoria.setFecha(new Timestamp(
												Calendar.getInstance()
														.getTimeInMillis()));
										auditoria.setMenu("Mantenimientos");
										auditoria.setModulo("RR.HH");
										auditoria.setOpcion("Comisiones");
										auditoria.setTipo_ope("Insertar");
										auditoria.setCant_reg(1);
										auditoria.setRef1(resultadoOperacion);
										auditoria.setRef2(comision
												.getVendedor().getCodigo());
										auditoria.setRef3(comision
												.getVendedor().getNombre());
										auditoria.setRef4(Sesion
												.formateaFechaVista(comision
														.getFecha_inicial()));
										auditoria.setRef5(Sesion
												.formateaFechaVista(comision
														.getFecha_final()));
										auditoria.setRef6(comision
												.getComision_mayor() + "");
										auditoria.setRef7(comision
												.getComision_menor() + "");
										auditoria.setRef8(comision.getAcumula()
												+ "");
										auditoria.setRef9(comision
												.getSituacion() + "");
										auditoria.setRef10(comision
												.getMaestro() + "");
										auditoria.setRef11(comision
												.getComision_mayor_m() + "");
										auditoria.setRef12(comision
												.getComision_menor_m() + "");
										auditoriaService
												.insertarAuditoria(auditoria);
										resultadoOperacion = "Ingresado correctamente";
										Sesion.mensajeInformacion(
												mantenimientoComision,
												resultadoOperacion);
										mantenimientoComision.activaControles();
										mantenimientoComision.limpiar();

									}
								} else {
									resultadoOperacion = "El vendedor ya tiene una comisión para el rango de fechas elegidas.";
									Sesion.mensajeError(mantenimientoComision,
											resultadoOperacion);
								}
							} else {
								resultadoOperacion = "No se puede registrar la comisión, porqué ya existe un vendedor maestro.";
								Sesion.mensajeError(mantenimientoComision,
										resultadoOperacion);
							}
						} catch (SQLException e1) {
							resultadoOperacion = e1.getMessage();
							Sesion.mensajeError(mantenimientoComision,
									resultadoOperacion);
						} catch (Exception e1) {
							resultadoOperacion = e1.getMessage();
							Sesion.mensajeError(mantenimientoComision,
									resultadoOperacion);
						}
						break;
					default:
						try {
							Comision antigua = new Comision();
							antigua.setFecha_inicial(mantenimientoComision
									.getFechaIniAntigua());
							antigua.setFecha_final(mantenimientoComision
									.getFechaFinAntigua());
							existe = servicio.validarUpdate(comision, antigua);

							boolean existeMaestro = false;
							//es el maestro
							if (comision.getMaestro() == 1) {
								//si existe
								existeMaestro = servicio.verificaMaestro();
								if(existeMaestro==true){
									boolean isMaestro = servicio.verificaMaestro(comision);
									if(isMaestro==true){
										//permite actualizar, porque eres el maestro
										existeMaestro = false;
									}else{
										existeMaestro = true;
									}
								}
								
							}
							if (existeMaestro == false) {
								if (existe == false) {
									int r = servicio.modificarComision(
											comision, antigua);
									if (r > 0) {
										// auditoria
										auditoria = new Auditoria();
										auditoria.setUsu(Sesion.usuario);
										auditoria.setCIA(Sesion.cia);
										auditoria.setFecha(new Timestamp(
												Calendar.getInstance()
														.getTimeInMillis()));
										auditoria.setMenu("Mantenimientos");
										auditoria.setModulo("RR.HH");
										auditoria.setOpcion("Comisiones");
										auditoria.setTipo_ope("Modificar");
										auditoria.setCant_reg(1);
										auditoria.setRef1(resultadoOperacion);
										auditoria.setRef2(comision
												.getVendedor().getCodigo());
										auditoria.setRef3(comision
												.getVendedor().getNombre());
										auditoria.setRef4(Sesion
												.formateaFechaVista(comision
														.getFecha_inicial()));
										auditoria.setRef5(Sesion
												.formateaFechaVista(comision
														.getFecha_final()));
										auditoria.setRef6(comision
												.getComision_mayor() + "");
										auditoria.setRef7(comision
												.getComision_menor() + "");
										auditoria.setRef8(comision.getAcumula()
												+ "");
										auditoria.setRef9(comision
												.getSituacion() + "");
										auditoria.setRef10(comision
												.getMaestro() + "");
										auditoria.setRef11(comision
												.getComision_mayor_m() + "");
										auditoria.setRef12(comision
												.getComision_menor_m() + "");
										auditoriaService
												.insertarAuditoria(auditoria);
										resultadoOperacion = "Modificado correctamente";
										Sesion.mensajeInformacion(
												mantenimientoComision,
												resultadoOperacion);
										mantenimientoComision.activaControles();
										mantenimientoComision.limpiar();
									}
								} else {
									resultadoOperacion = "El vendedor ya tiene una comisión para el rango de fechas elegidas";
									Sesion.mensajeError(mantenimientoComision,
											resultadoOperacion);
								}
							} else {
								resultadoOperacion = "No se puede actualizar la comisión, porqué ya existe un vendedor maestro.";
								Sesion.mensajeError(mantenimientoComision,
										resultadoOperacion);
							}
						} catch (SQLException e1) {
							resultadoOperacion = e1.getMessage();
							Sesion.mensajeError(mantenimientoComision,
									resultadoOperacion);
						} catch (Exception e1) {
							resultadoOperacion = e1.getMessage();
							Sesion.mensajeError(mantenimientoComision,
									resultadoOperacion);
						}
					}
				} else {
					Sesion.mensajeError(mantenimientoComision,
							"Fecha final tiene que ser mayor a fecha inicial.");
				}
			}
			else {
				Sesion.mensajeError(mantenimientoComision,
						"Seleccione si es acumulable.");
			}
		}

		if (e.getSource() == mantenimientoComision.getBtnNuevo()) {
			mantenimientoComision.setOperacion(0);
			mantenimientoComision.desactivaControles();
			mantenimientoComision.limpiar();
		}

		if (e.getSource() == mantenimientoComision.getBtnModificar()) {
			if (mantenimientoComision.getCboAcumula().getSelectedIndex() != 0) {
				mantenimientoComision.setOperacion(1);
				mantenimientoComision.desactivaControles();
			} else {
				Sesion.mensajeError(mantenimientoComision,
						"Seleccione un registro de la tabla.");
			}

		}
		if (e.getSource() == mantenimientoComision.getBtnBuscar()
				|| e.getSource() == mantenimientoComision.getTxtBuscarr()) {
			try {
				List<Comision> comisiones = servicio
						.buscar(mantenimientoComision.getBuscar());
				mantenimientoComision.cargarTabla(comisiones);
				// auditoria
				auditoria = new Auditoria();
				auditoria.setUsu(Sesion.usuario);
				auditoria.setCIA(Sesion.cia);
				auditoria.setFecha(new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));
				auditoria.setMenu("Mantenimientos");
				auditoria.setModulo("RR.HH");
				auditoria.setOpcion("Comisiones");
				auditoria.setTipo_ope("Consulta");
				auditoria.setCant_reg(comisiones.size());
				auditoria.setRef1(mantenimientoComision.getBuscar());
				auditoriaService.insertarAuditoria(auditoria);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
		if (e.getSource() == mantenimientoComision.getBtnCancelar()) {
			mantenimientoComision.activaControles();
			mantenimientoComision.limpiar();
		}

	}

}
