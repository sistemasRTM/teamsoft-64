package ventanas;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import controlador.ClienteController;
import bean.Cliente;
import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import recursos.TextFilterDocument;

public class FIMantenimientoCliente extends MaestroInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtBuscar;
	private JButton btnBuscar;
	private JLabel lblBuscar;
	private String datos[][];
	private String cabecera[] = { "Código", "Nombres", "Dirección" };
	private DefaultTableModel dtm;
	private JTable tbEmpleado;
	private JScrollPane scpEmpleado;
	private JTabbedPane tbpCliente;
	private JPanel pnlBusqueda;
	private JPanel pnlProceso;

	private TextFilterDocument tdfCodigo = new TextFilterDocument(
			TextFilterDocument.DIGITS, 10);

	// ////proceso/////////
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtAbreviado;
	private JTextField txtPais;
	private JTextField txtDireccion;
	private JTextField txtTelf1;
	private JTextField txtTelf2;
	private JTextField txtTelf3;
	private JTextField txtNombreContacto;
	private JTextField txtCargo;
	private JTextField txtMail;
	private JTextField txtRuc;
	private JTextField txtDni;
	private JTextField txtLimiteCredito;
	private JTextField txtDscto;
	private JTextField txtSaldoCtaCte;
	private MaestroComboBox cboDepartamento;
	private MaestroComboBox cboProvincia;
	private MaestroComboBox cboDistrito;
	private MaestroComboBox cboZona;
	private MaestroComboBox cboTipo;
	private MaestroComboBox cboTipoDoc;
	private MaestroComboBox cboAgpxDscto;
	private MaestroComboBox cboRefComercial;
	private MaestroComboBox cboAgenteRetenedor;
	private MaestroComboBox cboCondPago;
	private MaestroComboBox cboSitCrediticia;
	private MaestroComboBox cboVendedor;
	private MaestroComboBox cboCobrador;
	private MaestroComboBox cboListaPrecio;
	private MaestroComboBox cboSituacion;
	private JButton btnPassword;
	private JPanel panel;
	private JLabel lblCodigo;
	private JLabel lblNombre;
	private JLabel lblAbreviado;
	private JLabel lblPais;
	private JLabel lblDepartamento;
	private JLabel lblProvincia;
	private JLabel lblDistrito;
	private JLabel lblDireccion;
	private JLabel lblZona;
	private JLabel lblTelf1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JLabel lblTelf2;
	private JLabel lblTelf3;
	private JLabel lblCargo;
	private JLabel lblNombreContacto;
	private JLabel lblMail;
	private JPanel panel_4;
	private JLabel lblTipo;
	private JLabel lbltipoDoc;
	private JLabel lblAgpxDscto;
	private JLabel lblRefComercial;
	private JPanel panel_6;
	private JLabel lblRuc;
	private JLabel lblDni;
	private JLabel lblAgenteRetenedor;
	private JPanel panel_5;
	private JLabel lblCondPago;
	private JLabel lblSitCrediticia;
	private JLabel lblVendedor;
	private JLabel lblCobrador;
	private JLabel lblLimiteDeCredito;
	private JLabel lblDscto;
	private JLabel lblListaDePrecio;
	private JLabel lblSituacion;
	private JLabel lblSaldoCtaCte;
	private JLabel lblMaestroDeClientes;
	private JPanel panel_1;

	// ///////////////////

	public FIMantenimientoCliente() {
		setSize(900, 580);
		setTitle(Sesion.titulo + "-" + Sesion.tifCliente);
		//
		toolBar.setVisible(true);
		setResizable(true);
		//
		tbpCliente = new JTabbedPane();
		tbpCliente.setBounds(0, 0, 870, 500);
		contenedorCenter.add(tbpCliente);

		pnlProceso = new JPanel();
		pnlProceso.setLayout(null);
		pnlProceso.setAutoscrolls(true);

		pnlBusqueda = new JPanel();
		pnlBusqueda.setLayout(null);
		pnlBusqueda.setAutoscrolls(true);
		tbpCliente.addTab("Busqueda", pnlBusqueda);
		tbpCliente.addTab("Procesar", pnlProceso);

		tbpCliente.setSelectedIndex(0);
		tbpCliente.setEnabledAt(1, true);

		crearPanelBusqueda();
		crearPanelProceso();

	}

	void crearPanelBusqueda() {
		lblBuscar = new JLabel("Código:");
		lblBuscar.setBounds(10, 11, 46, 14);
		pnlBusqueda.add(lblBuscar);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(66, 11, 335, 20);
		txtBuscar.setDocument(tdfCodigo);
		pnlBusqueda.add(txtBuscar);

		dtm = new DefaultTableModel(datos, cabecera);

		tbEmpleado = new JTable(dtm);

		scpEmpleado = new JScrollPane(tbEmpleado);
		scpEmpleado.setBounds(10, 42, 495, 213);
		pnlBusqueda.add(scpEmpleado);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(411, 9, 94, 23);
		pnlBusqueda.add(btnBuscar);
	}

	void crearPanelProceso() {

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Razon Social",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(26, 60, 346, 101);
		pnlProceso.add(panel);
		panel.setLayout(null);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(86, 25, 86, 20);
		panel.add(txtCodigo);
		txtCodigo.setColumns(10);

		lblCodigo = new JLabel("Codigo");
		lblCodigo.setBounds(10, 27, 46, 14);
		panel.add(lblCodigo);

		lblNombre = new JLabel("Nombre ");
		lblNombre.setBounds(10, 56, 50, 14);
		panel.add(lblNombre);

		lblAbreviado = new JLabel("Abreviado");
		lblAbreviado.setBounds(10, 80, 56, 14);
		panel.add(lblAbreviado);

		txtNombre = new JTextField();
		txtNombre.setBounds(86, 50, 250, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);

		txtAbreviado = new JTextField();
		txtAbreviado.setBounds(86, 75, 122, 20);
		panel.add(txtAbreviado);
		txtAbreviado.setColumns(10);

		lblMaestroDeClientes = new JLabel("MAESTRO DE CLIENTES");
		lblMaestroDeClientes.setBounds(295, 30, 177, 28);
		pnlProceso.add(lblMaestroDeClientes);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Direcci\u00F3n",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(26, 172, 346, 170);
		pnlProceso.add(panel_1);
		panel_1.setLayout(null);

		lblPais = new JLabel("Pais");
		lblPais.setBounds(10, 17, 46, 14);
		panel_1.add(lblPais);

		lblDepartamento = new JLabel("Departamento");
		lblDepartamento.setBounds(10, 42, 100, 14);
		panel_1.add(lblDepartamento);

		lblProvincia = new JLabel("Provincia");
		lblProvincia.setBounds(10, 67, 80, 14);
		panel_1.add(lblProvincia);

		lblDistrito = new JLabel("Distrito");
		lblDistrito.setBounds(10, 92, 46, 14);
		panel_1.add(lblDistrito);

		lblDireccion = new JLabel("Direcci\u00F3n");
		lblDireccion.setBounds(10, 117, 80, 14);
		panel_1.add(lblDireccion);

		lblZona = new JLabel("Zona");
		lblZona.setBounds(10, 142, 46, 14);
		panel_1.add(lblZona);

		txtPais = new JTextField();
		txtPais.setBounds(89, 14, 86, 20);
		panel_1.add(txtPais);
		txtPais.setColumns(10);

		cboDepartamento = new MaestroComboBox("UBIGEO", "coddpto", "descrip",
				"codprov = '00' and coddist = '00' ");
		cboDepartamento.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				if (e.getStateChange() == ItemEvent.SELECTED) {
					cboProvincia.removeAllItems();
					MaestroBean departamento = (MaestroBean) cboDepartamento
							.getSelectedItem();
					cboProvincia.executeQuery("UBIGEO", "codprov", "descrip",
							"coddpto ='" + departamento.getCodigo()
									+ "' and coddist='00' and codprov<>'00' ");
					cboProvincia.setSelectedIndex(0);
				}

			}
		});
		cboDepartamento.setBounds(89, 39, 247, 20);
		panel_1.add(cboDepartamento);

		cboProvincia = new MaestroComboBox();
		cboProvincia.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (cboProvincia.getSelectedItem() != null) {
						if (!cboProvincia.getSelectedItem().toString()
								.equals("")) {
							MaestroBean provincia = (MaestroBean) cboProvincia
									.getSelectedItem();
							MaestroBean departamento = (MaestroBean) cboDepartamento
									.getSelectedItem();
							cboDistrito.removeAllItems();
							cboDistrito.executeQuery(
									"UBIGEO",
									"coddist",
									"descrip",
									"codprov ='" + provincia.getCodigo()
											+ "' and coddist<>'00' and "
											+ "coddpto ='"
											+ departamento.getCodigo() + "'");
						} else {
							cboDistrito.removeAllItems();
						}
					} else {
						cboDistrito.removeAllItems();
					}
				}
			}
		});

		cboProvincia.setBounds(89, 64, 247, 20);
		panel_1.add(cboProvincia);

		cboDistrito = new MaestroComboBox();
		cboDistrito.setBounds(89, 89, 247, 20);
		panel_1.add(cboDistrito);

		txtDireccion = new JTextField();
		txtDireccion.setBounds(89, 114, 247, 20);
		panel_1.add(txtDireccion);
		txtDireccion.setColumns(10);

		cboZona = new MaestroComboBox("TTABD", "tbespe", "desesp",
				"tbiden = 'FAZON'");
		cboZona.setBounds(89, 139, 167, 20);
		panel_1.add(cboZona);

		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Telefonos",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(26, 343, 168, 101);
		pnlProceso.add(panel_2);
		panel_2.setLayout(null);

		lblTelf1 = new JLabel("Telf. 1");
		lblTelf1.setBounds(10, 24, 46, 14);
		panel_2.add(lblTelf1);

		lblTelf2 = new JLabel("Telf. 2");
		lblTelf2.setBounds(10, 49, 46, 14);
		panel_2.add(lblTelf2);

		lblTelf3 = new JLabel("Telf. 3");
		lblTelf3.setBounds(10, 76, 46, 14);
		panel_2.add(lblTelf3);

		txtTelf1 = new JTextField();
		txtTelf1.setBounds(61, 21, 86, 20);
		panel_2.add(txtTelf1);
		txtTelf1.setColumns(10);

		txtTelf2 = new JTextField();
		txtTelf2.setBounds(61, 46, 86, 20);
		panel_2.add(txtTelf2);
		txtTelf2.setColumns(10);

		txtTelf3 = new JTextField();
		txtTelf3.setBounds(61, 73, 86, 20);
		panel_2.add(txtTelf3);
		txtTelf3.setColumns(10);

		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Contacto",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(204, 343, 168, 101);
		pnlProceso.add(panel_3);
		panel_3.setLayout(null);

		lblNombreContacto = new JLabel("Nombre");
		lblNombreContacto.setBounds(10, 23, 46, 14);
		panel_3.add(lblNombreContacto);

		lblCargo = new JLabel("Cargo");
		lblCargo.setBounds(10, 48, 46, 14);
		panel_3.add(lblCargo);

		lblMail = new JLabel("Mail");
		lblMail.setBounds(10, 73, 46, 14);
		panel_3.add(lblMail);

		txtNombreContacto = new JTextField();
		txtNombreContacto.setBounds(72, 20, 86, 20);
		panel_3.add(txtNombreContacto);
		txtNombreContacto.setColumns(10);

		txtCargo = new JTextField();
		txtCargo.setBounds(72, 45, 86, 20);
		panel_3.add(txtCargo);
		txtCargo.setColumns(10);

		txtMail = new JTextField();
		txtMail.setBounds(72, 70, 86, 20);
		panel_3.add(txtMail);
		txtMail.setColumns(10);

		panel_4 = new JPanel();
		panel_4.setBounds(393, 69, 447, 138);
		pnlProceso.add(panel_4);
		panel_4.setLayout(null);

		lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(10, 11, 46, 14);
		panel_4.add(lblTipo);

		lbltipoDoc = new JLabel("Tipo doc.");
		lbltipoDoc.setToolTipText("");
		lbltipoDoc.setBounds(10, 36, 55, 14);
		panel_4.add(lbltipoDoc);

		lblAgpxDscto = new JLabel("Agp.X dscto");
		lblAgpxDscto.setBounds(10, 66, 100, 14);
		panel_4.add(lblAgpxDscto);

		lblRefComercial = new JLabel("Ref. Comercial");
		lblRefComercial.setBounds(10, 91, 76, 14);
		panel_4.add(lblRefComercial);
		////juridico, natural, etc
		cboTipo = new MaestroComboBox("TTABD", "tbespe", "desesp",
				"tbiden = 'INTPR'");
		cboTipo.setBounds(84, 8, 140, 20);
		panel_4.add(cboTipo);
		/////
		
		cboTipoDoc = new MaestroComboBox();
		cboTipoDoc.addItem("BV");
		cboTipoDoc.addItem("BG");
		cboTipoDoc.addItem("FC");
		cboTipoDoc.addItem("FG");
		cboTipoDoc.addItem("CT");
		cboTipoDoc.setBounds(84, 33, 67, 20);
		panel_4.add(cboTipoDoc);

		cboAgpxDscto = new MaestroComboBox("TTABD", "tbespe", "desesp",
				"tbiden = 'FTICL'");
		cboAgpxDscto.setBounds(83, 63, 126, 20);
		panel_4.add(cboAgpxDscto);

		cboRefComercial = new MaestroComboBox("TTABD", "tbespe", "desesp",
				"tbiden = 'CBAFL'");
		cboRefComercial.setBounds(84, 88, 125, 20);
		panel_4.add(cboRefComercial);

		panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "Identificaci\u00F3n",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setBounds(234, 11, 193, 87);
		panel_4.add(panel_6);
		panel_6.setLayout(null);

		lblRuc = new JLabel("RUC");
		lblRuc.setBounds(10, 25, 46, 14);
		panel_6.add(lblRuc);

		lblDni = new JLabel("DNI");
		lblDni.setBounds(10, 50, 46, 14);
		panel_6.add(lblDni);

		txtRuc = new JTextField();
		txtRuc.setBounds(43, 22, 140, 20);
		panel_6.add(txtRuc);
		txtRuc.setColumns(10);

		txtDni = new JTextField();
		txtDni.setBounds(43, 47, 140, 20);
		panel_6.add(txtDni);
		txtDni.setColumns(10);

		lblAgenteRetenedor = new JLabel("Agente Retenedor");
		lblAgenteRetenedor.setBounds(250, 113, 110, 14);
		panel_4.add(lblAgenteRetenedor);

		cboAgenteRetenedor = new MaestroComboBox();
		cboAgenteRetenedor.addItem("SI");
		cboAgenteRetenedor.addItem("NO");
		cboAgenteRetenedor.setBounds(360, 110, 67, 20);
		panel_4.add(cboAgenteRetenedor);

		panel_5 = new JPanel();
		panel_5.setBounds(393, 218, 447, 226);
		pnlProceso.add(panel_5);
		panel_5.setLayout(null);

		lblCondPago = new JLabel("Cond. Pago");
		lblCondPago.setBounds(10, 12, 71, 14);
		panel_5.add(lblCondPago);

		lblSitCrediticia = new JLabel("Sit. Crediticia");
		lblSitCrediticia.setBounds(10, 38, 90, 14);
		panel_5.add(lblSitCrediticia);

		lblVendedor = new JLabel("Vendedor");
		lblVendedor.setBounds(10, 64, 75, 14);
		panel_5.add(lblVendedor);

		lblCobrador = new JLabel("Cobrador");
		lblCobrador.setBounds(10, 90, 75, 14);
		panel_5.add(lblCobrador);

		lblLimiteDeCredito = new JLabel("Limite de Credito");
		lblLimiteDeCredito.setBounds(10, 116, 100, 14);
		panel_5.add(lblLimiteDeCredito);

		lblDscto = new JLabel("% Dscto");
		lblDscto.setBounds(10, 142, 65, 14);
		panel_5.add(lblDscto);

		lblListaDePrecio = new JLabel("Lista de precio");
		lblListaDePrecio.setBounds(10, 168, 100, 14);
		panel_5.add(lblListaDePrecio);

		lblSituacion = new JLabel("Situaci\u00F3n");
		lblSituacion.setBounds(10, 194, 65, 14);
		panel_5.add(lblSituacion);

		cboCondPago = new MaestroComboBox("TCPAG", "cpacve", "cpades");
		cboCondPago.setBounds(104, 11, 162, 20);
		panel_5.add(cboCondPago);

		cboSitCrediticia = new MaestroComboBox("TTABD", "tbespe", "desesp",
				"tbiden = 'STCRE'");
		cboSitCrediticia.setBounds(104, 37, 162, 20);
		panel_5.add(cboSitCrediticia);

		cboVendedor = new MaestroComboBox("TAGEN", "AGECVE", "AGENOM");
		cboVendedor.setBounds(104, 66, 299, 20);
		panel_5.add(cboVendedor);

		cboCobrador = new MaestroComboBox("TAGEN", "AGECVE", "AGENOM");
		cboCobrador.setBounds(104, 92, 299, 20);
		panel_5.add(cboCobrador);

		txtLimiteCredito = new JTextField();
		txtLimiteCredito.setBounds(104, 116, 86, 20);
		panel_5.add(txtLimiteCredito);
		txtLimiteCredito.setColumns(10);

		txtDscto = new JTextField();
		txtDscto.setBounds(104, 142, 86, 20);
		panel_5.add(txtDscto);
		txtDscto.setColumns(10);

		cboListaPrecio = new MaestroComboBox("TTABD", "tbespe", "desesp",
				"tbiden = 'FALPR'");
		cboListaPrecio.setBounds(104, 165, 166, 20);
		panel_5.add(cboListaPrecio);

		cboSituacion = new MaestroComboBox("TTABD", "tbespe", "desesp",
				"tbiden = 'STCLI'");
		cboSituacion.setBounds(104, 191, 108, 20);
		panel_5.add(cboSituacion);

		lblSaldoCtaCte = new JLabel("Saldo Cta. Cte.");
		lblSaldoCtaCte.setBounds(200, 142, 90, 14);
		panel_5.add(lblSaldoCtaCte);

		txtSaldoCtaCte = new JTextField();
		txtSaldoCtaCte.setBounds(294, 139, 86, 20);
		panel_5.add(txtSaldoCtaCte);
		txtSaldoCtaCte.setColumns(10);

		btnPassword = new JButton("Password");
		btnPassword.setBounds(281, 190, 122, 23);
		panel_5.add(btnPassword);

		cboListaPrecio.setSelectedIndex(1);
		cboTipo.setSelectedIndex(2);
		cboAgpxDscto.setSelectedIndex(4);
		cboRefComercial.setSelectedIndex(3);
		cboAgenteRetenedor.setSelectedIndex(1);
		cboCondPago.setSelectedIndex(2);

	}

	public void limpiarTabla() {
		tbEmpleado.setModel(dtm = new DefaultTableModel(datos, cabecera));
	}

	public void cargarTabla(List<Cliente> listadoClientes) {
		limpiarTabla();
		for (Cliente cliente : listadoClientes) {
			Object datos[] = { cliente.getCodigo(), cliente.getNombre(),
					cliente.getDireccion() };
			dtm.addRow(datos);
		}
	}

	public void setControlador(ClienteController controlador) {
		btnBuscar.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
		btnModificar.addActionListener(controlador);
		btnNuevo.addActionListener(controlador);
		btnCancelar.addActionListener(controlador);
		btnPassword.addActionListener(controlador);
	}

	public JButton getBuscar() {
		return btnBuscar;
	}

	public JButton getSalir() {
		return btnSalir;
	}

	public String getCodigo() {
		return txtBuscar.getText().trim();
	}
	
	public String getCod(){
		return txtCodigo.getText().trim();
	}
	public String getNombre(){
		return txtNombre.getText().trim();
	}
	public String getAbreviatura(){
		return txtAbreviado.getText().trim();
	}
	public String getRuc(){
		return txtRuc.getText().trim();
	}
	public String getDni(){
		return txtDni.getText().trim();
	}
	//public String getTipoIde(){
	//	return txt
	//}
	public String getDireccion(){
		return txtDireccion.getText().trim();
	}
	public String getDistrito(){
		return cboDistrito.getSelectedItem().toString();
	}
	public String getProvincia(){
		return cboProvincia.getSelectedItem().toString();
	}
	public String getDepartamento(){
		return cboDepartamento.getSelectedItem().toString();
	}
	public String getPais(){
		return txtPais.getText().trim();
	}
	public String getTelef1(){
		return txtTelf1.getText().trim();
	}
	public String getTelef2(){
		return txtTelf2.getText().trim();
	}
	public String getTelef3(){
		return txtTelf3.getText().trim();
	}
	public String getAxd(){
		MaestroBean bean = (MaestroBean)cboAgpxDscto.getSelectedItem();
		return bean.getCodigo();
	}
	public String getTipo(){
		MaestroBean bean = (MaestroBean)cboTipo.getSelectedItem();
		return bean.getCodigo();
	}
	public String getRef(){
		MaestroBean bean = (MaestroBean)cboRefComercial.getSelectedItem();
		return bean.getCodigo();
	}
	public String getVendedor(){
		MaestroBean bean = (MaestroBean)cboVendedor.getSelectedItem();
		return bean.getCodigo();
	}
	public String getCobrador(){
		MaestroBean bean = (MaestroBean)cboCobrador.getSelectedItem();
		return bean.getCodigo();
	}
	public String getZona(){
		MaestroBean bean = (MaestroBean)cboZona.getSelectedItem();
		return bean.getCodigo();
	}
	public String getSituacion(){
		MaestroBean bean = (MaestroBean)cboSituacion.getSelectedItem();
		return bean.getCodigo();
	}
	public String getLimiteCredito(){
		return txtLimiteCredito.getText().trim();
	}
	public String getSaldoCuentaCorriente(){
		return txtSaldoCtaCte.getText().trim();
	}
	public String getSituacionCredito(){
		MaestroBean bean = (MaestroBean)cboSitCrediticia.getSelectedItem();
		return bean.getCodigo();
	}
	public String getCondicionPago(){
		MaestroBean bean = (MaestroBean)cboCondPago.getSelectedItem();
		return bean.getCodigo();
	}
	public String getListaPrecios(){
		MaestroBean bean = (MaestroBean)cboCondPago.getSelectedItem();
		return bean.getCodigo();
	}
	public String getContactoNombre(){
		return txtNombreContacto.getText().trim();
	}
	public String getContactoPuesto(){
		return txtCargo.getText().trim();
	}
	public String getContactoEmail(){
		return txtMail.getText().trim();
	}
	
	
	/*////////////
		pstm.setString(6, cliente.getTipoIde());
	
	//////////////*/
	// emp.setCodigo(Integer.parseInt(tbEmpleado.getValueAt(
	// tbEmpleado.getSelectedRow(), 0).toString()));
	public JButton getBtnNuevo() {
		return btnNuevo;
	}
	
	public JButton getBtnModificar() {
		return btnModificar;
	}
	
	public JButton getBtnSalir() {
		return btnSalir;
	}

	public JButton getBtnPassword() {

		return btnPassword;

	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}
	public JButton getBtnEliminar() {
		return btnEliminar;
	}
	
	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JTabbedPane getTbpCliente() {

		return tbpCliente;

	}

}
