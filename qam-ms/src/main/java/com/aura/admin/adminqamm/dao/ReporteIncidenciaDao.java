/**
 * 
 */
package com.aura.admin.adminqamm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aura.admin.adminqamm.config.DataSourceConfig;
import com.aura.admin.adminqamm.dto.VRepIncidenciasDto;
import com.aura.admin.adminqamm.exception.BusinessException;

import lombok.AllArgsConstructor;

/**
 * @author Cesar Agustin Soto
 *
 */
@Service
@AllArgsConstructor
public class ReporteIncidenciaDao {

    private final DataSourceConfig dataSourceConfig;
    
    private static final Logger logger = LoggerFactory.getLogger(ReporteIncidenciaDao.class);
    
	
	public List<VRepIncidenciasDto> obtenerVRepIncidencia(String centroCosto, String cliente, String clienteSubcontrato, Date fchTransferenciaInicio, Date fchTransferenciaFin) throws BusinessException {
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
		
		logger.info("Realizando ... obtenerVRepIncidencia");
			
		Boolean conAnd = Boolean.FALSE;
		Boolean conAndDos = Boolean.FALSE;
		Boolean conAndTres = Boolean.FALSE;
		
		StringBuilder queryStr = new StringBuilder();
		queryStr.append("SELECT * FROM quincena_amm_blanca.v_Rep_Incidencias ")
//		.append(VRepIncidencias.class.getSimpleName())
		.append(" c");
				
		if (StringUtils.isNotBlank(centroCosto)) {
			queryStr.append(" WHERE c.Centro_Costos = '")
				.append(centroCosto).append("'");
			
			conAnd = Boolean.TRUE;
		}
		
//		if (fchTransferenciaFin!=null && fchTransferenciaInicio!=null) {
//			if (conAnd) {
//				queryStr.append(" AND STR_TO_DATE(c.fechaStp, '%Y-%m-%d') BETWEEN '")
//					.append(dateFormatter.format(fchTransferenciaInicio))
//					.append("' AND '")
//					.append(dateFormatter.format(fchTransferenciaFin))
//					.append("'");
//			} else {
//					queryStr.append(" WHERE STR_TO_DATE(c.fechaStp, '%Y-%m-%d') BETWEEN '")
//					.append(dateFormatter.format(fchTransferenciaInicio))
//					.append("' AND '")
//					.append(dateFormatter.format(fchTransferenciaFin))
//					.append("'");
//			}
//			conAndDos = Boolean.TRUE;
//		}
//		
//		
//		if (StringUtils.isNotBlank(cliente)) {
//			if (conAnd || conAndDos) {
//				queryStr.append(" AND c.cliente='").append(cliente)
//				.append("'");
//			} else {
//				queryStr.append(" WHERE c.cliente='").append(cliente)
//				.append("'");
//			}
//			conAndTres = Boolean.TRUE;
//		}
//		
//		if (StringUtils.isNotBlank(clienteSubcontrato)) {
//				if (conAnd || conAndDos || conAndTres) {
//					queryStr.append(" AND c.clienteSubcontrato='").append(clienteSubcontrato)
//					.append("'");
//				} else {
//					queryStr.append(" WHERE c.clienteSubcontrato='").append(clienteSubcontrato)
//					.append("'");
//				}
//			}
			
		
		List<VRepIncidenciasDto> result = new ArrayList<VRepIncidenciasDto>();
		DataSource dataSource = dataSourceConfig.getDataSource();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
		    conn = dataSource.getConnection();
		    stmt = conn.prepareStatement(queryStr.toString());
		    
		    rs = stmt.executeQuery();
		    
		    logger.info("/**** Result :: "+queryStr.toString());
		    while (rs.next()) {
		    	result.add(leerIncidencia(rs));
		    }
		    
		    rs.close();
		    conn.close();
//		    return list;
		  } catch (SQLException e){
	            logger.error("Error al consultar los registros de incidencias: "+e.getMessage(),e);
	            throw new BusinessException(e.getMessage(),500);
	      }
		
		
//		try {
//			Query query = em.createQuery(queryStr.toString());
//			result = query.getResultList();
//		} catch (NoResultException e) {
//			LOG.error("No hay registro en cuenta banco para la persona con ID ");
//			result = new ArrayList<VRepIncidencias>();
//		}
		
		return result;
	}


	private VRepIncidenciasDto leerIncidencia(ResultSet rs) throws BusinessException {
		VRepIncidenciasDto repIncidencia = new VRepIncidenciasDto();
		
		try {
			repIncidencia.setCentroCostos(rs.getString(1));
			repIncidencia.setCliente(rs.getString(2));
			repIncidencia.setRfcCliente(rs.getString(3));
			repIncidencia.setRegistroPatronalCliente(rs.getString(4));
			repIncidencia.setClienteSubcontrato(rs.getString(5));
			repIncidencia.setRfcClienteSubcontrato(rs.getString(6));
			repIncidencia.setClaveColaborador(rs.getString(7));
			repIncidencia.setCurp(rs.getString(8));
			repIncidencia.setNombre(rs.getString(9));
			repIncidencia.setApellidoPat(rs.getString(10));
			repIncidencia.setApellidoMat(rs.getString(11));
			repIncidencia.setPeriodicidad(rs.getString(12));
			repIncidencia.setPeriodo(rs.getString(13));
			repIncidencia.setProducto(rs.getString(14));
			repIncidencia.setBanco(rs.getString(15));
			repIncidencia.setClave(rs.getString(16));
			repIncidencia.setCuenta(rs.getString(17));
			repIncidencia.setTarjeta(rs.getString(18));
			repIncidencia.setImporte(rs.getString(19));
			repIncidencia.setComision(rs.getString(20));
			repIncidencia.setTotal(rs.getString(21));
			repIncidencia.setSaldoGratis(rs.getString(22));
			repIncidencia.setMontoNeto(rs.getString(23));
			repIncidencia.setFchTransferencia(rs.getString(24));
			repIncidencia.setClaveAutorizacion(rs.getString(25));
			repIncidencia.setIdAnti(rs.getString(26));
			repIncidencia.setFechaNacimiento(rs.getString(27));
			repIncidencia.setSexo(rs.getString(28));
			repIncidencia.setFechaStp(rs.getString(29));
			repIncidencia.setEstado(rs.getString(30));
			repIncidencia.setMotivoRechazo(rs.getString(31));
		} catch (SQLException e) {
			logger.error("Error al leer registro de incidencia: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
		}
		
		return repIncidencia;
	}

}
