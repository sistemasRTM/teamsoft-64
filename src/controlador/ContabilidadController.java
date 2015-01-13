package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import bean.LibroKardex;
import bean.RegistroOperacionDiaria;
import bean.TARTCON;
import bean.TASDC;
import bean.TASDT;
import bean.TCIEPER;
import bean.TESCOM;
import bean.TFVAD;
import bean.TNCDD;
import bean.TPEDH;
import bean.TPEDL;
import bean.TREGOP;
import bean.TREGV;
import bean.TREPCO;
import bean.TTIPTRAN;
import delegate.GestionComision;
import delegate.GestionContabilidad;
import recursos.Numero_a_Letra;
import recursos.Sesion;
import servicio.ComisionService;
import servicio.LibroKardexService;
import servicio.ReImpresionService;
import servicio.RegistroOperacionDiariaService;
import servicio.TARTCONService;
import servicio.TASDCService;
import servicio.TASDTService;
import servicio.TESCOMService;
import servicio.TREGOPService;
import servicio.TREPCOService;
import servicio.TTIPTRANService;
import util.Conexion;
import ventanas.FIMantenimientoTASDC;
import ventanas.FIMantenimientoTASDT;
import ventanas.FIMantenimientoTARTCON;
import ventanas.FIMantenimientoTESCOM;
import ventanas.FIMantenimientoTREGOP;
import ventanas.FIMantenimientoTREPCO;
import ventanas.FIMantenimientoTTIPTRAN;
import ventanas.FIReImpresionDocumentos;
import ventanas.FIRegistrarMovimiento;
import ventanas.FIRegistroOperacionDiaria;
import ventanas.FIReporteLibroDiario;
import ventanas.FIReporteLibroKardex;
import ventanas.FIReporteLibroMayor;

public class ContabilidadController implements ActionListener, KeyListener {

	private FIRegistroOperacionDiaria mRegistroOperacionDiaria;
	private FIReporteLibroKardex mReporteLibroKardex;
	private FIReporteLibroDiario mReporteLibroDiario;
	private FIReporteLibroMayor mReporteLibroMayor;
	private FIMantenimientoTASDC fiMADC;
	private FIMantenimientoTASDT fiMADT;
	private FIMantenimientoTARTCON fiMAPC;
	private FIMantenimientoTESCOM fiMEC;
	private FIMantenimientoTREPCO fiMRPC;
	private FIMantenimientoTTIPTRAN fiMTT;
	private FIMantenimientoTREGOP fiMRO;
	private FIReImpresionDocumentos fImpr;
	private FIRegistrarMovimiento fiRegMov;

	ComisionService comisionService = GestionComision.getComisionService();

	RegistroOperacionDiariaService servicioROD = GestionContabilidad
			.getRegistroOperacionDiariaService();
	TASDCService servicioTASDC = GestionContabilidad.getTASDCService();
	TASDTService servicioTASDT = GestionContabilidad.getTASDTService();
	TREPCOService servicioTREPCO = GestionContabilidad.getTREPCOService();
	TREGOPService servicioTREGOP = GestionContabilidad.getTREGOPService();
	TESCOMService servicioTESCOM = GestionContabilidad.getTESCOMService();
	TTIPTRANService servicioTTIPTRAN = GestionContabilidad.getTTIPTRANService();
	TARTCONService servicioTARTCON = GestionContabilidad.getTARTCONService();
	ReImpresionService servicioReImpresion = GestionContabilidad
			.getReImpresionService();
	LibroKardexService servicioKardex = GestionContabilidad.getLibroKardexService();
	
	List<RegistroOperacionDiaria> detalleErrores = new ArrayList<RegistroOperacionDiaria>();
	List<LibroKardex> detalleErroresLibroKardex = new ArrayList<LibroKardex>();
	// datos para el detalle de errores
	RegistroOperacionDiaria reporteError = null;
	//***************************************
	File file = new File("D:/contabilidad/LibroKardex.txt");
	PrintWriter pw;
	String separador = "|";
	LibroKardex libro = new LibroKardex();
	List<Object[]> saldosIniciales = new ArrayList<Object[]>();
	//***************************************
	
	public ContabilidadController(FIReImpresionDocumentos fImpr) {
		this.fImpr = fImpr;
	}

	public ContabilidadController(
			FIRegistroOperacionDiaria mRegistroOperacionDiaria) {
		this.mRegistroOperacionDiaria = mRegistroOperacionDiaria;
	}

	public ContabilidadController(
			FIReporteLibroDiario mReporteLibroDiario) {
		this.mReporteLibroDiario = mReporteLibroDiario;
	}
	
	public ContabilidadController(
			FIReporteLibroKardex mReporteLibroKardex) {
		this.mReporteLibroKardex = mReporteLibroKardex;
	}
	
	public ContabilidadController(
			FIReporteLibroMayor mReporteLibroMayor) {
		this.mReporteLibroMayor = mReporteLibroMayor;
	}
	
	public ContabilidadController(FIMantenimientoTASDC fiMADC) {
		this.fiMADC = fiMADC;
		try {
			this.fiMADC.cargarTabla(servicioTASDC.listar());
		} catch (SQLException e) {
			Sesion.mensajeError(fiMADC, "Error inesperado, " + e.getMessage()
					+ " " + e.getErrorCode() + " " + e.getCause());
		}
	}

	public ContabilidadController(FIMantenimientoTASDT fiMADT) {
		this.fiMADT = fiMADT;
		try {
			this.fiMADT.cargarTabla(servicioTASDT.listar());
		} catch (SQLException e) {
			Sesion.mensajeError(fiMADT, "Error inesperado, " + e.getMessage()
					+ " " + e.getErrorCode() + " " + e.getCause());
		}
	}

	public ContabilidadController(FIMantenimientoTARTCON fiMAPC) {
		this.fiMAPC = fiMAPC;
		try {
			this.fiMAPC.cargarTabla(servicioTARTCON.listar());
		} catch (SQLException e) {
			Sesion.mensajeError(fiMAPC, "Error inesperado, " + e.getMessage()
					+ " " + e.getErrorCode() + " " + e.getCause());
		}
	}

	public ContabilidadController(FIMantenimientoTESCOM fiMEC) {
		this.fiMEC = fiMEC;
		try {
			this.fiMEC.cargarTabla(servicioTESCOM.listar());
		} catch (SQLException e) {
			Sesion.mensajeError(fiMEC, "Error inesperado, " + e.getMessage()
					+ " " + e.getErrorCode() + " " + e.getCause());
		}
	}

	public ContabilidadController(FIMantenimientoTREPCO fiMRPC) {
		this.fiMRPC = fiMRPC;
		try {
			this.fiMRPC.cargarTabla(servicioTREPCO.listar());
		} catch (SQLException e) {
			Sesion.mensajeError(fiMRPC, "Error inesperado, " + e.getMessage()
					+ " " + e.getErrorCode() + " " + e.getCause());
		}
	}

	public ContabilidadController(FIMantenimientoTTIPTRAN fiMTT) {
		this.fiMTT = fiMTT;
		try {
			this.fiMTT.cargarTabla(servicioTTIPTRAN.listar());
		} catch (SQLException e) {
			Sesion.mensajeError(fiMTT, "Error inesperado, " + e.getMessage()
					+ " " + e.getErrorCode() + " " + e.getCause());
		}
	}

	public ContabilidadController(FIMantenimientoTREGOP fiMRO) {
		this.fiMRO = fiMRO;
		try {
			this.fiMRO.cargarTabla(servicioTREGOP.listar());
		} catch (SQLException e) {
			Sesion.mensajeError(fiMRO, "Error inesperado, " + e.getMessage()
					+ " " + e.getErrorCode() + " " + e.getCause());
		}
	}
	
	public ContabilidadController(FIRegistrarMovimiento fiRegMov){
		this.fiRegMov = fiRegMov;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (fiMADC != null) {
			if (e.getSource() == fiMADC.getTxtBCodigo()) {
				buscarPorCodigoMADC();
			}
		}
		if (fiMADT != null) {
			if (e.getSource() == fiMADT.getTxtBCodigo()) {
				buscarPorCodigoMADT();
			}
		}
		if (fiMAPC != null) {
			if (e.getSource() == fiMAPC.getTxtBCodigo()) {
				buscarPorCodigoMAPC();
			}
			if (e.getSource() == fiMAPC.getTxtValor1()
					|| e.getSource() == fiMAPC.getTxtValor2()
					|| e.getSource() == fiMAPC.getTxtValor3()) {

				if (!fiMAPC.getValor1().equals("")
						&& !fiMAPC.getValor2().equals("")
						&& !fiMAPC.getValor3().equals("")) {
					obtenerEquivalencia();
				}
			}
		}
		if (fiMEC != null) {
			if (e.getSource() == fiMEC.getTxtBCodigo()) {
				buscarPorCodigoMEC();
			}
		}
		if (fiMRPC != null) {
			if (e.getSource() == fiMRPC.getTxtBCodigo()) {
				buscarPorCodigoMRPC();
			}
		}
		if (fiMTT != null) {
			if (e.getSource() == fiMTT.getTxtBCodigo()) {
				buscarPorCodigoMTT();
			}
		}
		if (fiMRO != null) {
			if (e.getSource() == fiMRO.getTxtBCodigo()) {
				buscarPorCodigoMRO();
			}
		}
	}

	private void reImpresion() {
		String serie = fImpr.getSerie();
		String tipo = fImpr.getTipo();
		String desde = fImpr.getDesde();
		String hasta = fImpr.getHasta();
		String mensaje = "";
		List<TREGV> errores = new ArrayList<TREGV>();
		
		if (serie.equals("")) {
			mensaje += "Ingrese serie\n";
		}
		if (desde.equals("")) {
			mensaje += "Ingrese el número desde que desea imprimir\n";
		}
		if (hasta.equals("")) {
			hasta = desde;
		}
		if (mensaje.equals("")) {
			int iDesde = Integer.parseInt(desde);
			int iHasta = Integer.parseInt(hasta);
			if (iDesde <= iHasta) {
				try {
					int vueltasSerie = serie.length();
					int vueltasDesde = desde.length();
					int vueltasHasta = hasta.length();

					for (int i = 0; i < 3 - vueltasSerie; i++) {
						serie = "0" + serie;
					}
					for (int i = 0; i < 7 - vueltasDesde; i++) {
						desde = "0" + desde;
					}
					for (int i = 0; i < 7 - vueltasHasta; i++) {
						hasta = "0" + hasta;
					}

					List<TREGV> listado = servicioReImpresion
							.obtenerCorrelativos(tipo, serie + desde, serie
									+ hasta);
					if(tipo.equals("FC") || tipo.equals("BV")){
					for (TREGV tregv : listado) {
						if (tregv.getRvsitu().equals("99")) {
							if (tregv.getRvtdoc().equals("FC")) {
								imprimirAnulado(tregv.getRvndoc(),
										"FacturaAnulado");
							} else {
								imprimirAnulado(tregv.getRvndoc(),
										"BoletaAnulado");
							}
						} else {
								List<TPEDH> listadoTPEDH = servicioReImpresion.listarDetallePedidoTPEDD(
												Integer.parseInt(tregv.getRvndoc().substring(0, 3)),
												tregv.getRvtdoc(),
												Integer.parseInt(tregv.getRvndoc().substring(3,tregv.getRvndoc().trim().length())));
								if (listadoTPEDH.size() > 0) {
									if (tregv.getRvtdoc().equals("FC")) {
										listadoTPEDH = procesarDocumentoTPEDDF(
												listadoTPEDH, tregv.getRvmone());
										if(listadoTPEDH.get(0).getError()==null){
											imprimirTPEDDF(listadoTPEDH);
										}else{
											tregv.setError(listadoTPEDH.get(0).getError());
											errores.add(tregv);
										}
									}else{
										listadoTPEDH = procesarDocumentoTPEDDB(
												listadoTPEDH, tregv.getRvmone());
										if(listadoTPEDH.get(0).getError()==null){
											imprimirTPEDDB(listadoTPEDH);
										}else{
											tregv.setError(listadoTPEDH.get(0).getError());
											errores.add(tregv);
										}
									}
								} else {
									List<TFVAD> listadoTFVAD = servicioReImpresion.listarDetallePedidoTFVAD(
													Integer.parseInt(tregv.getRvndoc().substring(0, 3)),
													Integer.parseInt(tregv.getRvndoc().substring(3,tregv.getRvndoc().trim().length())),
													tregv.getRvtdoc());
									if (listadoTFVAD.size() > 0) {
										if (tregv.getRvtdoc().equals("FC")) {
											listadoTFVAD = procesarDocumentoTFVADF(
													listadoTFVAD, tregv.getRvmone());
											imprimirTFVADF(listadoTFVAD);
										}else{
											listadoTFVAD = procesarDocumentoTFVADB(
													listadoTFVAD, tregv.getRvmone());
											imprimirTFVADB(listadoTFVAD);
										}
									} else {
										tregv.setError("Documento hallado en el registro de ventas, pero no en la tabla\n de pedidos ni facturacion varios, buscar en otra compañia como TM.");
										errores.add(tregv);
									}
								}
						}
						
					}
					
					} else if(tipo.equals("ND")){
						for (TREGV tregv : listado) {
							if (tregv.getRvsitu().equals("99")) {
								imprimirAnulado(tregv.getRvndoc(),"BoletaAnulado");
							} else {
								List<TFVAD> listadoTFVAD = servicioReImpresion.listarDetallePedidoTFVADND(
										Integer.parseInt(tregv.getRvndoc().substring(0, 3)),
										Integer.parseInt(tregv.getRvndoc().substring(3,tregv.getRvndoc().trim().length())),
										tregv.getRvtdoc());
								if (listadoTFVAD.size() > 0) {
									listadoTFVAD = procesarDocumentoTFVADND(
											listadoTFVAD, tregv.getRvmone());
									imprimirTFVADND(listadoTFVAD);
								}else{
									tregv.setError("Documento hallado en el registro de ventas, pero no en la tabla\n de facturacion varios, buscar en otra compañia como TM.");
									errores.add(tregv);
								}
							}
						}
					} else if(tipo.equals("NC")){
						for (TREGV tregv : listado) {
							if (tregv.getRvsitu().equals("99")) {
								imprimirAnulado(tregv.getRvndoc(),"BoletaAnulado");
							} else {
								List<TNCDD> listadoNC = servicioReImpresion.listarNotasDeCredito(
										Integer.parseInt(tregv.getRvndoc().substring(0, 3)),
										Integer.parseInt(tregv.getRvndoc().substring(3,tregv.getRvndoc().trim().length())));
								if(listadoNC.size()>0){
									listadoNC = procesarDocumentoNC(
											listadoNC, tregv.getRvmone());
									imprimirNC(listadoNC);
								}else{
									List<TFVAD> listadoTFVAD = servicioReImpresion.listarDetallePedidoTFVADNC(
											Integer.parseInt(tregv.getRvndoc().substring(0, 3)),
											Integer.parseInt(tregv.getRvndoc().substring(3,tregv.getRvndoc().trim().length())),
											tregv.getRvtdoc());
									if (listadoTFVAD.size() > 0) {
										listadoTFVAD = procesarDocumentoTFVADNC(
												listadoTFVAD, tregv.getRvmone());
										imprimirTFVADNC(listadoTFVAD);
									}else{
										tregv.setError("Documento hallado en el registro de ventas, pero no en la tabla\n de notas de creditos ni facturacion varios, buscar en otra compañia como TM.");
										errores.add(tregv);
									}
								}
							}
						}
					}
					
					if(errores.size()>0){
						generarReporteError(errores);
					}
					
					if(listado.size()==0){
						Sesion.mensajeInformacion(fImpr, "No se han encontrado resultados para los siguientes datos:\nserie:"+serie+" tipo:"+tipo+" desde:"+desde+" hasta:"+hasta );
					}
				} catch (SQLException e) {
					Sesion.mensajeError(fImpr,"Error SQL en ReImpresion:"+e.getMessage() + " " + e.getCause());
					e.printStackTrace();
				} catch (NumberFormatException e) {
					Sesion.mensajeError(fImpr,"Error de Formato en ReImpresion:"+e.getMessage() + " " + e.getCause());
					e.printStackTrace();
				} catch (Exception e) {
					Sesion.mensajeError(fImpr,"Error General en ReImpresion:"+e.getMessage() + " " + e.getCause());
					e.printStackTrace();
				}
			} else {
				Sesion.mensajeError(fImpr,
						"El número hasta debe ser mayor al número desde");
			}
		} else {
			Sesion.mensajeError(fImpr, mensaje);
		}
	}

	private String getMesLetras(String mes){
		String letras="";
		switch (Integer.parseInt(mes)) {
			case 1: letras="ENERO";	break;
			case 2: letras="FEBRERO"; break;
			case 3: letras="MARZO";	break;
			case 4: letras="ABRIL";	break;
			case 5: letras="MAYO";	break;
			case 6: letras="JUNIO";	break;
			case 7: letras="JULIO";	break;
			case 8: letras="AGODTO"; break;
			case 9: letras="SEPTIEMBRE"; break;
			case 10: letras="OCTUBRE"; break;
			case 11: letras="NOVIEMBRE"; break;
			case 12: letras="DICIEMBRE"; break;
		}
		return letras;
	}
	
	private void generarReporteError(List<TREGV> errores) {
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnValue = fileChooser.showSaveDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				PrintWriter pw = new PrintWriter(new FileWriter(fileChooser.getSelectedFile().toString()+".txt"));
				for (TREGV tregv : errores) {
					pw.println(tregv.getRvndoc()+"|"+tregv.getRvtdoc()+"|"+tregv.getRvsitu()+"|"+tregv.getError());
				}
				pw.flush();
				pw.close();
				Sesion.mensajeInformacion(fImpr, 
						"Se a exportado con exito los documentos encontrados en el registro de ventas,\npero no hallados en las tablas de pedidos y facturación varios o motocicletas/motores sin datos adicionales,\n" +
						"verifique la siguiente ruta: \n -"+fileChooser.getSelectedFile().toString()+".txt");
			}else{
				generarReporteError(errores);
			}
		} catch (Exception e) {
			Sesion.mensajeError(fImpr,"Error general generando reporte de errores :"+e.getMessage() + " " + e.getCause());
			e.printStackTrace();
		}
	}

	private List<TNCDD> procesarDocumentoNC(List<TNCDD> listadoNC, double rvmone) {
		try {
			listadoNC.get(0).setDocumento("NotaCredito");
			int vueltasSerie = (listadoNC.get(0).getNHPVTA() + "").length();
			int vueltaNumero = (listadoNC.get(0).getNHNUME() + "").length();
			int vueltasSerie2 = (listadoNC.get(0).getNHPVTN() + "").length();
			int vueltaNumero2 = (listadoNC.get(0).getNHFABO() + "").length();
			
			String serie = listadoNC.get(0).getNHPVTA() + "";
			String numero = listadoNC.get(0).getNHNUME() + "";
			String serie2 = listadoNC.get(0).getNHPVTN() + "";
			String numero2 = listadoNC.get(0).getNHFABO() + "";

			
			String etiqueta = "";
			double igv = 0;
			double vven = 0;
			double pdpvt = 0;
			double pdrefe = 0;
			String pdref5 = "2.00 %";
			String moneda = " NUEVOS SOLES.";
			
			for (int i = 0; i < 3 - vueltasSerie; i++) {
				serie = "0" + serie;
			}
			for (int i = 0; i < 7 - vueltaNumero; i++) {
				numero = "0" + numero;
			}
			for (int i = 0; i < 3 - vueltasSerie2; i++) {
				serie2 = "0" + serie2;
			}
			for (int i = 0; i < 7 - vueltaNumero2; i++) {
				numero2 = "0" + numero2;
			}
			
			
			listadoNC.get(0).setSerie(serie);
			listadoNC.get(0).setNumero(numero);
			listadoNC.get(0).setSerie2(serie2);
			listadoNC.get(0).setNumero2(numero2);
			
			if (rvmone == 0) {
				for (TNCDD tncdd : listadoNC) {
					tncdd.setNCREF1C(Double.parseDouble(tncdd.getNCREF1()) / 100);
					if (tncdd.getNCREFA() > 0) {
						tncdd.setFLAG("*");
						if (tncdd.getNCREF5().equals("00050")) {
							pdref5 = "0.50 %";
						}
					} else {
						tncdd.setFLAG("");
					}
					tncdd.setNCPVT(tncdd.getNCNPVT());
					tncdd.setNCIGV(tncdd.getNCNIGV());
					tncdd.setNCVVA(tncdd.getNCNVVA());
					igv += tncdd.getNCIGV();
					vven += tncdd.getNCVVA();
					pdpvt += tncdd.getNCPVT();
					pdrefe += tncdd.getNCREFA();
					
					tncdd.setPU(Sesion.formatoDecimalVista_2(tncdd.getNCUNIT()- (tncdd.getNCUNIT() * (tncdd.getNCREF1C() / 100))));
				}
			} else {
				moneda = " DOLARES AMERICANOS.";
				for (TNCDD tncdd : listadoNC) {
					tncdd.setNCREF1C(Double.parseDouble(tncdd.getNCREF1()) / 100);
					if (tncdd.getNCREFA() > 0) {
						tncdd.setFLAG("*");
						if (tncdd.getNCREF5().equals("00050")) {
							pdref5 = "0.50 %";
						}
					} else {
						tncdd.setFLAG("");
					}
					tncdd.setNCPVT(tncdd.getNCEPVT());
					tncdd.setNCIGV(tncdd.getNCEIGV());
					tncdd.setNCVVA(tncdd.getNCEVVA());
					igv += tncdd.getNCIGV();
					vven += tncdd.getNCVVA();
					pdpvt += tncdd.getNCPVT();
					pdrefe += tncdd.getNCREFA();
					
					tncdd.setPU(Sesion.formatoDecimalVista_2(tncdd.getNCUNIT()- (tncdd.getNCUNIT() * (tncdd.getNCREF1C() / 100))));
				}
			}

			if (pdrefe > 0) {
				etiqueta += "Operación Sujeta a Percepción " + pdref5+ "   Total " + Sesion.formatoDecimalVista_2(pdrefe) + "\n";
				etiqueta += "Afecto a percepción aplicable a siguiente factura\n";
				etiqueta += "Comprobante de Percepción Venta Interna \n";
			}
			etiqueta += "Son : "+ Numero_a_Letra.Convertir(Sesion.formateaDecimal_2(pdpvt),	true) + moneda + "\n";
			etiqueta += "\t\t S.E.U.O. \n";
			
			listadoNC.get(0).setEtiqueta(etiqueta);
			listadoNC.get(0).setValorVenta(Sesion.formatoDecimalVista_2(vven));
			listadoNC.get(0).setMontoIGV(Sesion.formatoDecimalVista_2(igv));
			listadoNC.get(0).setTotal(Sesion.formatoDecimalVista_2(pdpvt));

		} catch (Exception e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error general, procesando nota de credito.\n" + e.getMessage() + " " + e.getCause());
		}
		return listadoNC;
	}

	private void imprimirNC(List<TNCDD> listadoNC) {
		try {
			JasperReport reporte = (JasperReport) JRLoader
					.loadObject(ClassLoader.getSystemResource("reportes/"+ listadoNC.get(0).getDocumento() + ".jasper"));
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.clear();
			parametros.put(JRParameter.REPORT_LOCALE, Locale.US);
			parametros.put("DIA",(listadoNC.get(0).getNHFECP() + "").substring(6, 8));
			parametros.put("MES",getMesLetras((listadoNC.get(0).getNHFECP() + "").substring(4, 6)));
			parametros.put("ANIO",(listadoNC.get(0).getNHFECP() + "").substring(0, 4));
			parametros.put("NHPVTA", listadoNC.get(0).getSerie() + " - ");
			parametros.put("NHNUME", listadoNC.get(0).getNumero());
			parametros.put("NHNOMC", listadoNC.get(0).getNHNOMC());
			parametros.put("NHDIRC", listadoNC.get(0).getNHDIRC());
			parametros.put("NHTDOC", listadoNC.get(0).getNHTDOC());
			parametros.put("CONCEPTO", listadoNC.get(0).getDESESP());
			parametros.put("DISC_PROC_DPTC", listadoNC.get(0).getNHDISC().trim()+ " "+ listadoNC.get(0).getCLIPRO().trim()+ " "+ listadoNC.get(0).getCLIDPT().trim());
			parametros.put("CLNIDE", listadoNC.get(0).getCLNIDE());
			parametros.put("NHPVTN", listadoNC.get(0).getSerie2() + " - ");
			parametros.put("NHFABO", listadoNC.get(0).getNumero2());
			parametros.put("ETIQUETA", listadoNC.get(0).getEtiqueta());
			parametros.put("VALOR_VENTA", listadoNC.get(0).getValorVenta());
			parametros.put("MONTO_IGV", listadoNC.get(0).getMontoIGV());
			parametros.put("TOTAL", listadoNC.get(0).getTotal());
			JasperPrint jpPedidos = JasperFillManager.fillReport(reporte,
					parametros, new JRBeanCollectionDataSource(listadoNC));
			//JasperViewer.viewReport(jpPedidos,false);
			
			AttributeSet attributeSet = new HashAttributeSet();
			attributeSet.add(new PrinterName(Sesion.impresoraMatricial, null));
			DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
			PrintService[] impresoras = PrintServiceLookup.lookupPrintServices(
					docFormat, attributeSet);
			JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
			jrprintServiceExporter.setParameter(
					JRExporterParameter.JASPER_PRINT, jpPedidos);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_SERVICE,
					impresoras[0]);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
					Boolean.FALSE);
			jrprintServiceExporter.exportReport();
			
		} catch (JRException e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error en Ireport, pedido factura. "+ e.getMessage() + " " + e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error general, pedido factura. " + e.getMessage() + " " + e.getCause());
		}
	}
	
	private List<TFVAD> procesarDocumentoTFVADNC(List<TFVAD> listadoTFVAD,
			double rvmone) {
		try {
			listadoTFVAD.get(0).setDocumento("NotaCreditoVarios");
			
			int vueltasSerie = (listadoTFVAD.get(0).getFvpvta() + "").length();
			int vueltaNumero = (listadoTFVAD.get(0).getFvnume() + "").length();
			String serie = listadoTFVAD.get(0).getFvpvta() + "";
			String numero = listadoTFVAD.get(0).getFvnume() + "";
			double igv = 0;
			double vven = 0;
			double pdpvt = 0;
			String etiqueta = "";
			String simbolo = "S/:";
			String moneda = " NUEVOS SOLES.";
			for (int i = 0; i < 3 - vueltasSerie; i++) {
				serie = "0" + serie;
			}
			for (int i = 0; i < 7 - vueltaNumero; i++) {
				numero = "0" + numero;
			}
			listadoTFVAD.get(0).setSerie(serie);
			listadoTFVAD.get(0).setNumero(numero);
			if (rvmone == 0) {
				for (TFVAD tfvad : listadoTFVAD) {
					tfvad.setFdpvt(tfvad.getFdnpvt());
					tfvad.setFdigv(tfvad.getFdnigv());
					tfvad.setFdvva(tfvad.getFdnvva());
					igv += tfvad.getFdigv();
					pdpvt += tfvad.getFdpvt();
					vven += tfvad.getFdvva();
				}
			} else {
				moneda = " DOLARES AMERICANOS.";
				simbolo = "US$";
				for (TFVAD tfvad : listadoTFVAD) {
					tfvad.setFdpvt(tfvad.getFdepvt());
					tfvad.setFdigv(tfvad.getFdeigv());
					tfvad.setFdvva(tfvad.getFdevva());
					igv += tfvad.getFdigv();
					pdpvt += tfvad.getFdpvt();
					vven += tfvad.getFdvva();
				}
			}

			etiqueta += "Son : "
					+ Numero_a_Letra.Convertir(Sesion.formateaDecimal_2(pdpvt),
							true) + moneda + "\n";
			etiqueta += "\t\t S.E.U.O. \n";
						
			listadoTFVAD.get(0).setEtiqueta(etiqueta);
			listadoTFVAD.get(0).setValorVenta(
					simbolo + Sesion.formatoDecimalVista_2(vven));
			listadoTFVAD.get(0).setMontoIGV(
					simbolo + Sesion.formatoDecimalVista_2(igv));
			listadoTFVAD.get(0).setTotal(
					simbolo + Sesion.formatoDecimalVista_2(pdpvt));

		} catch (Exception e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error general, procesando facturacion varios de nota de creditos.\n"+ e.getMessage() + " " + e.getCause());
		}
		return listadoTFVAD;
	}
	
	private void imprimirTFVADNC(List<TFVAD> listadoTFVAD) {
		try {
			JasperReport reporte = (JasperReport) JRLoader
					.loadObject(ClassLoader.getSystemResource("reportes/"
							+ listadoTFVAD.get(0).getDocumento() + ".jasper"));
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.clear();
			parametros.put(JRParameter.REPORT_LOCALE, Locale.US);
			parametros.put("dia",(listadoTFVAD.get(0).getFvfech() + "").substring(6, 8));
			parametros.put("mes",getMesLetras( (listadoTFVAD.get(0).getFvfech() + "").substring(4, 6)));
			parametros.put("anio",(listadoTFVAD.get(0).getFvfech() + "").substring(0, 4));
			parametros.put("fvpvta", listadoTFVAD.get(0).getSerie() + " - ");
			parametros.put("fvnume", listadoTFVAD.get(0).getNumero());
			parametros.put("fvnomc", listadoTFVAD.get(0).getFvnomc());
			parametros.put("fvtdoc", listadoTFVAD.get(0).getFvtdoc());
			parametros.put("fvdirc", listadoTFVAD.get(0).getFvdirc());
			parametros.put("disc_proc_dptc ", listadoTFVAD.get(0).getFvdisc().trim()	+ " "+ listadoTFVAD.get(0).getClipro().trim()+ " "	+ listadoTFVAD.get(0).getClidpt().trim());
			parametros.put("clnide", listadoTFVAD.get(0).getClnide());
			parametros.put("fvdesc", listadoTFVAD.get(0).getFvdesc());
			parametros.put("etiqueta", listadoTFVAD.get(0).getEtiqueta());
			parametros.put("valor_venta", listadoTFVAD.get(0).getValorVenta());
			parametros.put("monto_igv", listadoTFVAD.get(0).getMontoIGV());
			parametros.put("total", listadoTFVAD.get(0).getTotal());
			parametros.put("fvvend", listadoTFVAD.get(0).getFvvend());
			JasperPrint jpPedidos = JasperFillManager.fillReport(reporte,parametros, new JRBeanCollectionDataSource(listadoTFVAD));
			//JasperViewer.viewReport(jpPedidos, false);
			
			AttributeSet attributeSet = new HashAttributeSet();
			attributeSet.add(new PrinterName(Sesion.impresoraMatricial, null));
			DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
			PrintService[] impresoras = PrintServiceLookup.lookupPrintServices(
					docFormat, attributeSet);
			JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
			jrprintServiceExporter.setParameter(
					JRExporterParameter.JASPER_PRINT, jpPedidos);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_SERVICE,
					impresoras[0]);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
					Boolean.FALSE);
			jrprintServiceExporter.exportReport();
			
		} catch (JRException e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error en Ireport, imprimiendo facturacion varios de nota de creditos. "+ e.getMessage() + " " + e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error general, imprimiendo facturacion varios de nota de creditos. "+ e.getMessage() + " " + e.getCause());
		}
	}
	
	private List<TFVAD> procesarDocumentoTFVADND(List<TFVAD> listadoTFVAD,
			double rvmone) {
		try {
			listadoTFVAD.get(0).setDocumento("NotaDebito");
			
			int vueltasSerie = (listadoTFVAD.get(0).getFvpvta() + "").length();
			int vueltaNumero = (listadoTFVAD.get(0).getFvnume() + "").length();
			String serie = listadoTFVAD.get(0).getFvpvta() + "";
			String numero = listadoTFVAD.get(0).getFvnume() + "";
			double igv = 0;
			double vven = 0;
			double pdpvt = 0;
			String etiqueta = "";
			String simbolo = "S/:";
			String moneda = " NUEVOS SOLES.";
			for (int i = 0; i < 3 - vueltasSerie; i++) {
				serie = "0" + serie;
			}
			for (int i = 0; i < 7 - vueltaNumero; i++) {
				numero = "0" + numero;
			}
			listadoTFVAD.get(0).setSerie(serie);
			listadoTFVAD.get(0).setNumero(numero);
			if (rvmone == 0) {
				for (TFVAD tfvad : listadoTFVAD) {
					tfvad.setFdpvt(tfvad.getFdnpvt());
					tfvad.setFdigv(tfvad.getFdnigv());
					tfvad.setFdvva(tfvad.getFdnvva());
					igv += tfvad.getFdigv();
					pdpvt += tfvad.getFdpvt();
					vven += tfvad.getFdvva();
				}
			} else {
				moneda = " DOLARES AMERICANOS.";
				simbolo = "US$";
				for (TFVAD tfvad : listadoTFVAD) {
					tfvad.setFdpvt(tfvad.getFdepvt());
					tfvad.setFdigv(tfvad.getFdeigv());
					tfvad.setFdvva(tfvad.getFdevva());
					igv += tfvad.getFdigv();
					pdpvt += tfvad.getFdpvt();
					vven += tfvad.getFdvva();
				}
			}

			etiqueta += "Son : "
					+ Numero_a_Letra.Convertir(Sesion.formateaDecimal_2(pdpvt),
							true) + moneda + "\n";
			etiqueta += "\t\t S.E.U.O. \n";
						
			listadoTFVAD.get(0).setEtiqueta(etiqueta);
			listadoTFVAD.get(0).setValorVenta(
					simbolo + Sesion.formatoDecimalVista_2(vven));
			listadoTFVAD.get(0).setMontoIGV(
					simbolo + Sesion.formatoDecimalVista_2(igv));
			listadoTFVAD.get(0).setTotal(
					simbolo + Sesion.formatoDecimalVista_2(pdpvt));

		} catch (Exception e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error general, procesando facturacion varios de notas de debito.\n"+ e.getMessage() + " " + e.getCause());
		}
		return listadoTFVAD;
	}
	
	private void imprimirTFVADND(List<TFVAD> listadoTFVAD) {
		try {
			JasperReport reporte = (JasperReport) JRLoader
					.loadObject(ClassLoader.getSystemResource("reportes/"
							+ listadoTFVAD.get(0).getDocumento() + ".jasper"));
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.clear();
			parametros.put(JRParameter.REPORT_LOCALE, Locale.US);
			parametros.put("dia",(listadoTFVAD.get(0).getFvfech() + "").substring(6, 8));
			parametros.put("mes",getMesLetras( (listadoTFVAD.get(0).getFvfech() + "").substring(4, 6)));
			parametros.put("anio",(listadoTFVAD.get(0).getFvfech() + "").substring(0, 4));
			parametros.put("fvpvta", listadoTFVAD.get(0).getSerie() + " - ");
			parametros.put("fvnume", listadoTFVAD.get(0).getNumero());
			parametros.put("fvnomc", listadoTFVAD.get(0).getFvnomc());
			parametros.put("fvdirc", listadoTFVAD.get(0).getFvdirc());
			parametros.put("disc_proc_dptc ", listadoTFVAD.get(0).getFvdisc().trim()	+ " "+ listadoTFVAD.get(0).getClipro().trim()+ " "	+ listadoTFVAD.get(0).getClidpt().trim());
			parametros.put("clnide", listadoTFVAD.get(0).getClnide());
			parametros.put("fvdesc", listadoTFVAD.get(0).getFvdesc());
			parametros.put("etiqueta", listadoTFVAD.get(0).getEtiqueta());
			parametros.put("valor_venta", listadoTFVAD.get(0).getValorVenta());
			parametros.put("monto_igv", listadoTFVAD.get(0).getMontoIGV());
			parametros.put("total", listadoTFVAD.get(0).getTotal());
			parametros.put("fvvend", listadoTFVAD.get(0).getFvvend());
			JasperPrint jpPedidos = JasperFillManager.fillReport(reporte,parametros, new JRBeanCollectionDataSource(listadoTFVAD));
			//JasperViewer.viewReport(jpPedidos, false);
			
			AttributeSet attributeSet = new HashAttributeSet();
			attributeSet.add(new PrinterName(Sesion.impresoraMatricial, null));
			DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
			PrintService[] impresoras = PrintServiceLookup.lookupPrintServices(
					docFormat, attributeSet);
			JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
			jrprintServiceExporter.setParameter(
					JRExporterParameter.JASPER_PRINT, jpPedidos);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_SERVICE,
					impresoras[0]);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
					Boolean.FALSE);
			jrprintServiceExporter.exportReport();
			
		} catch (JRException e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error en Ireport, imprimiendo facturacion varios de nota de debito. "+ e.getMessage() + " " + e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error general, imprimiendo facturacion varios de nota de debito. "+ e.getMessage() + " " + e.getCause());
		}
	}

	private List<TFVAD> procesarDocumentoTFVADF(List<TFVAD> listadoTFVAD,
			double rvmone) {
		try {
			listadoTFVAD.get(0).setDocumento("FacturaVarios");
			
			int vueltasSerie = (listadoTFVAD.get(0).getFvpvta() + "").length();
			int vueltaNumero = (listadoTFVAD.get(0).getFvnume() + "").length();
			int vueltaCorrelativo = (listadoTFVAD.get(0).getFvcorr() + "")
					.length();
			String serie = listadoTFVAD.get(0).getFvpvta() + "";
			String numero = listadoTFVAD.get(0).getFvnume() + "";
			String correlativo = listadoTFVAD.get(0).getFvcorr() + "";
			double igv = 0;
			double vven = 0;
			double pdpvt = 0;
			String etiqueta = "";
			String simbolo = "S/:";
			String moneda = " NUEVOS SOLES.";
			for (int i = 0; i < 3 - vueltasSerie; i++) {
				serie = "0" + serie;
			}
			for (int i = 0; i < 7 - vueltaNumero; i++) {
				numero = "0" + numero;
			}
			for (int i = 0; i < 7 - vueltaCorrelativo; i++) {
				correlativo = "0" + correlativo;
			}
			listadoTFVAD.get(0).setSerie(serie);
			listadoTFVAD.get(0).setNumero(numero);
			if (rvmone == 0) {
				for (TFVAD tfvad : listadoTFVAD) {
					tfvad.setFdpvt(tfvad.getFdnpvt());
					tfvad.setFdigv(tfvad.getFdnigv());
					tfvad.setFdvva(tfvad.getFdnvva());
					igv += tfvad.getFdigv();
					pdpvt += tfvad.getFdpvt();
					vven += tfvad.getFdvva();
				}
			} else {
				moneda = " DOLARES AMERICANOS.";
				simbolo = "US$";
				for (TFVAD tfvad : listadoTFVAD) {
					tfvad.setFdpvt(tfvad.getFdepvt());
					tfvad.setFdigv(tfvad.getFdeigv());
					tfvad.setFdvva(tfvad.getFdevva());
					igv += tfvad.getFdigv();
					pdpvt += tfvad.getFdpvt();
					vven += tfvad.getFdvva();
				}
			}

			etiqueta += "Son : "
					+ Numero_a_Letra.Convertir(Sesion.formateaDecimal_2(pdpvt),
							true) + moneda + "\n";
			etiqueta += "\t\t S.E.U.O. \n";
			etiqueta += "Correl: " + correlativo + "   Vend: "
					+ listadoTFVAD.get(0).getFvvend().trim() + "     FA:"
					+ serie + numero;
			
			listadoTFVAD.get(0).setEtiqueta(etiqueta);
			listadoTFVAD.get(0).setValorVenta(
					simbolo + Sesion.formatoDecimalVista_2(vven));
			listadoTFVAD.get(0).setMontoIGV(
					simbolo + Sesion.formatoDecimalVista_2(igv));
			listadoTFVAD.get(0).setTotal(
					simbolo + Sesion.formatoDecimalVista_2(pdpvt));

		} catch (Exception e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error general, procesando facturacion varios de factura. "+ e.getMessage() + " " + e.getCause());
		}
		return listadoTFVAD;
	}

	private void imprimirTFVADF(List<TFVAD> listadoTFVAD) {
		try {
			JasperReport reporte = (JasperReport) JRLoader
					.loadObject(ClassLoader.getSystemResource("reportes/"
							+ listadoTFVAD.get(0).getDocumento() + ".jasper"));
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.clear();
			parametros.put(JRParameter.REPORT_LOCALE, Locale.US);
			parametros.put("fvnomc", listadoTFVAD.get(0).getFvnomc());
			parametros.put("fvdirc", listadoTFVAD.get(0).getFvdirc());
			parametros.put("DISC_PROC_DPTC", listadoTFVAD.get(0).getFvdisc()
					.trim()
					+ " "
					+ listadoTFVAD.get(0).getClipro().trim()
					+ " "
					+ listadoTFVAD.get(0).getClidpt().trim());
			parametros.put("clnide", listadoTFVAD.get(0).getClnide());
			parametros.put("CPADES", listadoTFVAD.get(0).getCpades());
			parametros.put("fvclie", listadoTFVAD.get(0).getFvclie());
			parametros.put("DIA",
					(listadoTFVAD.get(0).getFvfech() + "").substring(6, 8));
			parametros.put("MES",
					(listadoTFVAD.get(0).getFvfech() + "").substring(4, 6));
			parametros.put("ANIO",
					(listadoTFVAD.get(0).getFvfech() + "").substring(0, 4));
			parametros.put("fvpvta", listadoTFVAD.get(0).getSerie() + " - ");
			parametros.put("fvnume", listadoTFVAD.get(0).getNumero());
			parametros.put("ETIQUETA", listadoTFVAD.get(0).getEtiqueta());
			parametros.put("IGV", listadoTFVAD.get(0).getFdpigv() + "%");
			parametros.put("VALOR_VENTA", listadoTFVAD.get(0).getValorVenta());
			parametros.put("MONTO_IGV", listadoTFVAD.get(0).getMontoIGV());
			parametros.put("TOTAL", listadoTFVAD.get(0).getTotal());
			JasperPrint jpPedidos = JasperFillManager.fillReport(reporte,
					parametros, new JRBeanCollectionDataSource(listadoTFVAD));
			//JasperViewer.viewReport(jpPedidos, false);
			
			AttributeSet attributeSet = new HashAttributeSet();
			attributeSet.add(new PrinterName(Sesion.impresoraMatricial, null));
			DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
			PrintService[] impresoras = PrintServiceLookup.lookupPrintServices(
					docFormat, attributeSet);
			JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
			jrprintServiceExporter.setParameter(
					JRExporterParameter.JASPER_PRINT, jpPedidos);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_SERVICE,
					impresoras[0]);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
					Boolean.FALSE);
			jrprintServiceExporter.exportReport();
			
		} catch (JRException e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error en Ireport, imprimiendo facturacion varios de factura. "+ e.getMessage() + " " + e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error general, imprimiendo facturacion varios de factura. "+ e.getMessage() + " " + e.getCause());
		}
	}
	
	private List<TFVAD> procesarDocumentoTFVADB(List<TFVAD> listadoTFVAD,
			double rvmone) {
		try {
			listadoTFVAD.get(0).setDocumento("BoletaVarios");
			
			int vueltasSerie = (listadoTFVAD.get(0).getFvpvta() + "").length();
			int vueltaNumero = (listadoTFVAD.get(0).getFvnume() + "").length();
			int vueltaCorrelativo = (listadoTFVAD.get(0).getFvcorr() + "")
					.length();
			String serie = listadoTFVAD.get(0).getFvpvta() + "";
			String numero = listadoTFVAD.get(0).getFvnume() + "";
			String correlativo = listadoTFVAD.get(0).getFvcorr() + "";
			double pdpvt = 0;
			String etiqueta = "";
			String simbolo = "S/:";
			String moneda = " NUEVOS SOLES.";
			for (int i = 0; i < 3 - vueltasSerie; i++) {
				serie = "0" + serie;
			}
			for (int i = 0; i < 7 - vueltaNumero; i++) {
				numero = "0" + numero;
			}
			for (int i = 0; i < 7 - vueltaCorrelativo; i++) {
				correlativo = "0" + correlativo;
			}
			listadoTFVAD.get(0).setSerie(serie);
			listadoTFVAD.get(0).setNumero(numero);
			if (rvmone == 0) {
				for (TFVAD tfvad : listadoTFVAD) {
					tfvad.setFdpvt(tfvad.getFdnpvt());
					pdpvt += tfvad.getFdpvt();
				}
			} else {
				moneda = " DOLARES AMERICANOS.";
				simbolo = "US$";
				for (TFVAD tfvad : listadoTFVAD) {
					tfvad.setFdpvt(tfvad.getFdepvt());
					pdpvt += tfvad.getFdpvt();
				}
			}

			etiqueta += "Son : "+ Numero_a_Letra.Convertir(Sesion.formateaDecimal_2(pdpvt),true) + moneda + "\n";
			etiqueta += "\t\t S.E.U.O. \n";
			etiqueta += "Correl: " + correlativo + "   Vend: "+ listadoTFVAD.get(0).getFvvend().trim();

			listadoTFVAD.get(0).setEtiqueta(etiqueta);
			listadoTFVAD.get(0).setTotal(simbolo + Sesion.formatoDecimalVista_2(pdpvt));

		} catch (Exception e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error general, procesando facturacion varios de boleta. "+ e.getMessage() + " " + e.getCause());
		}
		return listadoTFVAD;
	}
	
	private void imprimirTFVADB(List<TFVAD> listadoTFVAD) {
		try {
			JasperReport reporte = (JasperReport) JRLoader
					.loadObject(ClassLoader.getSystemResource("reportes/"
							+ listadoTFVAD.get(0).getDocumento() + ".jasper"));
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.clear();
			parametros.put(JRParameter.REPORT_LOCALE, Locale.US);
			parametros.put("fvnomc", listadoTFVAD.get(0).getFvnomc());
			parametros.put("fvdirc", listadoTFVAD.get(0).getFvdirc());
			parametros.put("DISC_PROC_DPTC", listadoTFVAD.get(0).getFvdisc().trim()	+ " "+ listadoTFVAD.get(0).getClipro().trim()+ " "+ listadoTFVAD.get(0).getClidpt().trim());
			parametros.put("clnide", listadoTFVAD.get(0).getClnide());
			parametros.put("CPADES", listadoTFVAD.get(0).getCpades());
			parametros.put("FECHA",(listadoTFVAD.get(0).getFvfech() + "").substring(6, 8)+"/"+(listadoTFVAD.get(0).getFvfech() + "").substring(4, 6)+"/"+(listadoTFVAD.get(0).getFvfech() + "").substring(0, 4));
			parametros.put("fvpvta", listadoTFVAD.get(0).getSerie() + " - ");
			parametros.put("fvnume", listadoTFVAD.get(0).getNumero());
			parametros.put("ETIQUETA", listadoTFVAD.get(0).getEtiqueta());
			parametros.put("TOTAL", listadoTFVAD.get(0).getTotal());
			JasperPrint jpPedidos = JasperFillManager.fillReport(reporte,parametros, new JRBeanCollectionDataSource(listadoTFVAD));
			//JasperViewer.viewReport(jpPedidos, false);
			
			AttributeSet attributeSet = new HashAttributeSet();
			attributeSet.add(new PrinterName(Sesion.impresoraMatricial, null));
			DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
			PrintService[] impresoras = PrintServiceLookup.lookupPrintServices(
					docFormat, attributeSet);
			JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
			jrprintServiceExporter.setParameter(
					JRExporterParameter.JASPER_PRINT, jpPedidos);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_SERVICE,
					impresoras[0]);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
					Boolean.FALSE);
			jrprintServiceExporter.exportReport();
			
		} catch (JRException e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error en Ireport, imprimiendo facturacion varios de boleta. "+ e.getMessage() + " " + e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error general, imprimiendo facturacion varios de boleta. "+ e.getMessage() + " " + e.getCause());
		}
	}
	
	private List<TPEDH> procesarDocumentoTPEDDB(List<TPEDH> listadoTPEDH,double rvmone) {
		try {
			boolean isRepuesto = true;
			listadoTPEDH.get(0).setDocumento("Boleta");
			if (listadoTPEDH.get(0).getARTFAM().trim().equals("001")) {
				isRepuesto = false;
				listadoTPEDH.get(0).setDocumento(
						listadoTPEDH.get(0).getDocumento() + "Motocicleta");
			} else if (listadoTPEDH.get(0).getARTFAM().trim().equals("008")
					|| listadoTPEDH.get(0).getARTFAM().trim().equals("009")) {
				isRepuesto = false;
				listadoTPEDH.get(0).setDocumento(
						listadoTPEDH.get(0).getDocumento() + "Motor");
			}
			int vueltasSerie = (listadoTPEDH.get(0).getPHPVTA() + "").length();
			int vueltaFabo = (listadoTPEDH.get(0).getPDFABO() + "").length();
			int vueltaNumero = (listadoTPEDH.get(0).getPHNUME() + "").length();
			String serie = listadoTPEDH.get(0).getPHPVTA() + "";
			String fabo = listadoTPEDH.get(0).getPDFABO() + "";
			String numero = listadoTPEDH.get(0).getPHNUME() + "";
			String guia = "";
			String etiqueta = "";
			String etiqueta2 = "";
			double pdpvt = 0;
			double pdrefe = 0;
			String pdref5 = "2.00 %";
			String simbolo = "S/:";
			String moneda = " NUEVOS SOLES.";
			for (int i = 0; i < 3 - vueltasSerie; i++) {
				serie = "0" + serie;
			}
			for (int i = 0; i < 7 - vueltaFabo; i++) {
				fabo = "0" + fabo;
			}
			for (int i = 0; i < 7 - vueltaNumero; i++) {
				numero = "0" + numero;
			}
			if (listadoTPEDH.get(0).getPDGUIA() > 0) {
				int vueltasGuia = (listadoTPEDH.get(0).getPDGUIA() + "")
						.length();
				guia = listadoTPEDH.get(0).getPDGUIA() + "";
				for (int i = 0; i < 7 - vueltasGuia; i++) {
					guia = "0" + guia;
				}
			} 
			listadoTPEDH.get(0).setSerie(serie);
			listadoTPEDH.get(0).setFabo(fabo);
			
			if (rvmone == 0) {
				for (TPEDH tpedh : listadoTPEDH) {
					tpedh.setPDREF1C(Double.parseDouble(tpedh.getPDREF1()) / 100);
					if (tpedh.getPDREFE() > 0) {
						tpedh.setFLAG("*");
						if (tpedh.getPDREF5().equals("00050")) {
							pdref5 = "0.50 %";
						}
					} else {
						tpedh.setFLAG("");
					}
					tpedh.setPDPVT(tpedh.getPDNPVT());				
					pdpvt += tpedh.getPDPVT();
					pdrefe += tpedh.getPDREFE();
					if (isRepuesto) {
						tpedh.setPU(Sesion.formateaDecimal_4(tpedh.getPDUNIT()
								- (tpedh.getPDUNIT() * (tpedh.getPDREF1C() / 100))));
					} else {
						tpedh.setPU(Sesion.formateaDecimal_4(tpedh.getPDPVT()
								/ tpedh.getPDCANT()));
						tpedh.setPDUNIT((tpedh.getPDPVT() / tpedh.getPDCANT())
								/ ((100 - tpedh.getPDREF1C()) / 100));

						TPEDL tpedl = servicioReImpresion.getDetalleMotorOMoto(
								tpedh.getPHPVTA(), tpedh.getPHNUME(),
								tpedh.getPDARTI());
						if(tpedl!=null){
						tpedh.setPimvin(tpedl.getPimvin());
						tpedh.setPimcha(tpedl.getPimcha());
						tpedh.setPimmot(tpedl.getPimmot());
						tpedh.setPimcat(tpedl.getPimcat());
						tpedh.setPimmar(tpedl.getPimmar());
						tpedh.setPimmod(tpedl.getPimmod());
						tpedh.setPimtca(tpedl.getPimtca());
						tpedh.setPimano(tpedl.getPimano());
						tpedh.setPimcol(tpedl.getPimcol());
						tpedh.setPimcil(tpedl.getPimcil());
						tpedh.setPimcmo(tpedl.getPimcmo());
						tpedh.setPimpse(tpedl.getPimpse());
						tpedh.setPimaru(tpedl.getPimaru());
						tpedh.setPimlaa(tpedl.getPimlaa());
						tpedh.setPimpol(tpedl.getPimpol());
						tpedh.setPimtit(tpedl.getPimtit());
						tpedh.setPimd01(tpedl.getPimd01());
						tpedh.setPimd02(tpedl.getPimd02());
						tpedh.setPimd03(tpedl.getPimd03());
						tpedh.setPimd04(tpedl.getPimd04());
						tpedh.setPimd05(tpedl.getPimd05());
						tpedh.setPimd06(tpedl.getPimd06());
						tpedh.setPimd08(tpedl.getPimd08());
						tpedh.setPimd09(tpedl.getPimd09());
						tpedh.setPimd10(tpedl.getPimd10());
						tpedh.setPimd11(tpedl.getPimd11());
						tpedh.setPimd15(tpedl.getPimd15());
						}else{
							listadoTPEDH.get(0).setError("no se encontrarón los datos adicionales de la motocicleta/motor con código :"+tpedh.getPDARTI());
						}
					}
				}
			} else {
				moneda = " DOLARES AMERICANOS.";
				simbolo = "US$";
				for (TPEDH tpedh : listadoTPEDH) {
					tpedh.setPDREF1C(Double.parseDouble(tpedh.getPDREF1()) / 100);
					if (tpedh.getPDREFE() > 0) {
						tpedh.setFLAG("*");
						if (tpedh.getPDREF5().equals("00050")) {
							pdref5 = "0.50 %";
						}
					} else {
						tpedh.setFLAG("");
					}
					tpedh.setPDPVT(tpedh.getPDEPVT());				
					pdpvt += tpedh.getPDPVT();
					pdrefe += tpedh.getPDREFE();
					if (isRepuesto) {
						tpedh.setPU(Sesion.formateaDecimal_4(tpedh.getPDPVT()
								/ tpedh.getPDCANT()));
						tpedh.setPDUNIT((tpedh.getPDPVT() / tpedh.getPDCANT())
								/ ((100 - tpedh.getPDREF1C()) / 100));
					} else {
						tpedh.setPU(Sesion.formateaDecimal_4(tpedh.getPDUNIT()
								- (tpedh.getPDUNIT() * (tpedh.getPDREF1C() / 100))));

						TPEDL tpedl = servicioReImpresion.getDetalleMotorOMoto(
								tpedh.getPHPVTA(), tpedh.getPHNUME(),
								tpedh.getPDARTI());
						if(tpedl!=null){
						tpedh.setPimvin(tpedl.getPimvin());
						tpedh.setPimcha(tpedl.getPimcha());
						tpedh.setPimmot(tpedl.getPimmot());
						tpedh.setPimcat(tpedl.getPimcat());
						tpedh.setPimmar(tpedl.getPimmar());
						tpedh.setPimmod(tpedl.getPimmod());
						tpedh.setPimtca(tpedl.getPimtca());
						tpedh.setPimano(tpedl.getPimano());
						tpedh.setPimcol(tpedl.getPimcol());
						tpedh.setPimcil(tpedl.getPimcil());
						tpedh.setPimcmo(tpedl.getPimcmo());
						tpedh.setPimpse(tpedl.getPimpse());
						tpedh.setPimaru(tpedl.getPimaru());
						tpedh.setPimlaa(tpedl.getPimlaa());
						tpedh.setPimpol(tpedl.getPimpol());
						tpedh.setPimtit(tpedl.getPimtit());
						tpedh.setPimd01(tpedl.getPimd01());
						tpedh.setPimd02(tpedl.getPimd02());
						tpedh.setPimd03(tpedl.getPimd03());
						tpedh.setPimd04(tpedl.getPimd04());
						tpedh.setPimd05(tpedl.getPimd05());
						tpedh.setPimd06(tpedl.getPimd06());
						tpedh.setPimd08(tpedl.getPimd08());
						tpedh.setPimd09(tpedl.getPimd09());
						tpedh.setPimd10(tpedl.getPimd10());
						tpedh.setPimd11(tpedl.getPimd11());
						tpedh.setPimd15(tpedl.getPimd15());
						}else{
							listadoTPEDH.get(0).setError("no se encontrarón los datos adicionales de la motocicleta/motor con código :"+tpedh.getPDARTI());
						}
					}
				}
			}

			if (pdrefe > 0) {
				etiqueta += "Operación Sujeta a Percepción " + pdref5+ "   Total " + simbolo+ Sesion.formatoDecimalVista_2(pdrefe) + "\n";
				etiqueta += "Total a Pagar con Percepción " + simbolo+ Sesion.formatoDecimalVista_2(pdrefe + pdpvt) + "\n";
				etiqueta += "Comprobante de Percepción Venta Interna \n";
			}
			etiqueta += "Son : "+ Numero_a_Letra.Convertir(Sesion.formateaDecimal_2(pdpvt),	true) + moneda + "\n";
			etiqueta += "\t\t S.E.U.O. \n";	
			if(!guia.equals("")){
				etiqueta2 += "NP: " + serie + numero + "   Vend: "+ listadoTPEDH.get(0).getPHUSAP().trim() +"   GU:"+ serie + guia;
			}else{
				etiqueta2 += "NP: " + serie + numero + "   Vend: "+ listadoTPEDH.get(0).getPHUSAP().trim() +"   .BV:"+ serie + fabo;
			}
			listadoTPEDH.get(0).setEtiqueta(etiqueta);
			listadoTPEDH.get(0).setEtiqueta2(etiqueta2);
			listadoTPEDH.get(0).setTotal(simbolo + Sesion.formatoDecimalVista_2(pdpvt));
		} catch (Exception e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error general, procesando pedido boleta. "+ e.getMessage() + " " + e.getCause());
		}
		return listadoTPEDH;
	}
	
	private void imprimirTPEDDB(List<TPEDH> listadoTPEDH) {
		try {
			JasperReport reporte = (JasperReport) JRLoader
					.loadObject(ClassLoader.getSystemResource("reportes/"+ listadoTPEDH.get(0).getDocumento() + ".jasper"));
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.clear();
			parametros.put(JRParameter.REPORT_LOCALE, Locale.US);
			parametros.put("PHNOMC", listadoTPEDH.get(0).getPHNOMC());
			parametros.put("PHDIRC", listadoTPEDH.get(0).getPHDIRC());
			parametros.put("DISC_PROC_DPTC", listadoTPEDH.get(0).getPHDISC().trim()+ " "+ listadoTPEDH.get(0).getCLIPRO().trim()+ " "+ listadoTPEDH.get(0).getCLIDPT().trim());
			parametros.put("PHRUCC", listadoTPEDH.get(0).getCLNIDE());
			parametros.put("CPADES", listadoTPEDH.get(0).getCPADES());
			parametros.put("FECHA",(listadoTPEDH.get(0).getPDFECF() + "").substring(6, 8)+"/"+(listadoTPEDH.get(0).getPDFECF() + "").substring(4, 6)+"/"+(listadoTPEDH.get(0).getPDFECF() + "").substring(0, 4));
			parametros.put("PHPVTA", listadoTPEDH.get(0).getSerie() + " - ");
			parametros.put("PDFABO", listadoTPEDH.get(0).getFabo());
			parametros.put("ETIQUETA", listadoTPEDH.get(0).getEtiqueta());
			parametros.put("ETIQUETA2", listadoTPEDH.get(0).getEtiqueta2());
			parametros.put("TOTAL", listadoTPEDH.get(0).getTotal());
			JasperPrint jpPedidos = JasperFillManager.fillReport(reporte,
					parametros, new JRBeanCollectionDataSource(listadoTPEDH));
			//JasperViewer.viewReport(jpPedidos,false);
		
			AttributeSet attributeSet = new HashAttributeSet();
			attributeSet.add(new PrinterName(Sesion.impresoraMatricial, null));
			DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
			PrintService[] impresoras = PrintServiceLookup.lookupPrintServices(
					docFormat, attributeSet);
			JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
			jrprintServiceExporter.setParameter(
					JRExporterParameter.JASPER_PRINT, jpPedidos);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_SERVICE,
					impresoras[0]);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
					Boolean.FALSE);
			jrprintServiceExporter.exportReport();
				
		} catch (JRException e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error en Ireport, imprimiendo boleta. "+ e.getMessage() + " " + e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error general, imprimiendo boleta. "+ e.getMessage() + " " + e.getCause());
		}
	}

	private List<TPEDH> procesarDocumentoTPEDDF(List<TPEDH> listadoTPEDH,
			double rvmone) {
		try {
			boolean isRepuesto = true;
			listadoTPEDH.get(0).setDocumento("Factura");
	
			if (listadoTPEDH.get(0).getARTFAM().trim().equals("001")) {
				isRepuesto = false;
				listadoTPEDH.get(0).setDocumento(
						listadoTPEDH.get(0).getDocumento() + "Motocicleta");
			} else if (listadoTPEDH.get(0).getARTFAM().trim().equals("008")
					|| listadoTPEDH.get(0).getARTFAM().trim().equals("009")) {
				isRepuesto = false;
				listadoTPEDH.get(0).setDocumento(
						listadoTPEDH.get(0).getDocumento() + "Motor");
			}
			int vueltasSerie = (listadoTPEDH.get(0).getPHPVTA() + "").length();
			int vueltaFabo = (listadoTPEDH.get(0).getPDFABO() + "").length();
			int vueltaNumero = (listadoTPEDH.get(0).getPHNUME() + "").length();
			String serie = listadoTPEDH.get(0).getPHPVTA() + "";
			String fabo = listadoTPEDH.get(0).getPDFABO() + "";
			String numero = listadoTPEDH.get(0).getPHNUME() + "";
			String guia = "";
			double igv = 0;
			double vven = 0;
			String pigv = "19.00%";
			String etiqueta = "";
			double pdpvt = 0;
			double pdrefe = 0;
			String pdref5 = "2.00 %";
			String simbolo = "S/:";
			String moneda = " NUEVOS SOLES.";
			for (int i = 0; i < 3 - vueltasSerie; i++) {
				serie = "0" + serie;
			}
			for (int i = 0; i < 7 - vueltaFabo; i++) {
				fabo = "0" + fabo;
			}
			for (int i = 0; i < 7 - vueltaNumero; i++) {
				numero = "0" + numero;
			}
			if (listadoTPEDH.get(0).getPDGUIA() > 0) {
				int vueltasGuia = (listadoTPEDH.get(0).getPDGUIA() + "")
						.length();
				guia = listadoTPEDH.get(0).getPDGUIA() + "";
				for (int i = 0; i < 7 - vueltasGuia; i++) {
					guia = "0" + guia;
				}
			} else {
				guia = fabo;
			}
			if (listadoTPEDH.get(0).getPHREF3().trim().substring(0, 2)
					.equals("03")) {
				pigv = "18.00%";
			}
			listadoTPEDH.get(0).setSerie(serie);
			listadoTPEDH.get(0).setNumero(numero);
			listadoTPEDH.get(0).setFabo(fabo);
			listadoTPEDH.get(0).setGuia(guia);
			listadoTPEDH.get(0).setPigv(pigv);
			
			if (rvmone == 0) {
				for (TPEDH tpedh : listadoTPEDH) {
					tpedh.setPDREF1C(Double.parseDouble(tpedh.getPDREF1()) / 100);
					if (tpedh.getPDREFE() > 0) {
						tpedh.setFLAG("*");
						if (tpedh.getPDREF5().equals("00050")) {
							pdref5 = "0.50 %";
						}
					} else {
						tpedh.setFLAG("");
					}
					tpedh.setPDPVT(tpedh.getPDNPVT());
					tpedh.setPDIGV(tpedh.getPDNIGV());
					tpedh.setVVEN(tpedh.getVVENS());
					igv += tpedh.getPDIGV();
					vven += tpedh.getVVEN();
					pdpvt += tpedh.getPDPVT();
					pdrefe += tpedh.getPDREFE();
					if (isRepuesto) {
						tpedh.setPU(Sesion.formateaDecimal_4(tpedh.getPDUNIT()
								- (tpedh.getPDUNIT() * (tpedh.getPDREF1C() / 100))));
					} else {
						tpedh.setPU(Sesion.formateaDecimal_4(tpedh.getPDPVT()
								/ tpedh.getPDCANT()));
						tpedh.setPDUNIT((tpedh.getPDPVT() / tpedh.getPDCANT())
								/ ((100 - tpedh.getPDREF1C()) / 100));

						TPEDL tpedl = servicioReImpresion.getDetalleMotorOMoto(
								tpedh.getPHPVTA(), tpedh.getPHNUME(),
								tpedh.getPDARTI());
						if(tpedl!=null){
						tpedh.setPimvin(tpedl.getPimvin());
						tpedh.setPimcha(tpedl.getPimcha());
						tpedh.setPimmot(tpedl.getPimmot());
						tpedh.setPimcat(tpedl.getPimcat());
						tpedh.setPimmar(tpedl.getPimmar());
						tpedh.setPimmod(tpedl.getPimmod());
						tpedh.setPimtca(tpedl.getPimtca());
						tpedh.setPimano(tpedl.getPimano());
						tpedh.setPimcol(tpedl.getPimcol());
						tpedh.setPimcil(tpedl.getPimcil());
						tpedh.setPimcmo(tpedl.getPimcmo());
						tpedh.setPimpse(tpedl.getPimpse());
						tpedh.setPimaru(tpedl.getPimaru());
						tpedh.setPimlaa(tpedl.getPimlaa());
						tpedh.setPimpol(tpedl.getPimpol());
						tpedh.setPimtit(tpedl.getPimtit());
						tpedh.setPimd01(tpedl.getPimd01());
						tpedh.setPimd02(tpedl.getPimd02());
						tpedh.setPimd03(tpedl.getPimd03());
						tpedh.setPimd04(tpedl.getPimd04());
						tpedh.setPimd05(tpedl.getPimd05());
						tpedh.setPimd06(tpedl.getPimd06());
						tpedh.setPimd08(tpedl.getPimd08());
						tpedh.setPimd09(tpedl.getPimd09());
						tpedh.setPimd10(tpedl.getPimd10());
						tpedh.setPimd11(tpedl.getPimd11());
						tpedh.setPimd15(tpedl.getPimd15());
						}else{
							listadoTPEDH.get(0).setError("no se encontrarón los datos adicionales de la motocicleta/motor con código :"+tpedh.getPDARTI());
						}
					}
				}
			} else {
				moneda = " DOLARES AMERICANOS.";
				simbolo = "US$";
				for (TPEDH tpedh : listadoTPEDH) {
					tpedh.setPDREF1C(Double.parseDouble(tpedh.getPDREF1()) / 100);
					if (tpedh.getPDREFE() > 0) {
						tpedh.setFLAG("*");
						if (tpedh.getPDREF5().equals("00050")) {
							pdref5 = "0.50 %";
						}
					} else {
						tpedh.setFLAG("");
					}
					tpedh.setPDPVT(tpedh.getPDEPVT());
					tpedh.setPDIGV(tpedh.getPDEIGV());
					tpedh.setVVEN(tpedh.getVVEND());
					igv += tpedh.getPDIGV();
					vven += tpedh.getVVEN();
					pdpvt += tpedh.getPDPVT();
					pdrefe += tpedh.getPDREFE();
					if (isRepuesto) {
						tpedh.setPU(Sesion.formateaDecimal_4(tpedh.getPDPVT()
								/ tpedh.getPDCANT()));
						tpedh.setPDUNIT((tpedh.getPDPVT() / tpedh.getPDCANT())
								/ ((100 - tpedh.getPDREF1C()) / 100));
					} else {
						tpedh.setPU(Sesion.formateaDecimal_4(tpedh.getPDUNIT()
								- (tpedh.getPDUNIT() * (tpedh.getPDREF1C() / 100))));

						TPEDL tpedl = servicioReImpresion.getDetalleMotorOMoto(
								tpedh.getPHPVTA(), tpedh.getPHNUME(),
								tpedh.getPDARTI());
						if(tpedl!=null){
						tpedh.setPimvin(tpedl.getPimvin());
						tpedh.setPimcha(tpedl.getPimcha());
						tpedh.setPimmot(tpedl.getPimmot());
						tpedh.setPimcat(tpedl.getPimcat());
						tpedh.setPimmar(tpedl.getPimmar());
						tpedh.setPimmod(tpedl.getPimmod());
						tpedh.setPimtca(tpedl.getPimtca());
						tpedh.setPimano(tpedl.getPimano());
						tpedh.setPimcol(tpedl.getPimcol());
						tpedh.setPimcil(tpedl.getPimcil());
						tpedh.setPimcmo(tpedl.getPimcmo());
						tpedh.setPimpse(tpedl.getPimpse());
						tpedh.setPimaru(tpedl.getPimaru());
						tpedh.setPimlaa(tpedl.getPimlaa());
						tpedh.setPimpol(tpedl.getPimpol());
						tpedh.setPimtit(tpedl.getPimtit());
						tpedh.setPimd01(tpedl.getPimd01());
						tpedh.setPimd02(tpedl.getPimd02());
						tpedh.setPimd03(tpedl.getPimd03());
						tpedh.setPimd04(tpedl.getPimd04());
						tpedh.setPimd05(tpedl.getPimd05());
						tpedh.setPimd06(tpedl.getPimd06());
						tpedh.setPimd08(tpedl.getPimd08());
						tpedh.setPimd09(tpedl.getPimd09());
						tpedh.setPimd10(tpedl.getPimd10());
						tpedh.setPimd11(tpedl.getPimd11());
						tpedh.setPimd15(tpedl.getPimd15());
						}else{
							listadoTPEDH.get(0).setError("no se encontrarón los datos adicionales de la motocicleta/motor con código :"+tpedh.getPDARTI());
						}
					}
				}
			}

			if (pdrefe > 0) {
				etiqueta += "Operación Sujeta a Percepción " + pdref5
						+ "   Total " + simbolo
						+ Sesion.formatoDecimalVista_2(pdrefe) + "\n";
				etiqueta += "Total a Pagar con Percepción " + simbolo
						+ Sesion.formatoDecimalVista_2(pdrefe + pdpvt) + "\n";
				etiqueta += "Comprobante de Percepción Venta Interna \n";
			}
			etiqueta += "Son : "
					+ Numero_a_Letra.Convertir(Sesion.formateaDecimal_2(pdpvt),
							true) + moneda + "\n";
			etiqueta += "\t\t S.E.U.O. \n";
			etiqueta += "PD: " + serie + numero + "   Vend: "
					+ listadoTPEDH.get(0).getPHUSAP().trim() + "     FA:"
					+ serie + fabo;

			listadoTPEDH.get(0).setEtiqueta(etiqueta);
			listadoTPEDH.get(0).setValorVenta(
					simbolo + Sesion.formatoDecimalVista_2(vven));
			listadoTPEDH.get(0).setMontoIGV(
					simbolo + Sesion.formatoDecimalVista_2(igv));
			listadoTPEDH.get(0).setTotal(
					simbolo + Sesion.formatoDecimalVista_2(pdpvt));

		} catch (Exception e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error general, procesar pedido factura. " + e.getMessage() + " " + e.getCause());
		}
		return listadoTPEDH;
	}

	private void imprimirTPEDDF(List<TPEDH> listadoTPEDH) {
		try {
			JasperReport reporte = (JasperReport) JRLoader
					.loadObject(ClassLoader.getSystemResource("reportes/"+ listadoTPEDH.get(0).getDocumento() + ".jasper"));
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.clear();
			parametros.put(JRParameter.REPORT_LOCALE, Locale.US);
			parametros.put("PHNOMC", listadoTPEDH.get(0).getPHNOMC());
			parametros.put("PHDIRC", listadoTPEDH.get(0).getPHDIRC());
			parametros.put("DISC_PROC_DPTC", listadoTPEDH.get(0).getPHDISC().trim()+ " "+ listadoTPEDH.get(0).getCLIPRO().trim()+ " "+ listadoTPEDH.get(0).getCLIDPT().trim());
			parametros.put("PHRUCC", listadoTPEDH.get(0).getCLNIDE());
			parametros.put("CPADES", listadoTPEDH.get(0).getCPADES());
			parametros.put("PHCLIE", listadoTPEDH.get(0).getCLICVE());
			parametros.put("DIA",(listadoTPEDH.get(0).getPDFECF() + "").substring(6, 8));
			parametros.put("MES",(listadoTPEDH.get(0).getPDFECF() + "").substring(4, 6));
			parametros.put("ANIO",(listadoTPEDH.get(0).getPDFECF() + "").substring(0, 4));
			parametros.put("GUIA", listadoTPEDH.get(0).getSerie() + "-"	+ listadoTPEDH.get(0).getGuia());
			parametros.put("PHPVTA", listadoTPEDH.get(0).getSerie() + " - ");
			parametros.put("PDFABO", listadoTPEDH.get(0).getFabo());
			parametros.put("ETIQUETA", listadoTPEDH.get(0).getEtiqueta());
			parametros.put("IGV", listadoTPEDH.get(0).getPigv());
			parametros.put("VALOR_VENTA", listadoTPEDH.get(0).getValorVenta());
			parametros.put("MONTO_IGV", listadoTPEDH.get(0).getMontoIGV());
			parametros.put("TOTAL", listadoTPEDH.get(0).getTotal());
			JasperPrint jpPedidos = JasperFillManager.fillReport(reporte,
					parametros, new JRBeanCollectionDataSource(listadoTPEDH));
			//JasperViewer.viewReport(jpPedidos,false);
			
			AttributeSet attributeSet = new HashAttributeSet();
			attributeSet.add(new PrinterName(Sesion.impresoraMatricial, null));
			DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
			PrintService[] impresoras = PrintServiceLookup.lookupPrintServices(
					docFormat, attributeSet);
			JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
			jrprintServiceExporter.setParameter(
					JRExporterParameter.JASPER_PRINT, jpPedidos);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_SERVICE,
					impresoras[0]);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
					Boolean.FALSE);
			jrprintServiceExporter.exportReport();
			 
		} catch (JRException e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error en Ireport, pedido factura. "+ e.getMessage() + " " + e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error general, pedido factura. " + e.getMessage() + " " + e.getCause());
		}
	}

	private void imprimirAnulado(String rvndoc, String jasper) {
		try {
			JasperReport reporte = (JasperReport) JRLoader
					.loadObject(ClassLoader.getSystemResource("reportes/"
							+ jasper + ".jasper"));
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.clear();
			parametros.put("serie", rvndoc.substring(0, 3));
			parametros.put("numero", rvndoc.substring(3, rvndoc.length()));
			JasperPrint jpPedidos = JasperFillManager.fillReport(reporte,
					parametros);
			//JasperViewer.viewReport(jpPedidos,false);
			
			AttributeSet attributeSet = new HashAttributeSet();
			attributeSet.add(new PrinterName(Sesion.impresoraMatricial, null));
			DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
			PrintService[] impresoras = PrintServiceLookup.lookupPrintServices(
					docFormat, attributeSet);
			JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
			jrprintServiceExporter.setParameter(
					JRExporterParameter.JASPER_PRINT, jpPedidos);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_SERVICE,
					impresoras[0]);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
					Boolean.FALSE);
			jrprintServiceExporter.exportReport();
			
		} catch (JRException e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error en Ireport, imprimiendo anulados. "+ e.getMessage() + " " + e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			Sesion.mensajeError(fImpr, "Error general, imprimiendo anulados. "+ e.getMessage() + " " + e.getCause());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// **********************************************************************
		if (fImpr != null) {
			if (e.getSource() == fImpr.getBtnSalir()) {
				fImpr.salir();
				FIReImpresionDocumentos.close();
			} else {
				reImpresion();
			}
		}
		// *********************************************************************
		if (mRegistroOperacionDiaria != null) {
			if (e.getSource() == mRegistroOperacionDiaria.getSalir()) {
				FIRegistroOperacionDiaria.close();
				mRegistroOperacionDiaria.salir();
			} else if (e.getSource() == mRegistroOperacionDiaria.getProcesar()
					|| e.getSource() == mRegistroOperacionDiaria
							.getTxtPeriodo()) {
				procesar();
			} else if (e.getSource() == mRegistroOperacionDiaria
					.getCerrarPeriodo()) {
				if (Sesion.mensajeConfirmacion(mRegistroOperacionDiaria,
						"Seguro que desea cerrar el periodo trabajado") == 0) {
					cerrarPeriodo();
				}
			} else if (e.getSource() == mRegistroOperacionDiaria
					.getExportarTXT()) {
				exportar();
			} else if (e.getSource() == mRegistroOperacionDiaria.getCancelar()) {
				cancelar();
			}
		}
		if(fiRegMov!=null){
			if (e.getSource() == fiRegMov.getBtnSalir()) {
				fiRegMov.dispose();
				FIRegistrarMovimiento.close();
			}
		}
		// *********************************************************************
		// MANTENIMIENTO ASOCIACION DOCUMENTO CLIENTE
		// *********************************************************************
		if (fiMADC != null) {
			if (e.getSource() == fiMADC.getBtnNuevo()) {
				nuevoMADC();
			}
			if (e.getSource() == fiMADC.getBtnModificar()) {
				modificarMADC();
			}
			if (e.getSource() == fiMADC.getBtnGuardar()) {
				guardarMADC();
			}
			if (e.getSource() == fiMADC.getBtnCancelar()) {
				cancelarMADC();
			}
			if (e.getSource() == fiMADC.getBtnSalir()) {
				fiMADC.dispose();
			}
			if (e.getSource() == fiMADC.getTxtBCodigo()) {
				buscarPorCodigoMADC();
			}
		}
		// *********************************************************************
		// MANTENIMIENTO ASOCIACION DOCUMENTO TRANSACCION
		// *********************************************************************
		if (fiMADT != null) {
			if (e.getSource() == fiMADT.getBtnNuevo()) {
				nuevoMADT();
			}
			if (e.getSource() == fiMADT.getBtnModificar()) {
				modificarMADT();
			}
			if (e.getSource() == fiMADT.getBtnGuardar()) {
				guardarMADT();
			}
			if (e.getSource() == fiMADT.getBtnCancelar()) {
				cancelarMADT();
			}
			if (e.getSource() == fiMADT.getBtnSalir()) {
				fiMADT.dispose();
			}
			if (e.getSource() == fiMADT.getTxtBCodigo()) {
				buscarPorCodigoMADT();
			}
		}
		// *********************************************************************
		// MANTENIMIENTO ASOCIACION PRESENTACION COMERCIAL
		// *********************************************************************
		if (fiMAPC != null) {
			if (e.getSource() == fiMAPC.getTxtValor1()
					|| e.getSource() == fiMAPC.getTxtValor2()
					|| e.getSource() == fiMAPC.getTxtValor3()) {

				if (!fiMAPC.getValor1().equals("")
						&& !fiMAPC.getValor2().equals("")
						&& !fiMAPC.getValor3().equals("")) {
					obtenerEquivalencia();
				} else {
					Sesion.mensajeError(
							fiMAPC,
							"los campos Valor 1,Valor 2 y Valor 3 son obligatorios \npara obtener el código de presentación comercial");
				}
			}

			if (e.getSource() == fiMAPC.getBtnNuevo()) {
				nuevoMAPC();
			}
			if (e.getSource() == fiMAPC.getBtnModificar()) {
				modificarMAPC();
			}
			if (e.getSource() == fiMAPC.getBtnGuardar()) {
				guardarMAPC();
			}
			if (e.getSource() == fiMAPC.getBtnCancelar()) {
				cancelarMAPC();
			}
			if (e.getSource() == fiMAPC.getBtnSalir()) {
				fiMAPC.dispose();
			}
			if (e.getSource() == fiMAPC.getTxtBCodigo()) {
				buscarPorCodigoMAPC();
			}
		}
		// *********************************************************************
		// MANTENIMIENTO ESTABLECIMIENTO COMERCIAL
		// *********************************************************************
		if (fiMEC != null) {
			if (e.getSource() == fiMEC.getBtnNuevo()) {
				nuevoMEC();
			}
			if (e.getSource() == fiMEC.getBtnModificar()) {
				modificarMEC();
			}
			if (e.getSource() == fiMEC.getBtnGuardar()) {
				guardarMEC();
			}
			if (e.getSource() == fiMEC.getBtnCancelar()) {
				cancelarMEC();
			}
			if (e.getSource() == fiMEC.getBtnSalir()) {
				fiMEC.dispose();
			}
			if (e.getSource() == fiMEC.getTxtBCodigo()) {
				buscarPorCodigoMEC();
			}
		}
		// *********************************************************************
		// MANTENIMIENTO REGISTRO PRESENTACION COMERCIAL
		// *********************************************************************
		if (fiMRPC != null) {
			if (e.getSource() == fiMRPC.getBtnNuevo()) {
				nuevoMRPC();
			}
			if (e.getSource() == fiMRPC.getBtnModificar()) {
				modificarMRPC();
			}
			if (e.getSource() == fiMRPC.getBtnGuardar()) {
				guardarMRPC();
			}
			if (e.getSource() == fiMRPC.getBtnCancelar()) {
				cancelarMRPC();
			}
			if (e.getSource() == fiMRPC.getBtnSalir()) {
				fiMRPC.dispose();
			}
			if (e.getSource() == fiMRPC.getTxtBCodigo()) {
				buscarPorCodigoMRPC();
			}
		}
		// *********************************************************************
		// MANTENIMIENTO TIPO DE TRANSACCION
		// *********************************************************************
		if (fiMTT != null) {
			if (e.getSource() == fiMTT.getBtnNuevo()) {
				nuevoMTT();
			}
			if (e.getSource() == fiMTT.getBtnModificar()) {
				modificarMTT();
			}
			if (e.getSource() == fiMTT.getBtnGuardar()) {
				guardarMTT();
			}
			if (e.getSource() == fiMTT.getBtnCancelar()) {
				cancelarMTT();
			}
			if (e.getSource() == fiMTT.getBtnSalir()) {
				fiMTT.dispose();
			}
			if (e.getSource() == fiMTT.getTxtBCodigo()) {
				buscarPorCodigoMTT();
			}
		}
		// *********************************************************************
		// MANTENIMIENTO REGISTRO DE OPERACIONES
		// *********************************************************************
		if (fiMRO != null) {
			if (e.getSource() == fiMRO.getBtnNuevo()) {
				nuevoMRO();
			}
			if (e.getSource() == fiMRO.getBtnModificar()) {
				modificarMRO();
			}
			if (e.getSource() == fiMRO.getBtnGuardar()) {
				guardarMRO();
			}
			if (e.getSource() == fiMRO.getBtnCancelar()) {
				cancelarMRO();
			}
			if (e.getSource() == fiMRO.getBtnSalir()) {
				fiMRO.dispose();
			}
			if (e.getSource() == fiMRO.getTxtBCodigo()) {
				buscarPorCodigoMRO();
			}
		}
		// *********************************************************************
		// *********************************************************************
		// REPORTRE LIBRO KARDEX
		// *********************************************************************
		if (mReporteLibroKardex != null) {
			if (e.getSource() == mReporteLibroKardex.getSalir()) {
				FIReporteLibroKardex.close();
				mReporteLibroKardex.salir();
			} else if (e.getSource() == mReporteLibroKardex.getProcesar()) {
				procesarLibroKarex();
			} 
		}
		
		if (mReporteLibroDiario != null) {
			if (e.getSource() == mReporteLibroDiario.getSalir()) {
				FIReporteLibroDiario.close();
				mReporteLibroDiario.salir();
			} else if (e.getSource() == mReporteLibroDiario.getProcesar()) {
				procesarLibroDiario();
			} 
		}
		
		if (mReporteLibroMayor != null) {
			if (e.getSource() == mReporteLibroMayor.getSalir()) {
				FIReporteLibroMayor.close();
				mReporteLibroMayor.salir();
			} else if (e.getSource() == mReporteLibroMayor.getProcesar()) {
				procesarLibroMayor();
			} 
		}
	}

	private void procesarLibroMayor() {
		String ejer = mReporteLibroMayor.getEjercicio();
		if(ejer.isEmpty()){
		int ejercicio = Integer.parseInt(ejer);			
		File file = new File("D:/contabilidad/LibroMayor.txt");
		PrintWriter pw;
		try {
			pw = new PrintWriter(file);
			String libro="MPERIODO|MNUMSIOPE|MNUMCTACON|MFECOPE|MGLOSA|MDEBE|MHABER|MESTOPE|MCENCOS|MINTKARDEX|MINTVTACOM|MINTREG|";
			pw.println(libro);
			PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(""
					+ "select  asaeje||asaper||'00'||'|'||asacve||asaseq||'|'||trim(asacta)||'|'||  "
					+ "substr(asafem,7,2)||'/'||substr(asafem,5,2)||'/'||substr(asafem,1,4)||'|'||trim(asadde)||'|'||  "
					+ "case when asacoa='C' then asaimn || ''   when asacoa='A' then '0.00' end ||'|'||  "
					+ "case when asacoa='C'  then '0.00'  when asacoa='A' then asaimn || ''  "
					+ "end ||'|'||  '1'||'|'||  trim(asacos) ||'|' ,  "
					+ "case    when substring(asacve,1,2)='RV'  then   "
					+ "     case when (select mqtval from prodtecni.mqtkar where mqtcve=asaeje||asaper||trim(asare2)||trim(asare1)  fetch first 1 rows only) is null then '|' "
					+ "  else  (select mqtval from prodtecni.mqtkar where mqtcve=asaeje||asaper||trim(asare2)||trim(asare1)  fetch first 1 rows only) || '|' end "
					+ "  when substring(asacve,1,2)='IN' then substring(asare4,1,2)||substring(asadde,1,3)||right(trim(asare4),6) || '|' "
					+ "  when substring(asacve,1,2) not in ('RV','IN') then '|' end , "
					+ " case when substring(asacve,1,2) in ('RV','RC') then asacve||asaseq else '' end ||'|', "
					+ "asacve||asaseq ||'|'   "
					+ "from speed400tc.tasad where asaeje =?");
			pstm.setInt(1, ejercicio);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				pw.println(rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4));
			}
			rs.close();
			pstm.close();
			pw.flush();
			pw.close();
			JOptionPane.showMessageDialog(null, "El proceso ha terminado con exito, verifique la siguiente ruta\nD:\\contabilidad\\LibroMayor.txt", "Mensaje de Información",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		}else{
			Sesion.mensajeInformacion(mReporteLibroMayor, "Ingrese ejercicio");
		}
		
	}

	private void procesarLibroDiario() {
		String ejer = mReporteLibroDiario.getEjercicio();
		if(ejer.isEmpty()){
		int ejercicio = Integer.parseInt(ejer);	
		File file = new File("D:/contabilidad/LibroDiario.txt");
		PrintWriter pw;
		try {
			pw = new PrintWriter(file);
			String libro="DPERIODO|DNUMSIOPE|DCODCUE|DNUMCTACON|DFECOPE|DGLOSA|DDEBE|DHABER|DESTOPE|DCENCOS|DINTKARDEX|DINTVTACOM|DINTREG|";
			pw.println(libro);
			PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(""
					+ "select  asaeje||asaper||'00'||'|'||asacve||asaseq||'|'||'01'||'|'||trim(asacta)||'|'||  "
					+ "substr(asafem,7,2)||'/'||substr(asafem,5,2)||'/'||substr(asafem,1,4)||'|'||trim(asadde)||'|'||  "
					+ "case when asacoa='C' then asaimn || ''   when asacoa='A' then '0.00' end ||'|'||  "
					+ "case when asacoa='C'  then '0.00'  when asacoa='A' then asaimn || ''  "
					+ "end ||'|'||  '1'||'|'||  trim(asacos) ||'|' ,  "
					+ "case    when substring(asacve,1,2)='RV'  then   "
					+ "     case when (select mqtval from prodtecni.mqtkar where mqtcve=asaeje||asaper||trim(asare2)||trim(asare1)  fetch first 1 rows only) is null then '|' "
					+ "  else  (select mqtval from prodtecni.mqtkar where mqtcve=asaeje||asaper||trim(asare2)||trim(asare1)  fetch first 1 rows only) || '|' end "
					+ "  when substring(asacve,1,2)='IN' then substring(asare4,1,2)||substring(asadde,1,3)||right(trim(asare4),6) || '|' "
					+ "  when substring(asacve,1,2) not in ('RV','IN') then '|' end , "
					+ " case when substring(asacve,1,2) in ('RV','RC') then asacve||asaseq else '' end ||'|', "
					+ "asacve||asaseq ||'|'   "
					+ "from speed400tc.tasad where asaeje =?");
			pstm.setInt(1, ejercicio);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				pw.println(rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4));
			}
			rs.close();
			pstm.close();
			
			pw.flush();
			pw.close();
			JOptionPane.showMessageDialog(null, "El proceso ha terminado con exito, verifique la siguiente ruta\nD:\\contabilidad\\LibroDiario.txt", "Mensaje de Información",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		}else{
			Sesion.mensajeInformacion(mReporteLibroDiario, "Ingrese ejercicio");
		}
		
	}

	// MANTENIMIENTO ASOCIACION DOCUMENTO CLIENTE
	// ****************************************************************************************************
	private void buscarPorCodigoMADC() {
		try {
			String situacion;
			if (fiMADC.getBActivo().equals("Todos")) {
				situacion = "01,99";
			} else {
				situacion = fiMADC.getBActivo();
			}
			fiMADC.cargarTabla(servicioTASDC.buscarPorSituacion(
					fiMADC.getBCodigo(), situacion));
		} catch (SQLException e) {
			Sesion.mensajeError(fiMADC, "Error inesperado, " + e.getMessage()
					+ " " + e.getErrorCode() + " " + e.getCause());
		}
	}

	private void nuevoMADC() {
		fiMADC.desactivaControles();
		fiMADC.limpiarDatos();
		fiMADC.setOperacion(0);
	}

	private void modificarMADC() {
		if (fiMADC.getTabla().getSelectedRow() > -1) {
			fiMADC.desactivaControles();
			fiMADC.setOperacion(1);
		} else {
			Sesion.mensajeError(fiMADC, "Seleccione un registro de la tabla.");
		}
	}

	private void cancelarMADC() {
		fiMADC.activaControles();
		fiMADC.limpiarDatos();
	}

	private void guardarMADC() {
		String msjError = "";
		if (fiMADC.getCodigo().equals("")) {
			msjError += "-Ingrese campo codigo";
		}
		if (fiMADC.getDocumento().equals("")) {
			msjError += "\n-Ingrese campo documento";
		}
		if (fiMADC.getAbreviado().equals("")) {
			msjError += "\n-Ingrese campo abreviado";
		}
		if (msjError.equals("")) {
			TASDC tasdc = new TASDC();
			tasdc.setCodigo(fiMADC.getCodigo());
			tasdc.setDocumento(fiMADC.getDocumento());
			tasdc.setAbreviado(fiMADC.getAbreviado());
			if (fiMADC.getDActivo()) {
				tasdc.setSituacion("01");
			} else {
				tasdc.setSituacion("99");
			}
			tasdc.setUsu_crea(Sesion.usuario);
			tasdc.setUsu_mod(Sesion.usuario);
			tasdc.setFecha_crea(Sesion.fechaActualNumerica());
			tasdc.setFecha_mod(Sesion.fechaActualNumerica());
			try {
				List<TASDC> listado = servicioTASDC.buscarPorCodigo(tasdc
						.getCodigo(),tasdc.getAbreviado());
				switch (fiMADC.getOperacion()) {
				case 0:
					if (listado.size() > 0) {
						Sesion.mensajeInformacion(fiMADC, "Ya existe "
								+ listado.size()
								+ " registro(s) con ese codigo");
					} else {
						int resultado = servicioTASDC.insertar(tasdc);
						if (resultado > 0) {
							fiMADC.cargarTabla(servicioTASDC.listar());
							Sesion.mensajeInformacion(fiMADC,
									"registro exitoso");
						}
					}
					break;
				case 1:
					tasdc.setId(fiMADC.getId());
					if (listado.size() > 0) {
						if (tasdc.getId() == listado.get(0).getId()) {
							int resultado = servicioTASDC.modificar(tasdc);
							if (resultado > 0) {
								fiMADC.cargarTabla(tasdc);
								Sesion.mensajeInformacion(fiMADC,
										"Modificación exitosa");
							}

						} else {
							Sesion.mensajeInformacion(fiMADC, "Ya existe "
									+ listado.size()
									+ " registro(s) con ese codigo");
						}
					} else {
						int resultado = servicioTASDC.modificar(tasdc);
						if (resultado > 0) {
							fiMADC.cargarTabla(tasdc);
							Sesion.mensajeInformacion(fiMADC,
									"Modificación exitosa");
						}
					}
					break;
				}
			} catch (SQLException e) {
				Sesion.mensajeError(
						fiMADC,
						"Error inesperado, " + e.getMessage() + " "
								+ e.getErrorCode() + " " + e.getCause());
				fiMADC.activaControles();
				fiMADC.limpiarDatos();
			}
			fiMADC.activaControles();
			fiMADC.limpiarDatos();
		} else {
			Sesion.mensajeError(fiMADC, msjError);
		}
	}

	// MANTENIMIENTO ASOCIACION DOCUMENTO TRANSACCION
	// ****************************************************************************************************
	private void buscarPorCodigoMADT() {
		try {
			String situacion;
			if (fiMADT.getBActivo().equals("Todos")) {
				situacion = "01,99";
			} else {
				situacion = fiMADT.getBActivo();
			}
			fiMADT.cargarTabla(servicioTASDT.buscarPorCodigo(
					fiMADT.getBCodigo(), situacion));
		} catch (SQLException e) {
			Sesion.mensajeError(fiMADT, "Error inesperado, " + e.getMessage()
					+ " " + e.getErrorCode() + " " + e.getCause());
		}
	}

	private void nuevoMADT() {
		fiMADT.desactivaControles();
		fiMADT.limpiarDatos();
		fiMADT.setOperacion(0);
	}

	private void modificarMADT() {
		if (fiMADT.getTabla().getSelectedRow() > -1) {
			fiMADT.desactivaControles();
			fiMADT.setOperacion(1);
		} else {
			Sesion.mensajeError(fiMADT, "Seleccione un registro de la tabla.");
		}
	}

	private void cancelarMADT() {
		fiMADT.activaControles();
		fiMADT.limpiarDatos();
	}

	private void guardarMADT() {
		String msjError = "";
		if (fiMADT.getCodigo().equals("")) {
			msjError += "-Ingrese campo codigo";
		}
		if (fiMADT.getDocumento().equals("")) {
			msjError += "\n-Ingrese campo documento";
		}
		if (fiMADT.getAbreviado().equals("")) {
			msjError += "\n-Ingrese campo abreviado";
		}
		if (fiMADT.getMovimiento().equals("")) {
			msjError += "\n-Ingrese campo movimiento";
		}
		if (fiMADT.getTipoMovimiento().equals("")) {
			msjError += "\n-Ingrese campo tipo de movimiento";
		}
		if (msjError.equals("")) {
			TASDT tasdt = new TASDT();
			tasdt.setCodigo(fiMADT.getCodigo());
			tasdt.setDocumento(fiMADT.getDocumento());
			tasdt.setAbreviado(fiMADT.getAbreviado());
			tasdt.setMovimiento(fiMADT.getMovimiento());
			tasdt.setTipomovimiento(fiMADT.getTipoMovimiento());
			if (fiMADT.getDActivo()) {
				tasdt.setSituacion("01");
			} else {
				tasdt.setSituacion("99");
			}
			tasdt.setUsu_crea(Sesion.usuario);
			tasdt.setUsu_mod(Sesion.usuario);
			tasdt.setFecha_crea(Sesion.fechaActualNumerica());
			tasdt.setFecha_mod(Sesion.fechaActualNumerica());
			try {
				List<TASDT> listado = servicioTASDT.buscarPorCodigo(
						tasdt.getCodigo(), tasdt.getAbreviado(),
						tasdt.getMovimiento(), tasdt.getTipomovimiento());
				switch (fiMADT.getOperacion()) {
				case 0:
					if (listado.size() > 0) {
						Sesion.mensajeInformacion(fiMADT, "Ya existe "
								+ listado.size()
								+ " registro(s) con ese codigo");
					} else {
						int resultado = servicioTASDT.insertar(tasdt);
						if (resultado > 0) {
							fiMADT.cargarTabla(servicioTASDT.listar());
							Sesion.mensajeInformacion(fiMADT,
									"registro exitoso");
						}
					}
					break;
				case 1:
					tasdt.setId(fiMADT.getId());
					if (listado.size() > 0) {
						if (tasdt.getId() == listado.get(0).getId()) {
							int resultado = servicioTASDT.modificar(tasdt);
							if (resultado > 0) {
								fiMADT.cargarTabla(tasdt);
								Sesion.mensajeInformacion(fiMADT,
										"Modificación exitosa");
							}

						} else {
							Sesion.mensajeInformacion(fiMADT, "Ya existe "
									+ listado.size()
									+ " registro(s) con ese codigo");
						}
					} else {
						int resultado = servicioTASDT.modificar(tasdt);
						if (resultado > 0) {
							fiMADT.cargarTabla(tasdt);
							Sesion.mensajeInformacion(fiMADT,
									"Modificación exitosa");
						}
					}
					break;
				}
			} catch (SQLException e) {
				Sesion.mensajeError(
						fiMADT,
						"Error inesperado, " + e.getMessage() + " "
								+ e.getErrorCode() + " " + e.getCause());
				fiMADT.activaControles();
				fiMADT.limpiarDatos();
			}
			fiMADT.activaControles();
			fiMADT.limpiarDatos();
		} else {
			Sesion.mensajeError(fiMADT, msjError);
		}
	}

	// ****************************************************************************************************

	// MANTENIMIENTO ASOCIACION PRESENTACION COMERCIAL
	// ****************************************************************************************************

	private void obtenerEquivalencia() {
		try {
			TREPCO trepco = servicioTREPCO.obtenerEquivalencia(
					fiMAPC.getValor1(), fiMAPC.getValor2(), fiMAPC.getValor3());
			if (trepco != null) {
				fiMAPC.setCodigo(trepco.getCodigo());
			} else {
				fiMAPC.setCodigo("");
			}
			/*
			 * else{ Sesion.mensajeError(fiMAPC,
			 * "No se encontro código de presentación comercial para los valores ingresados"
			 * ); }
			 */
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void buscarPorCodigoMAPC() {
		try {
			String situacion;
			if (fiMAPC.getBActivo().equals("Todos")) {
				situacion = "01,99";
			} else {
				situacion = fiMAPC.getBActivo();
			}
			fiMAPC.cargarTabla(servicioTARTCON.buscarPorCodigo(
					fiMAPC.getBCodigo(), situacion));
		} catch (SQLException e) {
			Sesion.mensajeError(fiMAPC, "Error inesperado, " + e.getMessage()
					+ " " + e.getErrorCode() + " " + e.getCause());
		}
	}

	private void nuevoMAPC() {
		fiMAPC.desactivaControles();
		fiMAPC.limpiarDatos();
		fiMAPC.setOperacion(0);
	}

	private void modificarMAPC() {
		if (fiMAPC.getTabla().getSelectedRow() > -1) {
			fiMAPC.desactivaControles();
			fiMAPC.setOperacion(1);
		} else {
			Sesion.mensajeError(fiMAPC, "Seleccione un registro de la tabla.");
		}
	}

	private void cancelarMAPC() {
		fiMAPC.activaControles();
		fiMAPC.limpiarDatos();
	}

	private void guardarMAPC() {
		String msjError = "";
		if (fiMAPC.getDTipo().equals("")) {
			msjError += "-Ingrese campo tipo";
		}
		if (fiMAPC.getDArticulo().equals("")) {
			msjError += "\n-Ingrese campo articulo";
		}
		if (msjError.equals("")) {
			TARTCON tartcon = new TARTCON();
			tartcon.setCodigo(fiMAPC.getDCodigo());
			tartcon.setArticulo(fiMAPC.getDArticulo());
			tartcon.setTipo(fiMAPC.getDTipo());
			tartcon.setValor1(fiMAPC.getValor1());
			tartcon.setValor2(fiMAPC.getValor2());
			tartcon.setValor3(fiMAPC.getValor3());
			if (fiMAPC.getDActivo()) {
				tartcon.setSituacion("01");
			} else {
				tartcon.setSituacion("99");
			}
			tartcon.setUsu_crea(Sesion.usuario);
			tartcon.setUsu_mod(Sesion.usuario);
			tartcon.setFecha_crea(Sesion.fechaActualNumerica());
			tartcon.setFecha_mod(Sesion.fechaActualNumerica());
			try {
				List<TARTCON> listado = servicioTARTCON.buscarPorCodigo(
						tartcon.getCodigo(), tartcon.getArticulo(), "");
				switch (fiMAPC.getOperacion()) {
				case 0:
					if (listado.size() > 0) {
						Sesion.mensajeInformacion(fiMAPC,
								"el articulo ya tiene un codigo");
					} else {
						int resultado = servicioTARTCON.insertar(tartcon);
						if (resultado > 0) {
							fiMAPC.cargarTabla(servicioTARTCON.listar());
							Sesion.mensajeInformacion(fiMADC,
									"registro exitoso");
						}
					}
					break;
				case 1:
					tartcon.setId(fiMAPC.getId());
					if (listado.size() > 0) {
						if (tartcon.getId() == listado.get(0).getId()) {
							int resultado = servicioTARTCON.modificar(tartcon);
							if (resultado > 0) {
								fiMAPC.cargarTabla(tartcon);
								Sesion.mensajeInformacion(fiMAPC,
										"Modificación exitosa");
							}

						} else {
							Sesion.mensajeInformacion(fiMAPC,
									"el articulo ya tiene un codigo");
						}
					} else {
						int resultado = servicioTARTCON.modificar(tartcon);
						if (resultado > 0) {
							fiMAPC.cargarTabla(tartcon);
							Sesion.mensajeInformacion(fiMAPC,
									"Modificación exitosa");
						}
					}
					break;
				}
			} catch (SQLException e) {
				Sesion.mensajeError(
						fiMAPC,
						"Error inesperado, " + e.getMessage() + " "
								+ e.getErrorCode() + " " + e.getCause());
				fiMAPC.activaControles();
				fiMAPC.limpiarDatos();
			}
			fiMAPC.activaControles();
			fiMAPC.limpiarDatos();
		} else {
			Sesion.mensajeError(fiMAPC, msjError);
		}
	}

	// ****************************************************************************************************

	// MANTENIMIENTO REGISTRO PRESENTACION COMERCIAL
	// ****************************************************************************************************
	private void buscarPorCodigoMRPC() {
		try {
			String situacion;
			if (fiMRPC.getBActivo().equals("Todos")) {
				situacion = "01,99";
			} else {
				situacion = fiMRPC.getBActivo();
			}
			fiMRPC.cargarTabla(servicioTREPCO.buscarPorCodigo(
					fiMRPC.getBCodigo(), situacion));
		} catch (SQLException e) {
			Sesion.mensajeError(fiMRPC, "Error inesperado, " + e.getMessage()
					+ " " + e.getErrorCode() + " " + e.getCause());
		}
	}

	private void nuevoMRPC() {
		fiMRPC.desactivaControles();
		fiMRPC.limpiarDatos();
		fiMRPC.setOperacion(0);
	}

	private void modificarMRPC() {
		if (fiMRPC.getTabla().getSelectedRow() > -1) {
			fiMRPC.desactivaControles();
			fiMRPC.setOperacion(1);
		} else {
			Sesion.mensajeError(fiMRPC, "Seleccione un registro de la tabla.");
		}
	}

	private void cancelarMRPC() {
		fiMRPC.activaControles();
		fiMRPC.limpiarDatos();
	}

	private void guardarMRPC() {
		String msjError = "";
		if (fiMRPC.getCodigo().equals("")) {
			msjError += "-Ingrese campo codigo";
		}
		if (fiMRPC.getValor1().equals("")) {
			msjError += "\n-Ingrese campo valor 1";
		}
		if (fiMRPC.getValor2().equals("")) {
			msjError += "\n-Ingrese campo valor 2";
		}
		if (fiMRPC.getValor3().equals("")) {
			msjError += "\n-Ingrese campo valor 3";
		}
		if (msjError.equals("")) {
			TREPCO trepco = new TREPCO();
			trepco.setCodigo(fiMRPC.getCodigo());
			trepco.setValor1(fiMRPC.getValor1());
			trepco.setValor2(fiMRPC.getValor2());
			trepco.setValor3(fiMRPC.getValor3());
			if (fiMRPC.getDActivo()) {
				trepco.setSituacion("01");
			} else {
				trepco.setSituacion("99");
			}
			trepco.setUsu_crea(Sesion.usuario);
			trepco.setUsu_mod(Sesion.usuario);
			trepco.setFecha_crea(Sesion.fechaActualNumerica());
			trepco.setFecha_mod(Sesion.fechaActualNumerica());
			try {
				List<TREPCO> listado = servicioTREPCO.buscarPorCodigo(trepco
						.getCodigo());
				switch (fiMRPC.getOperacion()) {
				case 0:
					if (listado.size() > 0) {
						Sesion.mensajeInformacion(fiMRPC, "Ya existe "
								+ listado.size()
								+ " registro(s) con ese codigo");
					} else {
						int resultado = servicioTREPCO.insertar(trepco);
						if (resultado > 0) {
							fiMRPC.cargarTabla(servicioTREPCO.listar());
							Sesion.mensajeInformacion(fiMRPC,
									"registro exitoso");
						}
					}
					break;
				case 1:
					trepco.setId(fiMRPC.getId());
					if (listado.size() > 0) {
						if (trepco.getId() == listado.get(0).getId()) {
							int resultado = servicioTREPCO.modificar(trepco);
							if (resultado > 0) {
								fiMRPC.cargarTabla(trepco);
								Sesion.mensajeInformacion(fiMRPC,
										"Modificación exitosa");
							}

						} else {
							Sesion.mensajeInformacion(fiMRPC, "Ya existe "
									+ listado.size()
									+ " registro(s) con ese codigo");
						}
					} else {
						int resultado = servicioTREPCO.modificar(trepco);
						if (resultado > 0) {
							fiMRPC.cargarTabla(trepco);
							Sesion.mensajeInformacion(fiMRPC,
									"Modificación exitosa");
						}
					}
					break;
				}
			} catch (SQLException e) {
				Sesion.mensajeError(
						fiMRPC,
						"Error inesperado, " + e.getMessage() + " "
								+ e.getErrorCode() + " " + e.getCause());
				fiMRPC.activaControles();
				fiMRPC.limpiarDatos();
			}
			fiMRPC.activaControles();
			fiMRPC.limpiarDatos();
		} else {
			Sesion.mensajeError(fiMRPC, msjError);
		}
	}

	// ****************************************************************************************************

	// MANTENIMIENTO TIPO DE TRANSACCION
	// ****************************************************************************************************
	private void buscarPorCodigoMTT() {
		try {
			String situacion;
			if (fiMTT.getBActivo().equals("Todos")) {
				situacion = "01,99";
			} else {
				situacion = fiMTT.getBActivo();
			}
			fiMTT.cargarTabla(servicioTTIPTRAN.buscarPorCodigo(
					fiMTT.getBCodigo(), situacion));
		} catch (SQLException e) {
			Sesion.mensajeError(fiMTT, "Error inesperado, " + e.getMessage()
					+ " " + e.getErrorCode() + " " + e.getCause());
		}
	}

	private void nuevoMTT() {
		fiMTT.desactivaControles();
		fiMTT.limpiarDatos();
		fiMTT.setOperacion(0);
	}

	private void modificarMTT() {
		if (fiMTT.getTabla().getSelectedRow() > -1) {
			fiMTT.desactivaControles();
			fiMTT.setOperacion(1);
		} else {
			Sesion.mensajeError(fiMTT, "Seleccione un registro de la tabla.");
		}
	}

	private void cancelarMTT() {
		fiMTT.activaControles();
		fiMTT.limpiarDatos();
	}

	private void guardarMTT() {
		String msjError = "";
		if (fiMTT.getCodigo().equals("")) {
			msjError += "-Ingrese campo codigo";
		}
		if (fiMTT.getDOperacion() == null) {
			msjError += "\n-Ingrese campo operacion";
		}
		if (fiMTT.getTransaccion().equals("")) {
			msjError += "\n-Ingrese campo transaccion";
		}
		if (fiMTT.getTipoMovimiento().equals("")) {
			msjError += "\n-Ingrese campo tipo de movimiento";
		}
		if (msjError.equals("")) {
			TTIPTRAN ttiptran = new TTIPTRAN();
			ttiptran.setCodigo(fiMTT.getCodigo());
			ttiptran.setOperacion(fiMTT.getDOperacion().getDescripcion());
			ttiptran.setMovimiento(fiMTT.getDOperacion().getCodigo());
			ttiptran.setTransaccion(fiMTT.getTransaccion());
			ttiptran.setTipomovimiento(fiMTT.getTipoMovimiento());
			if (fiMTT.getDActivo()) {
				ttiptran.setSituacion("01");
			} else {
				ttiptran.setSituacion("99");
			}
			ttiptran.setUsu_crea(Sesion.usuario);
			ttiptran.setUsu_mod(Sesion.usuario);
			ttiptran.setFecha_crea(Sesion.fechaActualNumerica());
			ttiptran.setFecha_mod(Sesion.fechaActualNumerica());
			try {
				List<TTIPTRAN> listado = servicioTTIPTRAN
						.buscarPorCodigo(ttiptran.getCodigo(),ttiptran.getMovimiento(),ttiptran.getTipomovimiento());
				switch (fiMTT.getOperacion()) {
				case 0:
					if (listado.size() > 0) {
						Sesion.mensajeInformacion(fiMTT,
								"Ya existe " + listado.size()
										+ " registro(s) con ese codigo");
					} else {
						int resultado = servicioTTIPTRAN.insertar(ttiptran);
						if (resultado > 0) {
							fiMTT.cargarTabla(servicioTTIPTRAN.listar());
							Sesion.mensajeInformacion(fiMTT, "registro exitoso");
						}
					}
					break;
				case 1:
					ttiptran.setId(fiMTT.getId());
					if (listado.size() > 0) {
						if (ttiptran.getId() == listado.get(0).getId()) {
							int resultado = servicioTTIPTRAN
									.modificar(ttiptran);
							if (resultado > 0) {
								fiMTT.cargarTabla(ttiptran);
								Sesion.mensajeInformacion(fiMTT,
										"Modificación exitosa");
							}

						} else {
							Sesion.mensajeInformacion(fiMTT, "Ya existe "
									+ listado.size()
									+ " registro(s) con ese codigo");
						}
					} else {
						int resultado = servicioTTIPTRAN.modificar(ttiptran);
						if (resultado > 0) {
							fiMTT.cargarTabla(ttiptran);
							Sesion.mensajeInformacion(fiMTT,
									"Modificación exitosa");
						}
					}
					break;
				}
			} catch (SQLException e) {
				Sesion.mensajeError(
						fiMTT,
						"Error inesperado, " + e.getMessage() + " "
								+ e.getErrorCode() + " " + e.getCause());
				fiMTT.activaControles();
				fiMTT.limpiarDatos();
			}
			fiMTT.activaControles();
			fiMTT.limpiarDatos();
		} else {
			Sesion.mensajeError(fiMTT, msjError);
		}
	}

	// ****************************************************************************************************

	// MANTENIMIENTO REGISTRO DE OPERACIONES
	// ****************************************************************************************************
	private void buscarPorCodigoMRO() {
		try {
			String situacion;
			if (fiMRO.getBActivo().equals("Todos")) {
				situacion = "01,99";
			} else {
				situacion = fiMRO.getBActivo();
			}
			fiMRO.cargarTabla(servicioTREGOP.buscarPorCodigo(
					fiMRO.getBCodigo(), situacion));
		} catch (SQLException e) {
			Sesion.mensajeError(fiMRO, "Error inesperado, " + e.getMessage()
					+ " " + e.getErrorCode() + " " + e.getCause());
		}
	}

	private void nuevoMRO() {
		fiMRO.desactivaControles();
		fiMRO.limpiarDatos();
		fiMRO.setOperacion(0);
	}

	private void modificarMRO() {
		if (fiMRO.getTabla().getSelectedRow() > -1) {
			fiMRO.desactivaControles();
			fiMRO.setOperacion(1);
		} else {
			Sesion.mensajeError(fiMRO, "Seleccione un registro de la tabla.");
		}
	}

	private void cancelarMRO() {
		fiMRO.activaControles();
		fiMRO.limpiarDatos();
	}

	private void guardarMRO() {
		String msjError = "";
		if (fiMRO.getCodigo().equals("")) {
			msjError += "-Ingrese campo codigo";
		}
		if (fiMRO.getTipo().equals("")) {
			msjError += "\n-Ingrese campo tipo";
		}
		if (fiMRO.getMovimiento().equals("")) {
			msjError += "\n-Ingrese campo movimiento";
		}
		if (msjError.equals("")) {
			TREGOP tregop = new TREGOP();
			tregop.setCodigo(fiMRO.getCodigo());
			tregop.setTipo(fiMRO.getTipo());
			tregop.setMovimiento(fiMRO.getMovimiento());
			if (fiMRO.getDActivo()) {
				tregop.setSituacion("01");
			} else {
				tregop.setSituacion("99");
			}
			tregop.setUsu_crea(Sesion.usuario);
			tregop.setUsu_mod(Sesion.usuario);
			tregop.setFecha_crea(Sesion.fechaActualNumerica());
			tregop.setFecha_mod(Sesion.fechaActualNumerica());
			try {
				List<TREGOP> listado = servicioTREGOP.buscarPorCodigo(tregop
						.getCodigo());
				switch (fiMRO.getOperacion()) {
				case 0:
					if (listado.size() > 0) {
						Sesion.mensajeInformacion(fiMRO,
								"Ya existe " + listado.size()
										+ " registro(s) con ese codigo");
					} else {
						int resultado = servicioTREGOP.insertar(tregop);
						if (resultado > 0) {
							fiMRO.cargarTabla(servicioTREGOP.listar());
							Sesion.mensajeInformacion(fiMRO, "registro exitoso");
						}
					}
					break;
				case 1:
					tregop.setId(fiMRO.getId());
					if (listado.size() > 0) {
						if (tregop.getId() == listado.get(0).getId()) {
							int resultado = servicioTREGOP.modificar(tregop);
							if (resultado > 0) {
								fiMRO.cargarTabla(tregop);
								Sesion.mensajeInformacion(fiMRO,
										"Modificación exitosa");
							}

						} else {
							Sesion.mensajeInformacion(fiMRO, "Ya existe "
									+ listado.size()
									+ " registro(s) con ese codigo");
						}
					} else {
						int resultado = servicioTREGOP.modificar(tregop);
						if (resultado > 0) {
							fiMRO.cargarTabla(tregop);
							Sesion.mensajeInformacion(fiMRO,
									"Modificación exitosa");
						}
					}
					break;
				}
			} catch (SQLException e) {
				Sesion.mensajeError(
						fiMRO,
						"Error inesperado, " + e.getMessage() + " "
								+ e.getErrorCode() + " " + e.getCause());
				fiMRO.activaControles();
				fiMRO.limpiarDatos();
			}
			fiMRO.activaControles();
			fiMRO.limpiarDatos();
		} else {
			Sesion.mensajeError(fiMRO, msjError);
		}
	}

	// ****************************************************************************************************

	// MANTENIMIENTO ESTABLECIMIENTO COMERCIAL
	// ****************************************************************************************************
	private void buscarPorCodigoMEC() {
		try {
			String situacion;
			if (fiMEC.getBActivo().equals("Todos")) {
				situacion = "01,99";
			} else {
				situacion = fiMEC.getBActivo();
			}
			fiMEC.cargarTabla(servicioTESCOM.buscarPorCodigo(
					fiMEC.getBCodigo(), situacion));
		} catch (SQLException e) {
			Sesion.mensajeError(fiMEC, "Error inesperado, " + e.getMessage()
					+ " " + e.getErrorCode() + " " + e.getCause());
		}
	}

	private void nuevoMEC() {
		fiMEC.desactivaControles();
		fiMEC.limpiarDatos();
		fiMEC.setOperacion(0);
	}

	private void modificarMEC() {
		if (fiMEC.getTabla().getSelectedRow() > -1) {
			fiMEC.desactivaControles();
			fiMEC.setOperacion(1);
		} else {
			Sesion.mensajeError(fiMEC, "Seleccione un registro de la tabla.");
		}
	}

	private void cancelarMEC() {
		fiMEC.activaControles();
		fiMEC.limpiarDatos();
	}

	private void guardarMEC() {
		String msjError = "";
		if (fiMEC.getCodigo().equals("")) {
			msjError += "-Ingrese campo codigo";
		}
		if (fiMEC.getEstablecimiento().equals("")) {
			msjError += "\n-Ingrese campo establecimiento";
		}
		if (fiMEC.getAlmacen().equals("")) {
			msjError += "\n-Ingrese campo almacen";
		}
		if (msjError.equals("")) {
			TESCOM tescom = new TESCOM();
			tescom.setCodigo(fiMEC.getCodigo());
			tescom.setEstablecimiento(fiMEC.getEstablecimiento());
			tescom.setAlmacen(fiMEC.getAlmacen());
			if (fiMEC.getDActivo()) {
				tescom.setSituacion("01");
			} else {
				tescom.setSituacion("99");
			}
			tescom.setUsu_crea(Sesion.usuario);
			tescom.setUsu_mod(Sesion.usuario);
			tescom.setFecha_crea(Sesion.fechaActualNumerica());
			tescom.setFecha_mod(Sesion.fechaActualNumerica());
			try {
				List<TESCOM> listado = servicioTESCOM.buscarPorCodigo(
						tescom.getCodigo(), tescom.getAlmacen(), "");
				switch (fiMEC.getOperacion()) {
				case 0:
					if (listado.size() > 0) {
						Sesion.mensajeInformacion(
								fiMEC,
								"Ya existe "
										+ listado.size()
										+ " registro(s) con ese codigo relacionado al almacen "
										+ tescom.getAlmacen());
					} else {
						int resultado = servicioTESCOM.insertar(tescom);
						if (resultado > 0) {
							fiMEC.cargarTabla(servicioTESCOM.listar());
							Sesion.mensajeInformacion(fiMEC, "registro exitoso");
						}
					}
					break;
				case 1:
					tescom.setId(fiMEC.getId());
					if (listado.size() > 0) {
						if (tescom.getId() == listado.get(0).getId()) {
							int resultado = servicioTESCOM.modificar(tescom);
							if (resultado > 0) {
								fiMEC.cargarTabla(tescom);
								Sesion.mensajeInformacion(fiMEC,
										"Modificación exitosa");
							}

						} else {
							Sesion.mensajeInformacion(
									fiMEC,
									"Ya existe "
											+ listado.size()
											+ " registro(s) con ese codigo relacionado al almacen "
											+ tescom.getAlmacen());
						}
					} else {
						int resultado = servicioTESCOM.modificar(tescom);
						if (resultado > 0) {
							fiMEC.cargarTabla(tescom);
							Sesion.mensajeInformacion(fiMEC,
									"Modificación exitosa");
						}
					}
					break;
				}
			} catch (SQLException e) {
				Sesion.mensajeError(
						fiMEC,
						"Error inesperado, " + e.getMessage() + " "
								+ e.getErrorCode() + " " + e.getCause());
				fiMEC.activaControles();
				fiMEC.limpiarDatos();
			}
			fiMEC.activaControles();
			fiMEC.limpiarDatos();
		} else {
			Sesion.mensajeError(fiMEC, msjError);
		}
	}
	// REPORTE LIBRO KARDEX
	//*****************************************************************************************************
	
	private void procesarLibroKarex() {
		String msjError = "";
		String ejercicio = mReporteLibroKardex.getEjercicio().trim();
		if (ejercicio.equals(""))
			msjError += "Ingrese Ejercicio \n";
		if (msjError.equals("")) {
			try {
				pw = new PrintWriter(file);
				cabecera();
				saldosIniciales(Integer.parseInt(ejercicio));
				detalleMovimientos(Integer.parseInt(ejercicio));
				saldosFinales(Integer.parseInt(ejercicio));
				pw.flush();
				pw.close();
				Sesion.mensajeInformacion(mReporteLibroKardex, "Reporte generado con exito, verifique la siguiente ruta\nD:\\contabilidad\\LibroKardex.txt");
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}else {
			Sesion.mensajeError(mRegistroOperacionDiaria, msjError);
		}
	}
	
	private void cabecera() {
		libro.setKperiodo("kperiodo");	
		libro.setKanexo("kanexo");
		libro.setKcatalogo("kcatalogo");
		libro.setKtipexist("ktipexist");
		libro.setKcodexist("kcodexist");
		libro.setKfecdoc("kfecdoc");
		libro.setKtipodoc("ktipodoc");
		libro.setKserdoc("kserdoc");
		libro.setKnumdoc("knumdoc");
		libro.setKtipope("ktipope");
		libro.setKdesexist("kdesexist");
		libro.setKunimed("kunimed");
		libro.setKmetval("kmetval");
		libro.setKuniing("kuniing");
		libro.setKcosing("kcosing");
		libro.setKtoting("ktoting");
		libro.setKuniret("kuniret");
		libro.setKcosret("cosret");
		libro.setKtotret("ktotret");
		libro.setKsalfin("ksalfin");
		libro.setKcosfin("kcosfin");
		libro.setKtotfin("ktotfin");
		libro.setKestope("kestope");
		libro.setKintdiamay("kintdiamay");
		libro.setKintvtacom("kintvtacom");
		libro.setKintreg("kintreg");
		grabar(libro);
	}
	
	private void saldosIniciales(int ejercicio) {
		try {
			PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(""
					+ "select saleje,salalm,salcod,saldic,condic,saldic*condic totales,"
					+ "(trim(tkanexo.tbalf1)||right('00'||salalm,3)) kanexo,"
					+ "(case when artfam not in ('096','998','999')  then '01' when artfam in ('096','998','999') then texist end) ktipexist,"
					+ "trim(salcod) kcodexist,"
					+ "trim(artdes) kdesexist, "
					+ "(case when tkunimed.tbalf2 is not null then trim(tkunimed.tbalf2) when tkunimed.tbalf2  is null then '' end) kunimed "
					+ "from tscal inner join tscar on saleje=sareje and salcod=sarcod "
					+ "left outer join tarti on salcod=artcod "
					+ "left outer join prodtecni.tarttex on artcoe=salcod  "
					+ "left outer join ttabd as tkanexo on tkanexo.tbiden='CBTCE' and tkanexo.tbespe=salalm "
					+ "left outer join ttabd as tkunimed on tkunimed.tbiden='UGMED' and tkunimed.tbespe=artmed  "
					+ "where saleje=?  ");
			pstm.setInt(1,ejercicio-1);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				libro.setKperiodo(ejercicio+"0100");	
				libro.setKanexo(rs.getString("kanexo"));
				libro.setKcatalogo("9");
				libro.setKtipexist(rs.getString("ktipexist"));
				libro.setKcodexist(rs.getString("kcodexist"));
				libro.setKfecdoc("01/01/"+ejercicio);
				libro.setKtipodoc("00");
				libro.setKserdoc("0");
				libro.setKnumdoc("0");
				libro.setKtipope("16");
				libro.setKdesexist(rs.getString("kdesexist"));
				libro.setKunimed(rs.getString("kunimed"));
				libro.setKmetval("1");
				libro.setKuniing(rs.getString("saldic"));
				libro.setKcosing(rs.getString("condic"));
				libro.setKtoting(rs.getString("totales").substring(0,rs.getString("totales").length()-3));
				libro.setKuniret("0.00");
				libro.setKcosret("0.00");
				libro.setKtotret("0.00");
				libro.setKsalfin("0.00");
				libro.setKcosfin("0.00");
				libro.setKtotfin("0.00");
				libro.setKestope("1");
				libro.setKintdiamay("INC0000006");
				libro.setKintvtacom("INC0000006");
				libro.setKintreg("INC0000006");
				grabar(libro);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void detalleMovimientos(int ejercicio) throws SQLException {
		int contador = 0;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select "
				+ "mdalma,trim(mdcoar) mdcoar,mdfech,mhfein,mdejer,mdcanr,mdcuna,mdtotc,mdcmov,mdtmov,mhref3,mhref2,mdperi,mdcomp, "
				+ "(mdejer||right('0'||mdperi,2)||'00') kperiodo, "
				+ "(trim(tkanexo.tbalf1)||right('00'||mdalma,3)) kanexo, "
				+ "'9' kcatalogo, "
				+ "(case when artfam not in ('096','998','999')  then '01' when artfam in ('096','998','999') then texist end) ktipexist, "
				+ "trim(mdcoar) kcodexist, "
				+ "substring(MDFECH,7,2)||'/'||substring(MDFECH,5,2)||'/'||substring(MDFECH,1,4) kfecdoc,"
				+ "(case when tktipope.tbalf1 is not null then trim(tktipope.tbalf1) when tktipope.tbalf1  is null then '' end) ktipope, "
				+ "trim(artdes) kdesexist,"
				+ "(case when tkunimed.tbalf2 is not null then trim(tkunimed.tbalf2) when tkunimed.tbalf2  is null then '' end) kunimed, "
				+ "'1' kmetval, "
				+ "(case when mhasto is not null and mhasto<>'' then mhasto when mhasto is null or mhasto='' then 'IN'||mdalma||mdejer||mdperi||mdcmov||mdtmov||mdcomp end) rastreo "
				+ "from tmovd inner join tmovh "
				+ "on mhejer=mdejer and mhperi=mdperi and mhalma=mdalma and mhcmov=mdcmov and mhtmov=mdtmov and mhcomp=mdcomp "
				+ "left outer join tarti on mdcoar=artcod  "
				+ "left outer join prodtecni.tarttex on artcoe=mdcoar  "
				+ "left outer join ttabd as tkanexo on tkanexo.tbiden='CBTCE' and tkanexo.tbespe=mdalma "
				+ "left outer join ttabd as tktipope on tktipope.tbiden='CBTTO' and tktipope.tbespe=mdcmov||mdtmov "
				+ "left outer join ttabd as tkunimed on tkunimed.tbiden='UGMED' and tkunimed.tbespe=mdumer  "
				+ "where mdejer=?   "
				+ "order by mdalma,mdcoar,mdfech,mhfein");
		pstm.setInt(1, ejercicio);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		
		String mdalma="";
		String mdcoar ="";
		try{
		while(!rs.isAfterLast()){
			mdalma=rs.getString("mdalma");
			while(mdalma.equals(rs.getString("mdalma"))){
				mdcoar = rs.getString("mdcoar");
				while(mdcoar.equals(rs.getString("mdcoar")) && mdalma.equals(rs.getString("mdalma"))){
					contador++;
					System.out.println(contador +"-" + 	rs.getString("mdalma") + rs.getString("mdcoar") + rs.getString("mdfech") + rs.getString("mhfein") );
					libro = new LibroKardex();
					libro.setKperiodo(rs.getString("kperiodo"));	
					libro.setKanexo(rs.getString("kanexo"));
					libro.setKcatalogo(rs.getString("kcatalogo"));
					libro.setKtipexist(rs.getString("ktipexist"));
					libro.setKcodexist(rs.getString("kcodexist"));
					libro.setKfecdoc(rs.getString("kfecdoc"));
					libro.setKtipodoc(ktipodoc(rs.getString("mdcmov"),rs.getString("mdtmov"),rs.getString("mhref3"),rs.getString("mhref2")));
					libro.setKserdoc(kserdoc(rs.getString("mdcmov"),rs.getString("mdtmov"),rs.getString("mhref3"),rs.getString("mhref2"),rs.getString("mdalma")+rs.getString("mdejer")+rs.getString("mdperi")+rs.getString("mdcmov")+rs.getString("mdtmov")));
					libro.setKnumdoc(knumdoc(rs.getString("mdcmov"),rs.getString("mdtmov"),rs.getString("mhref3"),rs.getString("mhref2"),rs.getInt("mdcomp")));
					libro.setKtipope(rs.getString("ktipope"));
					libro.setKdesexist(rs.getString("kdesexist"));
					libro.setKunimed(rs.getString("kunimed"));
					libro.setKmetval(rs.getString("kmetval"));
					if(rs.getString("mdcmov").equals("I")){
						libro.setKuniing(rs.getString("mdcanr")+"");
						libro.setKcosing(rs.getString("mdcuna")+"");
						libro.setKtoting(rs.getString("mdtotc")+"");
						libro.setKuniret("0.00");
						libro.setKcosret("0.00");
						libro.setKtotret("0.00");
					}else{
						libro.setKuniing("0.00");
						libro.setKcosing("0.00");
						libro.setKtoting("0.00");
						libro.setKuniret(rs.getString("mdcanr")+"");
						libro.setKcosret(rs.getString("mdcuna")+"");
						libro.setKtotret(rs.getString("mdtotc")+"");
					}
					libro.setKsalfin("0.00");
					libro.setKcosfin("0.00");
					libro.setKtotfin("0.00");
					libro.setKestope(kestope(rs.getInt("mdejer"),rs.getInt("mdperi"),rs.getInt("mdfech")));
					libro.setKintdiamay(rs.getString("rastreo"));
					libro.setKintvtacom(rs.getString("rastreo"));
					libro.setKintreg(rs.getString("rastreo"));
					grabar(libro);
					rs.next();
				}
				//System.out.println("quiebre articulo o almacen");
			}
			//System.out.println("quiebre almacen");
		}
		}catch(Exception e){
			if(e.getMessage().contains("Cursor position not valid")){
				//grabar(libro);
				System.out.println("fin de archivo");
				e.printStackTrace();
			}else{
				e.printStackTrace();
			}
		}
		rs.close();
		pstm.close();
	}
	
	private String kestope(int ejercicio, int periodo, int fecha) {
		int ejercicioD = Integer.parseInt((fecha+"").substring(0, 4));
		int periodoD = Integer.parseInt((fecha+"").substring(4, 6));
		String valor="";
		if(ejercicio==ejercicioD && periodo==periodoD){
			valor="1";
		}else{
			valor="8";
		}
		return valor;
	}
	
	private String knumdoc(String mdcmov, String mdtmov,String mhref3,String mhref2,int mdcomp) throws SQLException {
		String query = "";
		String valor="";
		if(mdcmov.equals("I") && mdtmov.equals("01")){
			query="select tolib2  from tocoh where tocnro=?";
			PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(query);
			pstm.setString(1, mhref2);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				valor=rs.getString(1);	
			}
			rs.close();
			pstm.close();
			return valor;
		}else if(mdcmov.equals("I") && mdtmov.equals("02")){
			valor=mhref3.substring(5);
			return valor;
		}else if(mdcmov.equals("I") && mdtmov.equals("03")){
			valor=mhref2.substring(5);
			return valor;
		}else if(mdcmov.equals("S") && mdtmov.equals("01")){
			valor=mhref2.substring(5);
			return valor;
		}else{
			valor=mdcomp+"";
			return valor;
		}
	}

	private String kserdoc(String mdcmov, String mdtmov,String mhref3,String mhref2,String key) {
		String valor="";
		if(mdcmov.equals("I") && mdtmov.equals("01")){
			valor="00";
			return valor;
		}else if(mdcmov.equals("I") && mdtmov.equals("02")){
			valor=mhref3.substring(2, 3);
			return valor;
		}else if(mdcmov.equals("I") && mdtmov.equals("03")){
			valor=mhref2.substring(2, 3);
			return valor;
		}else if(mdcmov.equals("S") && mdtmov.equals("01")){
			valor=mhref2.substring(2, 3);
			return valor;
		}else{
			valor=key;
			return valor;
		}
	}

	private String ktipodoc(String mdcmov, String mdtmov,String mhref3,String mhref2) throws SQLException {
		String query = "";
		String valor="";
		if(mdcmov.equals("I") && mdtmov.equals("01")){
			query="select (case when tdsuna is not null then tdsuna when tdsuna is null then '' end) from ttido where tdregi='C' and tdtipo='F3'";
		}else if(mdcmov.equals("I") && mdtmov.equals("02")){
			query="select (case when tdsuna is not null then tdsuna when tdsuna is null then '' end) from ttido where tdregi='C' and tdtipo='"+mhref3.substring(0,2)+"'";
		}else if(mdcmov.equals("I") && mdtmov.equals("03")){
			query="select (case when tdsuna is not null then tdsuna when tdsuna is null then '' end) from ttido where tdregi='V' and tdtipo='"+mhref2.substring(0,2)+"'";
		}else if(mdcmov.equals("S") && mdtmov.equals("01")){			
			query="select (case when tdsuna is not null then tdsuna when tdsuna is null then '' end) from ttido where tdregi='V' and tdtipo='"+mhref2.substring(0,2)+"'";
		}else{
			valor="00";
			return valor;
		}
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(query);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			valor=rs.getString(1);
		}
		rs.close();
		pstm.close();
		return valor;
	}
	
	private void saldosFinales(int ejercicio) {
		try {
			PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(""
					+ "select saleje,salalm,salcod,saldic,condic,round(saldic*condic,6) totales,"
					+ "(trim(tkanexo.tbalf1)||right('00'||salalm,3)) kanexo,"
					+ "(case when artfam not in ('096','998','999')  then '01' when artfam in ('096','998','999') then texist end) ktipexist,"
					+ "trim(salcod) kcodexist,"
					+ "trim(artdes) kdesexist, "
					+ "(case when tkunimed.tbalf2 is not null then trim(tkunimed.tbalf2) when tkunimed.tbalf2  is null then '' end) kunimed "
					+ "from tscal inner join tscar on saleje=sareje and salcod=sarcod "
					+ "left outer join tarti on salcod=artcod "
					+ "left outer join prodtecni.tarttex on artcoe=salcod  "
					+ "left outer join ttabd as tkanexo on tkanexo.tbiden='CBTCE' and tkanexo.tbespe=salalm "
					+ "left outer join ttabd as tkunimed on tkunimed.tbiden='UGMED' and tkunimed.tbespe=artmed  "
					+ "where saleje=? ");
			pstm.setInt(1,ejercicio);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				libro.setKperiodo(ejercicio+"1200");	
				libro.setKanexo(rs.getString("kanexo"));
				libro.setKcatalogo("9");
				libro.setKtipexist(rs.getString("ktipexist"));
				libro.setKcodexist(rs.getString("kcodexist"));
				libro.setKfecdoc("31/12/"+ejercicio);
				libro.setKtipodoc("00");
				libro.setKserdoc("0");
				libro.setKnumdoc("0");
				libro.setKtipope("99");
				libro.setKdesexist(rs.getString("kdesexist"));
				libro.setKunimed(rs.getString("kunimed"));
				libro.setKmetval("1");
				libro.setKuniing("0.00");
				libro.setKcosing("0.00");
				libro.setKtoting("0.00");
				libro.setKuniret("0.00");
				libro.setKcosret("0.00");
				libro.setKtotret("0.00");
				libro.setKsalfin(rs.getString("saldic"));
				libro.setKcosfin(rs.getString("condic"));
				libro.setKtotfin(rs.getString("totales").substring(0,rs.getString("totales").length()-3));
				libro.setKestope("1");
				libro.setKintdiamay("verificar");
				libro.setKintvtacom("verificar");
				libro.setKintreg("verificar");
				grabar(libro);
			}
			rs.close();
			pstm.close();
			Conexion.desconectar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void grabar(LibroKardex libro) {
		pw.println(libro.getKperiodo()+separador+
				libro.getKanexo()+separador+
				libro.getKcatalogo()+separador+
				libro.getKtipexist()+separador+
				libro.getKcodexist()+separador+
				libro.getKfecdoc()+separador+
				libro.getKtipodoc()+separador+
				libro.getKserdoc()+separador+
				libro.getKnumdoc()+separador+
				libro.getKtipope()+separador+
				libro.getKdesexist()+separador+
				libro.getKunimed()+separador+
				libro.getKmetval()+separador+
				libro.getKuniing()+separador+
				libro.getKcosing()+separador+
				libro.getKtoting()+separador+
				libro.getKuniret()+separador+
				libro.getKcosret()+separador+
				libro.getKtotret()+separador+
				libro.getKsalfin()+separador+
				libro.getKcosfin()+separador+
				libro.getKtotfin()+separador+
				libro.getKestope()+separador+
				libro.getKintdiamay()+separador+
				libro.getKintvtacom()+separador+
				libro.getKintreg()+separador
				);
	}

	// ****************************************************************************************************
	// REGISTRO DE OPERACIONES DIARIAS
	// ****************************************************************************************************
	private void procesar() {
		String msjError = "";
		String ejercicio = mRegistroOperacionDiaria.getEjercicio().trim();
		String periodo = mRegistroOperacionDiaria.getPeriodo().trim();
		if (ejercicio.equals(""))
			msjError += "Ingrese Ejercicio \n";
		if (periodo.equals(""))
			msjError += "Ingrese Periodo \n";
		if (periodo.trim().length() == 1)
			msjError += "-Ingrese formato correcto para el periodo.\n Por ejemplo: 01,02,03...12\n";
		if (msjError.equals("")) {
			try {
				boolean flag = false;
				String msj = "";
				TCIEPER tcieper = servicioROD
						.obtenerUltimoPeriodo("productos fiscalizados");
				if (tcieper != null) {
					int ejercicioTrabajable = Integer.parseInt(tcieper
							.getEjercicio().trim());
					int periodoTrabajable = Integer.parseInt(tcieper
							.getPeriodo().trim());
					if (periodoTrabajable == 12) {
						System.out.println("if");
						ejercicioTrabajable++;
						periodoTrabajable = 1;
					} else {
						System.out.println("else");
						periodoTrabajable++;
					}
					if (ejercicioTrabajable == Integer.parseInt(ejercicio)
							&& periodoTrabajable == Integer.parseInt(periodo)) {
						flag = true;
					} else {
						msj = "Debe cerrar el periodo " + periodoTrabajable
								+ " del ejercicio " + ejercicioTrabajable;
					}
				} else {
					// primer mes
					if (ejercicio.equals("2013") && periodo.equals("09")) {
						flag = true;
					} else {
						msj = "Debe cerrar el periodo 09 del ejercicio 2013";
					}
				}
				//flag = true;
				// **************************************************************************
				if (flag) {
					List<RegistroOperacionDiaria> listado = servicioROD.listar(
							Integer.parseInt(ejercicio),
							Integer.parseInt(periodo));
					if (listado.size() > 0) {
						mRegistroOperacionDiaria.getTxtEjercicio().setEnabled(
								false);
						mRegistroOperacionDiaria.getTxtPeriodo().setEnabled(
								false);
						mRegistroOperacionDiaria.limpiarTabla();
						mRegistroOperacionDiaria.limpiarTablaErrores();
						detalleErrores.clear();

						int contador = 0;
						for (RegistroOperacionDiaria registroOperacionDiaria : listado) {
							contador++;
							registroOperacionDiaria.setItem(contador + "");
							columna1(registroOperacionDiaria);
							columna2(registroOperacionDiaria);
							columna3(registroOperacionDiaria);
							columna4(registroOperacionDiaria);
							columna5(registroOperacionDiaria);
							columna6(registroOperacionDiaria);
							columna7(registroOperacionDiaria);
							columna8(registroOperacionDiaria);
							columna9(registroOperacionDiaria);
							columna10(registroOperacionDiaria);
							columnasVarios(registroOperacionDiaria);
							mRegistroOperacionDiaria
									.cargarTabla(registroOperacionDiaria);
						}
						if (detalleErrores.size() > 0) {
							mRegistroOperacionDiaria.cargarTablaErrores(
									detalleErrores, listado.size());
						}
					} else {
						Sesion.mensajeError(mRegistroOperacionDiaria,
								"No existen resultados para el ejercicio y periodo ingresado");
					}
				} else {
					Sesion.mensajeError(mRegistroOperacionDiaria, msj);
				}
				// **************************************************************************
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			Sesion.mensajeError(mRegistroOperacionDiaria, msjError);
		}
	}

	private void columna1(RegistroOperacionDiaria regop) {
		if (regop.getColumna1() != null) {
			if (regop.getColumna1().trim().equals("")) {
				regop.setColumna1("");
				agregarError(1, Integer.parseInt(regop.getItem()),
						"Campo obligatorio", regop);
			}
		} else {
			regop.setColumna1("");
			agregarError(1, Integer.parseInt(regop.getItem()),
					"Campo obligatorio", regop);
		}
	}

	private void columna2(RegistroOperacionDiaria regop) {
		if (regop.getColumna2() != null) {
			if (regop.getColumna2().trim().equals("")) {
				regop.setColumna2("");
				agregarError(2, Integer.parseInt(regop.getItem()),
						"Campo obligatorio", regop);
			}
		} else {
			regop.setColumna2("");
			agregarError(2, Integer.parseInt(regop.getItem()),
					"Campo obligatorio", regop);
		}
	}

	private void columna3(RegistroOperacionDiaria regop) {
		if (regop.getColumna3() != null) {
			if (regop.getColumna3().trim().equals("")) {
				regop.setColumna3("");
				agregarError(3, Integer.parseInt(regop.getItem()),
						"Campo obligatorio", regop);
			}
		} else {
			regop.setColumna3("");
			agregarError(3, Integer.parseInt(regop.getItem()),
					"Campo obligatorio", regop);
		}
	}

	private void columna4(RegistroOperacionDiaria regop) {
		if (regop.getColumna4() != null) {
			if (regop.getColumna4().trim().equals("")) {
				regop.setColumna4("");
				agregarError(4, Integer.parseInt(regop.getItem()),
						"Campo obligatorio", regop);
			}
		} else {
			regop.setColumna4("");
			agregarError(4, Integer.parseInt(regop.getItem()),
					"Campo obligatorio", regop);
		}
	}

	private void columna5(RegistroOperacionDiaria regop) {
		if (regop.getColumna5() != null) {
			if (regop.getColumna5().trim().equals("")) {
				regop.setColumna5("");
				agregarError(5, Integer.parseInt(regop.getItem()),
						"Campo obligatorio", regop);
			}
		} else {
			regop.setColumna5("");
			agregarError(5, Integer.parseInt(regop.getItem()),
					"Campo obligatorio", regop);
		}
	}

	private void columna6(RegistroOperacionDiaria regop) {
		if (regop.getColumna6() != null) {
			if (regop.getColumna6().trim().equals("")) {
				regop.setColumna6("");
				agregarError(6, Integer.parseInt(regop.getItem()),
						"Campo obligatorio", regop);
			}
		} else {
			regop.setColumna6("");
			agregarError(6, Integer.parseInt(regop.getItem()),
					"Campo obligatorio", regop);
		}
	}

	// numero de documento
	private void columna7(RegistroOperacionDiaria regop) {
		if (regop.getColumna7() != null) {
			if (regop.getColumna7().trim().equals("")) {
				regop.setColumna7("");
				agregarError(7, Integer.parseInt(regop.getItem()),
						"Campo obligatorio", regop);
			} else {// CAMBIO
				if (regop.getColumna6() != null) {
					if (!regop.getColumna6().trim().equals("")) {
						if (regop.getColumna6().trim().equalsIgnoreCase("I")) {
							regop.setColumna7(regop.getColumna7()
									.replaceAll("\\D", "").trim());
							if (regop.getColumna7().length() > 5) {
								regop.setColumna7(regop
										.getColumna7()
										.substring(
												regop.getColumna7().length() - 5,
												regop.getColumna7().length()));
							} else if (regop.getColumna7().length() < 5) {
								switch (regop.getColumna7().length()) {
								case 1:
									regop.setColumna7("0000"
											+ regop.getColumna7());
									break;
								case 2:
									regop.setColumna7("000"
											+ regop.getColumna7());
									break;
								case 3:
									regop.setColumna7("00"
											+ regop.getColumna7());
									break;
								case 4:
									regop.setColumna7("0" + regop.getColumna7());
									break;
								}
							}
							String formato = "2T2000-2013-I-"
									+ regop.getColumna7();
							regop.setColumna7(formato);
						}
					}
				}
			}
		} else {
			regop.setColumna7("");
			agregarError(7, Integer.parseInt(regop.getItem()),
					"Campo obligatorio", regop);
		}
	}

	private void columna8(RegistroOperacionDiaria regop) {
		if (regop.getColumna8() != null) {
			if (regop.getColumna8().trim().equals("")) {
				regop.setColumna8("");
				agregarError(8, Integer.parseInt(regop.getItem()),
						"Campo obligatorio", regop);
			}
		} else {
			regop.setColumna8("");
			agregarError(8, Integer.parseInt(regop.getItem()),
					"Campo obligatorio", regop);
		}
	}

	private void columna9(RegistroOperacionDiaria regop) {
		if (regop.getColumna1() != null) {
			if (regop.getColumna1().trim().equals("2")) {
				if (regop.getColumna9() != null) {
					if (regop.getColumna9().trim().equals("")) {
						regop.setColumna9("");
						agregarError(9, Integer.parseInt(regop.getItem()),
								"Campo obligatorio", regop);
					}
				} else {
					regop.setColumna9("");
					agregarError(9, Integer.parseInt(regop.getItem()),
							"Campo obligatorio", regop);
				}
			} else {
				if (regop.getColumna9() != null) {
					if (regop.getColumna9().trim().equals("")) {
						regop.setColumna9("");
					}
				} else {
					regop.setColumna9("");
				}
			}
		}
	}

	private void columna10(RegistroOperacionDiaria regop) {
		if (regop.getColumna1() != null) {
			if (regop.getColumna1().trim().equals("2")) {
				if (regop.getColumna10() != null) {
					if (regop.getColumna10().trim().equals("")) {
						regop.setColumna10("");
						agregarError(10, Integer.parseInt(regop.getItem()),
								"Campo obligatorio", regop);
					}
				} else {
					regop.setColumna10("");
					agregarError(10, Integer.parseInt(regop.getItem()),
							"Campo obligatorio", regop);
				}
			} else {
				if (regop.getColumna10() != null) {
					if (regop.getColumna10().trim().equals("")) {
						regop.setColumna10("");
					}
				} else {
					regop.setColumna10("");
				}
			}
		}
	}

	private void columnasVarios(RegistroOperacionDiaria regop) {
		if (regop.getMDALMA() != null) {
			if (regop.getMDALMA().equals("")) {
				regop.setMDALMA("");
			}
		} else {
			regop.setMDALMA("");
		}

		if (regop.getMDCMOV() != null) {
			if (regop.getMDCMOV().equals("")) {
				regop.setMDCMOV("");
			}
		} else {
			regop.setMDCMOV("");
		}

		if (regop.getMDTMOV() != null) {
			if (regop.getMDTMOV().equals("")) {
				regop.setMDTMOV("");
			}
		} else {
			regop.setMDTMOV("");
		}

		if (regop.getMDCOAR() != null) {
			if (regop.getMDCOAR().equals("")) {
				regop.setMDCOAR("");
			}
		} else {
			regop.setMDCOAR("");
		}

		if (regop.getMHREF1() != null) {
			if (regop.getMHREF1().equals("")) {
				regop.setMHREF1("");
			}
		} else {
			regop.setMHREF1("");
		}

		if (regop.getCLTIDE() != null) {
			if (regop.getCLTIDE().equals("")) {
				regop.setCLTIDE("");
			}
		} else {
			regop.setCLTIDE("");
		}

		if (regop.getCLNIDE() != null) {
			if (regop.getCLNIDE().equals("")) {
				regop.setCLNIDE("");
			}
		} else {
			regop.setCLNIDE("");
		}

		if (regop.getMHREF2() != null) {
			if (regop.getMHREF2().equals("")) {
				regop.setMHREF2("");
			}
		} else {
			regop.setMHREF2("");
		}

		if (regop.getMHCOMP() != null) {
			if (regop.getMHCOMP().equals("")) {
				regop.setMHCOMP("");
			}
		} else {
			regop.setMHCOMP("");
		}

		if (regop.getMHHRE1() != null) {
			if (regop.getMHHRE1().equals("")) {
				regop.setMHHRE1("");
			}
		} else {
			regop.setMHHRE1("");
		}
	}

	private void agregarError(int col, int fila, String msj,
			RegistroOperacionDiaria regop) {
		regop.setErrores(regop.getErrores() + 1);
		reporteError = new RegistroOperacionDiaria();
		reporteError.setFila(fila);
		reporteError.setColumna(col);
		reporteError.setDescripcion(msj);
		detalleErrores.add(reporteError);
	}

	private void exportar() {
		if (mRegistroOperacionDiaria.getTabla().getRowCount() > 0) {
			elegirDestino();
		} else {
			Sesion.mensajeError(mRegistroOperacionDiaria,
					"No existen datos en la tabla que exportar");
		}
	}

	private void elegirDestino() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = fileChooser.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File fileGeneral = fileChooser.getSelectedFile();
			if (fileGeneral.isDirectory()) {
				String ruta = fileGeneral.toString();
				String archivo = "/RegistroOperacionesDiarias"
						+ Sesion.getFechaActualVista2();
				String extension = ".txt";
				String resultado = exportarTxt(ruta + archivo + extension);
				Sesion.mensajeInformacion(mRegistroOperacionDiaria, resultado);
			} else {
				Sesion.mensajeError(
						mRegistroOperacionDiaria,
						"No esta permitido darle nombre al archivo a exportar,\nsolo debe seleccionar una carpeta de destino.");
				elegirDestino();
			}
		}
	}

	private String exportarTxt(String ruta) {
		String observaciones = "";
		int totalReg = 0;
		String resultado = "";
		String separador = "|";
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(ruta));
			for (int i = 0; i < mRegistroOperacionDiaria.getTabla()
					.getRowCount(); i++) {

				totalReg++;
				String col1 = mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 1).toString().trim();
				// ingreso 19
				if (col1.equals("1")) {
					if (mRegistroOperacionDiaria.getTabla().getValueAt(i, 3)
							.toString().trim().equals("004")) {
						observaciones = "NUMERO DE FACTURA DE IMPORTACION: "
								+ mRegistroOperacionDiaria.getTabla()
										.getValueAt(i, 23).toString().trim();
					} else if (mRegistroOperacionDiaria.getTabla()
							.getValueAt(i, 3).toString().trim().equals("007")) {
						observaciones = "HACE REFERENCIA A NOTA DE CREDITO";
					} else {
						observaciones = "";
					}
					pw.println(col1
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 2).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 3).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 4).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 5).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 6).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 7).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 8).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 9).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 10).toString().trim()
							+ separador + "" + separador + "" + separador + ""
							+ separador + "" + separador + "" + separador + ""
							+ separador + "" + separador + observaciones
							+ separador + "" + separador);
				} else {
					String col26 = "";
					String col27 = "";
					if(mRegistroOperacionDiaria.getTabla().getValueAt(i, 25).toString()!=null){
						col26 = mRegistroOperacionDiaria.getTabla().getValueAt(i, 25).toString().trim();
					}
					if(mRegistroOperacionDiaria.getTabla().getValueAt(i, 26).toString()!=null){
						col27 = mRegistroOperacionDiaria.getTabla().getValueAt(i, 26).toString().trim();
					}

					pw.println(col1
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 2).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 3).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 4).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 5).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 6).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 7).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 8).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 9).toString().trim()
							+ separador
							+ mRegistroOperacionDiaria.getTabla()
									.getValueAt(i, 10).toString().trim()
							+ separador + "" + separador + "" + separador + ""
							+ separador + "" + separador + col26 + separador + 
							col27 + separador + "" + separador + "" + separador);
				}
			}
			pw.flush();
			pw.close();
			resultado = "Se a exportado con exito " + totalReg + " registro(s)"
					+ ", verifique la siguiente ruta: \n -" + ruta;
		} catch (Exception ex) {
			ex.printStackTrace();
			pw.flush();
			pw.close();
			Sesion.mensajeError(
					mRegistroOperacionDiaria,
					"Error:"
							+ ex.getMessage()
							+ " "
							+ ex.getCause()
							+ "\nen el caso de haber errores por fila, primero corrija lo errores luego intente exportar el archivo.");
		}
		return resultado;
	}

	private void cancelar() {
		mRegistroOperacionDiaria.getTxtEjercicio().setEnabled(true);
		mRegistroOperacionDiaria.getTxtPeriodo().setEnabled(true);
		mRegistroOperacionDiaria.limpiarTabla();
		mRegistroOperacionDiaria.limpiarTablaErrores();
		detalleErrores.clear();
	}

	private void cerrarPeriodo() {
		if (mRegistroOperacionDiaria.getTabla().getRowCount() > 0) {
			for (int i = 0; i < mRegistroOperacionDiaria.getTabla()
					.getRowCount(); i++) {
				RegistroOperacionDiaria obj = new RegistroOperacionDiaria();

				obj.setColumna1(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 1).toString().trim());
				obj.setColumna2(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 2).toString().trim());
				obj.setColumna3(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 3).toString().trim());
				obj.setColumna4(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 4).toString().trim());
				obj.setColumna5(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 5).toString().trim());
				obj.setColumna6(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 6).toString().trim());
				obj.setColumna7(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 7).toString().trim());
				obj.setColumna8(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 8).toString().trim());
				obj.setColumna9(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 9).toString().trim());
				obj.setColumna10(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 10).toString().trim());
				obj.setMHCOMP(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 12).toString().trim());
				obj.setMDCORR(Integer.parseInt(mRegistroOperacionDiaria
						.getTabla().getValueAt(i, 13).toString().trim()));
				obj.setMDALMA(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 14).toString().trim());
				obj.setMDCMOV(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 15).toString().trim());
				obj.setMDTMOV(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 16).toString().trim());
				obj.setMDFECH(Integer.parseInt(mRegistroOperacionDiaria
						.getTabla().getValueAt(i, 17).toString().trim()));
				obj.setMDCOAR(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 18).toString().trim());
				obj.setMDCANR(Double.parseDouble(mRegistroOperacionDiaria
						.getTabla().getValueAt(i, 19).toString().trim()));
				obj.setMHREF1(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 20).toString().trim());
				obj.setCLTIDE(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 21).toString().trim());
				obj.setCLNIDE(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 22).toString().trim());
				obj.setMHREF2(mRegistroOperacionDiaria.getTabla()
						.getValueAt(i, 23).toString().trim());
				obj.setUsuario(Sesion.usuario);
				obj.setFecha(Sesion.getFechaActualVista2());
				try {
					servicioROD.grabarHistorico(obj);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			TCIEPER tcieper = new TCIEPER();
			tcieper.setEjercicio(mRegistroOperacionDiaria.getEjercicio());
			tcieper.setPeriodo(mRegistroOperacionDiaria.getPeriodo());
			tcieper.setSituacion("00");
			tcieper.setProceso("productos fiscalizados");
			tcieper.setUsucre(Sesion.usuario);
			tcieper.setFechacre(Sesion.fechaActualCompleta().trim());
			try {
				servicioROD.insertar(tcieper);
			} catch (SQLException e) {
				cancelar();
				e.printStackTrace();
			}
			cancelar();
			Sesion.mensajeInformacion(mRegistroOperacionDiaria,
					"El periodo ha sido cerrado correctamente");
		} else {
			Sesion.mensajeError(mRegistroOperacionDiaria,
					"Debe de procesar un periodo para esta opción");
		}

	}
	// ****************************************************************************************************
}
