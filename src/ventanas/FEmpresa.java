package ventanas;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.Sesion;
import controlador.SeguridadController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FEmpresa extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnAceptar;
	private JButton btnSalir;
	private JPanel pnGeneral;
	private JPanel pnEmpresa;
	private JTextField txtCIA;
	private JComboBox cboCIA;
	int contador = 0;

	public FEmpresa() {

		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(658, 129);
		setIconImage(Sesion.cargarImagen(Sesion.imgRTM));
		setTitle(Sesion.titulo + "-" + Sesion.tfCIA);
		// setUndecorated(true);
		// getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		getContentPane().setLayout(null);

		pnGeneral = new JPanel();
		pnGeneral.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pnGeneral.setBounds(10, 11, 628, 76);
		getContentPane().add(pnGeneral);
		pnGeneral.setLayout(null);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(522, 11, 89, 23);
		pnGeneral.add(btnAceptar);

		btnSalir = new JButton("Salir");
		btnSalir.setBounds(522, 41, 89, 23);
		pnGeneral.add(btnSalir);

		pnEmpresa = new JPanel();
		pnEmpresa.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Empresa",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0,
						128)));
		pnEmpresa.setBounds(10, 8, 502, 60);
		pnGeneral.add(pnEmpresa);
		pnEmpresa.setLayout(null);

		txtCIA = new JTextField();
		txtCIA.setEditable(false);
		txtCIA.setBounds(62, 22, 430, 20);
		pnEmpresa.add(txtCIA);
		
		if(Sesion.usuario.equalsIgnoreCase("dparreno")){
			cboCIA = new MaestroComboBox("SPEED407.TCIAS","CIANOM","CIACVE");
		}else{
			cboCIA = new MaestroComboBox(
					"SPEED407.TRCIA TR inner join SPEED407.TCIAS TC ON TR.CIACVE=TC.CIACVE",
					"TC.CIANOM", "TR.CIACVE", " UPPER(TR.USUCVE)=UPPER('"
							+ Sesion.usuario + "')");
		}
		
		cboCIA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MaestroBean bean = (MaestroBean) cboCIA.getSelectedItem();
				txtCIA.setText(bean.getCodigo());
			}
		});

		cboCIA.getEditor().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAceptar.doClick();
			}
		});
		cboCIA.setBounds(10, 22, 42, 20);
		pnEmpresa.add(cboCIA);

		cboCIA.setSelectedIndex(0);

	}

	public JComboBox getCboCIA() {
		return cboCIA;
	}

	public JTextField getTxtCIA() {
		return txtCIA;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}

	public String getCIA() {
		return txtCIA.getText().trim();
	}

	public void setControlador(SeguridadController controlador) {
		btnAceptar.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
	}

}
