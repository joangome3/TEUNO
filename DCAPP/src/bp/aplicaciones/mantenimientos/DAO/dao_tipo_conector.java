package bp.aplicaciones.mantenimientos.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import bp.aplicaciones.conexion.hibernateUtil;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_conector;

public class dao_tipo_conector {

	private SessionFactory sessionFactory = hibernateUtil.getSessionFactory();

	@SuppressWarnings("unchecked")
	public List<modelo_tipo_conector> consultarTipoConectores(long id1, long id2, String criterio1, String criterio2,
			int limite, int tipo) {
		Session session = null;
		Transaction transaction = null;
		List<modelo_tipo_conector> lista_tipo_conectores = new ArrayList<modelo_tipo_conector>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createNativeQuery("{CALL tipo_conector_obtenerTipoConectores(?, ?, ?, ?, ?, ?)}",
					modelo_tipo_conector.class);
			query.setParameter(1, id1);
			query.setParameter(2, id2);
			query.setParameter(3, criterio1);
			query.setParameter(4, criterio2);
			query.setParameter(5, limite);
			query.setParameter(6, tipo);
			lista_tipo_conectores = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return lista_tipo_conectores;
	}

	public void insertarTipoConector(modelo_tipo_conector tipo_conector) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(tipo_conector);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void actualizarTipoConector(modelo_tipo_conector tipo_conector) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(tipo_conector);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void eliminarTipoConector(modelo_tipo_conector tipo_conector) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.delete(tipo_conector);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
