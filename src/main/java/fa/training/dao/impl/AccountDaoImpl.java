package fa.training.dao.impl;

import fa.training.dao.AccountDao;
import fa.training.entities.Account;
import fa.training.utils.HibernateUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

import java.util.List;

public class AccountDaoImpl extends BaseDaoImpl<Account, Integer> implements AccountDao {
    public AccountDaoImpl() {
        super(Account.class);
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        try (Session session = HibernateUtils.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Account account = session.createQuery("from Account where accountNumber = :accountNumber", Account.class)
                    .setParameter("accountNumber", accountNumber).getSingleResult();
            session.getTransaction().commit();
            return account;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Account> getAccountByBalance(double minBalance) {
        try (Session session = HibernateUtils.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
            Root<Account> root = criteriaQuery.from(Account.class);
            criteriaQuery.select(root).where(criteriaBuilder.greaterThan(root.get("balance"), minBalance));
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
