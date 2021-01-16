package com.dylan.guestbook.repository;

import com.dylan.guestbook.entity.Guestbook;
import com.dylan.guestbook.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void updateTest() {
        Optional<Guestbook> result = guestbookRepository.findById(300L); //존재하는 번호로 테스트
            if(result.isPresent()){
                Guestbook guestbook = result.get();
                guestbook.changeTitle("Changed Title....");
                guestbook.changeContent("Changed Content...");
                guestbookRepository.save(guestbook);
            }
    }

    @Test
    public void testQuery1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook; //1
        String keyword = "1";
        BooleanBuilder booleanBuilder = new BooleanBuilder();  //2
        BooleanExpression expression = qGuestbook.title.contains(keyword); //3
        booleanBuilder.and(expression); //4

        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);

        result.forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    @Test
    public void testQuery2() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook; //1

        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent); // 1----------------
        builder.and(exAll); //2-----
        builder.and(qGuestbook.gno.gt(0L)); // 3----------

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

}
