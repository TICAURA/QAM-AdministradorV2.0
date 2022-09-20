/**
 * 
 */
package com.aura.admin.adminqamm.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aura.admin.adminqamm.config.DataSourceConfig;
import com.aura.admin.adminqamm.exception.BusinessException;

/**
 * @author Cesar Agustin Soto
 *
 */
@Service
public class CargaColombiaDao {
	
	@Autowired
	private DataSourceConfig dataSourceConfig;

	private final static Logger LOGGER = LogManager.getLogger(CargaColombiaDao.class);

	public String insertaAguilaFuncion(Integer idCargaMasiva, String rfcCliente) throws BusinessException{

	        DataSource dataSource = dataSourceConfig.getDataSource();
	        StringBuilder queryStr = new StringBuilder("{ ? = call F_INSERTA_AGUILA(");
	        		queryStr.append(idCargaMasiva)
	        		.append(", '").append(rfcCliente)
	        		.append("')};");
	        LOGGER.info("/**** Call Funcion Inserta Aguila ****/");
	        try (Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(queryStr.toString())){

	            cStmt.execute();
	        
	            String resul = cStmt.getString(1);
	            LOGGER.info("/**** Se ejecuto F_INSERTA_AGUILA :: "+resul);
	            
	            return resul;
	        }catch(SQLException e) {
	            LOGGER.error("Error al ejecutar funcion carga aguila: "+e.getMessage(),e);
	            throw new BusinessException(e.getMessage(),500);
	        }
	    }

}
