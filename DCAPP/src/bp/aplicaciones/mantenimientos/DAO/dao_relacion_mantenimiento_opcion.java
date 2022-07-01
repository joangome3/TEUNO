package bp.aplicaciones.mantenimientos.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import bp.aplicaciones.conexion.hibernateUtil;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_mantenimiento_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_opcion_mantenimiento;

public class dao_relacion_mantenimiento_opcion {

	private SessionFactory sessionFactory = hibernateUtil.getSessionFactory();

	@SuppressWarnings("unchecked")
	public List<modelo_relacion_mantenimiento_mantenimiento> obtenerRelacionesMantenimientos(long id1, long id2,
			String criterio1, String criterio2, int limite, int tipo) {
		Session session = null;
		Transaction transaction = null;
		List<modelo_relacion_mantenimiento_mantenimiento> lista_relaciones = new ArrayList<modelo_relacion_mantenimiento_mantenimiento>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createNativeQuery("{CALL mantenimiento_obtenerMantenimientos2(?, ?, ?, ?, ?, ?)}",
					modelo_empresa.class);
			query.setParameter(1, id1);
			query.setParameter(2, id2);
			query.setParameter(3, criterio1);
			query.setParameter(4, criterio2);
			query.setParameter(5, limite);
			query.setParameter(6, tipo);
			lista_relaciones = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return lista_relaciones;
	}

	@SuppressWarnings("unchecked")
	public List<modelo_relacion_opcion_mantenimiento> obtenerRelacionesOpciones(long id1, long id2, String criterio1,
			String criterio2, int limite, int tipo) {
		Session session = null;
		Transaction transaction = null;
		List<modelo_relacion_opcion_mantenimiento> lista_relaciones = new ArrayList<modelo_relacion_opcion_mantenimiento>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createNativeQuery("{CALL opcion_obtenerOpciones2(?, ?, ?, ?, ?, ?)}",
					modelo_empresa.class);
			query.setParameter(1, id1);
			query.setParameter(2, id2);
			query.setParameter(3, criterio1);
			query.setParameter(4, criterio2);
			query.setParameter(5, limite);
			query.setParameter(6, tipo);
			lista_relaciones = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return lista_relaciones;
	}

	public void insertarRelacionMantenimiento(modelo_relacion_mantenimiento_mantenimiento relacion) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(relacion);
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

	public void insertarRelacionOpcion(modelo_relacion_opcion_mantenimiento relacion) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(relacion);
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

	public void eliminarRelacionMantenimiento(modelo_relacion_mantenimiento_mantenimiento relacion) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.delete(relacion);
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

	public void eliminarRelacionOpcion(modelo_relacion_opcion_mantenimiento relacion) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.delete(relacion);
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
