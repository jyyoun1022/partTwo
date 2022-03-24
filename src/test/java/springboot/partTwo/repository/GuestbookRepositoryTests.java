package springboot.partTwo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import springboot.partTwo.entity.Guestbook;
import springboot.partTwo.entity.QGuestbook;

import java.util.List;
import java.util.stream.IntStream;

import static springboot.partTwo.entity.QGuestbook.guestbook;


@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository repository;

    @Test
    public void insertDummies(){

        IntStream.rangeClosed(1,300).forEach(i-> {
            Guestbook guestbook=Guestbook.builder()
                    .title("Title" + i)
                    .content("Content" + i)
                    .writer("User" + (i % 10))
                    .build();
            repository.save(guestbook);

        });
    }
    @Test
    @DisplayName(value = "QueryDsl")
    void testQuery1(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        String keyword ="1";
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = guestbook.title.contains(keyword);

        booleanBuilder.and(expression);

        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);

        List<Guestbook> content = result.getContent();
        content.forEach(i-> System.out.println(i));

    }
    @Test
    void testQuery2(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        String keyword ="Title";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = guestbook.title.contains(keyword);
        BooleanExpression exContent = guestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll);
        BooleanExpression gt = guestbook.gno.gt(0L);
        builder.and(gt);
        Page<Guestbook> result = repository.findAll(builder, pageable);

        List<Guestbook> content = result.getContent();
        content.forEach(i-> System.out.println(i));
    }
}
