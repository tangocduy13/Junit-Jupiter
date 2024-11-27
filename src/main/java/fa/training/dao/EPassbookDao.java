package fa.training.dao;

import fa.training.entities.EPassbook;

import java.util.List;

public interface EPassbookDao extends BaseDao<EPassbook, Integer> {
    void createNewSavings(String accountNumber, double depositedAmount,int term) throws Exception;
    List<Object[]> getPassbookDetail(int passbookId);
}
