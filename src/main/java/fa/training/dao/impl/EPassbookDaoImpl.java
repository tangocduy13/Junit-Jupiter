package fa.training.dao.impl;

import fa.training.dao.AccountDao;
import fa.training.dao.EPassbookDao;
import fa.training.entities.Account;
import fa.training.entities.EPassbook;
import fa.training.utils.HibernateUtils;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class EPassbookDaoImpl extends BaseDaoImpl<EPassbook,Integer>implements EPassbookDao {
    public EPassbookDaoImpl() {
        super(EPassbook.class);
    }

    @Override
    public void createNewSavings(String accountNumber, double depositedAmount, int term) throws Exception {
        try (Session session = HibernateUtils.getSessionFactory().getCurrentSession()) {


            session.beginTransaction();

            // Check if deposited amount is greater than the account balance
            BigDecimal depositedAmountBigDecimal = BigDecimal.valueOf(depositedAmount);
            Account account = session.createQuery("from Account where accountNumber = :accountNumber", Account.class)
                    .setParameter("accountNumber", accountNumber)
                    .getSingleResult();
            if (account.getBalance().compareTo(depositedAmountBigDecimal) < 0) {
                throw new Exception("Deposited amount cannot be greater than the account balance");
            }

            // Create EPassbook entity
            EPassbook ePassbook = EPassbook.builder()
                    .account(account)
                    .depositedAmount(depositedAmountBigDecimal)
                    .term(term)
                    .startDate(LocalDateTime.now())
                    .maturityDate(LocalDate.now().plusMonths(term))
                    .build();

            session.persist(ePassbook);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Object[]> getPassbookDetail(int passbookId) {
        try(Session session = HibernateUtils.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT ep.account.accountNumber, CONCAT(ep.account.lastName,' ', ep.account.firstName) AS full_name, ep.depositedAmount, ep.startDate, ep.term, ep.maturityDate, " +
                            "(CASE " +
                            "WHEN ep.term = 1 THEN ep.depositedAmount * 4.55 / 100 * ep.term " +
                            "WHEN ep.term = 2 THEN ep.depositedAmount * 4.65 / 100 * ep.term " +
                            "WHEN ep.term = 3 THEN ep.depositedAmount * 4.75 / 100 * ep.term " +
                            "WHEN ep.term = 6 THEN ep.depositedAmount * 6.2 / 100 * ep.term " +
                            "WHEN ep.term = 9 THEN ep.depositedAmount * 6.2 / 100 * ep.term " +
                            "WHEN ep.term = 12 THEN ep.depositedAmount * 6.4 / 100 * ep.term " +
                            "WHEN ep.term = 18 THEN ep.depositedAmount * 6.7 / 100 * ep.term " +
                            "WHEN ep.term = 24 THEN ep.depositedAmount * 6.7 / 100 * ep.term " +
                            "WHEN ep.term = 36 THEN ep.depositedAmount * 6.7 / 100 * ep.term " +
                            "ELSE 0 " +
                            "END) as estimated_interest " +
                            "FROM EPassbook ep WHERE ep.id = :passbookId")
                    .setParameter("passbookId", passbookId)
                    .getResultList();
        }
    }
//    @Override
//    public  save(EPassbook ePassbook) {
//        try(Session session = HibernateUtils.getSessionFactory().getCurrentSession()) {
//            session.beginTransaction();
//            session.persist(ePassbook);
//            session.getTransaction().commit();
//        }
//    }
}
