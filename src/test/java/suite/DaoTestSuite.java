package suite;

import fa.training.dao.AccountDaoTest;
import fa.training.dao.EPassbookDaoTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({AccountDaoTest.class, EPassbookDaoTest.class})
public class DaoTestSuite {
}
