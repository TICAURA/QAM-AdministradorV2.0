package com.aura.admin.adminqamm.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "det_carga_aguilas")
public class DetCarga {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_det_carga_aguilas")
    private Integer idDetCarga;
    
    @Column(name="id_carga_masiva")
    private Integer idCargaMasiva;

    @Column(name="name")
    private String name;
    
    @Column(name="surname")
    private String surname;
    
    @Column(name="surname_2")
    private String surname2;
    
    @Column(name="email")
    private String email;
    
    @Column(name="plate")
    private String plate;
    
    @Column(name="phone_number")
    private String phoneNumber;
    
    @Column(name="document_number")
    private String documentNumber;
    
    @Column(name="amount")
    private Double amount;
    
    @Column(name="created_at")
    private String createdAt;
    
    @Column(name="desc_error")
    private String descError;
    
    @Column(name="id_situacion")
    private String idSituacion;    
    
}
