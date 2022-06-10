/**
 * 
 */
package com.aura.admin.adminqamm.dao;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.aura.admin.adminqamm.model.Banco;
import com.aura.admin.adminqamm.model.CuentaBanco;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Acer
 *
 */
@Service
public class CuentaBancoDAO {
	
	/**
	 * Logger.
	 */
	private final static Logger LOG = LogManager
			.getLogger(CuentaBancoDAO.class);
	
//	EntityManagerFactory emf = Persistence.createEntityManagerFactory("qampersist");
	@PersistenceContext(name="qampersist")
	private EntityManager em;
	
	public CuentaBanco crearCuentBanco(CuentaBanco cuentaBanco) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Realizando crearCuentBanco ... " );
		}

		em.persist(cuentaBanco);

				
		return cuentaBanco;		
	}

	public List<CuentaBanco> buscarCuentaColaborador(Integer persId) {

		List<CuentaBanco> result = null;
		
		if (LOG.isInfoEnabled()) {
			LOG.info("Realizando buscarCuentaColaborador ... ");
		}
		
		StringBuilder queryStr = new StringBuilder();
		queryStr.append("SELECT c FROM ")
			.append(CuentaBanco.class.getSimpleName())
			.append(" c WHERE c.persona.idPersona = ").append(persId)
			.append(" ORDER BY c.esActivo DESC");
						
		Query query = em.createQuery(queryStr.toString());
		
		try {
			result = query.getResultList();
		} catch (NoResultException e) {
			LOG.error("No hay registro en cuenta banco para la persona con ID "+persId);
			result = new ArrayList<CuentaBanco>();
		}
				
		return result;
	}

	public void actualizarCuentaBanco(CuentaBanco cuentaBanco) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Realizando actualizarCuentaBanco ... " );
		}
		
//		em.getTransaction().begin();
		em.merge(cuentaBanco);
//		em.getTransaction().commit();						
	}

	public CuentaBanco buscarCuentaPorId(String clabe) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Realizando buscarCuentaPorId ... "+clabe);
		}
		
		StringBuilder queryStr = new StringBuilder();
		queryStr.append("SELECT c FROM ")
			.append(CuentaBanco.class.getSimpleName())
			.append(" c WHERE c.clabe = '").append(clabe)
			.append("'");
						
		Query query = em.createQuery(queryStr.toString());
		CuentaBanco result = null;
		
		try {
			
			result = (CuentaBanco) query.getSingleResult();
		} catch (NoResultException e) {
			LOG.error("No hay registro en cuenta banco para la persona con ID "+clabe);			
		}
				
		return result;
	}

	/**
	 * 
	 * @param descripcionBanco
	 * @return
	 */
	public Banco consultarBancoPorDescripcion(String descripcionBanco) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Realizando buscarCuentaPorId ... "+descripcionBanco);
		}
		
		StringBuilder queryStr = new StringBuilder();
		queryStr.append("SELECT b FROM cs_banco ")
			.append(" b WHERE b.descripcion = '").append(descripcionBanco)
			.append("'");
						
		Query query = em.createQuery(queryStr.toString());
		Banco result = null;
		
		try {
			
			result = (Banco) query.getSingleResult();
		} catch (NoResultException e) {
			LOG.error("No hay registro en banco con descripcion "+descripcionBanco);			
		}
				
		return result;
	}

	public List<CuentaBanco> buscarCuentaActivaColaborador(Integer idPersona) {
		List<CuentaBanco> result = null;
		
		if (LOG.isInfoEnabled()) {
			LOG.info("Realizando buscarCuentaColaborador ... ");
		}
		
		StringBuilder queryStr = new StringBuilder();
		queryStr.append("SELECT c FROM ")
			.append(CuentaBanco.class.getSimpleName())
			.append(" c WHERE c.persona.idPersona = ").append(idPersona)
			.append(" AND c.esActivo=").append(Boolean.TRUE);
						
		Query query = em.createQuery(queryStr.toString());
		
		try {
			result = query.getResultList();
		} catch (NoResultException e) {
			LOG.error("No hay registro en cuenta banco para la persona con ID "+idPersona);
			result = new ArrayList<CuentaBanco>();
		}
				
		return result;
	}
	

}
