package ventanas;

import javax.swing.*;
import controlador.SeguridadController;
import javax.swing.border.TitledBorder;
import recursos.Sesion;
import recursos.TextFilterDocument;

public class FLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnIngresar;
	private JLabel lblUsuario;
	private JLabel lblContrase�a;
	private JTextField txtUsuario;
	private JPasswordField txtContrase�a;
	private JPanel cpLogin;
	private TextFilterDocument tfdUsuario = new TextFilterDocument(10);
	private TextFilterDocument tfdPassword = new TextFilterDocument(10);
	
	/*
	public FLogin() {
		initialize();	
	}
	*/
	public FLogin(){
		setResizable(false);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(340, 170);
		setIconImage(Sesion.cargarImagen(Sesion.imgRTM));
		setTitle(Sesion.titulo+"-"+Sesion.tfLogin);
		
		cpLogin = new JPanel();
		cpLogin.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		cpLogin.setBounds(10, 11, 315, 117);
		cpLogin.setLayout(null);
		getContentPane().add(cpLogin);
		
		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(37, 11, 100, 25);
		cpLogin.add(lblUsuario);

		lblContrase�a = new JLabel("Contrase�a:");
		lblContrase�a.setBounds(37, 41, 100, 25);
		cpLogin.add(lblContrase�a);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(137, 11, 150, 25);
		txtUsuario.setDocument(tfdUsuario);
		cpLogin.add(txtUsuario);
		
		txtContrase�a = new JPasswordField();
		txtContrase�a.setBounds(137, 41, 150, 25);
		txtContrase�a.setDocument(tfdPassword);
		cpLogin.add(txtContrase�a);
		
		btnIngresar = new JButton("Ingresar");
		btnIngresar.setBounds(137, 76, 150, 30);
		cpLogin.add(btnIngresar);
		
		txtUsuario.requestFocus();
	}
	
	public JButton getIngresar() {
		return btnIngresar;
	}

	public String getUsuario() {
		return txtUsuario.getText().toUpperCase().trim();
	}

	@SuppressWarnings("deprecation")
	public String getContrase�a() {
		return txtContrase�a.getText().trim();
	}

	public JPasswordField getTxtContrase�a() {
		return txtContrase�a;
	}

	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	public void setControlador(SeguridadController controlador) {
		btnIngresar.addActionListener(controlador);
		txtContrase�a.addActionListener(controlador);
		txtUsuario.addActionListener(controlador);
	}

}



