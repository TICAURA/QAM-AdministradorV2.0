package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.dao.NominaDao;
import com.aura.admin.adminqamm.dto.*;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.util.PermitionENUM;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Nomina035Service {

    @Autowired
    private NominaDao nominaDao;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private NOM035ReporteService reporteService;


    public List<NOM035ListDto> lista(int loggedUser) throws BusinessException{

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.NOM035_ADMINISTRADOR.getId(),PermitionENUM.NOM035_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        return nominaDao.getListaNomina();
    }
    public List<NOM035CuestionarioDto> cuestionarios(int loggedUser)throws BusinessException {

        if (!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(), PermitionENUM.NOM035_ADMINISTRADOR.getId(), PermitionENUM.NOM035_VER.getId())) {
            throw new BusinessException("El usuario no tiene acceso a esta información.", 401);
        }
        return nominaDao.getCuestionarios();
    }
    public NOM035ReportDto getReporte(int loggedUser,int idColaborador, int idCuestionario)throws BusinessException {

        if (!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(), PermitionENUM.NOM035_ADMINISTRADOR.getId(), PermitionENUM.NOM035_VER.getId())) {
            throw new BusinessException("El usuario no tiene acceso a esta información.", 401);
        }

        NOM035ReportDto reporte = new NOM035ReportDto();
        reporte.setId(idColaborador);
        reporte.setNombre(nominaDao.getColaboradorNombre(idColaborador));
        reporte.setCuestionario(nominaDao.cuationarioReporte(idColaborador,idCuestionario));
        reporte.setCategorias(nominaDao.cuationarioCategorias(idColaborador,idCuestionario));
        reporte.setDominios(nominaDao.cuationarioDominios(idColaborador,idCuestionario));
        return reporte;
    }


    public XSSFWorkbook generarReporteRepuestas(int loggedUser,int idColaborador,int idCuestionario) throws BusinessException{

        if (!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(), PermitionENUM.NOM035_ADMINISTRADOR.getId(), PermitionENUM.NOM035_VER.getId())) {
            throw new BusinessException("El usuario no tiene acceso a esta información.", 401);
        }

        NOM035ReporteRespuestas reporteRespuestas = nominaDao.reporteRespuestasMetadata(idColaborador,idCuestionario);
        reporteRespuestas.setPreguntas(nominaDao.reporteRespuestasData(idColaborador,idCuestionario));

        return reporteService.buildExcelReporteRespuestas(reporteRespuestas);
    }

    public XSSFWorkbook generarReporteGeneral(int loggedUser,int idCliente) throws BusinessException{

        if (!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(), PermitionENUM.NOM035_ADMINISTRADOR.getId(), PermitionENUM.NOM035_VER.getId())) {
            throw new BusinessException("El usuario no tiene acceso a esta información.", 401);
        }

        List<NOM035ReporteGeneral> reporteGeneralRegistros = nominaDao.reporteGeneral(idCliente);



        return reporteService.buildExcelReporteGeneral(reporteGeneralRegistros);
    }

    public XSSFWorkbook generarReporteIndividual(int loggedUser, int idColaborador, int idCuestionario) throws BusinessException{

        if (!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(), PermitionENUM.NOM035_ADMINISTRADOR.getId(), PermitionENUM.NOM035_VER.getId())) {
            throw new BusinessException("El usuario no tiene acceso a esta información.", 401);
        }
        NOM035ReportDto reporteDto = this.getReporte(loggedUser,idColaborador,idCuestionario);


        return reporteService.buildExcelReporteIndividual(reporteDto);
    }
}
