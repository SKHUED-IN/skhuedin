package com.skhuedin.skhuedin.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhuedin.skhuedin.domain.user.Provider;
import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import com.skhuedin.skhuedin.dto.follow.FollowDeleteRequestDto;
import com.skhuedin.skhuedin.dto.follow.FollowMainResponseDto;
import com.skhuedin.skhuedin.dto.follow.FollowSaveRequestDto;
import com.skhuedin.skhuedin.service.FollowService;
import com.skhuedin.skhuedin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql("/truncate.sql")
class FollowApiControllerTest {

    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가
                .build();
    }

    static User generateUser(String email, String name) {
        return User.builder()
                .email(email)
                .name(name)
                .provider(Provider.SELF)
                .userImageUrl("/images/user.png")
                .role(Role.ROLE_USER)
                .build();
    }

    static FollowSaveRequestDto generateFollowSaveRequestDto(Long fromUserId, Long toUserId) {
        return FollowSaveRequestDto.builder()
                .fromUserId(fromUserId)
                .toUserId(toUserId)
                .build();
    }

    @Test
    @DisplayName("GET /api/follows/{followId} - 성공")
    void getFollow() throws Exception {

        // given
        User fromUser = generateUser("fromUser@email.com", "fromUser");
        User toUser = generateUser("toUser@email.com", "toUser");

        Long fromUserId = userService.save(fromUser);
        Long toUserId = userService.save(toUser);

        FollowSaveRequestDto requestDto = generateFollowSaveRequestDto(fromUserId, toUserId);

        // when
        Long followId = followService.save(requestDto);
        FollowMainResponseDto responseDto = followService.findById(followId);

        // then
        mockMvc.perform(get("/api/follows/{id}", responseDto.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/follows - 성공")
    @WithMockUser(username = "fromUser@email.com")
    void saveFollow() throws Exception {

        // given
        User fromUser = generateUser("fromUser@email.com", "fromUser");
        User toUser = generateUser("toUser@email.com", "toUser");

        Long fromUserId = userService.save(fromUser);
        Long toUserId = userService.save(toUser);

        // when
        FollowSaveRequestDto requestDto = generateFollowSaveRequestDto(fromUserId, toUserId);

        // then
        mockMvc.perform(post("/api/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST /api/follows Security Context와 요청한 user의 id와 불일치하여 예외를 던지는 테스트 - 실패")
    @WithMockUser(username = "user@email.com")
    void saveFollowWithNotSameUser() throws Exception {

        // given
        User fromUser = generateUser("fromUser@email.com", "fromUser");
        User toUser = generateUser("toUser@email.com", "toUser");

        Long fromUserId = userService.save(fromUser);
        Long toUserId = userService.save(toUser);

        // when
        FollowSaveRequestDto requestDto = generateFollowSaveRequestDto(fromUserId, toUserId);

        // then
        mockMvc.perform(post("/api/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("DELETE /api/follows - 성공")
    @WithMockUser(username = "fromUser@email.com")
    void deleteFollow() throws Exception {

        // given
        User fromUser = generateUser("fromUser@email.com", "fromUser");
        User toUser = generateUser("toUser@email.com", "toUser");

        Long fromUserId = userService.save(fromUser);
        Long toUserId = userService.save(toUser);

        FollowSaveRequestDto requestDto = generateFollowSaveRequestDto(fromUserId, toUserId);
        followService.save(requestDto);

        // when
        FollowDeleteRequestDto deleteRequestDto = FollowDeleteRequestDto.builder()
                .fromUserId(requestDto.getFromUserId())
                .toUserId(requestDto.getToUserId())
                .build();

        // then
        mockMvc.perform(delete("/api/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteRequestDto)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/follows Security Context와 요청한 user의 id와 불일치하여 예외를 던지는 테스트 - 실패")
    @WithMockUser(username = "user@email.com")
    void deleteFollowWithNotSameUser() throws Exception {

        // given
        User fromUser = generateUser("fromUser@email.com", "fromUser");
        User toUser = generateUser("toUser@email.com", "toUser");

        Long fromUserId = userService.save(fromUser);
        Long toUserId = userService.save(toUser);

        FollowSaveRequestDto requestDto = generateFollowSaveRequestDto(fromUserId, toUserId);
        followService.save(requestDto);

        // when
        FollowDeleteRequestDto deleteRequestDto = FollowDeleteRequestDto.builder()
                .fromUserId(requestDto.getFromUserId())
                .toUserId(requestDto.getToUserId())
                .build();

        // then
        mockMvc.perform(delete("/api/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteRequestDto)))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("GET /api/follows/from-user/{fromUserId} - 성공")
    void findByFromUserId() throws Exception {

        // given
        User fromUser = generateUser("fromUser@email.com", "fromUser");
        User toUser1 = generateUser("toUser1@email.com", "toUser1");
        User toUser2 = generateUser("toUser2@email.com", "toUser2");

        Long fromUserId = userService.save(fromUser);
        Long toUser1Id = userService.save(toUser1);
        Long toUser2Id = userService.save(toUser2);

        FollowSaveRequestDto requestDto1 = generateFollowSaveRequestDto(fromUserId, toUser1Id);
        FollowSaveRequestDto requestDto2 = generateFollowSaveRequestDto(fromUserId, toUser2Id);

        followService.save(requestDto1);
        followService.save(requestDto2);

        // when & then
        mockMvc.perform(get("/api/follows/from-user/{fromUserId}", fromUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/follows/to-user/{toUserId} - 성공")
    void findByToUserId() throws Exception {

        // given
        User fromUser1 = generateUser("fromUser1@email.com", "fromUser1");
        User fromUser2 = generateUser("fromUser2@email.com", "fromUser2");
        User toUser = generateUser("toUser@email.com", "toUser");

        Long fromUser1Id = userService.save(fromUser1);
        Long fromUser2Id = userService.save(fromUser2);
        Long toUserId = userService.save(toUser);

        FollowSaveRequestDto requestDto1 = generateFollowSaveRequestDto(fromUser1Id, toUserId);
        FollowSaveRequestDto requestDto2 = generateFollowSaveRequestDto(fromUser2Id, toUserId);

        followService.save(requestDto1);
        followService.save(requestDto2);

        // when & then
        mockMvc.perform(get("/api/follows/to-user/{toUserId}", toUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}