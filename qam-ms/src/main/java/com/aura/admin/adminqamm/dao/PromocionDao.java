package com.aura.admin.adminqamm.dao;

import com.aura.admin.adminqamm.config.DataSourceConfig;
import com.aura.admin.adminqamm.dto.Promocion;
import com.aura.admin.adminqamm.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PromocionDao {
    Logger LOGGER = LoggerFactory.getLogger(PromocionDao.class);

    @Autowired
    private DataSourceConfig dataSourceConfig;

    public List<Promocion> getPromociones() throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();

        String query = "call PROMO_TRAER_PROMOS(); ";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.execute();

            List<Promocion> promociones = new ArrayList<>();
            ResultSet resultSet = cStmt.getResultSet();
            while(resultSet.next()){
                promociones.add(Promocion.build(resultSet));
            }
            return promociones;

        }catch (SQLException e){
            LOGGER.error("Error obteniendo las promociones: "+e.getMessage(),e);
            throw new BusinessException("Error obteniendo las promociones.",500);
        }
    }

    public Promocion getPromocion(int idPromocion) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();

        String query = "call PROMO_TRAER_PROMO(?); ";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setInt(1,idPromocion);
            cStmt.execute();


            ResultSet resultSet = cStmt.getResultSet();
            if(resultSet.next()) {
                return Promocion.build(resultSet);
            }else{
                return null;
            }


        }catch (SQLException e){
            LOGGER.error("Error obteniendo la promocion: "+e.getMessage(),e);
            throw new BusinessException("Error obteniendo las promociones.",500);
        }
    }


    public Promocion crearPromocion(Promocion promocion)throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call PROMO_CREAR_PROMOCION(?,?,?,?,?,?,?,?,?,?);";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {
            cStmt.setString(1,promocion.getNombre());
            cStmt.setString(2,promocion.getDescripcion());
            cStmt.setInt(3,promocion.getTipoPromocion());
            cStmt.setString(4,promocion.getCodigoPromo());
            cStmt.setDate(5,new Date(promocion.getFechaInicio().getTime()));
            cStmt.setDate(6,new Date(promocion.getFechaFin().getTime()));
            cStmt.setInt(7,promocion.getMontoBeneficio());
            cStmt.setDouble(8,promocion.getPorcentajeBeneficio());
            cStmt.setInt(9,promocion.getMontoPorcentajeMaximo());
            cStmt.setBoolean(10,promocion.isActivo());

            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();
            rs.next();
            return Promocion.build(rs);

        }catch (SQLException e){
            LOGGER.error("Error guardando las promociones."+e.getMessage(),e);
            throw new BusinessException("Error guardando las promociones.",500);
        }
    }

    public void updatePromocion(Promocion promocion)throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call PROMO_UPDATE_PROMOCION(?,?,?,?,?,?,?,?,?,?);";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {
            cStmt.setInt(1,promocion.getIdPromocion());
            cStmt.setString(2,promocion.getNombre());
            cStmt.setString(3,promocion.getDescripcion());
            cStmt.setInt(4,promocion.getTipoPromocion());
            cStmt.setDate(5,new Date(promocion.getFechaInicio().getTime()));
            cStmt.setDate(6,new Date(promocion.getFechaFin().getTime()));
            cStmt.setInt(7,promocion.getMontoBeneficio());
            cStmt.setDouble(8,promocion.getPorcentajeBeneficio());
            cStmt.setInt(9,promocion.getMontoPorcentajeMaximo());
            cStmt.setBoolean(10,promocion.isActivo());

            cStmt.execute();

        }catch (SQLException e){
            LOGGER.error("Error actualizando las promociones."+e.getMessage(),e);
            throw new BusinessException("Error actualizando las promociones.",500);
        }
    }

    public void deletePromocion(int idPromocion) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();

        String query = "call PROMO_DELETE_PROMOCION(?); ";
        try(Connection con = dataSource.getConnection(); CallableStatement cStmt = con.prepareCall(query)) {

            cStmt.setInt(1,idPromocion);
            cStmt.execute();

        }catch (SQLException e){
            LOGGER.error("Error borrando las promociones."+e.getMessage(),e);
            throw new BusinessException("Error borrando las promociones.",500);
        }
    }




}
