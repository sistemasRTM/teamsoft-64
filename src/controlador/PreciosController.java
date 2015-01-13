package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import delegate.GestionPrecios;

import bean.ListaPrecio;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import recursos.Sesion;
import servicio.ListaPrecioService;
import ventanas.FIMantenimientoListaPrecios;

public class PreciosController implements ActionListener {
	FIMantenimientoListaPrecios mListaPrecios;
	File fileGeneral;
	ListaPrecioService servicio = GestionPrecios.getPrecioService();

	public PreciosController(FIMantenimientoListaPrecios mListaPrecios) {
		this.mListaPrecios = mListaPrecios;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (e.getSource() == mListaPrecios.getBtnOrigen()) {
			elegirOrigen();
		} else if (e.getSource() == mListaPrecios.getBtnDestino()) {
			elegirDestino();
		} else if (e.getSource() == mListaPrecios.getBtnProcesar()) {
			procesarListadePrecios(mListaPrecios.getOrigen());
		} else if (e.getSource() == mListaPrecios.getBtnSalir()) {
			mListaPrecios.dispose();
		} else {
		}

	}
	
	private void elegirOrigen(){
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filtro =  new FileNameExtensionFilter("Archivos Excel (*.xls)", "xls");
		fileChooser.setFileFilter(filtro);
		fileChooser.setAcceptAllFileFilterUsed(false);
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File fileGeneral = fileChooser.getSelectedFile();
			if(fileGeneral.exists()){
				String ruta = fileGeneral.toString();
				mListaPrecios.setOrigen(ruta);
			}else{
				Sesion.mensajeError(mListaPrecios, "El archivo no existe");
				elegirOrigen();
			}
			
		}
	}
	
	private void elegirDestino(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = fileChooser.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File fileGeneral = fileChooser.getSelectedFile();
			if(fileGeneral.isDirectory()){
				String ruta = fileGeneral.toString();
				mListaPrecios.setDestino(ruta);
			}else{
				Sesion.mensajeError(mListaPrecios, "No esta permitido darle nombre al archivo a exportar,\nsolo debe seleccionar una carpeta de destino.");
				elegirDestino();
			}
			
		}
	}

	private void procesarListadePrecios(String archivoDestino) {
		WritableWorkbook w = null;
		DataOutputStream out = null;
		try {
			Workbook archivoExcel = Workbook.getWorkbook(new File(
					archivoDestino));
			Sheet hoja = archivoExcel.getSheet(0);
			int numColumnas = hoja.getColumns();
			int numFilas = hoja.getRows();
			System.out.println(numColumnas);
			if(numColumnas==6){
				int secNuevos=0;
				int secModificados=0;
				int secPendientes=0;
				int secuencia=0;
				double igv = 0;
				String moneda="";
				String data, data1, data2, data3, data4, data5;
				String ruta = mListaPrecios.getDestino();
				/*crear hoja excel*/
				
				ruta = ruta + "\\ActualizacionPrecios.xls";
				File file = new File(ruta);

				out = new DataOutputStream(
				new FileOutputStream(file));
				w = Workbook.createWorkbook(out);
				WritableSheet s = w.createSheet("NuevosPrecios", 0);
				WritableSheet s1 = w.createSheet("PreciosModificados", 1);
				WritableSheet s2 = w.createSheet("Pendientes", 2);
				/*fin crear hoja excel*/
				// contruye cabecera
				WritableCellFormat wcf = new WritableCellFormat();
				wcf.setAlignment(Alignment.CENTRE);
				//GRILLA PARA EXCEL CABECERA
				wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
				//GRILLA PARA EXCEL DETALLE
				WritableCellFormat wcfDatos = new WritableCellFormat();
				wcfDatos.setBorder(Border.ALL, BorderLineStyle.THIN);
				// EDITAR EL TAMAÑO DE LAS CELDAS
				CellView cv0 = new CellView();
				CellView cv1 = new CellView();
				cv0.setSize(3200);
				cv1.setSize(5500);
				//hoja nuevos
				s.setColumnView(0, cv0);
				s.setColumnView(3, cv0);
				s.setColumnView(1, cv1);
				s.setColumnView(2, cv1);
				s.setColumnView(4, cv0);
				s.setColumnView(5, cv0);
				s.setColumnView(6, cv0);
				
				s.addCell(new Label(0, 0, "Código", wcf));
				s.addCell(new Label(1, 0, "Equivalente", wcf));
				s.addCell(new Label(2, 0, "Descripción", wcf));
				s.addCell(new Label(3, 0, "Precio", wcf));
				s.addCell(new Label(4, 0, "Moneda", wcf));
				s.addCell(new Label(5, 0, "Descuento", wcf));
				s.addCell(new Label(6, 0, "I.G.V", wcf));
				
				
				//hoja modificados
				s1.setColumnView(0, cv0);
				s1.setColumnView(3, cv0);
				s1.setColumnView(4, cv0);
				s1.setColumnView(1, cv1);
				s1.setColumnView(2, cv1);
				
				s1.addCell(new Label(0, 0, "Código", wcf));
				s1.addCell(new Label(1, 0, "Equivalente", wcf));
				s1.addCell(new Label(2, 0, "Descripción", wcf));
				s1.addCell(new Label(3, 0, "Precio", wcf));
				s1.addCell(new Label(4, 0, "Precio Anterior", wcf));
				//hoja pendientes
				s2.setColumnView(0, cv0);
				s2.setColumnView(3, cv0);
				s2.setColumnView(1, cv1);
				s2.setColumnView(2, cv1);
				s2.setColumnView(4, cv0);
				s2.setColumnView(5, cv0);
				
				s2.addCell(new Label(0, 0, "Código", wcf));
				s2.addCell(new Label(1, 0, "Equivalente", wcf));
				s2.addCell(new Label(2, 0, "Descripción", wcf));
				s2.addCell(new Label(3, 0, "Precio", wcf));
				s2.addCell(new Label(4, 0, "Moneda", wcf));
				s2.addCell(new Label(5, 0, "Descuento", wcf));
				//FIN EDITAR HOJAS
				
				//obtener secuencia
				secuencia = servicio.getSecuencia();
				igv = Double.parseDouble(servicio.listarTALIAS().getAli008());
				//inicia trabajo de archivo
				for (int fila = 1; fila < numFilas; fila++) {
					data = hoja.getCell(0, fila).getContents();// cod
					data1 = hoja.getCell(1, fila).getContents();// desc
					data2 = hoja.getCell(2, fila).getContents();// equ
					data3 = hoja.getCell(3, fila).getContents().replace(',', '.');// precio
					data4 = hoja.getCell(4, fila).getContents();// moneda
					data5 = hoja.getCell(5, fila).getContents();// descuento
					
					//sec artcod precio codli moneda descuento 
					ListaPrecio listaPrecioNueva = new ListaPrecio();
					listaPrecioNueva.setCodigolp(data);
					listaPrecioNueva.setDescripcion(data1);
					listaPrecioNueva.setEqu(data2);
					listaPrecioNueva.setPrecio(Double.parseDouble(data3));
					listaPrecioNueva.setLpcodi(mListaPrecios.getListaPrecio());
										
					if(data4==null){
						data4="";
						listaPrecioNueva.setMoneda(-1);
					}else{
						if(!data4.equals("")){
							listaPrecioNueva.setMoneda(Integer.parseInt(data4));
						}else{
							listaPrecioNueva.setMoneda(-1);
						}
					}
					
					if(data5==null){
						data5="";
						listaPrecioNueva.setDescuento(-1);
					}else{
						if(!data5.equals("")){
							data5=data5.replace(',', '.');
							listaPrecioNueva.setDescuento(Double.parseDouble(data5));
						}else{
							listaPrecioNueva.setDescuento(-1);
						}
					}
					
					
					ListaPrecio listaPrecioAntigua = servicio
							.verificaPrecio(listaPrecioNueva);
					if (listaPrecioAntigua == null) {
						if(data4.equals("") || data5.equals("")){
							secPendientes++;
							//generar hoja de texto de pendientes 
							s2.addCell(new Label(0, secPendientes,data,wcfDatos));
							s2.addCell(new Label(2, secPendientes,data1,wcfDatos));
							s2.addCell(new Label(1, secPendientes, data2,wcfDatos));
							s2.addCell(new jxl.write.Number(3, secPendientes, Double.parseDouble(data3),wcfDatos));
							s2.addCell(new jxl.write.Number(4, secPendientes, listaPrecioNueva.getMoneda(),wcfDatos));
							s2.addCell(new jxl.write.Number(5, secPendientes, listaPrecioNueva.getDescuento(),wcfDatos));
						}else{
							secNuevos++;
							secuencia++;
							String artmed = servicio.getARTMED(data.trim());
							listaPrecioNueva.setLpunvt(artmed);
							listaPrecioNueva.setLpsecu(secuencia);
							listaPrecioNueva.setLptigv(igv);
							
							//generar hoja de texto de nuevos precios 
							s.addCell(new Label(0, secNuevos,data,wcfDatos));
							s.addCell(new Label(2, secNuevos,data1,wcfDatos));
							s.addCell(new Label(1, secNuevos, data2,wcfDatos));
							s.addCell(new jxl.write.Number(3, secNuevos, Double.parseDouble(data3),wcfDatos));
							s.addCell(new Label(4, secNuevos, moneda = listaPrecioNueva.getMoneda() > 0 ? "D": "S",wcfDatos));
							s.addCell(new jxl.write.Number(5, secNuevos, listaPrecioNueva.getDescuento(),wcfDatos));
							s.addCell(new jxl.write.Number(6, secNuevos, igv,wcfDatos));
						    //servicio.registrarPrecios(listaPrecioNueva);							 
						}
					} else {
						secModificados++;
						//generar hoja de texto de precios modificados antes y nuevo 
						s1.addCell(new Label(0, secModificados,data,wcfDatos));
						s1.addCell(new Label(2,secModificados,data1,wcfDatos));
						s1.addCell(new Label(1, secModificados, data2,wcfDatos));
						s1.addCell(new jxl.write.Number(3, secModificados, Double.parseDouble(data3),wcfDatos));
						s1.addCell(new jxl.write.Number(4,secModificados,listaPrecioAntigua.getPrecio(),wcfDatos));
						servicio.modificarPrecios(listaPrecioNueva);
					}
				}
				//finalizar de archivo
				w.write();
				w.close();
				out.close();
				Sesion.mensajeInformacion(mListaPrecios, "El proceso ha terminado correctamente, verifique la siguiente ruta\n"+ruta);
			}else{
				Sesion.mensajeInformacion(mListaPrecios, "La cantidad de columnas en excel incorrecto, verifique por favor.");
			}
			
			/*
			if(numColumnas==4){
				// Recorre cada fila de la hoja
				for (int fila = 1; fila < numFilas; fila++) {
					data = hoja.getCell(0, fila).getContents();// cod
					data1 = hoja.getCell(1, fila).getContents();// desc
					data2 = hoja.getCell(2, fila).getContents();// equ
					data3 = hoja.getCell(3, fila).getContents().replace(',', '.');// precio
					
					
					ListaPrecio listaPrecioNueva = new ListaPrecio();
					listaPrecioNueva.setCodigolp(data);
					listaPrecioNueva.setDescripcion(data1);
					listaPrecioNueva.setEqu(data2);
					listaPrecioNueva.setPrecio(Double.parseDouble(data3));
					listaPrecioNueva.setLpcodi(mListaPrecios.getListaPrecio());

					ListaPrecio listaPrecioAntigua = servicio
							.verificaPrecio(listaPrecioNueva);
					if (listaPrecioAntigua == null) {
						secNuevos++;							
						//generar hoja de texto de nuevos precios 
						s.addCell(new Label(0, secNuevos,data,wcfDatos));
						s.addCell(new Label(1, secNuevos,data1,wcfDatos));
						s.addCell(new Label(2, secNuevos, data2,wcfDatos));
						s.addCell(new jxl.write.Number(3, secNuevos, Double.parseDouble(data3),wcfDatos));
						
						
												
					} else {
						secModificados++;
						//generar hoja de texto de precios modificados antes y nuevo 
						s1.addCell(new Label(0, secModificados,data,wcfDatos));
						s1.addCell(new Label(1,secModificados,data1,wcfDatos));
						s1.addCell(new Label(2, secModificados, data2,wcfDatos));
						s1.addCell(new jxl.write.Number(3, secModificados, Double.parseDouble(data3),wcfDatos));
						s1.addCell(new jxl.write.Number(4,secModificados,listaPrecioAntigua.getPrecio(),wcfDatos));
						
						servicio.modificarPrecios(listaPrecioNueva);
					}
				}
			}else{
				// Recorre cada fila de la hoja
				for (int fila = 1; fila < numFilas; fila++) {

					data = hoja.getCell(0, fila).getContents();// cod
					data1 = hoja.getCell(1, fila).getContents();// desc
					data2 = hoja.getCell(2, fila).getContents();// equ
					data3 = hoja.getCell(3, fila).getContents().replace(',', '.');// precio
					data4 = hoja.getCell(4, fila).getContents();// moneda
					data5 = hoja.getCell(5, fila).getContents().replace(',', '.');// descuento
					//sec artcod precio codli moneda descuento 
					
					ListaPrecio listaPrecioNueva = new ListaPrecio();
					listaPrecioNueva.setCodigolp(data);
					listaPrecioNueva.setDescripcion(data1);
					listaPrecioNueva.setEqu(data2);
					listaPrecioNueva.setPrecio(Double.parseDouble(data3));
					listaPrecioNueva.setMoneda(data4);
					listaPrecioNueva.setDescuento(Double.parseDouble(data5));
					listaPrecioNueva.setLpcodi(mListaPrecios.getListaPrecio());

					ListaPrecio listaPrecioAntigua = servicio
							.verificaPrecio(listaPrecioNueva);
					if (listaPrecioAntigua == null) {
					
						//generar hoja de texto de nuevos precios 
						s.addCell(new Label(0, fila + 1,data,wcfDatos));
						s.addCell(new Label(1, fila + 1,data1,wcfDatos));
						s.addCell(new Label(2, fila + 1, data2,wcfDatos));
						s.addCell(new jxl.write.Number(3, fila + 1, Double.parseDouble(data3),wcfDatos));
																		
					//	servicio.registrarPrecios(listaPrecioNueva);
					} else {
						//generar hoja de texto de precios modificados antes y despues 
						s1.addCell(new Label(0, fila + 1,data,wcfDatos));
						s1.addCell(new Label(1, fila + 1,data1,wcfDatos));
						s1.addCell(new Label(2, fila + 1, data2,wcfDatos));
						s1.addCell(new jxl.write.Number(3, fila + 1, Double.parseDouble(data3),wcfDatos));
						s1.addCell(new jxl.write.Number(4, fila + 1,listaPrecioAntigua.getPrecio(),wcfDatos));
						
						//servicio.modificarPrecios(listaPrecioNueva);
					}
				}
			}
			*/
		} catch (Exception ioe) {
			ioe.printStackTrace();
			if(w==null){
				try {
					w.write();
					w.close();
					out.close();
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void procesarListadePrecios2(String archivoDestino) {
		int secNuevos=0;
		int secModificados=0;
		try {
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory
					.create(new FileInputStream(archivoDestino));
			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				Row row = sheet.getRow(j);
				if (row != null) {
					if (row.getLastCellNum() > -1) {
						if (!row.getCell(row.getLastCellNum()-1).getStringCellValue().trim().equals("")) {
							Object[] fila = new Object[40];
							fila[0] = j;
							for (int i = 0; i < row.getLastCellNum(); i++) {
								Cell c = row.getCell(i);
								if (c != null) {
									c.setCellType(Cell.CELL_TYPE_STRING);
									fila[i + 1] = c.getStringCellValue();
								} else {
									fila[i + 1] = " ";
								}
							}
							//dtm.addRow(fila);
						}
					}
				}
			}
		} catch (Exception e) {
			Sesion.mensajeError(null, "Mensaje: " + e.getMessage()
					+ "\nCausa: " + e.getCause());
		}
	}

}
