package com.amigoscode;

import org.hibernate.resource.jdbc.spi.StatementInspector;

public class QueryLimitInspector implements StatementInspector {
    @Override
    public String inspect(String sql) {

        String lower = sql.toLowerCase();
        if (lower.startsWith("select")
                && !lower.contains(" limit ")
                && !lower.contains(" offset ")
                && !lower.contains("fetch first")) {
            return sql + " LIMIT 1000";
        }

        return sql;
    }
}