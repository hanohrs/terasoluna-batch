package jp.terasoluna.fw.collector.file;

import java.net.URL;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.CollectorTestUtil;
import jp.terasoluna.fw.collector.util.MemoryInfo;
import jp.terasoluna.fw.file.dao.FileQueryDAO;

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

@ContextConfiguration(locations = {
        "classpath:jp/terasoluna/fw/collector/db/dataSource.xml" }, loader = DaoTestCaseContextLoader.class)
public class FileValidateCollector011Test extends DaoTestCaseJunit4 {
    /**
     * Log.
     */
    private static Log logger = LogFactory.getLog(
            FileValidateCollector011Test.class);

    private FileQueryDAO csvFileQueryDAO = null;

    private int previousThreadCount = 0;

    public void setCsvFileQueryDAO(FileQueryDAO csvFileQueryDAO) {
        this.csvFileQueryDAO = csvFileQueryDAO;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        FileValidateCollector.setVerbose(true);
        super.onSetUpBeforeTransaction();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        FileValidateCollector.setVerbose(false);
        super.onTearDownAfterTransaction();
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

    @Test
    public void testFileValidateCollectorFinalize001() throws Exception {
        if (this.csvFileQueryDAO == null) {
            fail("csvFileQueryDAOがnullです。");
        }

        URL url = getClass().getClassLoader().getResource("USER_TEST.csv");
        if (logger.isDebugEnabled()) {
            if (url != null) {
                logger.debug("url.getPath() : " + url.getPath());
            } else {
                logger.debug("url.getPath() : " + null);
            }
        }

        if (url == null) {
            fail("urlがnullです。");
        }

        Validator validator = null;

        @SuppressWarnings("resource")
        Collector<B000001Data> it = new FileValidateCollector<B000001Data>(this.csvFileQueryDAO, url
                .getPath(), B000001Data.class, validator);

        try {
            // it = ac.execute();

            while (it.hasNext()) {
                it.next();
                // あえて途中で抜ける
                break;
            }
        } finally {
            // あえてクローズせずに放置
            // FileValidateCollector.closeQuietly(it);
        }

        // コレクタスレッド数チェック
        assertTrue(CollectorTestUtil.lessThanCollectorThreadCount(1
                + this.previousThreadCount));

    }

    @Test
    public void testFileValidateCollector011() throws Exception {
        if (this.csvFileQueryDAO == null) {
            fail("csvFileQueryDAOがnullです。");
        }

        URL url = getClass().getClassLoader().getResource("USER_TEST.csv");
        if (logger.isDebugEnabled()) {
            if (url != null) {
                logger.debug("url.getPath() : " + url.getPath());
            } else {
                logger.debug("url.getPath() : " + null);
            }
        }

        if (url == null) {
            fail("urlがnullです。");
        }

        int count_first = 0;
        Validator validator = null;

        Collector<B000001Data> it = new FileValidateCollector<B000001Data>(this.csvFileQueryDAO, url
                .getPath(), B000001Data.class, validator);

        try {
            // it = ac.execute();

            while (it.hasNext()) {
                it.next();
                count_first++;
            }
        } finally {
            // クローズ
            FileValidateCollector.closeQuietly(it);
        }

        // コレクタスレッド数チェック
        assertTrue(CollectorTestUtil.lessThanCollectorThreadCount(0
                + this.previousThreadCount));

        for (int i = 0; i < 7; i++) {
            int count = 0;

            long startTime = System.currentTimeMillis();

            Collector<B000001Data> it2 = new FileValidateCollector<B000001Data>(this.csvFileQueryDAO, url
                    .getPath(), B000001Data.class, validator);

            try {
                for (B000001Data data : it2) {
                    if (logger.isInfoEnabled() && data == null) {
                        logger.info("UserBean is null.##############");
                    }
                    count++;

                }
            } finally {
                FileValidateCollector.closeQuietly(it2);
            }

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

        // コレクタスレッド数チェック
        assertTrue(CollectorTestUtil.lessThanCollectorThreadCount(0
                + this.previousThreadCount));

    }

}
