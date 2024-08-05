package com.talkka.server.user.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talkka.server.common.exception.http.BadRequestException;
import com.talkka.server.common.exception.http.NotFoundException;
import com.talkka.server.oauth.domain.NaverOAuth2User;
import com.talkka.server.oauth.domain.OAuth2UserInfo;
import com.talkka.server.user.dao.UserRepository;
import com.talkka.server.user.dto.UserCreateDto;
import com.talkka.server.user.dto.UserCreateReqDto;
import com.talkka.server.user.dto.UserDto;
import com.talkka.server.user.dto.UserRespDto;
import com.talkka.server.user.dto.UserUpdateReqDto;
import com.talkka.server.user.enums.Grade;
import com.talkka.server.user.service.UserService;

@WebMvcTest(UserController.class)
@MockBean({
	JpaMetamodelMappingContext.class,
})
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@Mock
	private OAuth2UserInfo oAuth2User;

	@BeforeEach
	public void setUp() {
		Map<String, Object> attributes = new HashMap<>();
		Long userId = 1L;
		attributes.put("userId", userId);
		attributes.put("name", "testUser");
		attributes.put("nickname", "testUser");
		attributes.put("email", "test@example.com");
		attributes.put("oauthProvider", "naver");
		attributes.put("accessToken", "token");
		attributes.put("oauth2Id", "test");
		oAuth2User = new NaverOAuth2User(attributes, List.of(new SimpleGrantedAuthority("ROLE_USER")));
	}

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Nested
	@DisplayName("GET /api/users/{user_id}")
	public class UsersUserIdTest {

		@Test
		public void 유저_아이디의_정보를_요청하면_유저_정보를_반환한다() throws Exception {
			// given
			final Long userId = 1L;
			final UserDto userDto = new UserDto(
				userId,
				"name",
				"nickname",
				"email",
				"oauthProvider",
				"accessToken",
				Grade.USER,
				LocalDateTime.now(),
				LocalDateTime.now()
			);
			final UserRespDto expect = UserRespDto.of(userDto);
			given(userService.getUser(userId)).willReturn(userDto);
			// when
			// then
			mockMvc.perform(get("/api/users/{user_id}", userId)
					.with(oauth2Login().oauth2User(oAuth2User)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status_code").value(200))
				.andExpect(jsonPath("$.message").value("OK"))
				.andExpect(jsonPath("$.data.user_id").value(expect.getUserId()))
				.andExpect(jsonPath("$.data.nickname").value(expect.getNickname()))
				.andExpect(jsonPath("$.data.oauth_provider").value(expect.getOauthProvider()));
		}

		@Test
		public void 존재하지않는_유저아이디를_입력하면_404을_응답한다() throws Exception {
			// given
			final Long userId = 1L;
			given(userService.getUser(userId)).willThrow(new NotFoundException("존재하지 않는 유저입니다."));
			// when
			// then
			mockMvc.perform(get("/api/users/{user_id}", userId)
					.with(oauth2Login().oauth2User(oAuth2User)))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status_code").value(404))
				.andExpect(jsonPath("$.message").value("존재하지 않는 유저입니다."))
				.andExpect(jsonPath("$.data").doesNotExist());
		}
	}

	@Nested
	@DisplayName("POST /api/users")
	public class CreateUserTest {
		@Test
		void 유저가_생성을_요청하면_생성된_유저의_정보를_반환한다() throws Exception {
			// given
			final UserCreateReqDto userCreateReqDto = new UserCreateReqDto("nickname");
			final UserCreateDto userCreateDto = new UserCreateDto(
				"name",
				"test@test.com",
				"naver",
				userCreateReqDto.getNickname(),
				"token",
				Grade.USER
			);
			final UserDto expectedData = new UserDto(
				1L,
				userCreateDto.getName(),
				userCreateDto.getEmail(),
				userCreateDto.getNickname(),
				userCreateDto.getOauthProvider(),
				"accessToken",
				Grade.USER,
				LocalDateTime.now(),
				LocalDateTime.now()
			);
			given(userService.createUser(any(UserCreateDto.class))).willReturn(expectedData);
			// when
			// then
			// ApiRespDto<UserRespDto>
			mockMvc.perform(post("/api/users")
					.with(oauth2Login().oauth2User(oAuth2User))
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(userCreateReqDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status_code").value(200))
				.andExpect(jsonPath("$.message").value("OK"))
				.andExpect(jsonPath("$.data.user_id").exists())
				.andExpect(jsonPath("$.data.name").value(userCreateDto.getName()))
				.andExpect(jsonPath("$.data.email").value(userCreateDto.getEmail()))
				.andExpect(jsonPath("$.data.nickname").value(userCreateDto.getNickname()))
				.andExpect(jsonPath("$.data.oauth_provider").value(userCreateDto.getOauthProvider()));
		}

		@Test
		void 유저가_생성요청한_닉네임이_중복될경우_400을_반환한다() throws Exception {
			// given
			final UserCreateReqDto userCreateReqDto = new UserCreateReqDto("nickname");
			given(userService.createUser(any())).willThrow(new BadRequestException("중복된 닉네임 입니다."));
			// when
			// then
			mockMvc.perform(post("/api/users")
					.with(oauth2Login().oauth2User(oAuth2User))
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(userCreateReqDto)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status_code").value(400))
				.andExpect(jsonPath("$.message").value("중복된 닉네임 입니다."))
				.andExpect(jsonPath("$.data").doesNotExist());
		}
	}

	@Nested
	@DisplayName("PUT /api/users/{user_id}")
	public class UpdateUserTest {
		@Test
		void 유저가_수정요청을_할경우_수정된_유저의_정보를_반환한다() throws Exception {
			// given
			final UserUpdateReqDto userUpdateReqDto = new UserUpdateReqDto("nickname");
			final UserDto userDto = new UserDto(
				1L,
				"name",
				userUpdateReqDto.getNickname(),
				"email",
				"oauthProvider",
				"accessToken",
				Grade.USER,
				LocalDateTime.now(),
				LocalDateTime.now()
			);
			given(userService.updateUser(anyLong(), any(UserUpdateReqDto.class))).willReturn(userDto);
			// when
			// then
			mockMvc.perform(put("/api/users/1")
					.with(oauth2Login().oauth2User(oAuth2User))
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(userUpdateReqDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status_code").value(200))
				.andExpect(jsonPath("$.message").value("OK"))
				.andExpect(jsonPath("$.data.user_id").value(userDto.getUserId()))
				.andExpect(jsonPath("$.data.name").value(userDto.getName()))
				.andExpect(jsonPath("$.data.email").value(userDto.getEmail()))
				.andExpect(jsonPath("$.data.nickname").value(userDto.getNickname()))
				.andExpect(jsonPath("$.data.oauth_provider").value(userDto.getOauthProvider()));
		}

		@Test
		void 유저가_중복된_닉네임으로_수정요청을_할_경우_400을_반환한다() throws Exception {
			// given
			final UserUpdateReqDto userUpdateReqDto = new UserUpdateReqDto("nickname");
			given(userService.updateUser(anyLong(), any(UserUpdateReqDto.class)))
				.willThrow(new BadRequestException("중복된 닉네임 입니다."));
			// when
			// then
			mockMvc.perform(put("/api/users/1")
					.with(oauth2Login().oauth2User(oAuth2User))
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(userUpdateReqDto)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status_code").value(400))
				.andExpect(jsonPath("$.message").value("중복된 닉네임 입니다."))
				.andExpect(jsonPath("$.data").doesNotExist());
		}
	}

	@Nested
	@DisplayName("DELETE /api/users/{user_id}")
	public class DeleteUserTest {
		@Test
		void 유저가_삭제요청을_할경우_성공메세지를_반환한다() throws Exception {
			// given
			final Long userId = 1L;
			// when
			// then
			mockMvc.perform(delete("/api/users/{user_id}", userId)
					.with(oauth2Login().oauth2User(oAuth2User))
					.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status_code").value(200))
				.andExpect(jsonPath("$.message").value("OK"))
				.andExpect(jsonPath("$.data").doesNotExist());
		}

		@Test
		void 존재하지않는_유저아이디를_입력하면_400을_응답한다() throws Exception {
			// given
			final Long userId = 1L;
			given(userService.deleteUser(any(Long.class))).willThrow(new BadRequestException("존재하지 않는 유저입니다."));
			// when
			// then
			mockMvc.perform(delete("/api/users/{user_id}", userId)
					.with(oauth2Login().oauth2User(oAuth2User))
					.with(csrf()))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status_code").value(400))
				.andExpect(jsonPath("$.message").value("존재하지 않는 유저입니다."))
				.andExpect(jsonPath("$.data").doesNotExist());
		}
	}

	@Nested
	@DisplayName("GET /users/me")
	public class GetMeTest {
		@Test
		void 내정보를_요청하면_내정보를_반환한다() throws Exception {
			// given
			final Long userId = 1L;
			final UserDto userDto = new UserDto(
				userId,
				"name",
				"nickname",
				"email",
				"oauthProvider",
				"accessToken",
				Grade.USER,
				LocalDateTime.now(),
				LocalDateTime.now()
			);
			final UserRespDto expect = UserRespDto.of(userDto);
			given(userService.getUser(userId)).willReturn(userDto);
			// when
			// then
			mockMvc.perform(get("/api/users/me")
					.with(oauth2Login().oauth2User(oAuth2User)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status_code").value(200))
				.andExpect(jsonPath("$.message").value("OK"))
				.andExpect(jsonPath("$.data.user_id").value(expect.getUserId()))
				.andExpect(jsonPath("$.data.email").value(expect.getEmail()))
				.andExpect(jsonPath("$.data.name").value(expect.getName()))
				.andExpect(jsonPath("$.data.nickname").value(expect.getNickname()))
				.andExpect(jsonPath("$.data.oauth_provider").value(expect.getOauthProvider()));
		}
	}

	@Nested
	@DisplayName("PUT /users/me")
	public class UpdateMeTest {
		@Test
		void 내정보를_수정하면_수정된_내정보를_반환한다() throws Exception {
			// given
			final UserUpdateReqDto userUpdateReqDto = new UserUpdateReqDto("nickname");
			final UserDto userDto = new UserDto(
				1L,
				"name",
				userUpdateReqDto.getNickname(),
				"email",
				"oauthProvider",
				"accessToken",
				Grade.USER,
				LocalDateTime.now(),
				LocalDateTime.now()
			);
			given(userService.updateUser(any(Long.class), any(UserUpdateReqDto.class))).willReturn(userDto);
			// when
			// then
			mockMvc.perform(put("/api/users/me")
					.with(oauth2Login().oauth2User(oAuth2User))
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(userUpdateReqDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status_code").value(200))
				.andExpect(jsonPath("$.message").value("OK"))
				.andExpect(jsonPath("$.data.user_id").value(userDto.getUserId()))
				.andExpect(jsonPath("$.data.name").value(userDto.getName()))
				.andExpect(jsonPath("$.data.email").value(userDto.getEmail()))
				.andExpect(jsonPath("$.data.nickname").value(userDto.getNickname()))
				.andExpect(jsonPath("$.data.oauth_provider").value(userDto.getOauthProvider()));
		}

		@Test
		void 내정보를_수정요청한_닉네임이_중복될경우_400을_반환한다() throws Exception {
			// given
			final UserUpdateReqDto userUpdateReqDto = new UserUpdateReqDto("nickname");
			given(userService.updateUser(anyLong(), any(UserUpdateReqDto.class)))
				.willThrow(new BadRequestException("중복된 닉네임 입니다."));
			// when
			// then
			mockMvc.perform(put("/api/users/me")
					.with(oauth2Login().oauth2User(oAuth2User))
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(userUpdateReqDto)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status_code").value(400))
				.andExpect(jsonPath("$.message").value("중복된 닉네임 입니다."))
				.andExpect(jsonPath("$.data").doesNotExist());
		}
	}
}