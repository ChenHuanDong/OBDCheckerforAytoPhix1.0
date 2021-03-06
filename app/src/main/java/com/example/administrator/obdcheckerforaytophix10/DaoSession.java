package com.example.administrator.obdcheckerforaytophix10;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.administrator.obdcheckerforaytophix10.FileL;
import com.example.administrator.obdcheckerforaytophix10.OBDL;

import com.example.administrator.obdcheckerforaytophix10.FileLDao;
import com.example.administrator.obdcheckerforaytophix10.OBDLDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig fileLDaoConfig;
    private final DaoConfig oBDLDaoConfig;

    private final FileLDao fileLDao;
    private final OBDLDao oBDLDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        fileLDaoConfig = daoConfigMap.get(FileLDao.class).clone();
        fileLDaoConfig.initIdentityScope(type);

        oBDLDaoConfig = daoConfigMap.get(OBDLDao.class).clone();
        oBDLDaoConfig.initIdentityScope(type);

        fileLDao = new FileLDao(fileLDaoConfig, this);
        oBDLDao = new OBDLDao(oBDLDaoConfig, this);

        registerDao(FileL.class, fileLDao);
        registerDao(OBDL.class, oBDLDao);
    }
    
    public void clear() {
        fileLDaoConfig.clearIdentityScope();
        oBDLDaoConfig.clearIdentityScope();
    }

    public FileLDao getFileLDao() {
        return fileLDao;
    }

    public OBDLDao getOBDLDao() {
        return oBDLDao;
    }

}
