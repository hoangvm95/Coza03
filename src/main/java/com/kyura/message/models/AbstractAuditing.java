package com.kyura.message.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditing implements Serializable {

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	protected Instant createdAt = Instant.now();
	
	@LastModifiedDate
	@Column(name = "updated_at")
	protected Instant updatedAt = Instant.now();

	@CreatedBy
	@Column(name = "created_by", updatable = false)
	protected String createdBy;

	@LastModifiedBy
	@Column(name = "updated_by")
	protected String updatedBy;

}
