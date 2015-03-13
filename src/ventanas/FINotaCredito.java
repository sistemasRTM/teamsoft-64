package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import persistencia.NotaCreditoDAO;
import controlador.FacturacionController;
import recursos.GenerateExcel;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import recursos.TextFilterDocument;

public class FINotaCredito extends MaestroInternalFrame{
	
	private static final long serialVersionUID = 1L;
	private static FINotaCredito gui = null;
	private String ejer;
	private String peri;
	private JTextField txtEjercicio;
	private JLabel lblEjercicio;
	private JTextField txtPeriodo;
	private JLabel lblPeriodo;
	private JButton btnProcesar;
	private TextFilterDocument tfdEjercicio = new TextFilterDocument(
			TextFilterDocument.DIGITS, 4);
	private TextFilterDocument tfdPeriodo = new TextFilterDocument(
			TextFilterDocument.DIGITS, 2);
	
	
	
	public static FINotaCredito createInstance() {
		if (gui == null) {
			gui = new FINotaCredito();
		}
		return gui;
	}

	public static FINotaCredito getInstance() {
		return gui;
	}
	
	public FINotaCredito() {
		//initialize();
	}

	public void initialize() {
		setSize(428, 180);
		setTitle(getTitle()+ " - " + Sesion.tifConsultaNotaCredito);
		toolBar.setVisible(true);
		btnSalir.setVisible(true);
		
		lblEjercicio = new JLabel("Ejercicio");
		lblEjercicio.setBounds(38, 36, 57, 14);
		contenedorCenter.add(lblEjercicio);
		
		txtEjercicio = new JTextField();
		txtEjercicio.setBounds(95, 34, 86, 20);
		txtEjercicio.setDocument(tfdEjercicio);
		contenedorCenter.add(txtEjercicio);
			
		lblPeriodo = new JLabel("Periodo");
		lblPeriodo.setBounds(38, 64, 46, 14);
		contenedorCenter.add(lblPeriodo);
		
		txtPeriodo = new JTextField();
		txtPeriodo.setBounds(95, 60, 86, 20);
		txtPeriodo.setDocument(tfdPeriodo);
		contenedorCenter.add(txtPeriodo);
		
		btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(210, 32, 89, 23);
		contenedorCenter.add(btnProcesar);
	}
	
	public void setControlador(FacturacionController controlador){
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
	
	public JTextField getTxtEjercicio(){
		return txtEjercicio;
	}
	
	public JTextField getTxtPeriodo(){
		return txtPeriodo;
	}
	
}
