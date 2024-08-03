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
		// 중복 닉네임 체크
		if (!reqDto.getNickname().equals(user.getNickname())
			&& this.isDuplicatedNickname(reqDto.getNickname())) {
			throw new BadRequestException("중복된 닉네임 입니다.");
		}
		UserEntity updatedUser = UserEntity.builder() // refactoring 필요함.
			.userId(userId)
			.nickname(reqDto.getNickname())
			.oauthProvider(user.getOauthProvider())
			.accessToken(user.getAccessToken())
			.grade(user.getGrade())
			.createdAt(user.getCreatedAt())
			.updatedAt(user.getUpdatedAt())
			.busReviews(user.getBusReviews())
			.build();
		UserEntity savedUser = userRepository.save(updatedUser);

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
