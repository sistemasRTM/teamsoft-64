package recursos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class GenerateExcel {
	
	private String fileD;
	private String sheetname="Hoja1";
	private int columnas;
	private ArrayList<int[]> merges = new ArrayList<int[]>() ;
	private int cabecera=0;
	
	public GenerateExcel(){
		
	}
	//dparreno
	public GenerateExcel(JTable table) {		
		int value = elegirDestino();
		if(value == JFileChooser.APPROVE_OPTION){
			try {
				FileOutputStream fileOut = new FileOutputStream(fileD+".xlsx");
				XSSFWorkbook wb = new XSSFWorkbook();				
				XSSFCellStyle style = wb.createCellStyle();
				style.setBorderBottom((short) 1);
				style.setBorderLeft((short) 1);
				style.setBorderRight((short) 1);
				style.setBorderTop((short) 1);
				XSSFSheet sheet = wb.createSheet(sheetname) ;
				for (int i = 0; i < table.getRowCount(); i++) {
					XSSFRow row = sheet.createRow(i);
					for (int j = 0; j < table.getColumnCount(); j++) {
						XSSFCell cell= row.createCell(j);
						cell.setCellValue(table.getValueAt(i,j)+"");
						cell.setCellStyle(style);
					}
				}
				
				for (int i = 0; i < table.getColumnCount(); i++) {
					sheet.autoSizeColumn(i, true);
				}
				wb.write(fileOut);
				fileOut.flush();
				fileOut.close();
				JOptionPane.showMessageDialog(null, "Se ha generado el excel en la siguiente ruta:\n"+fileD+".xlsx");
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public GenerateExcel(ArrayList<String[]> filas) {		
		//procesar(filas);
		procesarXLSX(filas);
	}
	
	public GenerateExcel(ArrayList<String[]> filas,ArrayList<int[]> merges) {		
		procesar(filas);
		this.merges = merges;
	}
	
	public void run(ArrayList<String[]> filas){
		procesar(filas);
	}
	
	private void procesar(ArrayList<String[]> filas){
		int value = elegirDestino();
		if(value == JFileChooser.APPROVE_OPTION){
			try {
				FileOutputStream fileOut = new FileOutputStream(fileD+".xls");
				HSSFWorkbook wb = new HSSFWorkbook();//	HSSFWorkbook esto genera un archivo excel xls lo demas son dependencias como parte del excel			
				HSSFCellStyle styleBody = wb.createCellStyle();
				styleBody.setBorderBottom((short) 1);
				styleBody.setBorderLeft((short) 1);
				styleBody.setBorderRight((short) 1);
				styleBody.setBorderTop((short) 1);
				
				HSSFCellStyle styleHead = wb.createCellStyle();
				styleHead.setAlignment(CellStyle.ALIGN_CENTER);
				styleHead.setBorderBottom((short) 1);
				styleHead.setBorderLeft((short) 1);
				styleHead.setBorderRight((short) 1);
				styleHead.setBorderTop((short) 1);
				
				HSSFSheet sheet = wb.createSheet(sheetname) ;
				
				for (int i = 0; i < cabecera; i++) {
					String[] fila = filas.get(i);
					columnas = fila.length;
					HSSFRow row = sheet.createRow(i);
					for (int j = 0; j < fila.length; j++) {
						HSSFCell cell= row.createCell(j);
						cell.setCellValue(fila[j]);
						cell.setCellStyle(styleHead);
					}
				}
				
				for (int i = cabecera; i < filas.size(); i++) {
					String[] fila = filas.get(i);
					columnas = fila.length;
					HSSFRow row = sheet.createRow(i);
					for (int j = 0; j < fila.length; j++) {
						HSSFCell cell= row.createCell(j);
						cell.setCellValue(fila[j]);
						cell.setCellStyle(styleBody);
					}
				}
				
				
				for (int[] merge : merges) {
					sheet.addMergedRegion(new CellRangeAddress(merge[0], merge[1], merge[2], merge[3]));
				}
				
				for (int i = 0; i < columnas; i++) {
					sheet.autoSizeColumn(i, true);
				}
				System.out.println(columnas);
				wb.write(fileOut);
							
				fileOut.flush();
				fileOut.close();
				JOptionPane.showMessageDialog(null, "Se ha generado el excel en la siguiente ruta:\n"+fileD+".xls");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setCabecera(int cabecera){
		this.cabecera = cabecera;
	}
	
	public void addMergedRegion(int firstRow,int lastRow,int firstCol,int lastCol){
		//sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
		//sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 0));
		merges.add(new int[]{firstRow, lastRow, firstCol, lastCol});
	}
	
	private int elegirDestino() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnValue = fileChooser.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File fileGeneral = fileChooser.getSelectedFile();
			String ruta = fileGeneral.toString();
			fileD = ruta;
		}
		return returnValue;
	}
	
	//dparreno
	private void procesarXLSX(ArrayList<String[]> filas){
		int value = elegirDestino();
		if(value == JFileChooser.APPROVE_OPTION){
			try {
				FileOutputStream fileOut = new FileOutputStream(fileD+".xlsx");
				XSSFWorkbook wb = new XSSFWorkbook();//	XSSFWorkbook esto genera un archivo excel xlsx lo demas son dependencias como parte del excel				
				XSSFCellStyle styleBody = wb.createCellStyle();
				styleBody.setBorderBottom((short) 1);
				styleBody.setBorderLeft((short) 1);
				styleBody.setBorderRight((short) 1);
				styleBody.setBorderTop((short) 1);
				
				XSSFCellStyle styleHead = wb.createCellStyle();
				styleHead.setAlignment(CellStyle.ALIGN_CENTER);
				styleHead.setBorderBottom((short) 1);
				styleHead.setBorderLeft((short) 1);
				styleHead.setBorderRight((short) 1);
				styleHead.setBorderTop((short) 1);
				
				XSSFSheet sheet = wb.createSheet(sheetname) ;
				
				for (int i = 0; i < cabecera; i++) {
					String[] fila = filas.get(i);
					columnas = fila.length;
					XSSFRow row = sheet.createRow(i);
					for (int j = 0; j < fila.length; j++) {
						XSSFCell cell= row.createCell(j);
						cell.setCellValue(fila[j]);
						cell.setCellStyle(styleHead);
					}
				}
				
				for (int i = cabecera; i < filas.size(); i++) {
					String[] fila = filas.get(i);
					columnas = fila.length;
					XSSFRow row = sheet.createRow(i);
					for (int j = 0; j < fila.length; j++) {
						XSSFCell cell= row.createCell(j);
						cell.setCellValue(fila[j]);
						cell.setCellStyle(styleBody);
					}
				}
				
				
				for (int[] merge : merges) {
					sheet.addMergedRegion(new CellRangeAddress(merge[0], merge[1], merge[2], merge[3]));
				}
				
				for (int i = 0; i < columnas; i++) {
					sheet.autoSizeColumn(i, true);
				}
				System.out.println(columnas);
				wb.write(fileOut);
							
				fileOut.flush();
				fileOut.close();
				JOptionPane.showMessageDialog(null, "Se ha generado el excel en la siguiente ruta:\n"+fileD+".xls");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}