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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.bookmark.dto.BookmarkReqDto;
import com.talkka.server.bookmark.dto.BookmarkRespDto;
import com.talkka.server.bookmark.exception.BookmarkNotFoundException;
import com.talkka.server.bookmark.exception.BookmarkUserNotFoundException;
import com.talkka.server.bookmark.exception.enums.InvalidTransportTypeEnumException;
import com.talkka.server.bookmark.service.BookmarkService;
import com.talkka.server.oauth.domain.OAuth2UserInfo;
import com.talkka.server.review.exception.ContentAccessException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkController {
	private final BookmarkService bookmarkService;

	// 본인이 작성한 북마크 리스트만 조회
	@GetMapping("")
	@Secured({"USER", "ADMIN"})
	public ResponseEntity<?> getBookmarkList(@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo) {
		List<BookmarkRespDto> bookmarks = bookmarkService.getBookmarkByUserId(oAuth2UserInfo.getUserId());
		return ResponseEntity.ok(bookmarks);
	}

	@GetMapping("/{bookmarkId}")
	@Secured({"USER", "ADMIN"})
	public ResponseEntity<?> getBookmark(@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo,
		@PathVariable Long bookmarkId) {
		ResponseEntity<?> response;
		try {
			BookmarkRespDto bookmark = bookmarkService.getBookmarkById(oAuth2UserInfo.getUserId(), bookmarkId);
			response = ResponseEntity.ok(bookmark);
		} catch (BookmarkNotFoundException | BookmarkUserNotFoundException exception) {
			response = ResponseEntity.badRequest().body(exception.getMessage());
		} catch (ContentAccessException exception) {
			response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
		}
		return response;
	}

	@PostMapping("")
	@Secured({"USER", "ADMIN"})
	public ResponseEntity<?> createBookmark(@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo,
		BookmarkReqDto bookmarkReqDto) {
		ResponseEntity<?> response;
		try {
			BookmarkRespDto bookmark = bookmarkService.createBookmark(bookmarkReqDto, oAuth2UserInfo.getUserId());
			return ResponseEntity.ok(bookmark);
		} catch (BookmarkUserNotFoundException | InvalidTransportTypeEnumException exception) {
			response = ResponseEntity.badRequest().body(exception.getMessage());
		}
		return response;
	}

	@PutMapping("{bookmarkId}")
	@Secured({"USER", "ADMIN"})
	public ResponseEntity<?> updateBookmark(@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo,
		BookmarkReqDto bookmarkReqDto, @PathVariable Long bookmarkId) {
		ResponseEntity<?> response;
		try {
			BookmarkRespDto bookmark = bookmarkService.updateBookmark(bookmarkReqDto, oAuth2UserInfo.getUserId(),
				bookmarkId);
			response = ResponseEntity.ok(bookmark);
		} catch (BookmarkNotFoundException | BookmarkUserNotFoundException
				 | InvalidTransportTypeEnumException exception) {
			response = ResponseEntity.badRequest().body(exception.getMessage());
		} catch (ContentAccessException exception) {
			response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
		}
		return response;
	}

	@DeleteMapping("/{bookmarkId}")
	@Secured({"USER", "ADMIN"})
	public ResponseEntity<?> deleteBookmark(@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo,
		@PathVariable Long bookmarkId) {
		ResponseEntity<?> response;
		try {
			bookmarkService.deleteBookmark(oAuth2UserInfo.getUserId(), bookmarkId);
			response = ResponseEntity.ok().build();
		} catch (BookmarkNotFoundException | BookmarkUserNotFoundException exception) {
			response = ResponseEntity.badRequest().body(exception.getMessage());
		} catch (ContentAccessException exception) {
			response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
		}
		return response;
	}
}
