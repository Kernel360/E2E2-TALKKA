package com.talkka.server.bookmark.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.bookmark.dao.BookmarkDetailEntity;
import com.talkka.server.bookmark.dao.BookmarkDetailRepository;
import com.talkka.server.bookmark.dao.BookmarkEntity;
import com.talkka.server.bookmark.dao.BookmarkRepository;
import com.talkka.server.bookmark.dto.BookmarkReqDto;
import com.talkka.server.bookmark.dto.BookmarkRespDto;
import com.talkka.server.bookmark.exception.BookmarkNotFoundException;
import com.talkka.server.bookmark.exception.BookmarkUserNotFoundException;
import com.talkka.server.bookmark.exception.DuplicatedBookmarkNameException;
import com.talkka.server.bookmark.exception.InvalidBusDetailException;
import com.talkka.server.bookmark.exception.NotSupportedTypeException;
import com.talkka.server.bookmark.exception.enums.InvalidTransportTypeEnumException;
import com.talkka.server.bus.dao.BusRouteStationRepository;
import com.talkka.server.bus.dto.BusLiveInfoRespDto;
import com.talkka.server.bus.exception.BusRouteStationNotFoundException;
import com.talkka.server.bus.service.BusLiveInfoService;
import com.talkka.server.common.enums.TimeSlot;
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
	private final BusLiveInfoService busLiveInfoService;
	private final BusRouteStationRepository busRouteStationRepository;

	public BookmarkRespDto getBookmarkById(Long userId, Long bookmarkId) throws
		BookmarkNotFoundException,
		BookmarkUserNotFoundException,
		ContentAccessException {
		// 본인이 작성한 북마크만 조회 하도록 변경
		UserEntity user = userRepository.findById(userId).orElseThrow(BookmarkUserNotFoundException::new);
		BookmarkEntity bookmark = bookmarkRepository.findById(bookmarkId)
			.orElseThrow(BookmarkUserNotFoundException::new);
		contentAccessValidator.validateOwnerContentAccess(userId, user.getAuthRole(), bookmark.getUser().getId());
		return BookmarkRespDto.of(bookmark);
	}

	public List<BookmarkRespDto> getBookmarkByUserId(Long userId) {
		return bookmarkRepository.findByUserId(userId).stream()
			.map(BookmarkRespDto::of)
			.toList();
	}

	@Transactional
	public BookmarkRespDto createBookmark(BookmarkReqDto dto, Long userId) throws BookmarkUserNotFoundException,
		DuplicatedBookmarkNameException,
		InvalidTransportTypeEnumException {
		if (bookmarkRepository.existsByNameAndUserId(dto.name(), userId)) {
			throw new DuplicatedBookmarkNameException();
		}
		UserEntity user = userRepository.findById(userId).orElseThrow(BookmarkUserNotFoundException::new);
		BookmarkEntity bookmark = dto.toEntity(user);
		List<BookmarkDetailEntity> details = dto.details().stream()
			.map((detail) ->
				detail.toEntity(bookmark, busRouteStationRepository.findById(detail.busRouteStationId())
					.orElseThrow(BusRouteStationNotFoundException::new)))
			.toList();
		bookmark.updateBookmark(dto.name(), details);
		return BookmarkRespDto.of(bookmarkRepository.save(bookmark));
	}

	@Transactional
	public BookmarkRespDto updateBookmark(BookmarkReqDto dto, Long userId, Long bookmarkId) throws
		BookmarkUserNotFoundException, BookmarkNotFoundException, InvalidTransportTypeEnumException,
		DuplicatedBookmarkNameException, ContentAccessException, InvalidBusDetailException, NotSupportedTypeException {
		UserEntity user = userRepository.findById(userId).orElseThrow(BookmarkUserNotFoundException::new);
		BookmarkEntity bookmark = bookmarkRepository.findById(bookmarkId)
			.orElseThrow(BookmarkNotFoundException::new);
		if (!isValidNickname(dto, userId, bookmark)) {
			throw new DuplicatedBookmarkNameException();
		}
		contentAccessValidator.validateOwnerContentAccess(user.getId(), user.getAuthRole(), bookmark.getUser().getId());

		List<BookmarkDetailEntity> details = dto.details().stream()
			.map((detail) ->
				detail.toEntity(bookmark, busRouteStationRepository.findById(detail.busRouteStationId())
					.orElseThrow(BusRouteStationNotFoundException::new)))
			.toList();
		bookmark.updateBookmark(dto.name(), details);

		var saved = bookmarkRepository.save(bookmark);
		return BookmarkRespDto.of(saved);
	}

	@Transactional
	public Long deleteBookmark(Long userId, Long bookmarkId) throws
		BookmarkUserNotFoundException, BookmarkNotFoundException {
		UserEntity user = userRepository.findById(userId).orElseThrow(BookmarkUserNotFoundException::new);
		BookmarkEntity bookmark = bookmarkRepository.findById(bookmarkId)
			.orElseThrow(BookmarkUserNotFoundException::new);

		// 작성자거나 관리자가 아니면 ContentAccessException 발생
		contentAccessValidator.validateOwnerContentAccess(user.getId(), user.getAuthRole(), bookmark.getUser().getId());
		// 북마크와 북마크 상세를 삭제
		bookmarkRepository.delete(bookmark);
		return bookmarkId;
	}

	public List<BusLiveInfoRespDto> getBusPathInfosByBookmarkId(
		Long userId, Long bookmarkId
	) throws BookmarkNotFoundException, BookmarkUserNotFoundException, ContentAccessException,
		BusRouteStationNotFoundException {
		BookmarkEntity bookmark = bookmarkRepository.findById(bookmarkId)
			.orElseThrow(BookmarkNotFoundException::new);
		UserEntity user = userRepository.findById(userId).orElseThrow(BookmarkUserNotFoundException::new);
		contentAccessValidator.validateOwnerContentAccess(userId, user.getAuthRole(), bookmark.getUser().getId());

		var now = LocalDateTime.now();
		var timeSlot = TimeSlot.of(now);

		return bookmark.getDetails().stream()
			.map((detail) -> busLiveInfoService.getBusLiveInfo(detail.getRouteStation().getId(), timeSlot, 1L))
			.toList();
	}

	private boolean isValidNickname(BookmarkReqDto dto, Long userId, BookmarkEntity bookmark) {
		return bookmark.getName().equals(dto.name()) || !bookmarkRepository.existsByNameAndUserId(dto.name(), userId);
	}
}
