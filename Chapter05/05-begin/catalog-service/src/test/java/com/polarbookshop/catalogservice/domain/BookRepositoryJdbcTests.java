package com.polarbookshop.catalogservice.domain;

import com.polarbookshop.catalogservice.config.DataConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import(DataConfig.class)   // 引入 DataConfig, 因为需要 JdbcAudit 功能支持两个 timestamp 的自动设置
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")  // 使用 application-integration.yml
public class BookRepositoryJdbcTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    JdbcAggregateTemplate jdbcAggregateTemplate;

    @Test
    void findBookByIsbnWhenExisting(){
//        String isbn = "1234567890"; // 注意: testcontainer 只是拉起一个容器, 但是数据库以及数据的初始化都是使用 schema.sql,
//        // 所以使用 1234567890 会导致 pk(isbn) 已存在 的冲突

        String isbn = "1111567890";
        Book book = Book.of(isbn, "超好看", "兰兰", 120.0);
        jdbcAggregateTemplate.insert(book);

        Optional<Book> existing = bookRepository.findByIsbn(isbn);

        assertThat(existing).isPresent();
        assertThat(existing.get().isbn()).isEqualTo(isbn);
    }
}
