/*
 * Copyright 2015, Yahoo Inc.
 * Copyrights licensed under the Apache 2.0 License.
 * See the accompanying LICENSE file for terms.
 */
package com.yahoo.squidb.sql;

import com.yahoo.squidb.data.ViewModel;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A SQLite View. Views are read-only tables composed from a pre-defined SELECT statement.
 */
public class View extends QueryTable {

    private boolean temporary;

    private View(@Nullable Class<? extends ViewModel> modelClass, @Nonnull List<Property<?>> properties,
            @Nonnull String expression, @Nullable String databaseName, @Nullable String alias,
            @Nonnull Query query, boolean temporary) {
        super(modelClass, properties, expression, databaseName, query);
        this.alias = alias;
        this.temporary = temporary;
    }

    /**
     * Construct a View from the given query and with the given name
     *
     * @param query the {@link Query} that composes this View
     * @param name the name for this View
     * @return a new View instance
     */
    @Nonnull
    public static View fromQuery(@Nonnull Query query, @Nonnull String name) {
        return new View(null, Collections.<Property<?>>emptyList(), name, null, null, query, false);
    }

    /**
     * Construct a View from the given query with the given name and represented by the given model class
     *
     * @param query the {@link Query} that composes this View
     * @param name the name for this View
     * @param modelClass the model class representing this View
     * @return a new View instance
     */
    @Nonnull
    public static View fromQuery(@Nonnull Query query, @Nonnull String name,
            @Nonnull Class<? extends ViewModel> modelClass, @Nonnull List<Property<?>> properties) {
        return new View(modelClass, properties, name, null, null, query, false);
    }

    /**
     * Construct a temporary View from the given query and with the given name
     *
     * @param query the {@link Query} that composes this View
     * @param name the name for this View
     * @return a new View instance
     */
    @Nonnull
    public static View temporaryFromQuery(@Nonnull Query query, @Nonnull String name) {
        return new View(null, Collections.<Property<?>>emptyList(), name, null, null, query, true);
    }

    /**
     * Construct a temporary View from the given query with the given name and represented by the given model class
     *
     * @param query the {@link Query} that composes this View
     * @param name the name for this View
     * @param modelClass the model class representing this View
     * @return a new View instance
     */
    @Nonnull
    public static View temporaryFromQuery(@Nonnull Query query, @Nonnull String name,
            @Nonnull Class<? extends ViewModel> modelClass, @Nonnull List<Property<?>> properties) {
        return new View(modelClass, properties, name, null, null, query, true);
    }

    @Nonnull
    public View qualifiedFromDatabase(@Nonnull String databaseName) {
        return new View(modelClass, properties, getExpression(), databaseName, alias, query, temporary);
    }

    @Override
    @Nonnull
    public View as(@Nonnull String newAlias) {
        return (View) super.as(newAlias);
    }

    @Override
    @Nonnull
    protected View asNewAliasWithProperties(@Nonnull String newAlias, @Nonnull List<Property<?>> newProperties) {
        return new View(modelClass, newProperties, getExpression(), qualifier, newAlias, query, temporary);
    }

    /**
     * Append the SQL statement that creates this View to the given {@link StringBuilder}. Users should not call
     * this method and instead let {@link com.yahoo.squidb.data.SquidDatabase} build views automatically.
     */
    public void createViewSql(@Nonnull CompileContext compileContext, @Nonnull StringBuilder sql) {
        sql.append("CREATE ");
        if (temporary) {
            sql.append("TEMPORARY ");
        }
        sql.append("VIEW IF NOT EXISTS ")
                .append(getExpression()).append(" AS ")
                .append(query.toRawSql(compileContext));
    }
}
