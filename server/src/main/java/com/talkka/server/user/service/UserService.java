package com.talkka.server.user.service;

import org.springframework.stereotype.Service;

import com.talkka.server.common.exception.http.BadRequestException;
import com.talkka.server.common.exception.http.NotFoundException;
import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.dao.UserRepository;
import com.talkka.server.user.dto.UserCreateDto;
import com.talkka.server.user.dto.UserDto;
import com.talkka.server.user.dto.UserUpdateReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public UserDto getUser(Long userId) {
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

		return UserDto.of(user);
	}

	public UserDto createUser(UserCreateDto userCreateDto) {
		UserEntity user = userCreateDto.toEntity();
		if (this.isDuplicatedNickname(user.getNickname())) {
			throw new BadRequestException("중복된 닉네임 입니다.");
		}
		UserEntity savedUser = userRepository.save(user);

		return UserDto.of(savedUser);
	}

	public UserDto updateUser(Long userId, UserUpdateReqDto reqDto) {
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(() -> new BadRequestException("존재하지 않는 유저입니다."));

		if (!reqDto.getNickname().equals(user.getNickname())
			&& this.isDuplicatedNickname(reqDto.getNickname())) {
			throw new BadRequestException("중복된 닉네임 입니다.");
		}
		user.setNickname(reqDto.getNickname());
		UserEntity savedUser = userRepository.save(user);

		return UserDto.of(savedUser);
	}

	public Long deleteUser(Long userId) {
		boolean isExist = userRepository.existsById(userId);
		if (!isExist) {
			throw new BadRequestException("존재하지 않는 유저입니다.");
		}
		userRepository.deleteById(userId);
		return userId;
	}

	public boolean isDuplicatedNickname(String nickname) {
		return userRepository.existsByNickname(nickname);
	}
}
