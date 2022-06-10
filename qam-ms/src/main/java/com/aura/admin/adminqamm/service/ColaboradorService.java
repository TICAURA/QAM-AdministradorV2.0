package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.dto.ColaboradorDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Colaborador;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColaboradorService {

    public List<ColaboradorDto> getColaboradores(int loggedUser) throws BusinessException {
        return new ArrayList<ColaboradorDto>();
    }

    public ColaboradorDto getColaborador(int loggedUser,int colaboradorId) throws BusinessException{
    return new ColaboradorDto();
    }

    public int postColaborador(int loggedUser, ColaboradorDto colaboradorDto) throws BusinessException{
        return 1;
    }

    public void putColaborador(int loggedUser,ColaboradorDto colaboradorDto) throws BusinessException{

    }
    public void deleteColaborador(int loggedUser,int colaboradorId) throws BusinessException{

    }

}
