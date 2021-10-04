package com.polarbookshop.catalogservice.persistence;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

public record AuditMetadata(

		@CreatedDate
		Instant createdDate,

		@LastModifiedDate
		Instant lastModifiedDate

){}
