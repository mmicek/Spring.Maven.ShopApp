package DBHibernate;

//import org.hibernate.SessionFactory;

//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.service.ServiceRegistry;


public class HibernateUtil {
//	private static SessionFactory factory = null;
//	
//	private static SessionFactory buildSessionFactory() {
//		try {
//			Configuration configuration = new Configuration();
//            configuration.configure();
// 
//            StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
//            standardServiceRegistryBuilder.applySettings(configuration.getProperties());
//            ServiceRegistry serviceRegistry = standardServiceRegistryBuilder.build();
// 
//            return configuration.buildSessionFactory(serviceRegistry);
//		}catch(Exception e) {
//			throw new IllegalArgumentException("Cannot create SessionFactory in HibernateUtil");
//		}
//	}
//	
//	public static SessionFactory getSessionFactory() {
//		if(factory == null) factory = buildSessionFactory();
//		return factory;
//	}
}
