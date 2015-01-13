package ventanas;

import javax.swing.*;

import controlador.ContabilidadController;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import recursos.TextFilterDocument;

public class FIReImpresionDocumentos extends MaestroInternalFrame{
	private static final long serialVersionUID = 1L;
	private static FIReImpresionDocumentos gui = null;
	private JTextField txtDesde;
	private JLabel lblTDocumento;
	private JButton btnProcesar;
	private JLabel lblSerie;
	private JLabel lblDesde;
	private JLabel lblHasta;
	private JTextField txtSerie;
	private JTextField txtHasta;
	private JComboBox cboTDoc;
	private TextFilterDocument tfdSerie = new TextFilterDocument(TextFilterDocument.DIGITS,3);
	private TextFilterDocument tfdDesde = new TextFilterDocument(TextFilterDocument.DIGITS,10);
	private TextFilterDocument tfdHasta = new TextFilterDocument(TextFilterDocument.DIGITS,10);
	
	public static FIReImpresionDocumentos createInstance() {
		if (gui == null) {
			gui = new FIReImpresionDocumentos();
		}
		return gui;
	}

	public static FIReImpresionDocumentos getInstance() {
		return gui;
	}
	
	public FIReImpresionDocumentos() {
		//initialize();
	}
	
	public void initialize() {
		setSize(584, 105);
		setTitle(getTitle() + " - " +  Sesion.tifReImpresion);
		toolBar.setVisible(true);
		
		lblSerie = new JLabel("Serie:");
		lblSerie.setBounds(10, 10, 46, 14);
		contenedorCenter.add(lblSerie);
		
		txtSerie = new JTextField();
		txtSerie.setBounds(42, 7, 51, 20);
		txtSerie.setDocument(tfdSerie);
		contenedorCenter.add(txtSerie);
		
		lblTDocumento = new JLabel("T.Doc.");
		lblTDocumento.setBounds(99, 10, 39, 14);
		contenedorCenter.add(lblTDocumento);
		
		cboTDoc = new JComboBox();
		cboTDoc.setBounds(138, 7, 46, 20);
		cboTDoc.addItem("FC");
		cboTDoc.addItem("BV");
		cboTDoc.addItem("ND");
		cboTDoc.addItem("NC");
		contenedorCenter.add(cboTDoc);

		lblDesde = new JLabel("Desde:");
		lblDesde.setBounds(194, 10, 58, 14);
		contenedorCenter.add(lblDesde);
		
		txtDesde = new JTextField();
		txtDesde.setBounds(234, 7, 86, 20);
		txtDesde.setDocument(tfdDesde);
		contenedorCenter.add(txtDesde);

		lblHasta = new JLabel("Hasta:");
		lblHasta.setBounds(330, 10, 58, 14);
		contenedorCenter.add(lblHasta);
	
		txtHasta = new JTextField();
		txtHasta.setBounds(368, 7, 86, 20);
		txtHasta.setDocument(tfdHasta);
		contenedorCenter.add(txtHasta);
		
		btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(464, 6, 86, 23);
		contenedorCenter.add(btnProcesar);
	}
	
	public JButton getBtnSalir() {
		return btnSalir;
	}
	
	public JButton getBtnProcesar() {
		return btnProcesar;
	}
	
	public String getSerie(){
		return txtSerie.getText().trim();
	}
		
	public String getTipo(){
		return cboTDoc.getSelectedItem().toString();
	}
	
	public String getDesde(){
		return txtDesde.getText().trim();
	}
	
	public String getHasta(){
		return txtHasta.getText().trim();
	}
	
	public void setControlador(ContabilidadController controlador){
		btnSalir.addActionListener(controlador);
		btnProcesar.addActionListener(controlador);
	}
	
	public static void close() {
		gui = null;
	}

	public void salir() {
		dispose();
	}
}
