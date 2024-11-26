package hiber.dao;

import hiber.model.User;
import org.hibernate.NonUniqueResultException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

@Repository
public class UserDaoImp implements UserDao {

    Logger logger = Logger.getLogger(UserDaoImp.class.getName());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        return sessionFactory.getCurrentSession().createQuery("from User").getResultList();
    }


    @Override
    public List<User> getByCar(String model, int series) {
        List<User> users = sessionFactory.getCurrentSession()
                .createQuery("select u from User u where u.car.model = :model and u.car.series = :series",
                        User.class)
                .setParameter("model", model)
                .setParameter("series", series)
                .getResultList();
        if (users.isEmpty()) {
            logger.warning("Пользователей с такой машиной не найдено.");
        } else if (users.size() > 1) {
            logger.warning("Найдено " + users.size() + " пользовател(я/ей) с одинаковой машиной.");
        }
        return users;
    }

//    @Override
//    public User getByCar(String model, int series) {
//        TypedQuery<User> typedQuery = sessionFactory.getCurrentSession()
//                .createQuery("select u from User u where u.car.model = :model and u.car.series = :series",
//                        User.class)
//                .setParameter("model", model)
//                .setParameter("series", series);
//        try {
//            return typedQuery.getSingleResult();
//        } catch (NoResultException e) {
//            logger.warning("Пользователя с такой машиной не найдено.");
//            return null;
//        } catch (NonUniqueResultException e) {
//            logger.warning("Найдено более одного пользователя с одинаковой машиной.");
//            throw new IllegalStateException("Найдено несколько пользователей с одинаковой машиной.");
//        }
//    }
}
