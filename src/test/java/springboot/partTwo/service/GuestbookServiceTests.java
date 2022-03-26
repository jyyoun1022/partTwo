package springboot.partTwo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springboot.partTwo.dto.GuestbookDTO;
import springboot.partTwo.dto.PageRequestDTO;
import springboot.partTwo.dto.PageResultDTO;
import springboot.partTwo.entity.Guestbook;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Test
    public void testSearch(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("한글")
                .build();

        PageResultDTO<Guestbook, GuestbookDTO> resultDTO = service.getList(pageRequestDTO);

        System.out.println(resultDTO.isPrev());
        System.out.println(resultDTO.isNext());
        System.out.println(resultDTO.getTotalPage());

        System.out.println("================================================");
        for( GuestbookDTO guestbookDTO: resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }
        System.out.println("================================================");
        resultDTO.getDtoList().forEach(i-> System.out.println(i));


    }
}
