package ventanas;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTable;

import recursos.MaestroInternalFrame;
import recursos.Sesion;
import org.jdesktop.swingx.JXDatePicker;
import controlador.RRHHController;

import bean.EjerPer;

import javax.swing.JButton;
import javax.swing.JLabel;

public class FIAbrirPeriodo extends MaestroInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel pnlDatos;
	private JPanel pnlBusqueda;
	private DefaultTableModel dtm;
	private String cabecera[] = { "Fecha Inicial", "Fecha Final", "Situación" },
			datos[][] = {};
	private JScrollPane scpBusqueda;
	private JXTable tbBusqueda;
	private JXDatePicker dtpFechaIni;
	private JXDatePicker dtpFechaFin;
	private JButton btnAbrirPeriodo;
	private JLabel lblDesde;
	private JLabel lblFechaFin;
	private static FIAbrirPeriodo gui;
	public static FIAbrirPeriodo createInstance() {
		if (gui == null) {
			gui = new FIAbrirPeriodo();
		}
		return gui;
	}

	public static FIAbrirPeriodo getInstance() {
		return gui;
	}
	
	public FIAbrirPeriodo() {
		//initialize();
	}
	
	public void initialize(){
		setSize(375, 393);
		setTitle(getTitle() + "-" + Sesion.tifAbrirPeriodo);
		//
		toolBar.setVisible(true);
		//
		pnlDatos = new JPanel();
		pnlDatos.setLayout(null);
		pnlDatos.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Datos",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));
		pnlDatos.setBounds(10, 10, 343, 87);
		contenedorCenter.add(pnlDatos);

		dtpFechaIni = new JXDatePicker();
		//dtpFechaIni.setEnabled(false);
		dtpFechaIni.setBounds(102, 20, 123, 22);
		Sesion.formateaDatePicker(dtpFechaIni);
		pnlDatos.add(dtpFechaIni);

		dtpFechaFin = new JXDatePicker();
		//dtpFechaFin.setEnabled(false);
		dtpFechaFin.setBounds(102, 53, 123, 22);
		Sesion.formateaDatePicker(dtpFechaFin);
		pnlDatos.add(dtpFechaFin);

		btnAbrirPeriodo = new JButton("Abrir Periodo");
		btnAbrirPeriodo.setEnabled(false);
		btnAbrirPeriodo.setBounds(235, 20, 95, 23);
		pnlDatos.add(btnAbrirPeriodo);

		lblDesde = new JLabel("Fecha inicial:");
		lblDesde.setBounds(10, 21, 95, 14);
		pnlDatos.add(lblDesde);

		lblFechaFin = new JLabel("Fecha fin:");
		lblFechaFin.setBounds(10, 57, 103, 14);
		pnlDatos.add(lblFechaFin);
		contenedorCenter.add(getPnlBusqueda());
	}

	private JPanel getPnlBusqueda() {
		pnlBusqueda = new JPanel();
		pnlBusqueda.setLayout(null);
		pnlBusqueda.setBounds(10, 108, 343, 223);
		pnlBusqueda.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "BUSCAR",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));

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
					String fechaInicial = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 0).toString();
					
					String fechaFinal = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 1).toString();
					try {
						setFechaInicial(formatoDelTexto.parse(fechaInicial));
						setFechaFinal(formatoDelTexto.parse(fechaFinal));
					} catch (ParseException e1) {
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
					String fechaInicial = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 0).toString();
					
					String fechaFinal = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 1).toString();
					try {
						setFechaInicial(formatoDelTexto.parse(fechaInicial));
						setFechaFinal(formatoDelTexto.parse(fechaFinal));
					} catch (ParseException e1) {
					}
				}
			}
		});
		
		scpBusqueda = new JScrollPane(tbBusqueda);
		scpBusqueda.setBounds(10, 22, 325, 190);
		pnlBusqueda.add(scpBusqueda);

		return pnlBusqueda;
	}

	public void limpiarTabla() {
		tbBusqueda.setModel(dtm = new DefaultTableModel(datos, cabecera));

		TableColumn tcFIni = tbBusqueda.getColumn(0);
		TableColumn tcFFin = tbBusqueda.getColumn(1);
		TableColumn tcFSitu = tbBusqueda.getColumn(2);

		tcFIni.setPreferredWidth(110);
		tcFFin.setPreferredWidth(110);
		tcFSitu.setPreferredWidth(103);
	}

	public void cargarTabla(List<EjerPer> periodos) {
		limpiarTabla();
		for (EjerPer periodo : periodos) {
			String situacion = "";
			String fechaIni = Sesion.formateaFechaVista(periodo
					.getFechaInicial());
			String fechaFin = Sesion.formateaFechaVista(periodo
					.getFechaFinal());
			if (periodo.getSituacion() == 1) {
				situacion = "Cerrado";
			} else {
				situacion = "Abierto";
			}
			Object datos[] = { fechaIni,
					fechaFin,situacion};
			dtm.addRow(datos);
		}
	}
	
	public void setControlador(RRHHController controlador){
		btnAbrirPeriodo.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
	}
	
	public void setFechaInicial(Date fecha){
		dtpFechaIni.setDate(fecha);
	}
	
	public void setFechaFinal(Date fecha){
		dtpFechaFin.setDate(fecha);
	}
	
	public Date getFechaInicial() {
		return dtpFechaIni.getDate();
	}

	public Date getFechaFin() {
		return dtpFechaFin.getDate();
	}
	
	public JButton getBtnAbrirPeriodo(){
		return btnAbrirPeriodo;
	}
	
	public JButton getBtnSalir(){
		return btnSalir;
	}
	
	public static void close(){
		gui = null;
	}
	
	public void salir(){
		dispose();
	}
	
}
