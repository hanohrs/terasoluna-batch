/*
 * Copyright (c) 2016 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.fw.batch.executor.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import jp.terasoluna.fw.batch.executor.dao.SystemDao;
import jp.terasoluna.fw.batch.executor.vo.BatchJobData;
import jp.terasoluna.fw.batch.executor.vo.BatchJobListParam;
import jp.terasoluna.fw.batch.executor.vo.BatchJobListResult;
import jp.terasoluna.fw.batch.executor.vo.BatchJobManagementParam;

import org.apache.ibatis.session.RowBounds;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

/**
 * JobControlFinderImplのテストケースクラス
 */
public class JobControlFinderImplTest {

    JobControlFinder jobControlFinder;

    /**
     * 初期化処理を行う。
     */
    @Before
    public void setUp() {
        jobControlFinder = new JobControlFinderImpl(mock(
                SystemDao.class));
    }

    /**
     * resolveBatchJobResultテスト 【正常系】
     * 
     * <pre>
     * 事前条件
     * ・引数（固定）："0000000001"のjobSequenceId
     * ・SystemDaoの戻り値："0000000001"のjobSequenceIdを含む1件の{@code BatchJobDataResult}のリスト
     * 確認項目
     * ・"0000000001"のbatchJobListResultが返却されること
     * </pre>
     */
    @Test
    public void testResolveBatchJobResult01() {
        // テスト入力データ設定
        when(((JobControlFinderImpl) jobControlFinder).systemDao
                .selectJobList(any(RowBounds.class), any(BatchJobListParam.class))).thenReturn(
                        new ArrayList<BatchJobListResult>() {
                            private static final long serialVersionUID = 1L;

                            {
                                add(new BatchJobListResult() {
                                    {
                                        setJobSequenceId("0000000001");
                                    }
                                });
                            }
                        });

        // テスト実施
        BatchJobListResult batchJobListResult = jobControlFinder
                .resolveBatchJobResult(new String[] { "0000000001" });
        // 結果検証
        assertEquals("0000000001", batchJobListResult.getJobSequenceId());
    }

    /**
     * resolveBatchJobResultテスト 【正常系】
     * 
     * <pre>
     * 事前条件
     * ・引数（固定）："0000000001"のjobSequenceId
     * ・SystemDaoの戻り値：null
     * 確認項目
     * ・nullのbatchJobListResultが返却されること
     * </pre>
     */
    @Test
    public void testResolveBatchJobResult02() {
        // テスト入力データ設定
        when(((JobControlFinderImpl) jobControlFinder).systemDao
                .selectJobList(any(RowBounds.class),
                        any(BatchJobListParam.class)))
                .thenReturn(null);

        // テスト実施
        BatchJobListResult batchJobListResult = jobControlFinder
                .resolveBatchJobResult(new String[] { "0000000001" });
        // 結果検証
        assertNull(batchJobListResult);
    }

    /**
     * resolveBatchJobResultテスト 【正常系】
     *
     * <pre>
     * 事前条件
     * ・引数（固定）："0000000001"のjobSequenceId
     * ・SystemDaoの戻り値：空の{@code BatchJobDataResult}のリスト
     * 確認項目
     * ・nullのbatchJobListResultが返却されること
     * </pre>
     */
    @Test
    public void testResolveBatchJobResult03() {
        // テスト入力データ設定
        when(((JobControlFinderImpl) jobControlFinder).systemDao
                .selectJobList(any(RowBounds.class),
                        any(BatchJobListParam.class)))
                .thenReturn(new ArrayList<BatchJobListResult>());

        // テスト実施
        BatchJobListResult batchJobListResult = jobControlFinder
                .resolveBatchJobResult(new String[] { "0000000001" });
        // 結果検証
        assertNull(batchJobListResult);
    }

    /**
     * resolveBatchJobResultテスト 【正常系】
     * 
     * <pre>
     * 事前条件
     * ・引数：null
     * ・SystemDaoの戻り値（固定）："0000000001"のjobSequenceIdを含む1件の{@code BatchJobDataResult}のリスト
     * 確認項目
     * ・"0000000001"のbatchJobListResultが返却されること（引数に影響しないこと。）
     * </pre>
     */
    @Test
    public void testResolveBatchJobResult04() {
        // テスト入力データ設定
        when(((JobControlFinderImpl) jobControlFinder).systemDao
                .selectJobList(any(RowBounds.class), any(BatchJobListParam.class))).thenReturn(
                new ArrayList<BatchJobListResult>() {
                    private static final long serialVersionUID = 1L;

                    {
                        add(new BatchJobListResult() {
                            {
                                setJobSequenceId("0000000001");
                            }
                        });
                    }
                });

        // テスト実施
        BatchJobListResult batchJobListResult = jobControlFinder
                .resolveBatchJobResult(null);
        // 結果検証
        assertEquals("0000000001", batchJobListResult.getJobSequenceId());
    }

    /**
     * resolveBatchJobResultテスト 【正常系】
     *
     * <pre>
     * 事前条件
     * ・引数：空のString配列
     * ・SystemDaoの戻り値（固定）："0000000001"のjobSequenceIdを含む1件の{@code BatchJobDataResult}のリスト
     * 確認項目
     * ・"0000000001"のbatchJobListResultが返却されること（引数に影響しないこと。）
     * </pre>
     */
    @Test
    public void testResolveBatchJobResult05() {
        // テスト入力データ設定
        when(((JobControlFinderImpl) jobControlFinder).systemDao
                .selectJobList(any(RowBounds.class), any(BatchJobListParam.class))).thenReturn(
                new ArrayList<BatchJobListResult>() {
                    private static final long serialVersionUID = 1L;

                    {
                        add(new BatchJobListResult() {
                            {
                                setJobSequenceId("0000000001");
                            }
                        });
                    }
                });

        // テスト実施
        BatchJobListResult batchJobListResult = jobControlFinder
                .resolveBatchJobResult(new String[0]);
        // 結果検証
        assertEquals("0000000001", batchJobListResult.getJobSequenceId());
    }

    /**
     * resolveBatchJobResultテスト 【正常系】
     *
     * <pre>
     * 事前条件
     * ・引数："0000000001"を含むString配列
     * ・SystemDaoの戻り値（固定）："0000000001"のjobSequenceIdを含む1件の{@code BatchJobDataResult}のリスト
     * 確認項目
     * ・"0000000001"のbatchJobListResultが返却されること（引数に影響しないこと。）
     * </pre>
     */
    @Test
    public void testResolveBatchJobResult06() {
        // テスト入力データ設定
        when(((JobControlFinderImpl) jobControlFinder).systemDao
                .selectJobList(any(RowBounds.class), any(BatchJobListParam.class))).thenReturn(
                new ArrayList<BatchJobListResult>() {
                    private static final long serialVersionUID = 1L;

                    {
                        add(new BatchJobListResult() {
                            {
                                setJobSequenceId("0000000001");
                            }
                        });
                    }
                });

        // テスト実施
        BatchJobListResult batchJobListResult = jobControlFinder
                .resolveBatchJobResult(new String[]{"0000000001"});
        // 結果検証
        assertEquals("0000000001", batchJobListResult.getJobSequenceId());
    }

    /**
     * resolveBatchJobDataテスト 【正常系】
     * 
     * <pre>
     * 事前条件
     * ・とくになし
     * 確認項目
     * ・"0000000001"のBatchJobDataが返却されること
     * </pre>
     */
    @Test
    public void testResolveBatchJobData01() {
        // テスト入力データ設定
        when(((JobControlFinderImpl) jobControlFinder).systemDao
                .selectJob(any(BatchJobManagementParam.class))).thenReturn(
                        new BatchJobData() {
                            {
                                setJobSequenceId("0000000001");
                            }
                        });

        // テスト実施
        BatchJobData batchJobData = jobControlFinder.resolveBatchJobData(
                "0000000001");
        // 結果検証
        assertEquals("0000000001", batchJobData.getJobSequenceId());
    }

    /**
     * resolveBatchJobDataテスト 【正常系】
     * 
     * <pre>
     * 事前条件
     * ・ジョブアプリケーションコードの後ろに半角空白があること
     * 確認項目
     * ・"0000000001"のジョブアプリケーションコードが返却されること
     * </pre>
     */
    @Test
    public void testResolveBatchJobData02() {
        // テスト入力データ設定
        when(((JobControlFinderImpl) jobControlFinder).systemDao
                .selectJob(any(BatchJobManagementParam.class))).thenReturn(
                        new BatchJobData() {
                            {
                                setJobSequenceId("0000000001");
                                setJobAppCd("0000000001     ");
                            }
                        });

        // テスト実施
        BatchJobData batchJobData = jobControlFinder.resolveBatchJobData(
                "0000000001");
        // 結果検証
        assertEquals("0000000001", batchJobData.getJobAppCd());
    }

    /**
     * resolveBatchJobDataテスト 【異常系】
     * 
     * <pre>
     * 事前条件
     * ・とくになし
     * 確認項目
     * ・jobSequenceIdがnullのBatchJobDataが返却されること
     * </pre>
     */
    @Test
    public void testResolveBatchJobData03() {
        // テスト入力データ設定
        when(((JobControlFinderImpl) jobControlFinder).systemDao
                .selectJob(any(BatchJobManagementParam.class))).thenReturn(
                        new BatchJobData() {
                        });

        // テスト実施
        BatchJobData batchJobData = jobControlFinder.resolveBatchJobData(
                "0000000001");
        // 結果検証
        assertNull(batchJobData.getJobSequenceId());
    }

    /**
     * コンストラクタのテスト 【異常系】
     * 
     * <pre>
     * 事前条件
     * ・とくになし
     * 確認項目
     * ・{@code systemDao}がnullであるとき、アサーションエラーとして
            * 　{@code IllegalArgumentException}がスローされること。
     * </pre>
     *
     * @throws Exception 予期しない例外
     */
    @Test
    public void testJobControlFinderImpl01() throws Exception {
        try {
            // テスト実行
            new JobControlFinderImpl(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(
                    "[EAL025056] [Assertion failed] - JobControlFinderImpl requires to set SystemDao. please confirm the settings.",
                    e.getMessage());
        }
    }

}
