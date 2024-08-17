package com.talkka.server.bookmark.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.bookmark.dao.dto.BookmarkReqDto;
import com.talkka.server.bookmark.dao.dto.BookmarkRespDto;
import com.talkka.server.bookmark.dao.entity.BookmarkEntity;
import com.talkka.server.bookmark.dao.repository.BookmarkDetailRepository;
import com.talkka.server.bookmark.dao.repository.BookmarkRepository;
import com.talkka.server.bookmark.exception.BookmarkNotFoundException;
import com.talkka.server.bookmark.exception.BookmarkUserNotFoundException;
import com.talkka.server.common.validator.ContentAccessValidator;
import com.talkka.server.review.exception.ContentAccessException;
import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.dao.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkService {
	private final BookmarkRepository bookmarkRepository;
	private final BookmarkDetailRepository bookmarkDetailRepository;
	private final UserRepository userRepository;
	private final ContentAccessValidator contentAccessValidator;

	@Transactional
	public BookmarkRespDto createBookmark(BookmarkReqDto dto, Long userId) throws BookmarkUserNotFoundException {
		UserEntity user = userRepository.findById(userId).orElseThrow(BookmarkUserNotFoundException::new);
		BookmarkEntity bookmark = dto.toEntity(user);
		dto.details().stream()
			.map(detail -> detail.toEntity(bookmark))
			.forEach(bookmark.getDetails()::add);
		return BookmarkRespDto.of(bookmarkRepository.save(bookmark));
	}

	public BookmarkRespDto getByBookmarkId(Long id) throws BookmarkNotFoundException {
		BookmarkEntity bookmark = bookmarkRepository.findById(id).orElseThrow(BookmarkUserNotFoundException::new);
		return BookmarkRespDto.of(bookmark);
	}

	public List<BookmarkRespDto> getByUserId(Long userId) {
		return bookmarkRepository.findByUserId(userId).stream()
			.map(BookmarkRespDto::of)
			.toList();
	}

	@Transactional
	public BookmarkRespDto updateBookmark(BookmarkReqDto dto, Long bookmarkId, Long userId) throws
		BookmarkUserNotFoundException,
		BookmarkNotFoundException,
		ContentAccessException {

		UserEntity user = userRepository.findById(userId).orElseThrow(BookmarkUserNotFoundException::new);
		BookmarkEntity bookmark = bookmarkRepository.findById(bookmarkId)
			.orElseThrow(BookmarkUserNotFoundException::new);

		// 작성자거나 관리자가 아니면 ContentAccessException 발생
		contentAccessValidator.validateOwnerContentAccess(user.getId(), user.getGrade(), bookmark.getId());

		// 기존 북마크 상세를 전부 지우고 전체를 새로 저장함
		bookmarkDetailRepository.deleteByBookmarkId(bookmarkId);
		bookmark.updateBookmark(dto);

		return BookmarkRespDto.of(bookmarkRepository.save(bookmark));
	}

	@Transactional
	public Long deleteBookmark(Long bookmarkId, Long userId) throws
		BookmarkUserNotFoundException,
		BookmarkNotFoundException {

		UserEntity user = userRepository.findById(userId).orElseThrow(BookmarkUserNotFoundException::new);
		BookmarkEntity bookmark = bookmarkRepository.findById(bookmarkId)
			.orElseThrow(BookmarkUserNotFoundException::new);

		// 작성자거나 관리자가 아니면 ContentAccessException 발생
		contentAccessValidator.validateOwnerContentAccess(user.getId(), user.getGrade(), bookmark.getId());

		// 북마크와 북마크 상세를 삭제
		bookmarkRepository.delete(bookmark);

		return bookmarkId;
	}
}
