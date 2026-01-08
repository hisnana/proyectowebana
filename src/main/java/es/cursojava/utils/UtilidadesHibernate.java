package es.cursojava.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;

public class UtilidadesHibernate {

    // SessionFactory es un objeto "pesado" que se crea una vez
    // y se reutiliza durante toda la vida de la aplicación.
    // Aquí lo guardamos como static final (patrón Singleton: objeto que se crea una vez en memoria).
    private static final SessionFactory sessionFactory = buildSessionFactory();

    // Método privado que construye la SessionFactory a partir del fichero hibernate.cfg.xml
    private static SessionFactory buildSessionFactory() {
        try {
            // Carga la configuración por defecto desde hibernate.cfg.xml (en el classpath)
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Throwable ex) {
            // Si hay un error al construir la SessionFactory, lo mostramos
            System.err.println("Error al crear SessionFactory: " + ex.getMessage());
            // Lanzamos un error grave porque sin SessionFactory no podemos trabajar
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Devuelve la SessionFactory para quien la necesite
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // Método de ayuda para abrir una nueva sesión
    public static Session abrirSesion() {
        // Cada Session representa una "conversación" con la BD
        return sessionFactory.openSession();
    }
}
