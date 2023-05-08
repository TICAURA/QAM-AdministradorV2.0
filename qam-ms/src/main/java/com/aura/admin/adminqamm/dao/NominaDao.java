package com.aura.admin.adminqamm.dao;

import com.aura.admin.adminqamm.config.DataSourceConfig;
import com.aura.admin.adminqamm.dto.*;
import com.aura.admin.adminqamm.exception.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NominaDao {

    @Autowired
    private DataSourceConfig dataSourceConfig;

    private final static Logger LOGGER = LogManager.getLogger(NominaDao.class);

    public List<NOM035ListDto> getListaNomina() throws BusinessException{

        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call NOM035_GET_COLABORADORES();";

        try (Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)){

            cStmt.execute();

            ResultSet resultSet = cStmt.getResultSet();

            List<NOM035ListDto> nom035ListDtos = new ArrayList();
            while (resultSet.next()) {
                nom035ListDtos.add(NOM035ListDto.build(resultSet));
            }

            return nom035ListDtos;

        }catch(SQLException e) {
            LOGGER.error("Error obteniendo los datos de nomina 035: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    public List<NOM035CuestionarioDto> getCuestionarios() throws BusinessException {

        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call NOM035_GET_CUESTIONARIOS();";

        try (Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)){

            cStmt.execute();

            ResultSet resultSet = cStmt.getResultSet();

            List<NOM035CuestionarioDto> nom035CuestionarioDtos = new ArrayList();
            while (resultSet.next()) {
                nom035CuestionarioDtos.add(NOM035CuestionarioDto.build(resultSet));
            }

            return nom035CuestionarioDtos;

        }catch(SQLException e) {
            LOGGER.error("Error obteniendo los cuestionarios: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    public NOM035RegistroDto cuationarioReporte(int idColaborador, int idCuestionario) throws BusinessException{
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call NOM035_REPORTE_TOTAL(?,?);";

        try (Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)){
            cStmt.setInt(1,idColaborador);
            cStmt.setInt(2,idCuestionario);
            cStmt.execute();

            ResultSet resultSet = cStmt.getResultSet();
           if(resultSet.next()) {
               return NOM035RegistroDto.build(resultSet);
            } else{
               LOGGER.error("Error obteniendo reporte del cuestionarios ");
               throw new BusinessException("Error obteniendo reporte del cuestionarios. ",404);
           }

        }catch(SQLException e) {
            LOGGER.error("Error obteniendo los cuestionarios: "+e.getMessage(),e);
            throw new BusinessException("Error obteniendo reporte del cuestionarios. ",500);
        }
    }
    public List<NOM035RegistroDto> cuationarioCategorias(int idColaborador, int idCuestionario) throws BusinessException{
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call NOM035_REPORTE_CATEGORIAS(?,?);";

        try (Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)){
            cStmt.setInt(1,idColaborador);
            cStmt.setInt(2,idCuestionario);
            cStmt.execute();

            ResultSet resultSet = cStmt.getResultSet();

            List<NOM035RegistroDto> nom035RegistroDtos = new ArrayList();
            while (resultSet.next()) {
                nom035RegistroDtos.add(NOM035RegistroDto.build(resultSet));
            }

            return nom035RegistroDtos;

        }catch(SQLException e) {
            LOGGER.error("Error obteniendo los cuestionarios: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    public String getColaboradorNombre(int idColaborador) throws BusinessException{
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "SELECT CONCAT (p.nombre,' ',p.apellido_mat,' ',p.apellido_pat) as nombre from colaborador c INNER JOIN persona p on p.pers_id = c.pers_id where c.clave_colaborador = ?;";
        try (Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)){

            cStmt.setInt(1,idColaborador);
            cStmt.execute();

            ResultSet resultSet = cStmt.getResultSet();


            if (resultSet.next()) {
                return resultSet.getString("nombre");
            }else{
                LOGGER.error("Error obteniendo los datos del cuestionario.");
                throw new BusinessException("Error obteniendo los datos del cuestionario.",500);
            }


        }catch(SQLException e) {
            LOGGER.error("Error obteniendo los cuestionarios: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }

    }

    public List<NOM035RegistroDto> cuationarioDominios(int idColaborador, int idCuestionario) throws BusinessException{
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call NOM035_REPORTE_DOMINIOS(?,?);";

        try (Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)){

            cStmt.setInt(1,idColaborador);
            cStmt.setInt(2,idCuestionario);
            cStmt.execute();

            ResultSet resultSet = cStmt.getResultSet();

            List<NOM035RegistroDto> nom035RegistroDtos = new ArrayList();
            while (resultSet.next()) {
                nom035RegistroDtos.add(NOM035RegistroDto.build(resultSet));
            }

            return nom035RegistroDtos;

        }catch(SQLException e) {
            LOGGER.error("Error obteniendo los cuestionarios: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }
    public List<NOM035ReporteGeneral> reporteGeneral(int idCliente) throws BusinessException{
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call NOM035_REPORTE_TOTAL_CLIENTE(?);";

        try (Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)){

            cStmt.setInt(1,idCliente);
            cStmt.execute();

            ResultSet resultSet = cStmt.getResultSet();

            List<NOM035ReporteGeneral> nom035ReporteGenerals = new ArrayList();
            while (resultSet.next()) {
                nom035ReporteGenerals.add(NOM035ReporteGeneral.build(resultSet));
            }

            return nom035ReporteGenerals;

        }catch(SQLException e) {
            LOGGER.error("Error generando los datos del reporte general: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    public NOM035ReporteRespuestas reporteRespuestasMetadata(int idColaborador, int idCuestionario) throws BusinessException{
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call NOM035_REPORTE_RESPUESTAS_METADATA(?,?);";

        try (Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)){

            cStmt.setInt(1,idColaborador);
            cStmt.setInt(2,idCuestionario);
            cStmt.execute();

            ResultSet resultSet = cStmt.getResultSet();

            if (resultSet.next()) {
                return NOM035ReporteRespuestas.build(resultSet);
            }else{
                throw new BusinessException("No se pudo encontrar información para este reporte.",404);
            }

        }catch(SQLException e) {
            LOGGER.error("Error generando los datos del reporte general: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    public List<NOM035ReporteRespuestaRegistro> reporteRespuestasData(int idColaborador, int idCuestionario) throws BusinessException{
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call NOM035_REPORTE_RESPUESTAS_DATA(?,?);";

        try (Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)){

            cStmt.setInt(1,idColaborador);
            cStmt.setInt(2,idCuestionario);
            cStmt.execute();

            ResultSet resultSet = cStmt.getResultSet();

            List<NOM035ReporteRespuestaRegistro> nom035ReporteRespuestaRegistros = new ArrayList();
            while (resultSet.next()) {
                nom035ReporteRespuestaRegistros.add(NOM035ReporteRespuestaRegistro.build(resultSet));
            }

            return nom035ReporteRespuestaRegistros;

        }catch(SQLException e) {
            LOGGER.error("Error generando los datos del reporte general: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }


}
