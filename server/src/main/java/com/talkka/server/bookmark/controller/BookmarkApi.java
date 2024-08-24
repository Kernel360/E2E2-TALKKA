package com.talkka.server.bookmark.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.talkka.server.bookmark.dto.BookmarkReqDto;
import com.talkka.server.bookmark.dto.BookmarkRespDto;
import com.talkka.server.common.dto.ErrorRespDto;
import com.talkka.server.oauth.domain.OAuth2UserInfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "북마크 API", description = "북마크 API")
public interface BookmarkApi {

	@Operation(
		summary = "북마크 목록 조회",
		description = "북마크 목록을 조회합니다.",
		security = {
			@SecurityRequirement(name = "user"),
			@SecurityRequirement(name = "admin")
		}
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 북마크 목록을 조회했습니다.")
	})
	ResponseEntity<List<BookmarkRespDto>> getBookmarkList(
		@Parameter(hidden = true)
		OAuth2UserInfo oAuth2UserInfo);

	@Operation(
		summary = "북마크 조회",
		description = "북마크를 조회합니다.",
		security = {
			@SecurityRequirement(name = "user"),
			@SecurityRequirement(name = "admin")
		}
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 북마크를 조회했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = BookmarkRespDto.class)
			)),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			)),
		@ApiResponse(
			responseCode = "403",
			description = "권한이 없습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			)),
		@ApiResponse(
			responseCode = "404",
			description = "해당 ID의 북마크가 존재하지 않습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			)),
	})
	ResponseEntity<?> getBookmark(
		@Parameter(hidden = true)
		OAuth2UserInfo oAuth2UserInfo,
		@Parameter(description = "북마크 ID", required = true)
		Long bookmarkId);

	@Operation(
		summary = "북마크 생성",
		description = "북마크를 생성합니다.",
		security = {
			@SecurityRequirement(name = "user"),
			@SecurityRequirement(name = "admin")
		}
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 북마크를 생성했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = BookmarkRespDto.class)
			)),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> createBookmark(
		@Parameter(hidden = true)
		OAuth2UserInfo oAuth2UserInfo,
		@RequestBody(description = "북마크 생성 정보", required = true)
		BookmarkReqDto bookmarkReqDto);

	@Operation(
		summary = "북마크 수정",
		description = "북마크를 수정합니다.",
		security = {
			@SecurityRequirement(name = "user"),
			@SecurityRequirement(name = "admin")
		}
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 북마크를 수정했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = BookmarkRespDto.class)
			)),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			)),
		@ApiResponse(
			responseCode = "403",
			description = "권한이 없습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> updateBookmark(
		@Parameter(hidden = true)
		OAuth2UserInfo oAuth2UserInfo,
		@RequestBody(description = "북마크 수정 정보", required = true)
		BookmarkReqDto bookmarkReqDto, Long bookmarkId);

	@Operation(
		summary = "북마크 삭제",
		description = "북마크를 삭제합니다.",
		security = {
			@SecurityRequirement(name = "user"),
			@SecurityRequirement(name = "admin")
		}
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 북마크를 삭제했습니다."),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			)),
		@ApiResponse(
			responseCode = "403",
			description = "권한이 없습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> deleteBookmark(
		@Parameter(hidden = true)
		OAuth2UserInfo oAuth2UserInfo,
		@Parameter(description = "북마크 ID", required = true)
		Long bookmarkId);
}
