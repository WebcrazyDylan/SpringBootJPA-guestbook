package com.dylan.guestbook.controller;

import com.dylan.guestbook.dto.GuestbookDTO;
import com.dylan.guestbook.dto.PageRequestDTO;
import com.dylan.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService service ;

//    @GetMapping({"/","/list"})
//    public String list(){
//        log.info("list.............");
//        return "/guestbook/list";
//    }

    @GetMapping("/list")
    public void list(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO,
                     Model model) {
        log.info("list............." + pageRequestDTO);

        //PageResultDTO
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto..." + dto);

        //새로 추가된 엔티티의 번호
        Long gno = service.register(dto);
        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("gno: " + gno);
        GuestbookDTO dto = service.read(gno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(GuestbookDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {
        log.info("post modify.......");
        log.info("dto: " + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/guestbook/read";
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes) {
        log.info("gno: " + gno);
        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/guestbook/list";
    }


}
