package com.talkka.server.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.dao.UserRepository;
import com.talkka.server.user.dto.UserCreateDto;
import com.talkka.server.user.dto.UserDto;
import com.talkka.server.user.dto.UserUpdateDto;
import com.talkka.server.user.exception.DuplicatedNicknameException;
import com.talkka.server.user.exception.UserNotFoundException;
import com.talkka.server.user.vo.Nickname;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public UserDto getUser(Long userId) throws UserNotFoundException {
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(UserNotFoundException::new);

		return UserDto.of(user);
	}

	public List<UserDto> getAllUser() {
		return userRepository.findAll().stream().map(UserDto::of).toList();
	}

	public UserDto createUser(UserCreateDto dto) throws DuplicatedNicknameException {
		UserEntity user = dto.toEntity();
		if (this.isDuplicatedNickname(user.getNickname())) {
			throw new DuplicatedNicknameException();
		}

		UserEntity savedUser = userRepository.save(user);
		return UserDto.of(savedUser);
	}

	@Transactional
	public UserDto updateUser(UserUpdateDto dto) throws DuplicatedNicknameException, UserNotFoundException {
		Long userId = dto.userId();
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(UserNotFoundException::new);

		if (!dto.nickname().equals(user.getNickname()) && this.isDuplicatedNickname(dto.nickname())) {
			throw new DuplicatedNicknameException();
		}
		user.updateUser(dto.nickname());
		return UserDto.of(user);
	}

	public Long deleteUser(Long userId) throws UserNotFoundException {
		boolean isExist = userRepository.existsById(userId);
		if (!isExist) {
			throw new UserNotFoundException();
		}
		userRepository.deleteById(userId);
		return userId;
	}

	public boolean isDuplicatedNickname(Nickname nickname) {
		return userRepository.existsByNickname(nickname);
	}
}
