/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.cediant.database;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author miguel
 */
public class UserHelper {
    
    Session session;
    
    private Logger logger = LoggerFactory.getLogger(UserHelper.class);

    public User getUserByUsername (String username){
        User user = new User();
        try {
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Users as users where users.id.username LIKE '"+username+"'");
            user = (User) q.list().get(0);
            logger.info("User '"+user.getUsername()+"' recovered");
        } catch (Exception ex){
            logger.error("Database error");
            logger.error(ex.getMessage());
        }
        return user;        
    }
    
    public User getUserById (int id){
        User user = new User();
        try {
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Users as users where users.id.username LIKE "+id);
            user = (User) q.list().get(0);
            logger.info("User '"+user.getId()+"' recovered");
        } catch (Exception ex){
            logger.error("Database error");
            logger.error(ex.getMessage());
        }
        return user;        
    }
}
