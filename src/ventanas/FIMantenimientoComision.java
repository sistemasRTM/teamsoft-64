package ventanas;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JLabel;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;
import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import servicio.ComisionService;
import bean.Comision;
import bean.Vendedor;
import controlador.ComisionController;
import delegate.GestionComision;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.Calendar;

public class FIMantenimientoComision extends MaestroInternalFrame {

	private static final long serialVersionUID = 1L;
	private static FIMantenimientoComision gui = null;
	private JLabel lblFechaIni;
	private JLabel lblComisionXMayo;
	private JSpinner spnComisionmayor;
	private JLabel lblComisionXMenor;
	private JLabel lblSituacin;
	private JCheckBox chkSituacion;
	private JSpinner spnComisionmenor;
	private JXDatePicker dtpFechaIni;
	private JLabel lblVendedor_1;
	private JPanel pnlBusqueda;
	private JButton btnBuscar;
	private JTextField txtBuscar;
	private JLabel lblDescripcion_1;
	private DefaultTableModel dtm;
	private JXTable tbBusqueda;
	private String cabecera[] = { "Código", "Nombre", "Fecha Inicial",
			"Fecha Final", "C. x Mayor", "C. x Menor", "Acumulador","Situación","Maestro","C. x Mayor M.","C. x Menor M." },
			datos[][] = {};

	private JScrollPane scpBusqueda;
	private int operacion = 0;
	private JComboBox cboVendedor;
	private JLabel lblAcumula;
	private JComboBox cboAcumula;
	String cod = "";
	String nombre = "";
	Date fechaIniAntigua = null;
	Date fechaFinAntigua = null;
	ComisionService servicio = GestionComision.getComisionService();
	private JXDatePicker dtpFechaFin;
	private JLabel lblFechaFinal;
	private JPanel pnlComision;
	private JLabel lblMaestro;
	private JCheckBox chkMaestro;
	private JLabel lblComisinXMenor;
	private JLabel lblComisinXMayor;
	private JSpinner spComisionMayorM;
	private JSpinner spComisionMenorM;

	public static FIMantenimientoComision createInstance() {
		if (gui == null) {
			gui = new FIMantenimientoComision();

		}
		return gui;
	}

	public static FIMantenimientoComision getInstance() {
		return gui;
	}

	public FIMantenimientoComision() {
	//	initialize();
	}
	
	public void initialize(){
		setSize(887, 590);
		//
		
		setTitle(getTitle() + "-" + Sesion.tifComision);
		toolBar.setVisible(true);

		pnlComision = new JPanel();
		pnlComision.setBorder(new TitledBorder(null, "Datos de Comisi\u00F3n",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlComision.setBounds(23, 11, 836, 134);
		contenedorCenter.add(pnlComision);
		pnlComision.setLayout(null);

		dtpFechaIni = new JXDatePicker();
		dtpFechaIni.getEditor().setEnabled(false);
		dtpFechaIni.setBounds(125, 46, 122, 22);
		Sesion.formateaDatePicker(dtpFechaIni);
		pnlComision.add(dtpFechaIni);

		lblFechaIni = new JLabel("Fecha Inicial:");
		lblFechaIni.setBounds(10, 50, 97, 14);
		pnlComision.add(lblFechaIni);

		lblFechaFinal = new JLabel("Fecha Final:");
		lblFechaFinal.setBounds(10, 75, 97, 14);
		pnlComision.add(lblFechaFinal);

		dtpFechaFin = new JXDatePicker();
		dtpFechaFin.getEditor().setEnabled(false);
		Sesion.formateaDatePicker(dtpFechaFin);
		dtpFechaFin.setBounds(125, 71, 122, 22);
		pnlComision.add(dtpFechaFin);

		lblComisionXMayo = new JLabel("Comisi\u00F3n x Mayor:");
		lblComisionXMayo.setBounds(257, 75, 122, 14);
		pnlComision.add(lblComisionXMayo);

		lblComisionXMenor = new JLabel("Comisi\u00F3n x Menor:");
		lblComisionXMenor.setBounds(257, 50, 122, 14);
		pnlComision.add(lblComisionXMenor);

		Locale myLocale = new Locale("en", "EN");

		spnComisionmayor = new JSpinner();
		spnComisionmayor.setModel(new SpinnerNumberModel(0.000, 0.000, 100.000,
				0.001));
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(
				spnComisionmayor, "0.000");
		DecimalFormat format = editor.getFormat();
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(myLocale));
		spnComisionmayor.setEditor(editor);
		spnComisionmayor.setBounds(389, 72, 128, 20);
		pnlComision.add(spnComisionmayor);

		spnComisionmenor = new JSpinner();
		spnComisionmenor.setModel(new SpinnerNumberModel(0.000, 0.000, 100.000,
				0.001));
		spnComisionmenor.setBounds(389, 47, 128, 20);
		JSpinner.NumberEditor editor2 = new JSpinner.NumberEditor(
				spnComisionmenor, "0.000");
		DecimalFormat format2 = editor2.getFormat();
		format2.setDecimalFormatSymbols(new DecimalFormatSymbols(myLocale));
		spnComisionmenor.setEditor(editor2);
		pnlComision.add(spnComisionmenor);

		lblSituacin = new JLabel("Situaci\u00F3n:");
		lblSituacin.setBounds(10, 100, 87, 14);
		pnlComision.add(lblSituacin);

		chkSituacion = new JCheckBox("");
		chkSituacion.setSelected(true);
		chkSituacion.setBounds(122, 96, 97, 23);
		pnlComision.add(chkSituacion);

		lblVendedor_1 = new JLabel("Vendedor:");
		lblVendedor_1.setBounds(10, 24, 97, 14);
		pnlComision.add(lblVendedor_1);

		cboVendedor = new MaestroComboBox("tagen", "agecve", "agenom",
				"agetip = 'A' ");
		cboVendedor.setBounds(125, 20, 392, 20);
		pnlComision.add(cboVendedor);

		cboAcumula = new JComboBox();
		cboAcumula.setBounds(389, 98, 128, 23);
		cboAcumula.addItem("Seleccione");
		cboAcumula.addItem("ACUMULABLE");
		cboAcumula.addItem("NO ACUMULABLE");

		pnlComision.add(cboAcumula);

		lblAcumula = new JLabel("Acumula:");
		lblAcumula.setBounds(257, 100, 87, 14);
		pnlComision.add(lblAcumula);
		
		lblMaestro = new JLabel("Maestro:");
		lblMaestro.setBounds(527, 50, 71, 14);
		pnlComision.add(lblMaestro);
		
		chkMaestro = new JCheckBox("");
		chkMaestro.setBounds(699, 45, 97, 23);
		pnlComision.add(chkMaestro);
		
		lblComisinXMenor = new JLabel("Comisi\u00F3n x Menor Maestro:");
		lblComisinXMenor.setBounds(527, 75, 167, 14);
		pnlComision.add(lblComisinXMenor);
		
		spComisionMayorM = new JSpinner();
		spComisionMayorM.setBounds(699, 97, 122, 20);
		spComisionMayorM.setModel(new SpinnerNumberModel(0.000, 0.000, 100.000,
				0.001));
		JSpinner.NumberEditor editor3 = new JSpinner.NumberEditor(
				spComisionMayorM, "0.000");
		DecimalFormat format3 = editor3.getFormat();
		format3.setDecimalFormatSymbols(new DecimalFormatSymbols(myLocale));
		spComisionMayorM.setEditor(editor3);
		pnlComision.add(spComisionMayorM);
		
		lblComisinXMayor = new JLabel("Comisi\u00F3n x Mayor Maestro:");
		lblComisinXMayor.setBounds(527, 102, 167, 14);
		pnlComision.add(lblComisinXMayor);
		
		spComisionMenorM = new JSpinner();
		spComisionMenorM.setBounds(699, 72, 122, 20);
		spComisionMenorM.setModel(new SpinnerNumberModel(0.000, 0.000, 100.000,
				0.001));
		JSpinner.NumberEditor editor4 = new JSpinner.NumberEditor(
				spComisionMenorM, "0.000");
		DecimalFormat format4 = editor4.getFormat();
		format4.setDecimalFormatSymbols(new DecimalFormatSymbols(myLocale));
		spComisionMenorM.setEditor(editor4);
		pnlComision.add(spComisionMenorM);

		contenedorCenter.add(getPnlBusqueda());

		activaControles();
		limpiarTabla();

		try {
			List<Comision> comisiones = servicio.buscar("");
			cargarTabla(comisiones);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		requestFocus();
		try {
			setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
	
	private JPanel getPnlBusqueda() {
		pnlBusqueda = new JPanel();
		pnlBusqueda.setLayout(null);
		pnlBusqueda.setBounds(23, 156, 836, 373);
		pnlBusqueda.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "BUSCAR",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnBuscar.setBounds(366, 16, 75, 22);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(71, 17, 274, 20);

		lblDescripcion_1 = new JLabel("Nombre:");
		lblDescripcion_1.setBounds(6, 20, 88, 14);
		pnlBusqueda.add(lblDescripcion_1);

		dtm = new DefaultTableModel(datos, cabecera);

		tbBusqueda = new JXTable(dtm);
		tbBusqueda.getTableHeader().setReorderingAllowed(false);
		tbBusqueda.getTableHeader().setResizingAllowed(false);
		tbBusqueda.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbBusqueda.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		tbBusqueda.setEnabled(true);
		tbBusqueda.setEditable(false);
		tbBusqueda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (tbBusqueda.getSelectedRow() > -1) {
					SimpleDateFormat formatoDelTexto = new SimpleDateFormat(
							"dd/MM/yyyy");

					cod = tbBusqueda.getValueAt(tbBusqueda.getSelectedRow(), 0)
							.toString();
					nombre = tbBusqueda.getValueAt(tbBusqueda.getSelectedRow(),
							1).toString();
					String fechaIni = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 2).toString();
					String fechaFin = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 3).toString();
					double comisionMayor = Double.parseDouble(tbBusqueda
							.getValueAt(tbBusqueda.getSelectedRow(), 4)
							.toString());
					double comisionMenor = Double.parseDouble(tbBusqueda
							.getValueAt(tbBusqueda.getSelectedRow(), 5)
							.toString());
					String acumula = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 6).toString();
					String situacion = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 7).toString();
					String maestro = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 8).toString();
					double comisionMayorM = Double.parseDouble(tbBusqueda
							.getValueAt(tbBusqueda.getSelectedRow(),9)
							.toString());
					double comisionMenorM = Double.parseDouble(tbBusqueda
							.getValueAt(tbBusqueda.getSelectedRow(), 10)
							.toString());
					
					MaestroBean bean = new MaestroBean();
					bean.setDescripcion(nombre);
					cboVendedor.setSelectedItem(bean);
					getSpnComisionMayor(comisionMayor);
					getSpnComisionmenor(comisionMenor);
					spComisionMayorM.setValue(comisionMayorM);
					spComisionMenorM.setValue(comisionMenorM);
					try {
						getFecharegistro(formatoDelTexto.parse(fechaIni));
						getFechaFinal(formatoDelTexto.parse(fechaFin));
						fechaIniAntigua = formatoDelTexto.parse(fechaIni);
						fechaFinAntigua = formatoDelTexto.parse(fechaFin);
					} catch (ParseException e1) {
						
						System.out.println("error de parseo");
					}
					cboAcumula.setSelectedItem(acumula);
					if(situacion.equalsIgnoreCase("Activo")){
						chkSituacion.setSelected(true);
					}else{
						chkSituacion.setSelected(false);
					}
					if(maestro.equalsIgnoreCase("Activo")){
						chkMaestro.setSelected(true);
					}else{
						chkMaestro.setSelected(false);
					}
						
				}
			}
		});
		tbBusqueda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (tbBusqueda.getSelectedRow() > -1) {
					SimpleDateFormat formatoDelTexto = new SimpleDateFormat(
							"dd/MM/yyyy");

					cod = tbBusqueda.getValueAt(tbBusqueda.getSelectedRow(), 0)
							.toString();
					nombre = tbBusqueda.getValueAt(tbBusqueda.getSelectedRow(),
							1).toString();
					String fechaIni = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 2).toString();
					String fechaFin = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 3).toString();

					double comisionMayor = Double.parseDouble(tbBusqueda
							.getValueAt(tbBusqueda.getSelectedRow(), 4)
							.toString());
					double comisionMenor = Double.parseDouble(tbBusqueda
							.getValueAt(tbBusqueda.getSelectedRow(), 5)
							.toString());
					String acumula = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 6).toString();
					String situacion = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 7).toString();
					String maestro = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 8).toString();
					double comisionMayorM = Double.parseDouble(tbBusqueda
							.getValueAt(tbBusqueda.getSelectedRow(),9)
							.toString());
					double comisionMenorM = Double.parseDouble(tbBusqueda
							.getValueAt(tbBusqueda.getSelectedRow(), 10)
							.toString());
					spComisionMayorM.setValue(comisionMayorM);
					spComisionMenorM.setValue(comisionMenorM);
					MaestroBean bean = new MaestroBean();
					bean.setDescripcion(nombre);
					cboVendedor.setSelectedItem(bean);
					getSpnComisionMayor(comisionMayor);
					getSpnComisionmenor(comisionMenor);
					try {
						getFecharegistro(formatoDelTexto.parse(fechaIni));
						getFechaFinal(formatoDelTexto.parse(fechaFin));
						fechaIniAntigua = formatoDelTexto.parse(fechaIni);
						fechaFinAntigua = formatoDelTexto.parse(fechaFin);
					} catch (ParseException e1) {

					}
					cboAcumula.setSelectedItem(acumula);
					if(situacion.equalsIgnoreCase("Activo")){
						chkSituacion.setSelected(true);
					}else{
						chkSituacion.setSelected(false);
					}
					if(maestro.equalsIgnoreCase("Activo")){
						chkMaestro.setSelected(true);
					}else{
						chkMaestro.setSelected(false);
					}
				}
			}
		});

		scpBusqueda = new JScrollPane(tbBusqueda);
		scpBusqueda.setBounds(6, 45, 820, 317);

		pnlBusqueda.add(btnBuscar);
		pnlBusqueda.add(txtBuscar);
		pnlBusqueda.add(scpBusqueda);

		return pnlBusqueda;
	}

	public Vendedor getVendedor() {
		MaestroBean bean = (MaestroBean) cboVendedor.getSelectedItem();
		Vendedor vendedor = new Vendedor();
		if (bean.getCodigo() == null) {
			bean.setCodigo(cod);
		}
		vendedor.setCodigo(bean.getCodigo());
		vendedor.setNombre(bean.getDescripcion());

		return vendedor;
	}

	public Date getFechaini() {
		return dtpFechaIni.getDate();
	}

	public Date getFechafin() {
		return dtpFechaFin.getDate();
	}

	public double getComisionMayor() {
		double comision = -1;
		try {
			comision = Double.parseDouble(spnComisionmayor.getValue()
					.toString());
		} catch (Exception e) {
			Sesion.mensajeError(this, "Formato Incorrecto");
		}
		return comision;
	}
	
	public double getComisionMayorM() {
		double comision = -1;
		try {
			comision = Double.parseDouble(spComisionMayorM.getValue()
					.toString());
		} catch (Exception e) {
			Sesion.mensajeError(this, "Formato Incorrecto");
		}
		return comision;
	}

	public double getComisionMenor() {
		double comision = -1;
		try {
			comision = Double.parseDouble(spnComisionmenor.getValue()
					.toString());
		} catch (Exception e) {
			Sesion.mensajeError(this, "Formato Incorrecto");
		}
		return comision;
	}
	
	public double getComisionMenorM() {
		double comision = -1;
		try {
			comision = Double.parseDouble(spComisionMenorM.getValue()
					.toString());
		} catch (Exception e) {
			Sesion.mensajeError(this, "Formato Incorrecto");
		}
		return comision;
	}

	public void setControlador(ComisionController controlador) {
		btnNuevo.addActionListener(controlador);
		btnModificar.addActionListener(controlador);
		btnCancelar.addActionListener(controlador);
		btnGuardar.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
		btnBuscar.addActionListener(controlador);
		txtBuscar.addActionListener(controlador);
		chkSituacion.addActionListener(controlador);
	}

	public void limpiarTabla() {
		tbBusqueda.setModel(dtm = new DefaultTableModel(datos, cabecera));

		TableColumn colCodVendedor = tbBusqueda.getColumn(0);
		TableColumn colVendedor = tbBusqueda.getColumn(1);
		TableColumn colFechaIni = tbBusqueda.getColumn(2);
		TableColumn colFechaFin = tbBusqueda.getColumn(3);
		TableColumn colComMay = tbBusqueda.getColumn(4);
		TableColumn colComMen = tbBusqueda.getColumn(5);
		TableColumn colacumula = tbBusqueda.getColumn(6);
		TableColumn colSituacion = tbBusqueda.getColumn(7);
		TableColumn colMaestro = tbBusqueda.getColumn(8);
		TableColumn colComMayM = tbBusqueda.getColumn(9);
		TableColumn colComMenM = tbBusqueda.getColumn(10);
		
		colCodVendedor.setPreferredWidth(50);
		colVendedor.setPreferredWidth(240);
		colFechaIni.setPreferredWidth(90);
		colFechaFin.setPreferredWidth(90);
		colComMay.setPreferredWidth(80);
		colComMen.setPreferredWidth(80);
		colacumula.setPreferredWidth(125);
		colSituacion.setPreferredWidth(80);
		colMaestro.setPreferredWidth(80);
		colComMayM.setPreferredWidth(85);
		colComMenM.setPreferredWidth(85);
	}

	public void cargarTabla(List<Comision> comisiones) {
		limpiarTabla();
		for (Comision comision : comisiones) {
			String fechaIni = Sesion.formateaFechaVista(comision
					.getFecha_inicial());
			Date dFechaFin = comision.getFecha_final();
			String fechaFin = "";
			if (dFechaFin != null) {
				fechaFin = Sesion.formateaFechaVista(dFechaFin);
			}

			String acumula = "";
			if (comision.getAcumula().trim().equalsIgnoreCase("T")) {
				acumula = "ACUMULABLE";
			} else {
				acumula = "NO ACUMULABLE";
			}
			
			String situacion = "";
			if(comision.getSituacion()==1){
				situacion = "Activo";
			}else{
				situacion = "Inactivo";
			}
			
			String maestro = "";
			if(comision.getMaestro()==1){
				maestro = "Activo";
			}else{
				maestro = "Inactivo";
			}
			
			String cMayor = Sesion.formateaDecimal_3(comision
					.getComision_mayor());
			String cMenor = Sesion.formateaDecimal_3(comision
					.getComision_menor());
			String cMayorM = Sesion.formateaDecimal_3(comision
					.getComision_mayor_m());
			String cMenorM = Sesion.formateaDecimal_3(comision
					.getComision_menor_m());
			Object datos[] = { comision.getVendedor().getCodigo(),
					comision.getVendedor().getNombre().trim(), fechaIni,
					fechaFin, cMayor, cMenor, acumula,situacion,maestro,cMayorM,cMenorM};
			dtm.addRow(datos);
		}
	}

	public void cargarTabla(Comision comision) {
		limpiarTabla();

		String fechaIni = Sesion
				.formateaFechaVista(comision.getFecha_inicial());
		Date dFechaFin = comision.getFecha_final();
		String fechaFin = "";
		if (dFechaFin != null) {
			fechaFin = Sesion.formateaFechaVista(dFechaFin);
		}
		String acumula = "";
		if (comision.getAcumula().trim().equalsIgnoreCase("T")) {
			acumula = "ACUMULABLE";
		} else {
			acumula = "NO ACUMULABLE";
		}
		
		String situacion = "";
		if(comision.getSituacion()==1){
			situacion = "Activo";
		}else{
			situacion = "Inactivo";
		}
		String maestro = "";
		if(comision.getMaestro()==1){
			maestro = "Activo";
		}else{
			maestro = "Inactivo";
		}
		
		String cMayor = Sesion.formateaDecimal_3(comision.getComision_mayor());
		String cMenor = Sesion.formateaDecimal_3(comision.getComision_menor());
		String cMayorM = Sesion.formateaDecimal_3(comision
				.getComision_mayor_m());
		String cMenorM = Sesion.formateaDecimal_3(comision
				.getComision_menor_m());
		Object datos[] = { comision.getVendedor().getCodigo(),
				comision.getVendedor().getNombre().trim(), fechaIni, fechaFin,
				cMayor, cMenor, acumula,situacion,maestro,cMayorM,cMenorM};
		dtm.addRow(datos);

	}

	public void limpiar() {
		txtBuscar.setText("");
		cboAcumula.setSelectedIndex(0);
		System.out.println(cboVendedor.getItemCount());
		if(cboVendedor.getItemCount()>0){
			cboVendedor.setSelectedIndex(0);
		}else{
			
		}
		//cboVendedor.setSelectedIndex(0);
		spnComisionmayor.setValue(0.0);
		spnComisionmenor.setValue(0.0);
		spComisionMayorM.setValue(0.0);
		spComisionMenorM.setValue(0.0);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = Calendar.getInstance().getTime();
		String fechaFormateada = Sesion.formateaFechaVista(fecha);
		try {
			dtpFechaIni.setDate(sdf.parse(fechaFormateada));
			dtpFechaFin.setDate(sdf.parse(fechaFormateada));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void activaControles() {
		btnNuevo.setEnabled(true);
		btnModificar.setEnabled(true);

		scpBusqueda.setEnabled(true);
		tbBusqueda.setEnabled(true);
		btnBuscar.setEnabled(true);
		txtBuscar.setEnabled(true);

		btnCancelar.setEnabled(false);
		btnGuardar.setEnabled(false);

		cboAcumula.setEnabled(false);
		spnComisionmayor.setEnabled(false);
		spnComisionmenor.setEnabled(false);
		chkSituacion.setEnabled(false);
		dtpFechaIni.setEnabled(false);
		dtpFechaFin.setEnabled(false);
		chkMaestro.setEnabled(false);
		spComisionMayorM.setEnabled(false);
		spComisionMenorM.setEnabled(false);

		if (operacion == 0) {
			cboVendedor.setEnabled(false);
		}
		txtBuscar.requestFocus();
	}

	public void desactivaControles() {

		btnNuevo.setEnabled(false);
		btnModificar.setEnabled(false);

		scpBusqueda.setEnabled(false);
		tbBusqueda.setEnabled(false);
		btnBuscar.setEnabled(false);
		txtBuscar.setEnabled(false);
		limpiarTabla();

		cboAcumula.setEnabled(true);
		spnComisionmayor.setEnabled(true);
		spnComisionmenor.setEnabled(true);
		chkSituacion.setEnabled(true);
		dtpFechaIni.setEnabled(true);
		dtpFechaFin.setEnabled(true);
		chkMaestro.setEnabled(true);
		spComisionMayorM.setEnabled(true);
		spComisionMenorM.setEnabled(true);
		if (operacion == 0) {
			cboVendedor.setEnabled(true);
		}

		btnCancelar.setEnabled(true);
		btnGuardar.setEnabled(true);

	}

	public JTextField getTxtBuscarr() {
		
		return txtBuscar;
	}

	public JButton getBtnCancelar() {
	
		return btnCancelar;
	}

	public JButton getBtnSalir() {
	
		return btnSalir;
	}

	public JButton getBtnNuevo() {
		
		return btnNuevo;
	}

	public JButton getBtnGuardar() {

		return btnGuardar;
	}

	public JButton getBtnBuscar() {

		return btnBuscar;
	}

	public String getBuscar() {
		return txtBuscar.getText();
	}

	public JButton getBtnModificar() {
		return btnModificar;
	}

	public int getChkSituacion() {
		int dato = -1;
		if (chkSituacion.isSelected()) {
			dato = 1;// abierto-el que manda actualamente
		} else {
			dato = 0;// cerrado-para visualizar historico
		}

		return dato;
	}
	public int getChkMaestro() {
		int dato = -1;
		if (chkMaestro.isSelected()) {
			dato = 1;// abierto-el que manda actualamente
		} else {
			dato = 0;// cerrado-para visualizar historico
		}

		return dato;
	}
	
	public JComboBox getCboAcumula() {

		return cboAcumula;

	}

	public Date getFechaIniAntigua() {
		return fechaIniAntigua;
	}

	public Date getFechaFinAntigua() {
		return fechaFinAntigua;
	}

	public void getSpnComisionMayor(double ComisionMayor) {
		spnComisionmayor.setValue(ComisionMayor);
	}

	public void getSpnComisionmenor(double ComisionMenor) {
		spnComisionmenor.setValue(ComisionMenor);
	}

	public void getFecharegistro(Date fecha) {
		dtpFechaIni.setDate(fecha);
	}

	public void getFechaFinal(Date fecha) {
		dtpFechaFin.setDate(fecha);
	}

	public JXDatePicker getDtpFechaIni() {
		return dtpFechaIni;
	}

	public JXDatePicker getDtpFechaFin() {
		return dtpFechaFin;
	}

	public int getOperacion() {
		return operacion;
	}

	public void setOperacion(int operacion) {
		this.operacion = operacion;
	}

	public static void close() {
		gui = null;
	}

	public void salir() {
		dispose();

	}
}
