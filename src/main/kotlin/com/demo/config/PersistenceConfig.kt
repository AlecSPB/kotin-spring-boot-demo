package com.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.jpa.support.MergingPersistenceUnitManager
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager

import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(basePackageClasses = arrayOf(com.demo.repo.PackageMarker::class))
class PersistenceConfig {

    @Bean
    fun persistenceUnitManager(dataSource: DataSource): PersistenceUnitManager {
        val persistenceUnitManager = MergingPersistenceUnitManager()
        persistenceUnitManager.setPackagesToScan("com.demo.entity")
        persistenceUnitManager.defaultDataSource = dataSource
        return persistenceUnitManager
    }
}
