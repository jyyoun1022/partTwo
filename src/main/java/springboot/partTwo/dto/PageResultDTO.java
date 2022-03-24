package springboot.partTwo.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<EN,DTO> {

    private List<DTO> dtoList;
    private int start,end;
    private int totalPage;
    private int page;
    private int size;
    private boolean prev,next;
    private List<Integer>pageList;

    public PageResultDTO(Page<EN>result, Function<EN,DTO>fn){
        dtoList=result.stream().map(fn).collect(Collectors.toList());
        totalPage= result.getTotalPages();
        makePageList(result.getPageable());
    }
    private void makePageList(Pageable pageable){
        this.page=pageable.getPageNumber()+1;
        this.size=pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(page/10.0))*10;
        boolean prev = start>1;
        start = tempEnd-9;
        end = totalPage>tempEnd? tempEnd:totalPage;
        next = tempEnd<totalPage;
        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());

    }
}
