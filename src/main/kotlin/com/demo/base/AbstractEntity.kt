package com.demo.base

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.format.annotation.DateTimeFormat

import javax.persistence.*
import javax.validation.constraints.NotNull
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by iuliana.cosmina on 2/7/16.
 * Description:A template class which defines the common template for all entities in the project.
 */
@MappedSuperclass
abstract class AbstractEntity
/**
 * This constructor is required by JPA. All subclasses of this class will inherit this constructor.
 */
protected constructor() : Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    var id: Long? = null

    /**
     * This field is used for auditory and logging purposes. It is populated by the system when an entity instance is created.
     */
    @JsonIgnore
    @Column(name = "CREATED_AT", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var createdAt: Date = Date()

    /**
     * This field is used for auditory and logging purposes. It is populated by the system when an entity instance is modified.
     */
    @JsonIgnore
    @Column(name = "MODIFIED_AT", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var modifiedAt: Date = Date()

    @JsonIgnore
    @Version
    var version: Int = 0
}

