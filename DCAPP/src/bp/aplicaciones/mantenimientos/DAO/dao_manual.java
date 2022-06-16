package bp.aplicaciones.mantenimientos.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import bp.aplicaciones.conexion.hibernateUtil;
import bp.aplicaciones.mantenimientos.modelo.modelo_manual;

public class dao_manual {
	
	private SessionFactory sessionFactory = hibernateUtil.getSessionFactory();

	@SuppressWarnings("unchecked")
	public List<modelo_manual> consultarManuales(long id1, long id2, String criterio1, String criterio2,
			int limite, int tipo) {
		Session session = null;
		Transaction transaction = null;
		List<modelo_manual> lista_manuales = new ArrayList<modelo_manual>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createNativeQuery("{CALL manual_obtenerManuales(?, ?, ?, ?, ?, ?)}",
					modelo_manual.class);
			query.setParameter(1, id1);
			query.setParameter(2, id2);
			query.setParameter(3, criterio1);
			query.setParameter(4, criterio2);
			query.setParameter(5, limite);
			query.setParameter(6, tipo);
			lista_manuales = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return lista_manuales;
	}

	public void insertarManual(modelo_manual manual) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(manual);
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

	public void actualizarManual(modelo_manual manual) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(manual);
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

	public void eliminarManual(modelo_manual manual) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.delete(manual);
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
