package springboot.partTwo.service;

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
import springboot.partTwo.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

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

        Page<Guestbook> result = repository.findAll(pageable);
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
}
