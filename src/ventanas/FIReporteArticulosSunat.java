package ventanas;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import controlador.ContabilidadController;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import recursos.TextFilterDocument;
import javax.swing.JCheckBox;

public class FIReporteArticulosSunat extends MaestroInternalFrame{


	private static final long serialVersionUID = 1L;
	private static FIReporteArticulosSunat gui=null;
	private JTextField txtEjercicio;
	private JButton btnProcesar;
	private JLabel lblEjercicio;
	private TextFilterDocument tfdEjercicio = new TextFilterDocument(
			TextFilterDocument.DIGITS, 4);
	private JCheckBox chckbxCabecera;

	public static FIReporteArticulosSunat createInstance() {
		if (gui == null) {
			gui = new FIReporteArticulosSunat();
		}
		return gui;
	}

	public static FIReporteArticulosSunat getInstance() {
		return gui;
	}
	
	public FIReporteArticulosSunat() {
		//initialize();
	}
	
	public void initialize() {
		setSize(321, 138);
		setTitle(Sesion.titulo+"-"+Sesion.tifReporteArticulosSunat);
		//
		toolBar.setVisible(true);
		//
		lblEjercicio = new JLabel("Ejercicio:");
		lblEjercicio.setBounds(26, 13, 73, 14);
		contenedorCenter.add(lblEjercicio);

		txtEjercicio = new JTextField();
		txtEjercicio.setBounds(109, 11, 46, 20);
			
		txtEjercicio.setDocument(tfdEjercicio);
		contenedorCenter.add(txtEjercicio);

		btnProcesar = new JButton("Procesar");
		btnProcesar.setEnabled(false);
		btnProcesar.setBounds(165, 11, 104, 23);
		contenedorCenter.add(btnProcesar);
		
		chckbxCabecera = new JCheckBox("Cabecera");
		chckbxCabecera.setBounds(21, 34, 97, 23);
		contenedorCenter.add(chckbxCabecera);
		
		txtEjercicio.requestFocus();
	}
	public String getEjercicio() {
		return txtEjercicio.getText().trim();
	}
	
	public JTextField getTxtEjercicio(){
		return txtEjercicio;
	}
	
	public JButton getProcesar() {
		return btnProcesar;
	}

	public JButton getSalir() {
		return btnSalir;
	}
	public JButton getExportarTXT() {
		return btnExportarTxt;
	}
	
	
	public JButton getCancelar(){
		return btnCancelar;
	}
	
	public void setControlador(ContabilidadController controlador) {
		btnProcesar.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
		txtEjercicio.addActionListener(controlador);
	}
	
	public static void close(){
		gui = null;
	}
	
	public void salir(){
		dispose();
	}

	public boolean isHeader(){
		return chckbxCabecera.isSelected();
	}
}
