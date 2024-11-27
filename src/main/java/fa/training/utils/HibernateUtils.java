package fa.training.utils;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    @Getter
    private static SessionFactory sessionFactory;

    static {
        // Create a new Configuration object
        Configuration cfg = new Configuration();
        cfg.configure();

        // Get the SessionFactory object from Configuration
        if (sessionFactory == null) {
            sessionFactory = cfg.buildSessionFactory();
        }

    }

}
