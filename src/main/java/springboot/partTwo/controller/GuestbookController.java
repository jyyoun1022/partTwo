package springboot.partTwo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springboot.partTwo.dto.GuestbookDTO;
import springboot.partTwo.dto.PageRequestDTO;
import springboot.partTwo.dto.PageResultDTO;
import springboot.partTwo.entity.Guestbook;
import springboot.partTwo.service.GuestbookService;

import java.awt.event.WindowFocusListener;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model){
        log.info(pageRequestDTO);

        model.addAttribute("result",service.getList(pageRequestDTO));

        return "/guestbook/list";
    }

    @GetMapping("/register")
    public void register(){

    }
    @PostMapping("/register")
    public String register(GuestbookDTO guestbookDTO, RedirectAttributes redirectAttributes){
        Long gno = service.register(guestbookDTO);
        redirectAttributes.addFlashAttribute("msg",gno);
        return "redirect:/guestbook/list";
    }
    @GetMapping({"/read","/modify"})
    public void read(Model model,PageRequestDTO pageRequestDTO,Long gno){
        GuestbookDTO guestbookDTO = service.read(gno);
        model.addAttribute("dto",guestbookDTO);
    }
    @PostMapping("/modify")
    public String modify(RedirectAttributes redirectAttributes,
                         @ModelAttribute("guestbookDTO") GuestbookDTO guestbookDTO,
                         PageRequestDTO pageRequestDTO){
        log.info("dto"+guestbookDTO);
        service.modify(guestbookDTO);

        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        redirectAttributes.addAttribute("gno",guestbookDTO.getGno());

        return "redirect:/guestbook/read";

    }
    @PostMapping("/remove")
    public String remove(Long gno,RedirectAttributes redirectAttributes){

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg1",gno);
        return "redirect:/guestbook/list";
    }


}

