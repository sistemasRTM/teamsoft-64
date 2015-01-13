package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;

import recursos.MaestroInternalFrame;
import util.Conexion;

public class FIConsultasBD extends MaestroInternalFrame implements
		ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btnProcesar;
	private DefaultTableModel dtm;
	private JXTable tbGenerico;
	private JScrollPane scpGenerico;
	int contador = 0;
	private JLabel lblContador;
	private JTextArea txtSql;
	private JScrollPane scpPruebas;

	public FIConsultasBD() {
		setSize(1190, 650);
		//
		toolBar.setVisible(true);
		btnSalir.setVisible(true);
		btnSalir.addActionListener(this);
		//
		btnProcesar = new JButton("Consultar");
		btnProcesar.setBounds(1028, 5, 143, 38);
		btnProcesar.addActionListener(this);
		contenedorCenter.add(btnProcesar);

		dtm = new DefaultTableModel();

		tbGenerico = new JXTable(dtm);
		tbGenerico.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		scpGenerico = new JScrollPane(tbGenerico);
		scpGenerico.setBounds(10, 170, 1161, 387);
		contenedorCenter.add(scpGenerico);

		lblContador = new JLabel("");
		lblContador.setBounds(10, 568, 378, 27);
		contenedorCenter.add(lblContador);

		txtSql = new JTextArea();
		scpPruebas = new JScrollPane(txtSql);
		scpPruebas.setBounds(10, 11, 1008, 148);
		contenedorCenter.add(scpPruebas);
	}

	public JButton getPruebas() {
		return btnProcesar;
	}

	public String getSql() {
		return txtSql.getText().trim();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnProcesar) {
			try {
				PreparedStatement st = Conexion.obtenerConexion()
						.prepareStatement(getSql());
				//st.executeUpdate();
				ResultSet rs = st.executeQuery();
				cargaTabla(rs);
				st.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			dispose();
		}
	}

	void cargaTabla(ResultSet rs) throws SQLException {
		tbGenerico.setModel(dtm = new DefaultTableModel());
		tbGenerico.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// Obteniendo la informacion de las columnas que estan siendo
		// consultadas
		ResultSetMetaData rsMd = rs.getMetaData();
		// La cantidad de columnas que tiene la consulta
		int cantidadColumnas = rsMd.getColumnCount();
		// Establecer como cabezeras el nombre de las colimnas
		for (int i = 1; i <= cantidadColumnas; i++) {
			dtm.addColumn(rsMd.getColumnLabel(i));
		}
		// Creando las filas para el JTable
		contador = 0;
		while (rs.next()) {
			contador++;
			Object[] fila = new Object[cantidadColumnas];
			for (int i = 0; i < cantidadColumnas; i++) {
				fila[i] = rs.getObject(i + 1);
			}
			dtm.addRow(fila);
		}
		rs.close();
		tbGenerico.setModel(dtm);
		lblContador.setText("Total registros encontrados: " + contador);
	}

}
