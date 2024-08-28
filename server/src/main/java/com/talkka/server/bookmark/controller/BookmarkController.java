package com.talkka.server.bookmark.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.bookmark.dto.BookmarkReqDto;
import com.talkka.server.bookmark.dto.BookmarkRespDto;
import com.talkka.server.bookmark.exception.BookmarkNotFoundException;
import com.talkka.server.bookmark.exception.BookmarkUserNotFoundException;
import com.talkka.server.bookmark.exception.DuplicatedBookmarkNameException;
import com.talkka.server.bookmark.exception.InvalidBusDetailException;
import com.talkka.server.bookmark.exception.NotSupportedTypeException;
import com.talkka.server.bookmark.exception.enums.InvalidTransportTypeEnumException;
import com.talkka.server.bookmark.service.BookmarkService;
import com.talkka.server.bus.exception.BusRouteStationNotFoundException;
import com.talkka.server.common.dto.ErrorRespDto;
import com.talkka.server.oauth.domain.OAuth2UserInfo;
import com.talkka.server.review.exception.ContentAccessException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkController implements BookmarkApi {
	private final BookmarkService bookmarkService;

	// 본인이 작성한 북마크 리스트만 조회
	@Override
	@GetMapping("")
	@Secured({"USER", "ADMIN"})
	public ResponseEntity<List<BookmarkRespDto>> getBookmarkList(
		@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo) {
		List<BookmarkRespDto> bookmarks = bookmarkService.getBookmarkByUserId(oAuth2UserInfo.getUserId());
		return ResponseEntity.ok(bookmarks);
	}

	@Override
	@GetMapping("/{bookmarkId}")
	@Secured({"USER", "ADMIN"})
	public ResponseEntity<?> getBookmark(@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo,
		@PathVariable Long bookmarkId) {
		ResponseEntity<?> response;
		try {
			BookmarkRespDto bookmark = bookmarkService.getBookmarkById(oAuth2UserInfo.getUserId(), bookmarkId);
			response = ResponseEntity.ok(bookmark);
		} catch (BookmarkNotFoundException exception) {
			response = new ResponseEntity<>(ErrorRespDto.of(exception), HttpStatus.NOT_FOUND);
		} catch (BookmarkUserNotFoundException exception) {
			response = ResponseEntity.badRequest().body(ErrorRespDto.of(exception));
		} catch (ContentAccessException exception) {
			response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorRespDto.of(exception));
		}
		return response;
	}

	@Override
	@PostMapping("")
	@Secured({"USER", "ADMIN"})
	public ResponseEntity<?> createBookmark(@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo,
		@RequestBody @Valid BookmarkReqDto bookmarkReqDto) {
		ResponseEntity<?> response;
		try {
			BookmarkRespDto bookmark = bookmarkService.createBookmark(bookmarkReqDto, oAuth2UserInfo.getUserId());
			return ResponseEntity.ok(bookmark);
		} catch (BookmarkUserNotFoundException | InvalidTransportTypeEnumException
				 | InvalidBusDetailException | NotSupportedTypeException | DuplicatedBookmarkNameException exception) {
			response = ResponseEntity.badRequest().body(ErrorRespDto.of(exception));
		}
		return response;
	}

	@Override
	@PutMapping("/{bookmarkId}")
	@Secured({"USER", "ADMIN"})
	public ResponseEntity<?> updateBookmark(@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo,
		@RequestBody @Valid BookmarkReqDto bookmarkReqDto, @PathVariable Long bookmarkId) {
		ResponseEntity<?> response;
		try {
			BookmarkRespDto bookmark = bookmarkService.updateBookmark(bookmarkReqDto, oAuth2UserInfo.getUserId(),
				bookmarkId);
			response = ResponseEntity.ok(bookmark);
		} catch (BookmarkNotFoundException | BookmarkUserNotFoundException | DuplicatedBookmarkNameException
				 | InvalidBusDetailException | NotSupportedTypeException
				 | InvalidTransportTypeEnumException exception) {
			response = ResponseEntity.badRequest().body(ErrorRespDto.of(exception));
		} catch (ContentAccessException exception) {
			response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorRespDto.of(exception));
		}
		return response;
	}

	@Override
	@DeleteMapping("/{bookmarkId}")
	@Secured({"USER", "ADMIN"})
	public ResponseEntity<?> deleteBookmark(@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo,
		@PathVariable Long bookmarkId) {
		ResponseEntity<?> response;
		try {
			bookmarkService.deleteBookmark(oAuth2UserInfo.getUserId(), bookmarkId);
			response = ResponseEntity.ok().build();
		} catch (BookmarkNotFoundException | BookmarkUserNotFoundException exception) {
			response = ResponseEntity.badRequest().body(ErrorRespDto.of(exception));
		} catch (ContentAccessException exception) {
			response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorRespDto.of(exception));
		}
		return response;
	}

	@Override
	@GetMapping("/{bookmarkId}/paths")
	@Secured({"USER", "ADMIN"})
	public ResponseEntity<?> getBookmarkPathInfos(
		@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo,
		@PathVariable Long bookmarkId) {
		ResponseEntity<?> response;
		try {
			var body = bookmarkService.getBusPathInfosByBookmarkId(oAuth2UserInfo.getUserId(), bookmarkId);
			response = ResponseEntity.ok(body);
		} catch (BookmarkNotFoundException | BookmarkUserNotFoundException |
				 BusRouteStationNotFoundException exception) {
			response = ResponseEntity.badRequest().body(ErrorRespDto.of(exception));
		} catch (ContentAccessException exception) {
			response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorRespDto.of(exception));
		}
		return response;
	}
}
