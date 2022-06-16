package bp.aplicaciones.mantenimientos.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import bp.aplicaciones.conexion.hibernateUtil;
import bp.aplicaciones.equipos.modelo.modelo_gestion_equipo;

public class dao_gestion_equipo {

	private SessionFactory sessionFactory = hibernateUtil.getSessionFactory();

	@SuppressWarnings("unchecked")
	public List<modelo_gestion_equipo> consultarGestionDeEquipos(long id1, long id2, String criterio1, String criterio2,
			int limite, int tipo) {
		Session session = null;
		Transaction transaction = null;
		List<modelo_gestion_equipo> lista_gestion_equipos = new ArrayList<modelo_gestion_equipo>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createNativeQuery("{CALL gestion_equipo_obtenerGestionDeEquipos(?, ?, ?, ?, ?, ?)}",
					modelo_gestion_equipo.class);
			query.setParameter(1, id1);
			query.setParameter(2, id2);
			query.setParameter(3, criterio1);
			query.setParameter(4, criterio2);
			query.setParameter(5, limite);
			query.setParameter(6, tipo);
			lista_gestion_equipos = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return lista_gestion_equipos;
	}

	public void insertarGestionDeEquipo(modelo_gestion_equipo gestion_equipo) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(gestion_equipo);
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

	public void actualizarGestionDeEquipo(modelo_gestion_equipo gestion_equipo) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(gestion_equipo);
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

	public void eliminarGestionDeEquipo(modelo_gestion_equipo gestion_equipo) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.delete(gestion_equipo);
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
