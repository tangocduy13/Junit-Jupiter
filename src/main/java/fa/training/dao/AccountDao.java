package fa.training.dao;

import fa.training.entities.Account;

import java.util.List;

public interface AccountDao extends BaseDao<Account, Integer>{
    Account findByAccountNumber(String accountNumber);
    List<Account> getAccountByBalance(double minBalance);
}
