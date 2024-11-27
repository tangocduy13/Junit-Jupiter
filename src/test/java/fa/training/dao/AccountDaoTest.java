package fa.training.dao;

import fa.training.dao.impl.AccountDaoImpl;
import fa.training.entities.Account;
import fa.training.entities.EPassbook;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountDaoTest {
    private static AccountDao accountDao;
    @BeforeAll
    public static void setUp() {
        accountDao = new AccountDaoImpl();
    }
    @Test
    @Order(1)
    public void testInsertAccount() {
        Account account = Account.builder()
                        .accountNumber("1865823456")
                .balance(BigDecimal.valueOf(1000))
                .firstName("Duy")
                .lastName("Ta Ngoc")
                .createdDate(LocalDateTime.now())
                        .build();
        accountDao.save(account);
    }
    @Test
    @Order(2)
    public void testFindAccountById() {
        Account account = accountDao.findById(1);
        System.out.println(account);
        assertNotNull(account);
    }
    @Test
    @Order(3)
    public void testUpdateAccount() {
        Account account = accountDao.findById(1);
        account.setBalance(BigDecimal.valueOf(200000000));
        accountDao.update(account);
        System.out.println(accountDao.findById(1));
        assertEquals(new BigDecimal("200000000.0000"), accountDao.findById(1).getBalance());
    }
    @Test
    @Order(4)
    public void testFindByAccountNumber() {
        Account account = accountDao.findByAccountNumber("1865823456");
        System.out.println(account);
        assertNotNull(account);
    }
    @Test
    @Order(5)
    public void testInsertAccountForDelete() {
        Account account = Account.builder()
                .accountNumber("1865827456")
                .balance(BigDecimal.valueOf(1000))
                .firstName("Duy")
                .lastName("Nguyen")
                .createdDate(LocalDateTime.now())
                .build();
        accountDao.save(account);
    }
    @Test
    @Order(6)
    public void testGetAllAccounts() {
        accountDao.getAll().forEach(System.out::println);
        assertFalse(accountDao.getAll().isEmpty());
    }
    @Test
    @Order(7)
    public void testDeleteAccount() {
        accountDao.delete(2);
        System.out.println(accountDao.findById(2));
        assertNull(accountDao.findById(2));
    }
    @Test
    @Order(8)
    public void testSaveAccountWithListEPassbook() {
        Account account = Account.builder()
                .accountNumber("1875823456")
                .balance(BigDecimal.valueOf(5000))
                .firstName("Duy")
                .lastName("Ta Ngoc")
                .createdDate(LocalDateTime.now())
                .build();
        List<EPassbook> ePassbooks = new ArrayList<>();
        ePassbooks.add(EPassbook.builder()
                .depositedAmount(BigDecimal.valueOf(1000))
                .term(12)
                .startDate(LocalDateTime.now())
                .maturityDate(LocalDate.now().plusMonths(12))
                .account(account)
                .build());
        ePassbooks.add(EPassbook.builder()
                .depositedAmount(BigDecimal.valueOf(2000))
                .term(24)
                .startDate(LocalDateTime.now())
                .maturityDate(LocalDate.now().plusMonths(24))
                .account(account)
                .build());
        account.setEPassbooks(ePassbooks);
        accountDao.save(account);
    }
    @Test
    @Order(9)
    public void testGetAccountByBalanceMin100000000() {
        accountDao.getAccountByBalance(100000000).forEach(System.out::println);
        assertFalse(accountDao.getAccountByBalance(100000000).isEmpty());
    }
    @Test
    @Order(10)
    public void testIllegalInsertAccount() {
        Account account = Account.builder()
                .accountNumber("1875823456")
                .balance(BigDecimal.valueOf(1000))
                .firstName("Duy")
                .lastName("Ta Ngoc")
                .createdDate(LocalDateTime.now())
                .build();
        assertThrows(Exception.class, () -> accountDao.save(account));
    }
}
