package com.eventmanagement.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.eventmanagement.config.DatabaseConfig;
import java.sql.Connection;

public abstract class BaseDAO {
    protected static final Logger logger = LoggerFactory.getLogger(BaseDAO.class);

    protected Connection getConnection() {
        return DatabaseConfig.getInstance().getConnection();
    }
}