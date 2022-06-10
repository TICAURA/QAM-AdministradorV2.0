package com.aura.admin.adminqamm.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aura.admin.adminqamm.config.DataSourceConfig;
import com.aura.admin.adminqamm.dto.FacturaReporteDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FacturaDao {

	@Autowired
	private DataSourceConfig dataSourceConfig;
	
	private final static Logger LOG = LogManager.getLogger(FacturaDao.class);
	
	public List<FacturaReporteDto> getReporte(int month) {

			DataSource dataSource = dataSourceConfig.getDataSource();
			String query = "call GET_REPORTE_FACTURA(?);";

			try (Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)){

				cStmt.setInt(1,month);

				cStmt.execute();

				ResultSet resultSet = cStmt.getResultSet();
				List<FacturaReporteDto> facturas = new ArrayList();
				while (resultSet.next()) {
					facturas.add(generarFacturaReporte(resultSet));
				}

				return facturas;
     
			}catch(SQLException e) {
				LOG.error(e.getMessage(),e);
				System.out.println(e.getMessage());
			}
		
		return null;
	}
	
	
	
	public FacturaReporteDto generarFacturaReporte(ResultSet rs) throws SQLException {
		FacturaReporteDto f = new FacturaReporteDto();
		f.setClaveColaborador(rs.getInt("id"));
		f.setRfc(rs.getString("rfc"));
		f.setNombre(rs.getString("nombre"));
		f.setApellidoP(rs.getString("apellido_pat"));
		f.setApellidoM(rs.getString("apellido_mat"));
		f.setNombreC(rs.getString("nombre_completo"));
		f.setEmail(rs.getString("email"));
		f.setNss(rs.getString("nss"));
		f.setPeriodicidad(rs.getString("periodicidad"));
		f.setCurp(rs.getString("curp"));
		f.setIva(rs.getDouble("iva"));
		f.setComision(rs.getDouble("comision"));
		
		return f;
	}
	

}
