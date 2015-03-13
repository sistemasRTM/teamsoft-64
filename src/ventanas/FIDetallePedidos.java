package ventanas;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.jdesktop.swingx.JXDatePicker;

import controlador.FacturacionController;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import recursos.TextFilterDocument;

public class FIDetallePedidos extends MaestroInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private static FIDetallePedidos gui = null;
	private String fechaDesde;
	private String fechaHasta;
	private JXDatePicker dtpFechaDesde;
	private JLabel lblFechaDesde;
	private JXDatePicker dtpFechaHasta;
	private JLabel lblFechaHasta;
	private JButton btnProcesar;
	//private TextFilterDocument tfdfechaDesde = new TextFilterDocument(
		//	TextFilterDocument.DIGITS, 10);
	//private TextFilterDocument tfdFechaHasta = new TextFilterDocument(
		//	TextFilterDocument.DIGITS, 10);
	
	public static FIDetallePedidos createInstance() {
		if (gui == null) {
			gui = new FIDetallePedidos();
		}
		return gui;
	}

	public static FIDetallePedidos getInstance() {
		return gui;
	}
	
	public FIDetallePedidos() {
		//initialize();
	}
	
	
	public void initialize() {
		
		setSize(428, 180);
		setTitle(getTitle()+ " - " + Sesion.tifDetallePedido);
		toolBar.setVisible(true);
		btnSalir.setVisible(true);
		
		lblFechaDesde = new JLabel("F.Desde");
		lblFechaDesde.setBounds(38, 36, 57, 14);
		contenedorCenter.add(lblFechaDesde);
		
		dtpFechaDesde = new JXDatePicker();
		dtpFechaDesde.setBounds(95, 34, 86, 20);
		Sesion.formateaDatePicker(dtpFechaDesde);
		contenedorCenter.add(dtpFechaDesde);
		
		lblFechaHasta = new JLabel("F.hasta");
		lblFechaHasta.setBounds(38, 64, 46, 14);
		contenedorCenter.add(lblFechaHasta);
		
		dtpFechaHasta = new JXDatePicker();
		dtpFechaHasta.setBounds(95, 60, 86, 20);
		Sesion.formateaDatePicker(dtpFechaHasta);
		contenedorCenter.add(dtpFechaHasta);
		
		btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(210, 32, 89, 23);
		contenedorCenter.add(btnProcesar);
		
	}
	
	public void setControlador(FacturacionController controlador){
		dtpFechaDesde.addActionListener(controlador);
		dtpFechaHasta.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
		btnProcesar.addActionListener(controlador);
	}
	
	public static void close() {
		gui = null;
	}

	public void salir() {
		dispose();
	}
	
	public JButton getBtnSalir(){
		return btnSalir;
	}
	
	public JButton getBtnProcesar(){
		return btnProcesar;
	}
	
	public JXDatePicker getDtpFechaDesde(){
		return dtpFechaDesde;
	}
	
	public JXDatePicker getDtpFechaHasta(){
		return dtpFechaHasta;
	}
	
	
	

}
