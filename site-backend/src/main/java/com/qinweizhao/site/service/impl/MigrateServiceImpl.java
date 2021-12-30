package com.qinweizhao.site.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import com.qinweizhao.site.handler.migrate.MigrateHandlers;
import com.qinweizhao.site.model.enums.MigrateType;
import com.qinweizhao.site.service.MigrateService;

/**
 * Migrate service implementation.
 *
 * @author ryanwang
 * @date 2019-10-29
 */
@Service
public class MigrateServiceImpl implements MigrateService {

    private final MigrateHandlers migrateHandlers;

    public MigrateServiceImpl(MigrateHandlers migrateHandlers) {
        this.migrateHandlers = migrateHandlers;
    }

    @Override
    public void migrate(MultipartFile file, MigrateType migrateType) {
        Assert.notNull(file, "Multipart file must not be null");
        Assert.notNull(migrateType, "Migrate type must not be null");

        migrateHandlers.upload(file, migrateType);
    }
}
