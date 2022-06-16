package bp.aplicaciones.mantenimientos.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import bp.aplicaciones.conexion.hibernateUtil;
import bp.aplicaciones.mantenimientos.modelo.modelo_modelo;

public class dao_modelo {

	private SessionFactory sessionFactory = hibernateUtil.getSessionFactory();

	@SuppressWarnings("unchecked")
	public List<modelo_modelo> consultarModelos(long id1, long id2, String criterio1, String criterio2,
			int limite, int tipo) {
		Session session = null;
		Transaction transaction = null;
		List<modelo_modelo> lista_modelos = new ArrayList<modelo_modelo>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createNativeQuery("{CALL modelo_obtenerModelos(?, ?, ?, ?, ?, ?)}",
					modelo_modelo.class);
			query.setParameter(1, id1);
			query.setParameter(2, id2);
			query.setParameter(3, criterio1);
			query.setParameter(4, criterio2);
			query.setParameter(5, limite);
			query.setParameter(6, tipo);
			lista_modelos = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return lista_modelos;
	}

	public void insertarModelo(modelo_modelo modelo) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(modelo);
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

	public void actualizarModelo(modelo_modelo modelo) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(modelo);
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

	public void eliminarModelo(modelo_modelo modelo) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.delete(modelo);
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
