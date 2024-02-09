// package com.book_store.full.repository;

// import java.util.List;

// import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
// import org.springframework.stereotype.Repository;

// import com.book_store.full.dto.bookdto.Book;
// import com.book_store.full.dto.bookdto.BookElasticsearch;

// import org.springframework.data.elasticsearch.annotations.Query;

// @Repository
// public interface BookElasticsearchRepository extends ElasticsearchRepository<BookElasticsearch, String> {
//     Iterable<Book> findByTitle(String title);

//     @Query("{\"bool\": {\"should\": [" +
//             "{\"match\": {\"title\": {\"query\": \"?0\", \"operator\": \"and\"}}}," +
//             "{\"match\": {\"author\": {\"query\": \"?1\", \"operator\": \"and\"}}}," +
//             "{\"match\": {\"category\": {\"query\": \"?2\", \"operator\": \"and\"}}}," +
//             "{\"match\": {\"translator\": {\"query\": \"?3\", \"operator\": \"and\"}}}," +
//             "{\"match\": {\"publisher\": {\"query\": \"?4\", \"operator\": \"and\"}}}" +
//             "]}}")
//     List<BookElasticsearch> findByTitleOrAuthorOrCategoryOrTranslatorOrPublisher(
//             String title, String author, String category, String translator, String publisher);
// }
