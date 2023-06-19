package com.dgd.controller;

import com.dgd.model.dto.SharingApplicationDto;
import com.dgd.service.SharingApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;


@WebMvcTest(SharingApplicationController.class)
class SharingApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SharingApplicationService sharingApplicationService;

    private static final Long GOODID = 1L;
    @Test
    void saveSharingApplication() throws Exception{

    }

    @Test
    void readSharingApplication() throws Exception{

        //given

        List<SharingApplicationDto.Response> givenApplications = new ArrayList<>();

        for (int i = 1; i <4; i++) {

            SharingApplicationDto.Response givenApplication
                    = SharingApplicationDto.Response.builder()
                    .userId("testUser"+ i)
                    .profileUrl("profile" + i)
                    .distance(5.4)
                    .build();

            givenApplications.add(givenApplication);

        }

        given(sharingApplicationService.readSharingApplicationStatus(GOODID))
                .willReturn(givenApplications);

        //then
        mockMvc.perform(
                get("/sharing/application?goodId=" + GOODID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].userId").exists())
                .andExpect(jsonPath("$.[0].profileUrl").exists())
                .andExpect(jsonPath("$.[0].distance").exists())
                .andDo(print());

        verify(sharingApplicationService).readSharingApplicationStatus(GOODID);
    }
}