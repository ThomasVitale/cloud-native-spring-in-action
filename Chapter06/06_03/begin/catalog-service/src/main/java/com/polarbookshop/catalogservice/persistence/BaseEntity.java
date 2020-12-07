package com.polarbookshop.catalogservice.persistence;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(name = "sequence_generator", sequenceName = "entity_sequence")
    private Long id;

    @CreatedDate
    private Long createdDate;

    @LastModifiedDate
    private Long lastModifiedDate;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    protected Long getCreatedDate() {
        return createdDate;
    }

    protected void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    protected Long getLastModifiedDate() {
        return lastModifiedDate;
    }

    protected void setLastModifiedDate(Long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    protected int getVersion() {
        return version;
    }

    protected void setVersion(int version) {
        this.version = version;
    }
}
