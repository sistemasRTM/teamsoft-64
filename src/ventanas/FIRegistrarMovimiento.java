package ventanas;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import controlador.ContabilidadController;
import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import recursos.TextFilterDocument;
import util.Conexion;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FIRegistrarMovimiento extends MaestroInternalFrame{
	private static final long serialVersionUID = 1L;
	private static FIRegistrarMovimiento gui=null;
	private JLabel lblMovimiento;
	private JLabel lblTipo;
	private JLabel lblAlmacen;
	private MaestroComboBox cboMovimiento;
	private MaestroComboBox cboTipo;
	private MaestroComboBox cboAlmacen;
	private JLabel lblFecha;
	private JLabel lblTipoDoc;
	private JXDatePicker dtpFecha;
	private MaestroComboBox cboTipoDoc;
	private JTextField txtNumero;
	private JTextField txtNumeroDocCliente;
	private JLabel lblNmeroDocIden;
	private JLabel lblNmero;
	private JLabel lblTipoDocIden;
	private MaestroComboBox cboTipoDocCliente;
	private TextFilterDocument tftNumero = new TextFilterDocument(TextFilterDocument.DIGITS,20);
	private TextFilterDocument tftNumeroCli = new TextFilterDocument(TextFilterDocument.DIGITS,20);
	private JButton btnImportar;
	private JScrollPane scpDatos;
	private JXTable tbDatos;
	private DefaultTableModel dtmDatos;
	private String cabecera[]={"Artículo","Cantidad"};
	private String datos[][];
	private JButton btnGrabar;
	File fileO = null;
	ArrayList listado = new ArrayList();
	
	public static FIRegistrarMovimiento createInstance() {
		if (gui == null) {
			gui = new FIRegistrarMovimiento();
		}
		return gui;
	}

	public static FIRegistrarMovimiento getInstance() {
		return gui;
	}

	public FIRegistrarMovimiento() {
		initialize();
	}
	
	public void initialize(){
		setSize(1350, 660);
		//
		setTitle(getTitle() + "-" + Sesion.tifRegistroMovimiento);
				
		lblMovimiento = new JLabel("Movimiento:");
		lblMovimiento.setBounds(10, 25, 84, 14);
		contenedorCenter.add(lblMovimiento);
		
		cboMovimiento = new MaestroComboBox();
		cboMovimiento.setBounds(92, 22, 65, 20);
		contenedorCenter.add(cboMovimiento);
		
		lblTipo = new JLabel("Tipo Mov.:");
		lblTipo.setBounds(167, 25, 65, 14);
		contenedorCenter.add(lblTipo);
		
		cboTipo = new MaestroComboBox();
		cboTipo.setBounds(235, 22, 199, 20);
		contenedorCenter.add(cboTipo);
		
		lblAlmacen = new JLabel("Almac\u00E9n:");
		lblAlmacen.setBounds(436, 25, 65, 14);
		contenedorCenter.add(lblAlmacen);
		
		cboAlmacen = new MaestroComboBox();
		cboAlmacen.setBounds(503, 22, 215, 20);
		contenedorCenter.add(cboAlmacen);
		toolBar.setVisible(true);
				
		dtpFecha = new JXDatePicker();
		Sesion.formateaDatePicker(dtpFecha);
		dtpFecha.setBounds(92, 55, 100, 22);
		contenedorCenter.add(dtpFecha);
		
		lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(10, 63, 46, 14);
		contenedorCenter.add(lblFecha);
		
		lblTipoDoc = new JLabel("Tipo Doc.:");
		lblTipoDoc.setBounds(213, 59, 65, 14);
		contenedorCenter.add(lblTipoDoc);
		
		cboTipoDoc = new MaestroComboBox();
		cboTipoDoc.setBounds(282, 56, 152, 20);
		contenedorCenter.add(cboTipoDoc);
		
		lblNmero = new JLabel("N\u00FAmero:");
		lblNmero.setBounds(436, 59, 65, 14);
		contenedorCenter.add(lblNmero);
		
		txtNumero = new JTextField();
		txtNumero.setBounds(503, 53, 215, 20);
		txtNumero.setDocument(tftNumero);
		contenedorCenter.add(txtNumero);
		
		lblTipoDocIden = new JLabel("Tipo Doc. Iden.:");
		lblTipoDocIden.setBounds(10, 104, 100, 14);
		contenedorCenter.add(lblTipoDocIden);
		
		cboTipoDocCliente = new MaestroComboBox();
		cboTipoDocCliente.setBounds(115, 101, 163, 20);
		cboTipoDocCliente.setEnabled(false);
		contenedorCenter.add(cboTipoDocCliente);
		
		lblNmeroDocIden = new JLabel("N\u00FAmero Doc Iden.:");
		lblNmeroDocIden.setBounds(301, 104, 110, 14);
		contenedorCenter.add(lblNmeroDocIden);
		
		txtNumeroDocCliente = new JTextField();
		txtNumeroDocCliente.setBounds(480, 101, 115, 20);
		txtNumeroDocCliente.setDocument(tftNumeroCli);
		txtNumeroDocCliente.setEnabled(false);
		contenedorCenter.add(txtNumeroDocCliente);
		
		btnImportar = new JButton("Importar");
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				elegirOrigen();
				if(fileO!= null){
					listado.clear();
					listado = LeerExcel(fileO);
					for (int i = 0; i < listado.size(); i++) {
						ArrayList fila = (ArrayList)listado.get(i);
						Object[] datos = {fila.get(1),fila.get(2)};
						dtmDatos.addRow(datos);
					}
				}
			}
		});
		btnImportar.setBounds(10, 142, 89, 23);
		contenedorCenter.add(btnImportar);
		
		dtmDatos = new DefaultTableModel(datos,cabecera);
		tbDatos = new JXTable(dtmDatos);		
		scpDatos = new JScrollPane(tbDatos);
		scpDatos.setBounds(10, 176, 316, 200);
		contenedorCenter.add(scpDatos);
		
		MaestroBean mbSalida = new MaestroBean();
		mbSalida.setCodigo("S");
		mbSalida.setDescripcion("Salida");
		
		MaestroBean mbIngreso = new MaestroBean();
		mbIngreso.setCodigo("I");
		mbIngreso.setDescripcion("Ingreso");
		
		cboMovimiento.addItem(mbIngreso);
		cboMovimiento.addItem(mbSalida);
		
		MaestroBean mbFactura = new MaestroBean();
		mbFactura.setCodigo("FC");
		mbFactura.setDescripcion("FACTURA");
		
		MaestroBean mbBoleta = new MaestroBean();
		mbBoleta.setCodigo("BV");
		mbBoleta.setDescripcion("BOLETA");
		
		MaestroBean mbNotaCredito = new MaestroBean();
		mbNotaCredito.setCodigo("NC");
		mbNotaCredito.setDescripcion("NOTA DE CREDITO");
		
		MaestroBean mbGuiaRemision = new MaestroBean();
		mbGuiaRemision.setCodigo("GR");
		mbGuiaRemision.setDescripcion("GUIA DE REMISION");
		
		MaestroBean mbNotaDebito = new MaestroBean();
		mbNotaDebito.setCodigo("ND");
		mbNotaDebito.setDescripcion("NOTA DE DEBITO");
		
		cboTipoDoc.addItem(mbFactura);
		cboTipoDoc.addItem(mbBoleta);
		cboTipoDoc.addItem(mbGuiaRemision);
		cboTipoDoc.addItem(mbNotaDebito);
		cboTipoDoc.addItem(mbNotaCredito);
		
		btnGrabar = new JButton("Grabar");
		btnGrabar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mdcmovb = ((MaestroBean) cboMovimiento.getSelectedItem()).getCodigo();
				String mdtmovb = ((MaestroBean) cboTipo.getSelectedItem()).getCodigo();
				String mdalmab = ((MaestroBean) cboAlmacen.getSelectedItem()).getCodigo();
				String mdfechb = dtpFecha.getEditor().getText();
				String pdtdocb = ((MaestroBean) cboTipoDoc.getSelectedItem()).getCodigo();
				String pdndocb = txtNumero.getText().trim();
				String cltide = ((MaestroBean) cboTipoDocCliente.getSelectedItem()).getCodigo();
				String clnideb = txtNumeroDocCliente.getText().trim();
				
				int reg=0;
				if(mdcmovb.equals("S")){
					if(!clnideb.equals("")){
					for (int i = 0; i < listado.size(); i++) {
						ArrayList fila = (ArrayList)listado.get(i);
	
						try {
							
							PreparedStatement pstm = Conexion.obtenerConexion().
									prepareStatement("insert into prodtecni.regmov(mdcmovb ,mdtmovb ,mdalmab ,mdfechb ,pdtdocb ,pdndocb ,clnideb ,cltide ,mdcoarb ,mdcanrb,usuario,fecha ) values(?,?,?,?,?,?,?,?,?,?,?,?)");
							pstm.setString(1, mdcmovb);
							pstm.setString(2, mdtmovb);
							pstm.setString(3, mdalmab);
							pstm.setString(4, mdfechb);
							pstm.setString(5, pdtdocb);
							pstm.setString(6, pdndocb);
							pstm.setString(7, clnideb);
							pstm.setString(8, cltide);
							pstm.setString(9, fila.get(1).toString());
							pstm.setInt(10, Integer.parseInt(fila.get(2).toString()));
							pstm.setString(11, Sesion.usuario);
							pstm.setString(12, Sesion.getFechaActualVista2());
							reg+=pstm.executeUpdate();
							pstm.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
					}
					}else{
						Sesion.mensajeError(gui, "ingrese numero de documento del cliente" );
					}
				}else{
					for (int i = 0; i < listado.size(); i++) {
						ArrayList fila = (ArrayList)listado.get(i);
						Object[] datos = {fila.get(1),fila.get(2)};
						try {
							
							PreparedStatement pstm = Conexion.obtenerConexion().
									prepareStatement("insert into prodtecni.regmov(mdcmovb ,mdtmovb ,mdalmab ,mdfechb ,pdtdocb ,pdndocb ,mdcoarb ,mdcanrb,usuario,fecha ) values(?,?,?,?,?,?,?,?,?,?)");
							pstm.setString(1, mdcmovb);
							pstm.setString(2, mdtmovb);
							pstm.setString(3, mdalmab);
							pstm.setString(4, mdfechb);
							pstm.setString(5, pdtdocb);
							pstm.setString(6, pdndocb);
							pstm.setString(7, fila.get(1).toString());
							pstm.setInt(8, Integer.parseInt(fila.get(2).toString()));
							pstm.setString(9, Sesion.usuario);
							pstm.setString(10, Sesion.getFechaActualVista2());
							reg+=pstm.executeUpdate();
							pstm.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
					}
				}
				Sesion.mensajeInformacion(gui, "Se han registrado "+ reg+ " registros." );
				
			}
		});
		btnGrabar.setBounds(10, 387, 89, 23);
		contenedorCenter.add(btnGrabar);
		
		cboTipo.executeQuery("ttima", "tmtipo", "tmtipo || '-' ||tmdesc"," tmclas='I' order by tmtipo ");	
		cboAlmacen.executeQuery("talma", "alcodi", "alcodi || '-' ||alnomb");
		cboTipoDocCliente.executeQuery("ttabd", "tbespe", "desesp", " tbiden ='UGDID' ");
		
		cboMovimiento.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					MaestroBean mb = (MaestroBean)cboMovimiento.getSelectedItem();
					cboTipo.removeAllItems();
					cboTipo.executeQuery("ttima", "tmtipo", "tmtipo || '-' ||tmdesc"," tmclas='"+mb.getCodigo()+"' order by tmtipo ");
					cboTipo.setSelectedIndex(0);
					
					if(mb.getDescripcion().equals("Salida")){
						txtNumeroDocCliente.setEnabled(true);
						cboTipoDocCliente.setEnabled(true);
						txtNumeroDocCliente.setText("");
					}
					
					if(mb.getDescripcion().equals("Ingreso")){
						txtNumeroDocCliente.setEnabled(false);
						cboTipoDocCliente.setEnabled(false);
						txtNumeroDocCliente.setText("");
					}
				}
			}
		});
		
	}
	
	public JButton getBtnSalir(){
		return btnSalir;
	}
	
	public static void close(){
		gui = null;
	}
	
	public void salir(){
		dispose();
	}
	
	public void setControlador(ContabilidadController controlador) {
		btnSalir.addActionListener(controlador);
	}
	
	
	private void elegirOrigen() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			fileO = fileChooser.getSelectedFile();
			if (!fileO.isFile()) {
				Sesion.mensajeError(
						null,
						"Seleccione un archivo correcto.");
				elegirOrigen();
			} 
		}
	}
	
	public static ArrayList LeerExcel(File file) {
		ArrayList datos = new ArrayList();
		try {
			Workbook workbook = WorkbookFactory
					.create(new FileInputStream(file));
			Sheet sheet = workbook.getSheetAt(0);
			for (int j = 0; j <= sheet.getLastRowNum(); j++) {
				Row row = sheet.getRow(j);
				ArrayList fila = new ArrayList();
				fila.add(j + " ");
				for (int i = 0; i < row.getLastCellNum(); i++) {
					Cell c = row.getCell(i);
					if (c != null) {
						c.setCellType(Cell.CELL_TYPE_STRING);
						String celda = c.getStringCellValue();
						if(celda.contains(".") || celda.contains(",")){
							double decimal = Double.parseDouble(celda);
							celda = Sesion.formatoDecimalVista(decimal);
						}
						fila.add(celda);
					} else {
						fila.add(" ");
					}
				}
				datos.add(fila);
			}
		} catch (Exception e) {
			Sesion.mensajeError(null, "Mensaje: " + e.getMessage()
					+ "\nCausa: " + e.getCause());
		}
		return datos;
	}
	
}
