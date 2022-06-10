package com.aura.admin.adminqamm.service;


import com.aura.admin.adminqamm.dao.PromocionDao;
import com.aura.admin.adminqamm.dto.Promocion;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.util.PermitionENUM;
import com.aura.admin.adminqamm.util.PromoENUM;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PromocionService {

    @Autowired
    private PromocionDao promocionDao;

    @Autowired
    private PermissionService permissionService;


    public List<Promocion> getPromociones(int loggedUser) throws BusinessException {

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(), PermitionENUM.PROMOCIONES_ADMINISTRADOR.getId(), PermitionENUM.PROMOCIONES_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        return promocionDao.getPromociones();
    }
    public Promocion getPromocion(int loggedUser,int idPromocion) throws BusinessException {

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(), PermitionENUM.PROMOCIONES_ADMINISTRADOR.getId(), PermitionENUM.PROMOCIONES_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        return promocionDao.getPromocion(idPromocion);
    }


    public Promocion crearPromocion(int loggedUser,Promocion promocion) throws BusinessException {

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(), PermitionENUM.PROMOCIONES_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        String uuid = UUID.randomUUID().toString();
        String base64uuid = uuidToBase64(uuid);
        promocion.setCodigoPromo(base64uuid);


        validarPromocion(promocion);

        return promocionDao.crearPromocion(promocion);

    }

    public void updatePromocion(int loggedUser,Promocion promocion) throws BusinessException {

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(), PermitionENUM.PROMOCIONES_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        validarPromocion(promocion);

        promocionDao.updatePromocion(promocion);

    }

    public void deletePromocion(int loggedUser,int idPromocion) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(), PermitionENUM.PROMOCIONES_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        promocionDao.deletePromocion(idPromocion);
    }

    private static String uuidToBase64(String str) {
        Base64 base64 = new Base64();
        UUID uuid = UUID.fromString(str);
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return base64.encodeBase64URLSafeString(bb.array());
    }
    private static String uuidFromBase64(String str) {
        Base64 base64 = new Base64();
        byte[] bytes = base64.decodeBase64(str);
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid.toString();
    }

    private void validarPromocion(Promocion promocion) throws BusinessException {
        //validar que vengan todos los datos.
        if(promocion.getNombre()==null || promocion.getNombre().trim().isEmpty()){
            throw new BusinessException("Promoción inválida, por favor ingrese un nombre para la promoción.",406);
        }
        if(promocion.getTipoPromocion()==null || (
                promocion.getTipoPromocion() != PromoENUM.MONTO.getId() &&
                        promocion.getTipoPromocion() != PromoENUM.PORCENTAJE.getId() )){
            throw new BusinessException("Promoción inválida, por favor ingrese un tipo de promoción para la promoción.",406);
        }
        if(promocion.getFechaInicio()==null){
            throw new BusinessException("Promoción inválida, por favor ingrese una fecha de inicio para la promoción.",406);
        }
        if(promocion.getFechaFin()==null){
            throw new BusinessException("Promoción inválida, por favor ingrese una fecha de fin para la promoción.",406);
        }


        promocion.setFechaInicio(new Date(promocion.getFechaInicio().getTime() + (60 * 1000)));
        promocion.setFechaFin(new Date(promocion.getFechaFin().getTime() + (60 * 1000)));

        //Insertar por tipo, checar tipo y validar, solo poner datos de ese tipo.
        if(promocion.getTipoPromocion() == PromoENUM.MONTO.getId()){
            if(promocion.getMontoBeneficio() == null || promocion.getMontoBeneficio()<0){
                throw new BusinessException("Promoción inválida, por favor ingrese un monto válido para la promoción.",406);
            }
            promocion.setPorcentajeBeneficio(0d);
            promocion.setMontoPorcentajeMaximo(0);
        }else if(promocion.getTipoPromocion() == PromoENUM.PORCENTAJE.getId()){//porcentaje
            promocion.setMontoBeneficio(0);
            if(promocion.getPorcentajeBeneficio()== null || promocion.getPorcentajeBeneficio()>0.999d || promocion.getPorcentajeBeneficio()<0){
                throw new BusinessException("Promoción inválida, por favor ingrese un porcentaje válido para la promoción.",406);
            }
            if(promocion.getMontoPorcentajeMaximo()==null || promocion.getMontoPorcentajeMaximo()<0){
                throw new BusinessException("Promoción inválida, por favor ingrese un monto máximo para la promoción.",406);
            }
        }
    }

}