package jp.terasoluna.fw.file.dao.standard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jp.terasoluna.fw.file.dao.standard.ColumnFormatter;

/**
 * ColumnFormatterのスタブクラス。<br>
 * 入力に関係なくnullを返す。
 */
public class AbstractFileLineWriter_ColumnFormatterStub01 implements
                                                         ColumnFormatter {

    /**
     * {@inheritDoc}
     */
    public String format(Object t, Method method, String string)
                                                                throws IllegalArgumentException,
                                                                IllegalAccessException,
                                                                InvocationTargetException {
        return null;
    }

}
