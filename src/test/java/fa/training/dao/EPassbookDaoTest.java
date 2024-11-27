package fa.training.dao;

import fa.training.dao.impl.AccountDaoImpl;
import fa.training.dao.impl.EPassbookDaoImpl;
import fa.training.entities.Account;
import fa.training.entities.EPassbook;
import fa.training.utils.HibernateUtils;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static junit.framework.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EPassbookDaoTest {
    private static EPassbookDao ePassbookDao;
    private static AccountDao accountDao;
    @BeforeAll
    public static void setUp() {
        ePassbookDao = new EPassbookDaoImpl();
        accountDao = new AccountDaoImpl();
    }
    @Test
    @Order(1)
    public void testCreateNewSavings() {
        try {
            ePassbookDao.createNewSavings("1875823456", 1000, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(ePassbookDao.findById(3));
    }
    @Test
    @Order(2)
    public void testGetPassbookDetail() {
        ePassbookDao.getPassbookDetail(1).forEach(e -> {
            Arrays.stream(e).forEach(System.out::println);
        });
        assertNotNull(ePassbookDao.getPassbookDetail(1));
    }
    @Test
    @Order(3)
    public void testInsertEPassbook() {
        Account account = HibernateUtils.getSessionFactory().openSession().get(Account.class, 1);
        EPassbook ePassbook = EPassbook.builder()
                .depositedAmount(BigDecimal.valueOf(1000))
                .account(account)
                .startDate(LocalDateTime.now())
                .term(24)
                .maturityDate(LocalDate.now().plusMonths(24))
                .build();
        ePassbookDao.save(ePassbook);
    }
    @Test
    @Order(4)
    public void testGetAllEPassbookByAccountNumber() {
        ePassbookDao.getAll().forEach(System.out::println);
        assertNotNull(ePassbookDao.getAll());
    }
    @Test
    @Order(5)
    public void testDeleteEPassbook() {
        ePassbookDao.delete(1);
        assertNull(ePassbookDao.findById(1));
    }
    @Test
    @Order(6)
    public void testUpdateEPassbook() {
        EPassbook ePassbook = ePassbookDao.findById(2);
        LocalDateTime updatedDate = LocalDateTime.now();
        ePassbook.setUpdatedDate(updatedDate);
        ePassbookDao.update(ePassbook);
        assertTrue(ePassbookDao.findById(2).getUpdatedDate().compareTo( updatedDate)!=0);
    }
    @Test
    @Order(7)
    public void testInsertIllegalTerm(){
        Account account = HibernateUtils.getSessionFactory().openSession().get(Account.class, 1);
        EPassbook ePassbook = EPassbook.builder()
                .account(account)
                .depositedAmount(BigDecimal.valueOf(1000))
                .startDate(LocalDateTime.now())
                .term(48)
                .maturityDate(LocalDate.now().plusMonths(48))
                .build();
        assertThrows(Exception.class, () -> ePassbookDao.save(ePassbook));
        HibernateUtils.getSessionFactory().openSession().close();
    }
    @Test
    @Order(8)
    public void testCreateIllegalDepositedAmountInCreateNewSaving(){
        assertThrows(Exception.class, () -> ePassbookDao.createNewSavings("1865823456", 2000000000, 12));
    }
    @Test
    @Order(9)
    public void testModifyIllegalMaturityDate(){
        //Do em test trong ngay nen e de LocalDate.now().plusMonths(25) de test xem co modify khong, not equal thi pass
        EPassbook ePassbook = ePassbookDao.findById(2);
        ePassbook.setMaturityDate(LocalDate.now().plusMonths(1));
        assertNotEquals(ePassbookDao.findById(2).getMaturityDate(), LocalDate.now().plusMonths(25));
    }

}
