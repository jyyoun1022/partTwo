package springboot.partTwo.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springboot.partTwo.dto.GuestbookDTO;
import springboot.partTwo.dto.PageRequestDTO;
import springboot.partTwo.dto.PageResultDTO;
import springboot.partTwo.entity.Guestbook;
import springboot.partTwo.entity.QGuestbook;
import springboot.partTwo.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

import static springboot.partTwo.entity.QGuestbook.guestbook;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {
        Guestbook guestbook = dtoToEntity(dto);
        Guestbook save = repository.save(guestbook);
        return guestbook.getGno();
    }

    @Override
    public PageResultDTO<Guestbook,GuestbookDTO> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);

        Page<Guestbook> result = repository.findAll(booleanBuilder,pageable);

        Function<Guestbook,GuestbookDTO>fn=(en->entityToDto(en));

        return new PageResultDTO<>(result,fn);

    }


    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = repository.findById(gno);

        return result.isPresent()? entityToDto(result.get()) : null;
        }

    @Override
    public void modify(GuestbookDTO guestbookDTO) {
        Optional<Guestbook> result = repository.findById(guestbookDTO.getGno());
        if(result.isPresent()){
            Guestbook guestbook = result.get();
            guestbook.changeTitle(guestbookDTO.getTitle());
            guestbook.changeContent(guestbookDTO.getContent());

            repository.save(guestbook);
        }
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);

    }

    private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO){

        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        BooleanExpression expression = guestbook.gno.gt(0L);

        booleanBuilder.and(expression);

        if(type == null || type.trim().length()==0) {
            return booleanBuilder;
        }

        //검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")){
            conditionBuilder.or(guestbook.title.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(guestbook.content.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(guestbook.writer.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;




    }
}
