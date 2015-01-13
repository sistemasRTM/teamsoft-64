package ventanas;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import recursos.MaestroFrame;
import recursos.Sesion;
import controlador.SeguridadController;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FModulosGeneral extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton btnInventarios;
	private JButton btnFacturacion;
	private JButton btnCuentasXCobrar;
	private JButton btnCuentasXPagar;
	private JButton btnContabilidad;
	private JButton btnTesoreria;
	private JButton btnConsultas;
	private JButton btnPresupuestos;
	private JButton btnProduccion;
	private JButton btnLogistica;
	private JButton btnActivosFijos;
	private JButton btnConsiliacionBanc;
	private JButton btnRRHH;
	private JButton btnSalir;
	private JButton btnAdmin;
	private JLabel lblPresupuestos;
	private JLabel lblProduccion;
	private JLabel lblLogistica;
	private JLabel lblActivosFijos;
	private JLabel lblConsiliacionBancaria;
	private JLabel lblPlanillas;
	private JLabel lblSalir;
	private JLabel lblConsultaDeInformacion;
	private JLabel lblTesoreria;
	private JLabel lblContabilidad;
	private JLabel lblCuentasPorPagar;
	private JLabel lblCuentasPorCobrar;
	private JLabel lblFacturacion;
	private JLabel lblInventarios;
	private JLabel lblAdmin;

	
	public FModulosGeneral() {
		setTitle(Sesion.titulo+"-"+Sesion.tfGeneral);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height-33);
		setIconImage(Sesion.cargarImagen(Sesion.imgRTM));
		getContentPane().setLayout(null);
				
		btnFacturacion = new JButton();
		btnFacturacion.setEnabled(false);
		btnFacturacion.setBounds(162, 102, 61, 55);
		getContentPane().add(btnFacturacion);

		btnInventarios = new JButton();
		btnInventarios.setEnabled(false);
		btnInventarios.setBounds(162, 36, 61, 55);
		getContentPane().add(btnInventarios);
		
		lblFacturacion = new JLabel("Facturaci\u00F3n");
		lblFacturacion.setBounds(71, 124, 81, 14);
		getContentPane().add(lblFacturacion);
		
		lblInventarios = new JLabel("Inventarios");
		lblInventarios.setBounds(79, 55, 73, 14);
		getContentPane().add(lblInventarios);
		
		btnCuentasXCobrar = new JButton();
		btnCuentasXCobrar.setEnabled(false);
		btnCuentasXCobrar.setBounds(162, 168, 61, 55);
		getContentPane().add(btnCuentasXCobrar);
		
		btnCuentasXPagar = new JButton();
		btnCuentasXPagar.setEnabled(false);
		btnCuentasXPagar.setBounds(162, 234, 60, 55);
		getContentPane().add(btnCuentasXPagar);
		
		btnContabilidad = new JButton();
		btnContabilidad.setEnabled(false);
		btnContabilidad.setBounds(162, 300, 60, 55);
		getContentPane().add(btnContabilidad);
		
		btnTesoreria = new JButton();
		btnTesoreria.setEnabled(false);
		btnTesoreria.setBounds(162, 366, 60, 55);
		getContentPane().add(btnTesoreria);
		
		btnConsultas = new JButton();
		btnConsultas.setEnabled(false);
		btnConsultas.setBounds(162, 432, 60, 55);
		getContentPane().add(btnConsultas);
		
		lblCuentasPorCobrar = new JLabel("Cuentas por Cobrar");
		lblCuentasPorCobrar.setBounds(32, 195, 120, 14);
		getContentPane().add(lblCuentasPorCobrar);
		
		lblContabilidad = new JLabel("Contabilidad");
		lblContabilidad.setBounds(79, 323, 73, 14);
		getContentPane().add(lblContabilidad);
		
		lblTesoreria = new JLabel("Tesoreria");
		lblTesoreria.setBounds(93, 394, 59, 14);
		getContentPane().add(lblTesoreria);
		
		lblCuentasPorPagar = new JLabel("Cuentas por Pagar");
		lblCuentasPorPagar.setBounds(32, 260, 120, 14);
		getContentPane().add(lblCuentasPorPagar);
		
		lblConsultaDeInformacion = new JLabel("Consulta de Informaci\u00F3n");
		lblConsultaDeInformacion.setBounds(10, 455, 142, 14);
		getContentPane().add(lblConsultaDeInformacion);
		
		btnProduccion = new JButton();
		btnProduccion.setEnabled(false);
		btnProduccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MaestroFrame();
			}
		});
		btnProduccion.setBounds(248, 102, 61, 55);
		getContentPane().add(btnProduccion);
		
		btnPresupuestos = new JButton();
		btnPresupuestos.setEnabled(false);
		btnPresupuestos.setBounds(248, 36, 61, 55);
		getContentPane().add(btnPresupuestos);
		
		btnLogistica = new JButton();
		btnLogistica.setEnabled(false);
		btnLogistica.setBounds(248, 168, 61, 55);
		getContentPane().add(btnLogistica);
		
		btnActivosFijos = new JButton();
		btnActivosFijos.setEnabled(false);
		btnActivosFijos.setBounds(248, 234, 62, 55);
		getContentPane().add(btnActivosFijos);
		
		btnConsiliacionBanc = new JButton();
		btnConsiliacionBanc.setEnabled(false);
		btnConsiliacionBanc.setBounds(248, 300, 62, 55);
		getContentPane().add(btnConsiliacionBanc);
		
		btnRRHH = new JButton();
		btnRRHH.setEnabled(false);
		btnRRHH.setBounds(248, 366, 62, 55);
		getContentPane().add(btnRRHH);
		
		btnSalir = new JButton();
		btnSalir.setBounds(248, 432, 62, 55);
		getContentPane().add(btnSalir);
		
		btnAdmin = new JButton();
		btnAdmin.setEnabled(false);
		btnAdmin.setBounds(162, 498, 60, 55);
		getContentPane().add(btnAdmin);
		
		lblProduccion = new JLabel("Producci\u00F3n");
		lblProduccion.setBounds(332, 124, 81, 14);
		getContentPane().add(lblProduccion);
		
		lblPresupuestos = new JLabel("Presupuestos");
		lblPresupuestos.setBounds(332, 55, 89, 14);
		getContentPane().add(lblPresupuestos);
		
		lblLogistica = new JLabel("Logistica");
		lblLogistica.setBounds(332, 195, 120, 14);
		getContentPane().add(lblLogistica);
		
		lblConsiliacionBancaria = new JLabel("Consiliaci\u00F3n Bancaria");
		lblConsiliacionBancaria.setBounds(332, 323, 166, 14);
		getContentPane().add(lblConsiliacionBancaria);
		
		lblPlanillas = new JLabel("RR.HH");
		lblPlanillas.setBounds(332, 394, 59, 14);
		getContentPane().add(lblPlanillas);
		
		lblActivosFijos = new JLabel("Activos Fijos");
		lblActivosFijos.setBounds(332, 260, 120, 14);
		getContentPane().add(lblActivosFijos);
		
		lblSalir = new JLabel("Salir del Sistema");
		lblSalir.setBounds(332, 455, 142, 14);
		getContentPane().add(lblSalir);
				
		lblAdmin = new JLabel("Administraci\u00F3n del Sistema");
		lblAdmin.setBounds(2, 522, 166, 14);
		getContentPane().add(lblAdmin);
	}
	
	public JButton getBtnSalir(){
		return btnSalir;
	}
	
	public JButton getBtnFacturacion(){
		return btnFacturacion;
	}
	
	public JButton getBtnRRHH(){
		return btnRRHH;
	}
	
	public JButton getBtnAdmin(){
		return btnAdmin;
	}
	
	public JButton getBtnContabilidad(){
		return btnContabilidad;
	}
	
	public JButton getBtnCuentasXCobrar(){
		return btnCuentasXCobrar;
	}
	
	public JButton getBtnInventario(){
		return btnInventarios;
	}
	
	public void setControlador(SeguridadController controlador){
		btnFacturacion.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
		btnAdmin.addActionListener(controlador);
		btnRRHH.addActionListener(controlador);
		btnContabilidad.addActionListener(controlador);
		btnCuentasXCobrar.addActionListener(controlador);
		btnInventarios.addActionListener(controlador);
	}
}
