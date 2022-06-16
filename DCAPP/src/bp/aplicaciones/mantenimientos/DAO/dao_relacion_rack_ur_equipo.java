package bp.aplicaciones.mantenimientos.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import bp.aplicaciones.conexion.hibernateUtil;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_rack_ur_equipo;

public class dao_relacion_rack_ur_equipo {

	private SessionFactory sessionFactory = hibernateUtil.getSessionFactory();

	public void insertarRelacionRackUrEquipo(modelo_relacion_rack_ur_equipo relacion_gestion_equipo_ur_equipo) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(relacion_gestion_equipo_ur_equipo);
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

	public void actualizarRelacionRackUrEquipo(modelo_relacion_rack_ur_equipo relacion_gestion_equipo_ur_equipo) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(relacion_gestion_equipo_ur_equipo);
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

	public void eliminarRelacionRackUrEquipo(modelo_relacion_rack_ur_equipo relacion_gestion_equipo_ur_equipo) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.delete(relacion_gestion_equipo_ur_equipo);
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
