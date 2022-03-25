package springboot.partTwo.service;

import springboot.partTwo.dto.GuestbookDTO;
import springboot.partTwo.dto.PageRequestDTO;
import springboot.partTwo.dto.PageResultDTO;
import springboot.partTwo.entity.Guestbook;

public interface GuestbookService {

    Long register(GuestbookDTO dto);

    PageResultDTO<Guestbook,GuestbookDTO> getList(PageRequestDTO pageRequestDTO);

    GuestbookDTO read(Long gno);

    void modify(GuestbookDTO guestbookDTO);
    void remove(Long gno);

    default Guestbook dtoToEntity(GuestbookDTO dto){

        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    }

    default  GuestbookDTO entityToDto(Guestbook entity){

        GuestbookDTO dto =GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }
}
