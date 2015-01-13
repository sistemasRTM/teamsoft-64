package ventanas;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import recursos.MaestroComboBox;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import controlador.PreciosController;

public class FIMantenimientoListaPrecios extends MaestroInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtOrigen;
	private JTextField txtDestino;
	private JLabel lblListaDePrecios;
	private JComboBox cboListaPrecio;
	private JLabel lblSeleccioneExcel;
	private JButton btnOrigen;
	private JButton btnDestino;
	private JLabel lblSeleccioneDestino;
	private JButton btnProcesar;

	public FIMantenimientoListaPrecios() {
		setSize(606, 225);
		setTitle(Sesion.titulo+"-"+Sesion.tifListaPrecio);
		//
		toolBar.setVisible(true);

		cboListaPrecio = new MaestroComboBox("tlprh","distinct lpcodi","lpcodi as descrip");
		cboListaPrecio.setBounds(222, 26, 58, 20);
		contenedorCenter.add(cboListaPrecio);
		
		lblListaDePrecios = new JLabel("Lista de Precios:");
		lblListaDePrecios.setHorizontalAlignment(SwingConstants.LEFT);
		lblListaDePrecios.setBounds(17, 29, 202, 14);
		contenedorCenter.add(lblListaDePrecios);
		
		lblSeleccioneExcel = new JLabel("Seleccione Archivo Excel:");
		lblSeleccioneExcel.setHorizontalAlignment(SwingConstants.LEFT);
		lblSeleccioneExcel.setBounds(17, 61, 202, 14);
		contenedorCenter.add(lblSeleccioneExcel);
		
		btnOrigen = new JButton("Origen");
		btnOrigen.setBounds(222, 57, 89, 23);
		contenedorCenter.add(btnOrigen);
		
		txtOrigen = new JTextField();
		txtOrigen.setEditable(false);
		txtOrigen.setBounds(321, 58, 259, 20);
		contenedorCenter.add(txtOrigen);
		
		lblSeleccioneDestino = new JLabel("Seleccione Carpeta Destino:");
		lblSeleccioneDestino.setHorizontalAlignment(SwingConstants.LEFT);
		lblSeleccioneDestino.setBounds(17, 95, 202, 14);
		contenedorCenter.add(lblSeleccioneDestino);
		
		btnDestino = new JButton("Destino");
		btnDestino.setBounds(222, 91, 89, 23);
		contenedorCenter.add(btnDestino);
		
		txtDestino = new JTextField();
		txtDestino.setEditable(false);
		txtDestino.setBounds(321, 93, 259, 20);
		contenedorCenter.add(txtDestino);
		
		btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(17, 132, 89, 23);
		contenedorCenter.add(btnProcesar);
	}

	public String getListaPrecio() {
		return cboListaPrecio.getSelectedItem().toString();
	}
	
	public JButton getBtnProcesar() {
		return btnProcesar;
	}
	
	public JButton getBtnOrigen() {
		return btnOrigen;
	}

	public JButton getBtnDestino() {
		return btnDestino;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}
	
	public void setOrigen(String origen){
		txtOrigen.setText(origen);
	}
	
	public void setDestino(String destino){
		txtDestino.setText(destino);
	}
	
	public String getOrigen(){
		return txtOrigen.getText().trim();
	}

	public String getDestino(){
		return txtDestino.getText().trim();
	}
	
	public void setControlador(PreciosController controlador){
		btnDestino.addActionListener(controlador);
		btnOrigen.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
		btnProcesar.addActionListener(controlador);
	}
	
}
