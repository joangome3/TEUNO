package bp.aplicaciones.mantenimientos.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import bp.aplicaciones.conexion.hibernateUtil;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_equipo_tipo_conector;

public class dao_relacion_equipo_tipo_conector {

	private SessionFactory sessionFactory = hibernateUtil.getSessionFactory();

	public void insertarRelacionEquipoTipoConector(modelo_relacion_equipo_tipo_conector relacion_equipo_tipo_conector) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(relacion_equipo_tipo_conector);
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

	public void actualizarRelacionEquipoTipoConector(
			modelo_relacion_equipo_tipo_conector relacion_equipo_tipo_conector) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(relacion_equipo_tipo_conector);
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

	public void eliminarRelacionEquipoTipoConector(modelo_relacion_equipo_tipo_conector relacion_equipo_tipo_conector) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.delete(relacion_equipo_tipo_conector);
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
