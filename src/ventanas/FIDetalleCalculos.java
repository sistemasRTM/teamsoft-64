package ventanas;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import bean.CalculoComision;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JLabel;
import org.jdesktop.swingx.JXTable;
import controlador.RRHHController;
import recursos.MaestroInternalFrame;
import recursos.Sesion;

public class FIDetalleCalculos extends MaestroInternalFrame {

	private static final long serialVersionUID = 1L;
	private JScrollPane scpDetalle;
	private JXTable tbDetalle;
	private DefaultTableModel dtm;
	private String[] cabecera = {"Código", "Nombre","P.V.","# Doc.","T.Doc","# Doc. Ori.","Categoria","Articulo","V.Venta","Descuento","% Descuento","Tipo Venta","Fam","SubFam","Origen","Flag"};
	private String[][] datos;
	private JLabel lblTotal;
	private List<CalculoComision> pedidos;
	private List<CalculoComision> notasCredito;
	private static FIDetalleCalculos gui=null;
	
	public static FIDetalleCalculos createInstance() {
		if (gui == null) {
			gui = new FIDetalleCalculos();
		}
		return gui;
	}

	public static FIDetalleCalculos getInstance() {
		return gui;
	}

	public FIDetalleCalculos() {
		//initialize(pedidos, notasCredito);
	}

	public void initialize(List<CalculoComision> pedidos,List<CalculoComision> notasCredito){
		this.pedidos = pedidos;
		this.notasCredito = notasCredito;
		setSize(1350, 675);

		//
		setTitle(getTitle() + "-" + Sesion.tifDetalleCalculoComision);
		toolBar.setVisible(true);
		btnExcel.setVisible(true);
		btnSalir.setVisible(true);
		//

		dtm = new DefaultTableModel(datos, cabecera);
		tbDetalle = new JXTable(dtm);
		tbDetalle.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		scpDetalle = new JScrollPane(tbDetalle);
		scpDetalle.setBounds(10, 11, 1312, 574);
		contenedorCenter.add(scpDetalle);

		lblTotal = new JLabel("");
		lblTotal.setBounds(10, 596, 302, 14);
		contenedorCenter.add(lblTotal);

		cargarTabla();
	}
	
	private void limpiarTabla() {
		tbDetalle.setModel(dtm = new DefaultTableModel(datos, cabecera));
	
		TableColumn tcCodVend = tbDetalle.getColumn(0);
		TableColumn tcNomVen = tbDetalle.getColumn(1);
		TableColumn tcPV = tbDetalle.getColumn(2);
		TableColumn tcNumDoc = tbDetalle.getColumn(3);
		TableColumn tcTDoc = tbDetalle.getColumn(4);
		TableColumn tcNumDocOri = tbDetalle.getColumn(5);
		TableColumn tcCategoria = tbDetalle.getColumn(6);
		TableColumn tcCodArt = tbDetalle.getColumn(7);
		TableColumn tcVVenta = tbDetalle.getColumn(8);
		TableColumn tcDesc = tbDetalle.getColumn(9);
		TableColumn tcPorcDesc = tbDetalle.getColumn(10);
		TableColumn tcTipoVenta = tbDetalle.getColumn(11);
		
		tcNumDoc.setPreferredWidth(50);
		tcCodVend.setPreferredWidth(50);
		tcPV.setPreferredWidth(50);
		tcTDoc.setPreferredWidth(60);
		tcNumDocOri.setPreferredWidth(65);
		tcVVenta.setPreferredWidth(80);
		tcDesc.setPreferredWidth(80);
		tcTipoVenta.setPreferredWidth(90);
		tcPorcDesc.setPreferredWidth(95);
		tcCodArt.setPreferredWidth(100);
		tcNomVen.setPreferredWidth(230);
		tcCategoria.setPreferredWidth(360);
	}

	private void cargarTabla() {
		limpiarTabla();
		int i = 0;
		Object[] cabePedidos = {"","Pedidos","","","","","","","","","",""}; 
		Object[] colTalPed = {"phusap","agenom","phpvta","phnume","phtdoc","","","pdarti","pdnvva","pdnds2","pdref1","","Fam","SubFam","Origen","Flag"}; 
		dtm.addRow(cabePedidos);
		dtm.addRow(colTalPed);
		for (CalculoComision c : pedidos) {
				Object[] datos = { c.getPhusap().trim(),c.getAgenom().trim(),c.getPhpvta(),c.getPhnume(),c.getPdtdoc(),"",
						c.getArti().trim(),c.getNvva(), c.getNds2(),c.getRef1(),c.getTipoVenta(),c.getArtfam(),c.getArtsfa(),c.getOrigen(),c.isFlag() };
				dtm.addRow(datos);
			
		}
		Object[] cabeNA = {"","Notas de Abono","","","","","","","","","",""}; 
		Object[] colTalNA = {"phusap","agenom","nhpvta","nhnume","nhtdoc","nhfabo","","ncarti","ncnvva","ncnds2","ncref1","","Fam","SubFam","Origen","Flag"}; 
		dtm.addRow(cabeNA);
		dtm.addRow(colTalNA);
		for (CalculoComision c : notasCredito) {
				Object[] datos = { c.getPhusap().trim(),c.getAgenom().trim(),c.getNHPVTN(),c.getNHNUME(),c.getNHTDOC(),c.getNHFABO(),
						 c.getArti().trim(),c.getNvva(), c.getNds2(),c.getRef1(),c.getTipoVenta(),c.getArtfam(),c.getArtsfa(),c.getOrigen(),c.isFlag() };
				dtm.addRow(datos);
		}
		lblTotal.setText("Total Registros: " + i);
	}
		
	public JButton getBtnSalir(){
		return btnSalir;
	}
	
	public JButton getBtnExcel(){
		return btnExcel;
	}
	
	public JXTable getTabla(){
		return tbDetalle;
	}
	
	public static void close(){
		gui = null;
	}
	
	public void salir(){
		dispose();
	}
	
	public void setControlador(RRHHController controlador){
		btnExcel.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
	}
	
}
