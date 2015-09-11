/**
 * 
 */
package jp.terasoluna.fw.collector.db;

import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.CollectorTestUtil;
import jp.terasoluna.fw.collector.dao.UserListQueryResultHandleDao;
import jp.terasoluna.fw.collector.util.MemoryInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.test.context.ContextConfiguration;
import jp.terasoluna.fw.collector.unit.testcase.junit4.DaoTestCaseJunit4;
import jp.terasoluna.fw.collector.unit.testcase.junit4.loader.DaoTestCaseContextLoader;

/**
 * DaoValidateCollectorTest
 */
@ContextConfiguration(locations = {
        "classpath:jp/terasoluna/fw/collector/db/dataSource.xml" }, loader = DaoTestCaseContextLoader.class)
public class DaoValidateCollector004Test extends DaoTestCaseJunit4 {

    /**
     * Log.
     */
    private static Log logger = LogFactory.getLog(
            DaoValidateCollector004Test.class);

    private UserListQueryResultHandleDao userListQueryResultHandleDao = null;

    private int previousThreadCount = 0;

    public void setUserListQueryResultHandleDao(
            UserListQueryResultHandleDao userListQueryResultHandleDao) {
        this.userListQueryResultHandleDao = userListQueryResultHandleDao;
    }

    @Before
    public void onSetUp() throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info(MemoryInfo.getMemoryInfo());
        }
        System.gc();
        if (logger.isInfoEnabled()) {
            logger.info(MemoryInfo.getMemoryInfo());
        }
        this.previousThreadCount = CollectorTestUtil.getCollectorThreadCount();
    }

    @After
    public void onTearDown() throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info(MemoryInfo.getMemoryInfo());
        }
        System.gc();
        if (logger.isInfoEnabled()) {
            logger.info(MemoryInfo.getMemoryInfo());
        }
        CollectorTestUtil.allInterrupt();
    }

    /**
     * {@link jp.terasoluna.fw.collector.db.DaoValidateCollector#DaoValidateCollector(Object, String, Object, int, org.springframework.validation.Validator)}
     * のためのテスト・メソッド。
     */
    @Test
    public void testDaoValidateCollectorTestObjectStringObjectInt002() throws Exception {
        if (this.userListQueryResultHandleDao == null) {
            fail("userListQueryResultHandleDaoがnullです。");
        }

        int count_first = 0;
        Validator validator = null;

        Collector<UserBean> it = new DaoValidateCollector<UserBean>(this.userListQueryResultHandleDao, "collect", null, 100, validator);
        try {
            while (it.hasNext()) {
                it.next();
                count_first++;
            }
        } finally {
            DaoValidateCollector.closeQuietly(it);
        }

        // コレクタスレッド数チェック
        assertTrue(CollectorTestUtil.lessThanCollectorThreadCount(0
                + this.previousThreadCount));

        for (int i = 0; i < 2; i++) {
            int count = 0;

            long startTime = System.currentTimeMillis();

            Collector<UserBean> it2 = new DaoValidateCollector<UserBean>(this.userListQueryResultHandleDao, "collect", null, 100, validator);
            try {
                for (UserBean user : it2) {
                    if (logger.isInfoEnabled() && user == null) {
                        logger.info("UserBean is null.##############");
                    }

                    count++;
                }
            } finally {
                DaoValidateCollector.closeQuietly(it2);
            }

            // コレクタスレッド数チェック
            assertTrue(CollectorTestUtil.lessThanCollectorThreadCount(0
                    + this.previousThreadCount));

            long endTime = System.currentTimeMillis();

            if (logger.isInfoEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append("i:[");
                sb.append(String.format("%04d", i));
                sb.append("]");
                sb.append(" milliSec:[");
                sb.append(String.format("%04d", (endTime - startTime)));
                sb.append("]");
                logger.info(sb.toString());
            }

            assertEquals(count_first, count);
        }
    }

}
