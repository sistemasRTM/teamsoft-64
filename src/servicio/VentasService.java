package servicio;

import java.sql.SQLException;
import java.util.List;

import bean.TCLIE;
import bean.TREGV;
import bean.TREGVDTO;

public interface VentasService {
	public List<TREGV> listarTREGC(TREGV tregv) throws SQLException ;
	public TREGV buscarTFVAD(TREGV tregv) throws SQLException;
	public TREGV buscarTNCDH(TREGV tregv) throws SQLException;
	
	public List<TREGVDTO> obtenerVentasTotalesSoles(String cliente, int ejercicio) throws SQLException;
	public List<TREGVDTO> obtenerVentasTotalesSolesPorGrupo(String cliente, int ejercicio) throws SQLException;
	public List<TREGVDTO> obtenerVentasTotalesDolares(String cliente, int ejercicio) throws SQLException;
	public List<TREGVDTO> obtenerVentasTotalesDolaresPorGrupo(String cliente, int ejercicio) throws SQLException;
	
	public List<TREGVDTO> obtenerCantidadMotosVendidas(String cliente, String ejercicio) throws SQLException;
	public List<TREGVDTO> getCantidadMotosDevueltas(String cliente, String ejercicio) throws SQLException;
	public List<TREGVDTO> obtenerCantidadMotosVendidasPorGrupo(String cliente, String ejercicio) throws SQLException;
	public List<TREGVDTO> getCantidadMotosDevueltasPorGrupo(String cliente, String ejercicio) throws SQLException;
	public List<TREGVDTO> obtenerMontoMotosVendidas(String cliente, String ejercicio) throws SQLException;
	public List<TREGVDTO> getMontoMotosDevueltas(String cliente, String ejercicio) throws SQLException;
	public List<TREGVDTO> obtenerMontoMotosVendidasPorGrupo(String cliente, String ejercicio) throws SQLException;
	public List<TREGVDTO> getMontoMotosDevueltasPorGrupo(String cliente, String ejercicio) throws SQLException;
	public TCLIE getCliente(String codigo) throws SQLException;
	public TCLIE getClienteGrupo(String codigo) throws SQLException;
}
