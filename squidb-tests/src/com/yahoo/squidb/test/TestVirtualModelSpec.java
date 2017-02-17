/*
 * Copyright 2015, Yahoo Inc.
 * Copyrights licensed under the Apache 2.0 License.
 * See the accompanying LICENSE file for terms.
 */
package com.yahoo.squidb.test;

import com.yahoo.squidb.annotations.ColumnName;
import com.yahoo.squidb.annotations.ColumnSpec;
import com.yahoo.squidb.annotations.TableModelSpec;
import com.yahoo.squidb.annotations.defaults.DefaultLong;
import com.yahoo.squidb.annotations.defaults.DefaultString;

@TableModelSpec(className = "TestVirtualModel", tableName = "virtual_models", virtualModule = "fts4")
public class TestVirtualModelSpec {

    @ColumnName("test_num")
    @DefaultLong(7)
    long testNumber;

    @DefaultString(ColumnSpec.DEFAULT_NULL)
    String title;

    @DefaultString(ColumnSpec.DEFAULT_NULL)
    String body;
}
