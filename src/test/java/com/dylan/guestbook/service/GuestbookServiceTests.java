package com.dylan.guestbook.service;

import com.dylan.guestbook.dto.GuestbookDTO;
import com.dylan.guestbook.dto.PageRequestDTO;
import com.dylan.guestbook.dto.PageResultDTO;
import com.dylan.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    GuestbookService service ;

    @Test
    public void testRegister() {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample title. ~~~")
                .content("Sample Contents ~~~")
                .writer("Dylan")
                .build();

        System.out.println(service.register(guestbookDTO));
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);
        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }
    }

}
