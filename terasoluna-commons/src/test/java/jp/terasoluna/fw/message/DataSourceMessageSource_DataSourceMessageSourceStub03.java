/*
 * Copyright (c) 2007 NTT DATA Corporation
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

package jp.terasoluna.fw.message;

import java.util.Locale;
import java.util.Properties;

/**
 * DataSourceMessageSourceクラスで利用するスタブ
 *
 */
public class DataSourceMessageSource_DataSourceMessageSourceStub03 extends
        DataSourceMessageSource {
    /**
     * 
     */
    Properties messages = null;
        
    /**
     * 引数messageを格納
     */
    protected DBMessage dbm = null;
    
    /**
     * 返却値locale
     */
    protected Locale locale = null; 
    
    /**
     * 引数localeを格納
     */
    protected Locale locale2 = null;
    
    @Override
    protected Locale createLocale(DBMessage message) {
        this.dbm = message;
        return this.locale;
    }
    
    @Override
    protected Properties getMessages(Locale locale) {
        this.locale2 = locale;
        return this.messages;
    }
    
}
