package ra.webmvc.repository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.webmvc.entity.Category;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CategoryRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Category> findAll() {
        Session session = sessionFactory.openSession();
        TypedQuery<Category> query = session.createQuery("select C from Category C", Category.class);
        List<Category> categories = query.getResultList();
        session.close();
        return categories;
    }
    public Category findById(Long id) {
        Session session = sessionFactory.openSession();
        Category category = session.get(Category.class, id);
        session.close();
        return category;
    }

    public boolean isNameExist(String cateName) {
        Session session = sessionFactory.openSession();
        Category category = session.createQuery("from Category where cateName = :cateName", Category.class)
                .setParameter("cateName", cateName)
                .uniqueResult();
        session.close();
        return category != null;
    }

    public void insert(Category category) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            session.save(category);
            tx.commit();
        }catch(Exception e){
           if(tx != null){
               tx.rollback();
           }
           throw e;
        }finally{
            session.close();
        }
    }
    public void update(Category category) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            session.update(category);
            tx.commit();
        }catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
            throw e;
        }finally{
            session.close();
        }
    }
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            Category category = session.get(Category.class, id);
            if(category != null){
                session.delete(category);
            }
            tx.commit();
        }catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
    }
}
