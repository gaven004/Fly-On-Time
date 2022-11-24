package org.springframework.data.relational.core.dialect;

import org.springframework.data.relational.core.sql.IdentifierProcessing;
import org.springframework.data.relational.core.sql.LockOptions;

public class ClickHouseDialect extends AnsiDialect {
    /**
     * Singleton instance.
     */
    public static final ClickHouseDialect INSTANCE = new ClickHouseDialect();

    private static final LimitClause LIMIT_CLAUSE = new LimitClause() {
        @Override
        public String getLimit(long limit) {
            return String.format("LIMIT %d", limit);
        }

        @Override
        public String getOffset(long offset) {
            return getLimitOffset(Long.MAX_VALUE, offset);
        }

        @Override
        public String getLimitOffset(long limit, long offset) {
            return String.format("LIMIT %s, %s", offset, limit);
        }

        @Override
        public LimitClause.Position getClausePosition() {
            return Position.AFTER_ORDER_BY;
        }
    };

    private static final LockClause LOCK_CLAUSE = new LockClause() {
        @Override
        public String getLock(LockOptions lockOptions) {
            return "";
        }

        @Override
        public Position getClausePosition() {
            return Position.AFTER_ORDER_BY;
        }
    };

    protected ClickHouseDialect() {
    }

    @Override
    public LockClause lock() {
        return LOCK_CLAUSE;
    }

    @Override
    public LimitClause limit() {
        return LIMIT_CLAUSE;
    }

    @Override
    public IdentifierProcessing getIdentifierProcessing() {
        return IdentifierProcessing.NONE;
    }

}
