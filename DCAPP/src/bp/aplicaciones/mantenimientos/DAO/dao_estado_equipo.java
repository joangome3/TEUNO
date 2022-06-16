package bp.aplicaciones.mantenimientos.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import bp.aplicaciones.conexion.hibernateUtil;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_equipo;

public class dao_estado_equipo {

	private SessionFactory sessionFactory = hibernateUtil.getSessionFactory();

	@SuppressWarnings("unchecked")
	public List<modelo_estado_equipo> consultarEstadosEquipo(long id1, long id2, String criterio1, String criterio2,
			int limite, int tipo) {
		Session session = null;
		Transaction transaction = null;
		List<modelo_estado_equipo> lista_estados_equipo = new ArrayList<modelo_estado_equipo>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createNativeQuery("{CALL estado_equipo_obtenerEstadosEquipo(?, ?, ?, ?, ?, ?)}",
					modelo_estado_equipo.class);
			query.setParameter(1, id1);
			query.setParameter(2, id2);
			query.setParameter(3, criterio1);
			query.setParameter(4, criterio2);
			query.setParameter(5, limite);
			query.setParameter(6, tipo);
			lista_estados_equipo = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return lista_estados_equipo;
	}

	public void insertarEstadoEquipo(modelo_estado_equipo estado_equipo) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(estado_equipo);
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

	public void actualizarEstadoEquipo(modelo_estado_equipo estado_equipo) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(estado_equipo);
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

	public void eliminarEstadoEquipo(modelo_estado_equipo estado_equipo) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.delete(estado_equipo);
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
