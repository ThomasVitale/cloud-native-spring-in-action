package com.polarbookshop.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.Instant;

@With
@Builder
public record Book (

        @Id
        Long id,

        @NotBlank(message = "The book ISBN must be defined.")
		@Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.")
        String isbn,

        @NotBlank(message = "The book title must be defined.")
        String title,

        @NotBlank(message = "The book author must be defined.")
        String author,

        @NotNull(message = "The book price must be defined.")
        @Positive(message = "The book price must be greater than zero.")
        Double price,

        @CreatedDate
        Instant createdDate,

        @LastModifiedDate
        Instant lastModifiedDate,

        @Version
        Integer version

){
        public static Book of (String isbn, String title, String author, Double price){
                return new Book(null, isbn, title, author, price, null, null, null);
                //  Spring Data JDBC 判断实体是否是新对象的逻辑：
                //  - @Version == null → 新对象（isNew() = true）→ 触发 @CreatedDate
                //  - @Version != null（包括 0）→ 已有对象（isNew() = false）→ 不设置 @CreatedDate
                //
                //  version = 0 是 Integer 类型，不为 null，所以 isNew() = false，auditing 不设置 createdDate，INSERT 时 created_date = null 违反 NOT NULL 约束。
                //
                //  修复： 将 version = 0 改为 null：
        }
}
