package com.aura.admin.adminqamm.dao;

import com.aura.admin.adminqamm.config.DataSourceConfig;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Colaborador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AdminDao {

    Logger logger = LoggerFactory.getLogger(AdminDao.class);

    @Autowired
    private DataSourceConfig dataSourceConfig;

    public boolean validatePermition(int userId,int permitionId) throws BusinessException{


            DataSource dataSource = dataSourceConfig.getDataSource();
            String query = "call CHECAR_PERMISO(?,?,?);";

        try (Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)){

            cStmt.setInt(1,userId);
            cStmt.setInt(2,permitionId);
            cStmt.registerOutParameter(3, java.sql.Types.INTEGER);
            cStmt.execute();

            int valid=cStmt.getInt(3);
            logger.error(valid+" valid permition:"+(valid==1));
            return valid==1;

        }catch (SQLException e){
            logger.error("Error checando si la factura existe: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }


    public boolean validarEmail(String email)throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call CHECAR_EMAIL(?);";

        try (Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)){

            cStmt.setString(1,email);

            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();
            rs.next();
            int id = rs.getInt("EXISTS_COL");
            logger.error(id+" EXIST:"+(id==1));
            return id==1;

        }catch (SQLException e){
            logger.error("Error checando si la factura existe: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    public void actualizarContra(String email, String contra) throws BusinessException {
        DataSource dataSource = dataSourceConfig.getDataSource();
        String query = "call CAMBIAR_CONTRA(?,?);";

        try (Connection con = dataSource.getConnection();CallableStatement cStmt = con.prepareCall(query)){

            cStmt.setString(1,email);
            cStmt.setString(2,contra);


            cStmt.execute();

        }catch (SQLException e){
            logger.error("Error insertando el id del archivo factura: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }


}
