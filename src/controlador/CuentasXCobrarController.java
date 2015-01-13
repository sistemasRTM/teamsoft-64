package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import bean.TCLIE;
import bean.TCTXC;
import bean.TCTXCD;
import bean.TREGVDTO;
import delegate.GestionCuentasXCobrar;
import delegate.GestionVentas;
import recursos.Numero_a_Letra;
import recursos.Sesion;
import servicio.TCTXCDService;
import servicio.TCTXCService;
import servicio.VentasService;
import ventanas.FIEstadoCuenta;
import ventanas.FIHistorialComprasCliente;
import ventanas.FILetras;

public class CuentasXCobrarController implements ActionListener {
	
	FILetras ifLetras = new FILetras();
	FIEstadoCuenta ifEstadoCuenta = new FIEstadoCuenta();
	FIHistorialComprasCliente ifHistorialComprasCliente = new FIHistorialComprasCliente();
	
	TCTXCService servicio = GestionCuentasXCobrar.getTCTXCService();
	TCTXCDService servicioD = GestionCuentasXCobrar.getTCTXCDService();
	VentasService servicioV = GestionVentas.getVentasService();
	//
	String sdesde ="";
	String shasta ="";
	List<TCTXCD> listado = new ArrayList<TCTXCD>();
	List<TREGVDTO> listadoCompras = new ArrayList<TREGVDTO>();
	boolean proceso = false;
	
	public void setVista(FILetras ifLetras) {
		this.ifLetras = ifLetras;
	}

	public void setVista(FIEstadoCuenta ifEstadoCuenta) {
		this.ifEstadoCuenta = ifEstadoCuenta;
	}
	
	public void setVista(FIHistorialComprasCliente ifHistorialComprasCliente) {
		this.ifHistorialComprasCliente = ifHistorialComprasCliente;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ifLetras.getBtnSalir()) {
			FILetras.close();
			ifLetras.salir();
		} else if (e.getSource() == ifLetras.getBtnProcesar()
				|| e.getSource() == ifLetras.getTxtRUC()) {
			buscarLetras();
		} else if (e.getSource() == ifLetras.getBtnImprimir()) {
			imprimirLetras();
		} else if (e.getSource() == ifEstadoCuenta.getBtnSalir()) {
			FIEstadoCuenta.close();
			ifEstadoCuenta.salir();
		} else if (e.getSource() == ifEstadoCuenta.getBtnProcesar()
				|| e.getSource() == ifEstadoCuenta.getTxtRUC()) {
			Thread hilo = new Thread(){
				@Override
				public void run() {
					buscarEstadoCuenta();
				}
			};
			hilo.start();
			
		} else if (e.getSource() == ifHistorialComprasCliente.getBtnProcesar()){
			buscarVentas();
		} else if (e.getSource() == ifHistorialComprasCliente.getBtnSalir()) {
			FIHistorialComprasCliente.close();
			ifHistorialComprasCliente.salir();
		} 
	}

	private void buscarVentas() {
		String ejercicio = ifHistorialComprasCliente.getEjercicio();
		if(!ejercicio.equals("")){
			try {
				String cliente = "";
				List<TREGVDTO> listadoS = new ArrayList<TREGVDTO>();
				List<TREGVDTO> listadoD = new ArrayList<TREGVDTO>();
				List<TREGVDTO> listadoCM = new ArrayList<TREGVDTO>();
				List<TREGVDTO> listadoVM = new ArrayList<TREGVDTO>();
				if(ifHistorialComprasCliente.isGroup()){
					cliente = ifHistorialComprasCliente.getGrupo();
					listadoS = servicioV.obtenerVentasTotalesSolesPorGrupo(cliente, Integer.parseInt(ejercicio));
					listadoD = servicioV.obtenerVentasTotalesDolaresPorGrupo(cliente, Integer.parseInt(ejercicio));
					listadoCM = servicioV.obtenerCantidadMotosVendidasPorGrupo(cliente, ejercicio);
					listadoVM = servicioV.obtenerMontoMotosVendidasPorGrupo(cliente, ejercicio);
					
					List<TREGVDTO> listadoCMD = new ArrayList<TREGVDTO>();
					listadoCMD = servicioV.getCantidadMotosDevueltasPorGrupo(cliente, ejercicio);
					for (TREGVDTO tregvdto : listadoCM) {
						for (TREGVDTO tregvdtod : listadoCMD) {
							if(tregvdto.getClicve().trim().equals(tregvdtod.getClicve().trim())){
								tregvdto.setEnero 	  (tregvdto.getEnero()		+tregvdtod.getEnero());
								tregvdto.setFebrero	  (tregvdto.getFebrero()	+tregvdtod.getFebrero());
								tregvdto.setMarzo	  (tregvdto.getMarzo()		+tregvdtod.getMarzo());
								tregvdto.setAbril	  (tregvdto.getAbril()		+tregvdtod.getAbril());
								tregvdto.setMayo	  (tregvdto.getMayo()		+tregvdtod.getMayo());
								tregvdto.setJunio	  (tregvdto.getJunio()		+tregvdtod.getJunio());
								tregvdto.setJulio	  (tregvdto.getJulio()		+tregvdtod.getJulio());
								tregvdto.setAgosto	  (tregvdto.getAgosto()		+tregvdtod.getAgosto());
								tregvdto.setSeptiembre(tregvdto.getSeptiembre()	+tregvdtod.getSeptiembre());
								tregvdto.setOctubre	  (tregvdto.getOctubre()	+tregvdtod.getOctubre());
								tregvdto.setNoviembre (tregvdto.getNoviembre()	+tregvdtod.getNoviembre());
								tregvdto.setDiciembre (tregvdto.getDiciembre()	+tregvdtod.getDiciembre());
								tregvdto.setTotal	  (tregvdto.getTotal()		+tregvdtod.getTotal());
							}
						}
					}
					
					List<TREGVDTO> listadoVMD = new ArrayList<TREGVDTO>();
					listadoVMD = servicioV.getMontoMotosDevueltasPorGrupo(cliente, ejercicio);
					for (TREGVDTO tregvdto : listadoVM) {
						for (TREGVDTO tregvdtod : listadoVMD) {
							if(tregvdto.getClicve().trim().equals(tregvdtod.getClicve().trim())){
								tregvdto.setEnero 	  (tregvdto.getEnero()		+tregvdtod.getEnero());
								tregvdto.setFebrero	  (tregvdto.getFebrero()	+tregvdtod.getFebrero());
								tregvdto.setMarzo	  (tregvdto.getMarzo()		+tregvdtod.getMarzo());
								tregvdto.setAbril	  (tregvdto.getAbril()		+tregvdtod.getAbril());
								tregvdto.setMayo	  (tregvdto.getMayo()		+tregvdtod.getMayo());
								tregvdto.setJunio	  (tregvdto.getJunio()		+tregvdtod.getJunio());
								tregvdto.setJulio	  (tregvdto.getJulio()		+tregvdtod.getJulio());
								tregvdto.setAgosto	  (tregvdto.getAgosto()		+tregvdtod.getAgosto());
								tregvdto.setSeptiembre(tregvdto.getSeptiembre()	+tregvdtod.getSeptiembre());
								tregvdto.setOctubre	  (tregvdto.getOctubre()	+tregvdtod.getOctubre());
								tregvdto.setNoviembre (tregvdto.getNoviembre()	+tregvdtod.getNoviembre());
								tregvdto.setDiciembre (tregvdto.getDiciembre()	+tregvdtod.getDiciembre());
								tregvdto.setTotal	  (tregvdto.getTotal()		+tregvdtod.getTotal());
							}
						}
					}
				}else{
					cliente = ifHistorialComprasCliente.getCliente();
					listadoS = servicioV.obtenerVentasTotalesSoles(cliente, Integer.parseInt(ejercicio));
					listadoD = servicioV.obtenerVentasTotalesDolares(cliente, Integer.parseInt(ejercicio));
					listadoCM = servicioV.obtenerCantidadMotosVendidas(cliente, ejercicio);
					listadoVM = servicioV.obtenerMontoMotosVendidas(cliente, ejercicio);
		
					List<TREGVDTO> listadoCMD = new ArrayList<TREGVDTO>();
					listadoCMD = servicioV.getCantidadMotosDevueltas(cliente, ejercicio);
					
					if(listadoCM.size()>0 && listadoCMD.size()>0){
						listadoCM.get(0).setEnero		(listadoCM.get(0).getEnero()		+listadoCMD.get(0).getEnero());
						listadoCM.get(0).setFebrero		(listadoCM.get(0).getFebrero()		+listadoCMD.get(0).getFebrero());
						listadoCM.get(0).setMarzo		(listadoCM.get(0).getMarzo()		+listadoCMD.get(0).getMarzo());
						listadoCM.get(0).setAbril		(listadoCM.get(0).getAbril()		+listadoCMD.get(0).getAbril());
						listadoCM.get(0).setMayo		(listadoCM.get(0).getMayo()			+listadoCMD.get(0).getMayo());
						listadoCM.get(0).setJunio		(listadoCM.get(0).getJunio()		+listadoCMD.get(0).getJunio());
						listadoCM.get(0).setJulio		(listadoCM.get(0).getJulio()		+listadoCMD.get(0).getJulio());
						listadoCM.get(0).setAgosto		(listadoCM.get(0).getAgosto()		+listadoCMD.get(0).getAgosto());
						listadoCM.get(0).setSeptiembre	(listadoCM.get(0).getSeptiembre()	+listadoCMD.get(0).getSeptiembre());
						listadoCM.get(0).setOctubre		(listadoCM.get(0).getOctubre()		+listadoCMD.get(0).getOctubre());
						listadoCM.get(0).setNoviembre	(listadoCM.get(0).getNoviembre()	+listadoCMD.get(0).getNoviembre());
						listadoCM.get(0).setDiciembre	(listadoCM.get(0).getDiciembre()	+listadoCMD.get(0).getDiciembre());
						listadoCM.get(0).setTotal		(listadoCM.get(0).getTotal()		+listadoCMD.get(0).getTotal());
						//-------------------------------------------------------------------------------------------------
						List<TREGVDTO> listadoVMD = new ArrayList<TREGVDTO>();
						listadoVMD = servicioV.getMontoMotosDevueltas(cliente, ejercicio);
						
						listadoVM.get(0).setEnero		(listadoVM.get(0).getEnero()		+listadoVMD.get(0).getEnero());
						listadoVM.get(0).setFebrero		(listadoVM.get(0).getFebrero()		+listadoVMD.get(0).getFebrero());
						listadoVM.get(0).setMarzo		(listadoVM.get(0).getMarzo()		+listadoVMD.get(0).getMarzo());
						listadoVM.get(0).setAbril		(listadoVM.get(0).getAbril()		+listadoVMD.get(0).getAbril());
						listadoVM.get(0).setMayo		(listadoVM.get(0).getMayo()			+listadoVMD.get(0).getMayo());
						listadoVM.get(0).setJunio		(listadoVM.get(0).getJunio()		+listadoVMD.get(0).getJunio());
						listadoVM.get(0).setJulio		(listadoVM.get(0).getJulio()		+listadoVMD.get(0).getJulio());
						listadoVM.get(0).setAgosto		(listadoVM.get(0).getAgosto()		+listadoVMD.get(0).getAgosto());
						listadoVM.get(0).setSeptiembre	(listadoVM.get(0).getSeptiembre()	+listadoVMD.get(0).getSeptiembre());
						listadoVM.get(0).setOctubre		(listadoVM.get(0).getOctubre()		+listadoVMD.get(0).getOctubre());
						listadoVM.get(0).setNoviembre	(listadoVM.get(0).getNoviembre()	+listadoVMD.get(0).getNoviembre());
						listadoVM.get(0).setDiciembre	(listadoVM.get(0).getDiciembre()	+listadoVMD.get(0).getDiciembre());
						listadoVM.get(0).setTotal		(listadoVM.get(0).getTotal()		+listadoVMD.get(0).getTotal());
					}
				}
				if(listadoS.size()>0 || listadoD.size()>0){
					//ifHistorialComprasCliente.cargaTablaSD(listadoSD);
					//ifHistorialComprasCliente.cargaTablaCM(listadoCM);
					//ifHistorialComprasCliente.cargaTablaVM(listadoVM);
					listadoCompras.clear();
					for (TREGVDTO tregvdto : listadoS) {
						listadoCompras.add(tregvdto);
					}
					for (TREGVDTO tregvdto : listadoD) {
						listadoCompras.add(tregvdto);
					}
					for (TREGVDTO tregvdto : listadoCM) {
						listadoCompras.add(tregvdto);
					}
					for (TREGVDTO tregvdto : listadoVM) {
						listadoCompras.add(tregvdto);
					}
					exportarExcelCompras(ejercicio);
				}else{
					Sesion.mensajeInformacion(ifHistorialComprasCliente, "No existen resultados para los criterios ingresados");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			Sesion.mensajeInformacion(ifHistorialComprasCliente, "Ingrese ejercicio");
		}
	}

	private void buscarEstadoCuenta() {
		if(!proceso){
		proceso=true;
		listado.clear();
		listado = null;
		System.gc();
		System.runFinalization();
		listado = new ArrayList<TCTXCD>(); 
		
		try {
			Date desde = ifEstadoCuenta.getFechaDesde();
			Date hasta = ifEstadoCuenta.getFechaHasta();
			sdesde = Sesion.formateaFechaVista(desde);
			shasta = Sesion.formateaFechaVista(hasta);
			int idesde = Integer.parseInt(sdesde.substring(6, 10)
					+ sdesde.substring(3, 5) + sdesde.substring(0, 2));
			int ihasta = Integer.parseInt(shasta.substring(6, 10)
					+ shasta.substring(3, 5) + shasta.substring(0, 2));
			
			if(!ifEstadoCuenta.isAll()){
			if (!ifEstadoCuenta.getRUC().equals("")) {
				listado = servicioD.buscarTCTXCD(
						ifEstadoCuenta.getRUC(), idesde, ihasta);
				if (listado.size() > 0) {
					String ccpvtaA = "";
					String ccejerA = "";
					String ccperiA = "";
					String cctdocA = "";
					String ccndocA = "";
					
					if(listado.size() > 1){
						ccpvtaA = listado.get(1).getCcpvta().trim();
						ccejerA = listado.get(1).getCcejer().trim();
						ccperiA = listado.get(1).getCcperi().trim();
						cctdocA = listado.get(1).getCctdoc().trim();
						ccndocA = listado.get(1).getCcndoc().trim();
					}
					
					for (int i = 0; i < listado.size(); i++) {
						
						if(listado.get(i).getBhrefe()==null){
							listado.get(i).setBhrefe("");
						}
						if(listado.get(i).getDesesp()==null){
							listado.get(i).setDesesp("");
						}
						if(listado.get(i).getCcfpag()==null){
							listado.get(i).setCcfpag("");
						}
						if(listado.get(i).getCccheq()==null){
							listado.get(i).setCccheq("");
						}
						
						if(!(listado.get(i).getCcpvta().equals("") || listado.get(i).getCcpvta().equals("--------->"))){
							if(listado.get(i).getCcejer().trim().equals(ccejerA) && 
									listado.get(i).getCcperi().trim().equals(ccperiA) && 
										listado.get(i).getCctdoc().trim().equals(cctdocA) && 
											listado.get(i).getCcndoc().trim().equals(ccndocA)){
								if(listado.get(i).getCcimpn() != null){
									ccpvtaA = (Double.parseDouble(ccpvtaA)  - Double.parseDouble(listado.get(i).getCcimpn())) + "";
									listado.get(i).setCcsald(Double.parseDouble(ccpvtaA));
								}
							}else{
								ccpvtaA = listado.get(i).getCcpvta().trim();
								ccejerA = listado.get(i).getCcejer().trim();
								ccperiA = listado.get(i).getCcperi().trim();
								cctdocA = listado.get(i).getCctdoc().trim();
								ccndocA = listado.get(i).getCcndoc().trim();
								if(listado.get(i).getCcimpn() != null){
									ccpvtaA = (Double.parseDouble(ccpvtaA)  - Double.parseDouble(listado.get(i).getCcimpn())) + "";
									listado.get(i).setCcsald(Double.parseDouble(ccpvtaA));
								}
							}
						}
						
						if(!(listado.get(i).getCcpvta().equals("") || listado.get(i).getCcpvta().equals("--------->"))){
							listado.get(i).setCcpvta(Sesion.formatoDecimalVista_2_digitos(Double.parseDouble(listado.get(i).getCcpvta())));
						}
						if(listado.get(i).getCcimpn() != null){
							if(!listado.get(i).getCcimpn().equals("")){
								listado.get(i).setCcimpn(Sesion.formatoDecimalVista_2_digitos(Double.parseDouble(listado.get(i).getCcimpn())));
							}
						}else{
							listado.get(i).setCcimpn("");
						}						
					}
					double soles = servicioD.getSaldoSoles(ifEstadoCuenta.getRUC(), ihasta);
					double dolares = servicioD.getSaldoDolares(ifEstadoCuenta.getRUC(), ihasta);
					exportarExcel(soles,dolares);
				} else {
					Sesion.mensajeInformacion(ifEstadoCuenta,
							"No hay resultados para el criterio ingresado");
				}
				proceso=false;
			} else {
				Sesion.mensajeInformacion(ifEstadoCuenta,
						"Ingrese código del cliente");
			}
			}else{
				File destino = Sesion.elegirDestino();
				if(destino!=null){
					List<TCLIE> clientes = servicioD.getClientes();
					for (TCLIE tclie : clientes) {
						List<TCTXCD> lista = prepararDatosEstadoCuenta(idesde, ihasta, tclie.getClicve());
						for (TCTXCD tctxcd : lista) {
							listado.add(tctxcd); 
						}
					}
					exportarExcelTodos(destino);
					Sesion.mensajeInformacion(ifEstadoCuenta, "Su reporte se genero correctamente en la siguiente ruta:\n"+destino.toString());
				}else{
					Sesion.mensajeError(ifEstadoCuenta, "Ingrese nombre de archivo correcto");
				}
				System.out.println(destino.toString());
				proceso=false;
			}
		} catch (SQLException e) {
			proceso=false;
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			proceso=false;
			Sesion.mensajeInformacion(ifEstadoCuenta, "El reporte es muy grande, intente un rango de fechas mas corto.");
		}
		}else{
			Sesion.mensajeInformacion(ifEstadoCuenta, "Hay un proceso pendiente ejecutandose, espere a que termine");
		}
	}
	
	private void exportarExcelTodos(File destino) {
		try {		
			JasperReport reporte = (JasperReport) JRLoader
					.loadObject(ClassLoader
							.getSystemResource("reportes/EstadoCuentaTodos.jasper"));
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.clear();
			parametros.put(JRParameter.REPORT_LOCALE, Locale.US);
			parametros.put("fDesde", sdesde);
			parametros.put("fHasta", shasta);
			JasperPrint jpLetras = JasperFillManager.fillReport(reporte,
					parametros,new JRBeanCollectionDataSource(listado));
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jpLetras); 
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE, new File(destino.toString() + ".xlsx"));
			exporter.setParameter(JRXlsExporterParameter. IS_ONE_PAGE_PER_SHEET, new Boolean(false));
			exporter.setParameter(JRXlsExporterParameter. IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, new Boolean(false));
			exporter.setParameter(JRXlsExporterParameter. IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, new Boolean(false));
			exporter.exportReport();
			//JasperViewer.viewReport(jpLetras,false);
	} catch (JRException e) {
		Sesion.mensajeError(ifEstadoCuenta,"JRException: " + e.getMessage() + " " + e.getCause());
	} catch (ArrayIndexOutOfBoundsException e) {
		Sesion.mensajeError(ifEstadoCuenta,"ArrayIndexOutOfBoundsException: " + e.getMessage() + " " + e.getCause());
	} catch (Exception e) {
		Sesion.mensajeError(ifEstadoCuenta,"Exception: " + e.getMessage() + " " + e.getCause());
	}		
	}

	private List<TCTXCD> prepararDatosEstadoCuenta(int idesde,int ihasta,String cliente){
		List<TCTXCD> listado = new ArrayList<TCTXCD>();
		try {
			listado = servicioD.buscarTCTXCD(cliente, idesde, ihasta);
		
		if (listado.size() > 0) {
			String ccpvtaA = "";
			String ccejerA = "";
			String ccperiA = "";
			String cctdocA = "";
			String ccndocA = "";
			
			if(listado.size() > 1){
				ccpvtaA = listado.get(1).getCcpvta().trim();
				ccejerA = listado.get(1).getCcejer().trim();
				ccperiA = listado.get(1).getCcperi().trim();
				cctdocA = listado.get(1).getCctdoc().trim();
				ccndocA = listado.get(1).getCcndoc().trim();
			}
			
			double soles = servicioD.getSaldoSoles(cliente, ihasta);
			double dolares = servicioD.getSaldoDolares(cliente, ihasta);
			
			for (int i = 0; i < listado.size(); i++) {
				
				listado.get(i).setDireccion(listado.get(i).getClidir().trim()+" "+listado.get(i).getClidis().trim()+" "+listado.get(i).getClipro().trim()+" "+listado.get(i).getClidpt().trim());
				listado.get(i).setContacto(listado.get(i).getClcnom().trim());
				listado.get(i).setTelefonos(listado.get(i).getClite1().trim()+"/"+listado.get(i).getClite2().trim()+"/"+listado.get(i).getClite3().trim());
				
				listado.get(i).settDolares(dolares);
				listado.get(i).settSoles(soles);
				if(listado.get(i).getBhrefe()==null){
					listado.get(i).setBhrefe("");
				}
				if(listado.get(i).getDesesp()==null){
					listado.get(i).setDesesp("");
				}
				if(listado.get(i).getCcfpag()==null){
					listado.get(i).setCcfpag("");
				}
				if(listado.get(i).getCccheq()==null){
					listado.get(i).setCccheq("");
				}
				
				if(!(listado.get(i).getCcpvta().equals("") || listado.get(i).getCcpvta().equals("--------->"))){
					if(listado.get(i).getCcejer().trim().equals(ccejerA) && 
							listado.get(i).getCcperi().trim().equals(ccperiA) && 
								listado.get(i).getCctdoc().trim().equals(cctdocA) && 
									listado.get(i).getCcndoc().trim().equals(ccndocA)){
						if(listado.get(i).getCcimpn() != null){
							ccpvtaA = (Double.parseDouble(ccpvtaA)  - Double.parseDouble(listado.get(i).getCcimpn())) + "";
							listado.get(i).setCcsald(Double.parseDouble(ccpvtaA));
						}
					}else{
						ccpvtaA = listado.get(i).getCcpvta().trim();
						ccejerA = listado.get(i).getCcejer().trim();
						ccperiA = listado.get(i).getCcperi().trim();
						cctdocA = listado.get(i).getCctdoc().trim();
						ccndocA = listado.get(i).getCcndoc().trim();
						if(listado.get(i).getCcimpn() != null){
							ccpvtaA = (Double.parseDouble(ccpvtaA)  - Double.parseDouble(listado.get(i).getCcimpn())) + "";
							listado.get(i).setCcsald(Double.parseDouble(ccpvtaA));
						}
					}
				}
				
				if(!(listado.get(i).getCcpvta().equals("") || listado.get(i).getCcpvta().equals("--------->"))){
					listado.get(i).setCcpvta(Sesion.formatoDecimalVista_2_digitos(Double.parseDouble(listado.get(i).getCcpvta())));
				}
				if(listado.get(i).getCcimpn() != null){
					if(!listado.get(i).getCcimpn().equals("")){
						listado.get(i).setCcimpn(Sesion.formatoDecimalVista_2_digitos(Double.parseDouble(listado.get(i).getCcimpn())));
					}
				}else{
					listado.get(i).setCcimpn("");
				}						
			}
		} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listado;
	}

	private void buscarLetras() {
		try {
			List<TCTXC> cuentas = servicio.buscarPendientes(ifLetras.getRUC());
			if (cuentas.size() > 0) {
				ifLetras.cargaTabla(cuentas);
			} else {
				Sesion.mensajeInformacion(ifLetras,
						"Cliente no tiene letras pendientes");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void imprimirLetras() {
		try {
			if (ifLetras.getTablaDocumentos().getSelectedRow() > -1) {
				String ccndoc = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								0).toString().trim();
				String ccfech = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								1).toString().trim();
				String ccfeve = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								2).toString().trim();
				String ccsald = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								5).toString().trim();
				String clinom = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								6).toString().trim();
				String clidir = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								7).toString().trim();
				String clidis = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								8).toString().trim();
				String clipro = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								9).toString().trim();
				String clidpt = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								10).toString().trim();
				String clnide = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								11).toString().trim();
				String clite1 = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								12).toString().trim();
				String clnano = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								13).toString().trim();
				String clnadi = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								14).toString().trim();
				String clnate = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								15).toString().trim();
				String clnaru = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								16).toString().trim();
				String ccmone = ifLetras
						.getTablaDocumentos()
						.getValueAt(
								ifLetras.getTablaDocumentos().getSelectedRow(),
								17).toString().trim();

				String moneda = "";
				String monedaT = "";
				if (ccmone != null) {
					if (ccmone.equals("0")) {
						moneda = "S/.";
						monedaT = " NUEVOS SOLES.";
					} else if (ccmone.equals("1")) {
						moneda = "$.";
						monedaT = " DOLARES AMERICANOS.";
					} else {
						moneda = "";
						monedaT = "";
					}
				}

				String sl = Numero_a_Letra.Convertir(
						Sesion.formateaDecimal_2(Double.parseDouble(ccsald)),
						true).trim();
				JasperReport reporte = (JasperReport) JRLoader
						.loadObject(ClassLoader
								.getSystemResource("reportes/Letras.jasper"));
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.clear();
				parametros.put(JRParameter.REPORT_LOCALE, Locale.US);
				parametros.put("CCNDOC", ccndoc);
				parametros.put("CCFECH", ccfech);
				parametros.put("CCFEVE", ccfeve);
				parametros.put("CCSALD", moneda + ccsald);
				parametros.put("CLINOM", clinom);
				parametros.put("CLNIDE", clnide);
				parametros.put("CLIDIR", clidir);
				parametros.put("CLITE1", clite1);
				parametros.put("SL", sl + monedaT);
				parametros.put("CLIDIS_PRO_DPT", clidis + "-" + clipro + "-"
						+ clidpt);
				parametros.put("CLNANO", clnano + " ");
				parametros.put("CLNADI", clnadi + " ");
				parametros.put("CLNATE", clnate + " ");
				parametros.put("CLNARU", clnaru + " ");

				JasperPrint jpLetras = JasperFillManager.fillReport(reporte,
						parametros);
				// JasperViewer.viewReport(jpLetras);

				AttributeSet attributeSet = new HashAttributeSet();
				attributeSet
						.add(new PrinterName(Sesion.impresoraMatricial, null));
				DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
				PrintService[] impresoras = PrintServiceLookup
						.lookupPrintServices(docFormat, attributeSet);
				JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
				jrprintServiceExporter.setParameter(
						JRExporterParameter.JASPER_PRINT, jpLetras);
				jrprintServiceExporter.setParameter(
						JRPrintServiceExporterParameter.PRINT_SERVICE,
						impresoras[0]);
				jrprintServiceExporter.setParameter(
						JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
						Boolean.FALSE);
				jrprintServiceExporter.exportReport();

			} else {
				Sesion.mensajeError(ifLetras, "Seleccione una fila");
			}
		} catch (JRException e) {
			Sesion.mensajeError(ifLetras,"JRException: " + e.getMessage() + " " + e.getCause());
		} catch (ArrayIndexOutOfBoundsException e) {
			Sesion.mensajeError(ifLetras,"ArrayIndexOutOfBoundsException: " + e.getMessage() + " " + e.getCause());
		} catch (Exception e) {
			Sesion.mensajeError(ifLetras,"Exception: " + e.getMessage() + " " + e.getCause());
		}
	}
		
	private void exportarExcel(double soles,double dolares) {
		try {		
				JasperReport reporte = (JasperReport) JRLoader
						.loadObject(ClassLoader
								.getSystemResource("reportes/EstadoCuenta.jasper"));
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.clear();
				parametros.put(JRParameter.REPORT_LOCALE, Locale.US);
				parametros.put("codigo", listado.get(0).getClicve().trim());
				parametros.put("cliente",listado.get(0).getClinom().trim());
				parametros.put("direccion", listado.get(0).getClidir().trim()+" "+listado.get(0).getClidis().trim()+" "+listado.get(0).getClipro().trim()+" "+listado.get(0).getClidpt().trim());
				parametros.put("contacto", listado.get(0).getClcnom().trim());
				parametros.put("telefonos", listado.get(0).getClite1().trim()+"/"+listado.get(0).getClite2().trim()+"/"+listado.get(0).getClite3().trim());
				parametros.put("fDesde", sdesde);
				parametros.put("fHasta", shasta);
				parametros.put("soles", soles);
				parametros.put("dolares", dolares);
				JasperPrint jpLetras = JasperFillManager.fillReport(reporte,
						parametros,new JRBeanCollectionDataSource(listado));
				JasperViewer.viewReport(jpLetras,false);
				
				/*
				JFrame frame = new JFrame();
				frame.setLayout(new BorderLayout());
				
				
				JRViewer viewer =  new  JRViewer ( jpLetras ); 
				viewer.setVisible(true);
				viewer.setBounds(10,40,620,400);
				ifEstadoCuenta.getDesktopPane().add(viewer);
				
				JButton buton  = (JButton) ((JPanel)viewer.getComponent(0)).getComponent(0);
				((JPanel)viewer.getComponent(0)).remove(0);
				JButton buton2 = new JButton("",buton.getIcon());
				buton2.setToolTipText("Guardar");
				((JPanel)viewer.getComponent(0)).add(buton2);
			
				buton2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.out.println("clic");
					}
				});
				frame.add(viewer,BorderLayout.CENTER);
				frame.setVisible(true);
				frame.setSize(500, 500);
				frame.setLocationRelativeTo(null);
				frame.setIconImage(Sesion.cargarImagen(Sesion.imgRTM));
				frame.setTitle("JasperViewerCustomized");
				
				((JPanel)viewer.getComponent(0)).remove(0);
				JButton buton = new JButton("boton");
				((JPanel)viewer.getComponent(0)).add(buton);
				buton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.out.println("clic");
					}
				});
				
				//net.sf.jasperreports.view.JRViewer viewer2 =  new  net.sf.jasperreports.view.JRViewer ( jpLetras ); 
				//viewer2.setVisible(true);
				//viewer2.setBounds(650,420,620,400);
				
				//JRSaveContributor save = new 
				//viewer2.setSaveContributors(saveContribs)
				//ifEstadoCuenta.getDesktopPane().add(viewer2);
				//JasperViewer.viewReport(jpLetras,false);
				*/
		} catch (JRException e) {
			Sesion.mensajeError(ifEstadoCuenta,"JRException: " + e.getMessage() + " " + e.getCause());
		} catch (ArrayIndexOutOfBoundsException e) {
			Sesion.mensajeError(ifEstadoCuenta,"ArrayIndexOutOfBoundsException: " + e.getMessage() + " " + e.getCause());
		} catch (Exception e) {
			Sesion.mensajeError(ifEstadoCuenta,"Exception: " + e.getMessage() + " " + e.getCause());
		}
	}
	
	private void exportarExcelCompras(String ejercicio) {
		try {
			TCLIE tclie = new TCLIE();
			if(listadoCompras.size()>0){
				if(listadoCompras.get(0).isGrupo()){
					try {
						tclie = servicioV.getClienteGrupo(listadoCompras.get(0).getCliente());
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else{
					try {
						tclie = servicioV.getCliente(listadoCompras.get(0).getCliente());
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(tclie.getClidir()==null){
					tclie.setClidir("");
				}
				if(tclie.getClidis()==null){
					tclie.setClidis("");
				}
				if(tclie.getClipro()==null){
					tclie.setClipro("");
				}
				if(tclie.getClidpt()==null){
					tclie.setClidpt("");
				}
				
				JasperReport reporte = (JasperReport) JRLoader
						.loadObject(ClassLoader
								.getSystemResource("reportes/HistorialCompras.jasper"));
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.clear();
				parametros.put(JRParameter.REPORT_LOCALE, Locale.US);
				parametros.put("ejercicio", ejercicio);
				parametros.put("codigo", tclie.getClicve());
				parametros.put("cliente", tclie.getClinom());
				parametros.put("direccion", tclie.getClidir().trim() + " " + tclie.getClidis().trim() + " " + tclie.getClipro().trim() + " " + tclie.getClidpt().trim());
				JasperPrint jpCompras = JasperFillManager.fillReport(reporte,
						parametros,new JRBeanCollectionDataSource(listadoCompras));
				JasperViewer.viewReport(jpCompras,false);
			}
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}
