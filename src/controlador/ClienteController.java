package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import bean.Cliente;

import delegate.GestionCliente;

import recursos.Sesion;
import servicio.ClienteService;
import ventanas.FIMantenimientoCliente;

public class ClienteController implements ActionListener{
	FIMantenimientoCliente mantenimientoCliente;
	ClienteService servicio = GestionCliente.getClienteService();
		
	public ClienteController(FIMantenimientoCliente mantenimientoCliente){
		this.mantenimientoCliente = mantenimientoCliente;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==mantenimientoCliente.getBuscar()){
			String codigo = mantenimientoCliente.getCodigo();
			try {
				List<Cliente> clientes = servicio.buscraByPk(codigo);
				if(!(clientes.size()==0))
				mantenimientoCliente.cargarTabla(clientes);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource()==mantenimientoCliente.getSalir()){
			mantenimientoCliente.dispose();
		}
		if(e.getSource()==mantenimientoCliente.getBtnNuevo()){
			mantenimientoCliente.getTbpCliente().setEnabledAt(1,true);
			mantenimientoCliente.getTbpCliente().setEnabledAt(0,false);
			mantenimientoCliente.getTbpCliente().setSelectedIndex(1);
			
		}
		if(e.getSource()==mantenimientoCliente.getBtnCancelar()){
			mantenimientoCliente.getTbpCliente().setEnabledAt(1,false);
			mantenimientoCliente.getTbpCliente().setEnabledAt(0,true);
			mantenimientoCliente.getTbpCliente().setSelectedIndex(0);
		}
		if(e.getSource()==mantenimientoCliente.getBtnGuardar()){
			mantenimientoCliente.getTbpCliente().setEnabledAt(1,false);
			mantenimientoCliente.getTbpCliente().setEnabledAt(0,true);
			mantenimientoCliente.getTbpCliente().setSelectedIndex(0);
			
			if (mantenimientoCliente.getCodigo().trim().equals("")){
				Sesion.mensajeConfirmacion(mantenimientoCliente,"Codigo en blanco");
			}else{
				Cliente cliente = new Cliente();
				cliente.setCodigo(mantenimientoCliente.getCodigo().trim());
							
				try {
				Cliente clienteVerifica = servicio.verificaCliente(cliente);
				
				if(clienteVerifica==null){
					//grabar
					cliente.setCodigo(mantenimientoCliente.getCodigo());
					cliente.setNombre(mantenimientoCliente.getName());
					
					
				}else{
					//modificar
				}
					
				} catch (SQLException e1) {
					Sesion.mensajeConfirmacion(mantenimientoCliente,e1.getMessage());
				}
			}
			
			
		
			
			
			
			
		}
		
		
		
		
	}

}
