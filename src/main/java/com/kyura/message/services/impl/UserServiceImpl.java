package com.kyura.message.services.impl;

import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.exception.BadRequestException;
import com.kyura.message.common.ROLE;
import com.kyura.message.exception.TokenRefreshException;
import com.kyura.message.models.RefreshToken;
import com.kyura.message.models.ResetPassHistory;
import com.kyura.message.models.Role;
import com.kyura.message.models.User;
import com.kyura.message.payload.request.*;
import com.kyura.message.payload.response.*;
import com.kyura.message.repository.ResetPassHistoryRepository;
import com.kyura.message.repository.RoleRepository;
import com.kyura.message.repository.UserRepository;
import com.kyura.message.security.jwt.JwtUtils;
import com.kyura.message.services.MailService;
import com.kyura.message.services.RefreshTokenService;
import com.kyura.message.services.UserService;
import com.kyura.message.util.CommonUtil;
import com.kyura.message.util.Constant;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;

	@Autowired
	MailService mailService;

	@Autowired
	ResetPassHistoryRepository resetPassHistoryRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${fe.domain.url}")
	private String domainUrl;

	@Value("${mail.username}")
	private String adminEmail;


	@Override
	public ResponseEntity<?> login(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User userInfo = userRepository.findByUsernameAndStatus(userDetails.getUsername(), ACTIVE_STATUS.ACTIVE)
				.orElseThrow(() -> new BadRequestException(Constant.ERR_USER_NOT_FOUND));
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
		return ResponseEntity.ok(new MessageResponse<>(Constant.SUCCESS, new JwtResponse(jwt,
				refreshToken.getToken(),
				userDetails.getId(),
				userDetails.getUsername(),
				userDetails.getEmail(),
				userInfo.getPhone(),
				roles)));
	}

	@Transactional
	@Override
	public ResponseEntity<?> register(SignupRequest signUpRequest) {
		if(StringUtils.isBlank(signUpRequest.getPassword())) {
			throw new BadRequestException(Constant.ERR_PASSWORD_IS_REQUIRED);
		}
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new BadRequestException(Constant.ERR_USERNAME_EXISTED);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new BadRequestException(Constant.ERR_EMAIL_EXISTED);
		}

		if (userRepository.existsByPhone(signUpRequest.getPhone())) {
			throw new BadRequestException(Constant.ERR_PHONE_EXISTED);
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				signUpRequest.getPhone(),
				signUpRequest.getAvatar(),
				passwordEncoder.encode(signUpRequest.getPassword()),
				signUpRequest.getGender(),
				signUpRequest.getDate_of_birth(),
				signUpRequest.getAddress(),
				signUpRequest.getFullname()
		);
		user.setStatus(ACTIVE_STATUS.ACTIVE);
		Set<ROLE> requestRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (requestRoles == null) {
			Role userRole = roleRepository.findByName(ROLE.ROLE_USER)
					.orElseThrow(() -> new BadRequestException(Constant.ERR_ROLE_NOT_FOUND));
			roles.add(userRole);
		} else {
			requestRoles.forEach(role -> {
				switch (role) {
					case ROLE_ADMIN:
						Role adminRole = roleRepository.findByName(ROLE.ROLE_ADMIN)
								.orElseThrow(() -> new BadRequestException(Constant.ERR_ROLE_NOT_FOUND));
						roles.add(adminRole);


						break;
					case ROLE_MODERATOR:
						Role modRole = roleRepository.findByName(ROLE.ROLE_MODERATOR)
								.orElseThrow(() -> new BadRequestException(Constant.ERR_ROLE_NOT_FOUND));
						roles.add(modRole);

						break;


					case ROLE_DOCTOR:
						Role docRole = roleRepository.findByName(ROLE.ROLE_DOCTOR)
								.orElseThrow(() -> new BadRequestException(Constant.ERR_ROLE_NOT_FOUND));
						roles.add(docRole);

						break;


					case ROLE_HOSPITAL:
						Role hospitalRole = roleRepository.findByName(ROLE.ROLE_HOSPITAL)
								.orElseThrow(() -> new BadRequestException(Constant.ERR_ROLE_NOT_FOUND));
						roles.add(hospitalRole);

						break;


					default:
						Role userRole = roleRepository.findByName(ROLE.ROLE_USER)
								.orElseThrow(() -> new BadRequestException(Constant.ERR_ROLE_NOT_FOUND));
						roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse(Constant.SUCCESS));
	}

	@Override
	public ResponseEntity<?> refreshToken(TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByToken(requestRefreshToken)
				.map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser)
				.map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getUsername());
					return ResponseEntity.ok(new MessageResponse(Constant.SUCCESS, new TokenRefreshResponse(token, requestRefreshToken)));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
						"Refresh token is not in database!"));
	}

	@Override
	public ResponseEntity<?> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws Exception {
		Optional<User> userOpt = userRepository.findByEmail(forgotPasswordRequest.getEmail());
		if(!userOpt.isPresent()) {
			throw new BadRequestException(Constant.ERR_EMAIL_NOT_EXISTED);
		}
		User user = userOpt.get();
		String code = RandomStringUtils.randomAlphanumeric(20);
		List<ResetPassHistory> resetPassHistories = resetPassHistoryRepository.findByUserId(user.getId());
		if (!CollectionUtils.isEmpty(resetPassHistories)) {
			resetPassHistories.parallelStream().forEach(e -> {
				e.setStatus(ACTIVE_STATUS.INACTIVE);
			});
			resetPassHistoryRepository.saveAll(resetPassHistories);
		}
		resetPassHistoryRepository.save(new ResetPassHistory(user.getId(), code));
		String sendMailResult = this.sendForgotPasswordMail(user.getUsername(), user.getEmail(), code);
		return ResponseEntity.ok(new MessageResponse<>(sendMailResult));
	}

	@Transactional
	@Override
	public ResponseEntity<?> setNewPassword(NewPasswordRequest newPasswordRequest) {
		Optional<ResetPassHistory> resetPassHistoryOpt = resetPassHistoryRepository.findOneByActiveCodeAndStatus(newPasswordRequest.getCode(), ACTIVE_STATUS.ACTIVE);
		if(!resetPassHistoryOpt.isPresent()) {
			throw new BadRequestException(Constant.ERR_INVALID_RESET_PASS_HISTORY);
		}
		ResetPassHistory resetPassHistory = resetPassHistoryOpt.get();
		resetPassHistory.setStatus(ACTIVE_STATUS.INACTIVE);
		Optional<User> userOpt = userRepository.findById(resetPassHistory.getUserId());
		if(!userOpt.isPresent()) {
			throw new BadRequestException(Constant.ERR_USER_NOT_FOUND);
		}
		User user = userOpt.get();
		user.setPassword(passwordEncoder.encode(newPasswordRequest.getPassword()));
		userRepository.save(user);
		resetPassHistoryRepository.save(resetPassHistory);
		return ResponseEntity.ok(new MessageResponse(Constant.SUCCESS));
	}


	@Override
	public ResponseEntity<?> getAllUser(PagingRequest request) {
		Sort sort;
		List<Sort.Order> orderList = CommonUtil.parseOrders(request.getSort());
		if(CollectionUtils.isEmpty(orderList)) {
			sort = Sort.by(Sort.Direction.DESC, "createdAt");
		} else {
			sort = Sort.by(orderList);
		}
		Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);
		Page<User> users;
		if(StringUtils.isNotBlank(request.getSearchText())) {
			users = userRepository.findAllByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCase(
					request.getSearchText(), request.getSearchText(), request.getSearchText(), pageable);
		} else {
			users = userRepository.findAll(pageable);
		}
		if(users.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse(Constant.SUCCESS, Collections.EMPTY_LIST));
		}
		List<UserResponse> userResponseList = users.getContent().stream().map(e -> {
			UserResponse dto = new UserResponse();
			BeanUtils.copyProperties(e, dto);
			dto.setCreatedAt(e.getCreatedAt() != null ? e.getCreatedAt().toEpochMilli() : 0L);
			if(!CollectionUtils.isEmpty(e.getRoles())) {
				dto.setRoles(e.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
			}
			return dto;
		}).collect(Collectors.toList());
		return ResponseEntity.ok(new MessageResponse(Constant.SUCCESS, new PageImpl<>(userResponseList, pageable, userResponseList.size())));
	}

	@Transactional
	@Override
	public ResponseEntity<?> updateStatus(Long userId, ACTIVE_STATUS status) {
		User user = userRepository.findById(userId).orElseThrow(() -> new BadRequestException(Constant.ERR_USER_NOT_FOUND));
		user.setStatus(status);
		User savedUser = userRepository.save(user);
		UserResponse dto = new UserResponse();
		BeanUtils.copyProperties(savedUser, dto);
		if(!CollectionUtils.isEmpty(savedUser.getRoles())) {
			dto.setRoles(savedUser.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
		}
		return ResponseEntity.ok(new MessageResponse(Constant.SUCCESS, dto));
	}

	@Override
	public ResponseEntity<?> resetPass(Long userId) throws Exception {
		User user = userRepository.findById(userId).orElseThrow(() -> new BadRequestException(Constant.ERR_USER_NOT_FOUND));
		String newPass = CommonUtil.generateCode(2, 6);
		user.setPassword(passwordEncoder.encode(newPass));
		userRepository.save(user);
		String sendMailResult = this.sendResetPasswordMail(user.getUsername(), user.getEmail(), newPass);
		return ResponseEntity.ok(new MessageResponse(sendMailResult));
	}

	@Transactional
	@Override
	public ResponseEntity<?> updatePassword(String oldPass, String newPass) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User user = userRepository.findByUsernameAndStatus(userDetails.getUsername(), ACTIVE_STATUS.ACTIVE)
				.orElseThrow(() -> new BadRequestException(Constant.ERR_USER_NOT_FOUND));
		if(!passwordEncoder.matches(oldPass, user.getPassword())) {
			throw new BadRequestException(Constant.ERR_INVALID_CURRENT_PASSWORD);
		}
		user.setPassword(passwordEncoder.encode(newPass));
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse(Constant.SUCCESS));
	}

	@Override
	public ResponseEntity<?> getUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User user = userRepository.findByUsernameAndStatus(userDetails.getUsername(), ACTIVE_STATUS.ACTIVE)
				.orElseThrow(() -> new BadRequestException(Constant.ERR_USER_NOT_FOUND));
		UserResponse dto = new UserResponse();
		BeanUtils.copyProperties(user, dto);
		if(!CollectionUtils.isEmpty(user.getRoles())) {
			dto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
		}
		dto.setPhoneNationalNumber(dto.getPhone());
		String[] data = CommonUtil.parserE164Number(user.getPhone());
		if(data != null && data.length == 2) {
			dto.setPhoneCountryCode(data[0]);
			dto.setPhoneNationalNumber(data[1]);
		}
		return ResponseEntity.ok(new MessageResponse(Constant.SUCCESS, dto));
	}

	@Transactional
	@Override
	public ResponseEntity<?> update(UpdateUserRequest request) {
		User user = userRepository.findById(request.getId()).orElseThrow(() -> new BadRequestException(Constant.ERR_USER_NOT_FOUND));

		if (userRepository.existsByUsernameAndIdNot(request.getUsername(), request.getId())) {
			throw new BadRequestException(Constant.ERR_USERNAME_EXISTED);
		}

		if (userRepository.existsByEmailAndIdNot(request.getEmail(), request.getId())) {
			throw new BadRequestException(Constant.ERR_EMAIL_EXISTED);
		}

		if (userRepository.existsByPhoneAndIdNot(request.getPhone(), request.getId())) {
			throw new BadRequestException(Constant.ERR_PHONE_EXISTED);
		}
		// Update user's account
		user.setPhone(request.getPhone());
		user.setEmail(request.getEmail());
		user.setUsername(request.getUsername());
		User updatedUser = userRepository.save(user);
		UserResponse dto = new UserResponse();
		BeanUtils.copyProperties(updatedUser, dto);
		if(!CollectionUtils.isEmpty(user.getRoles())) {
			dto.setRoles(updatedUser.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
		}
		return ResponseEntity.ok(new MessageResponse(Constant.SUCCESS, dto));
	}

	@Override
	public List<UserOnlyReponse> findAllUsersWithUserRole() {
		List<User> user = userRepository.findAllUsersWithUserRole();
		List<UserOnlyReponse> userReponeseList = new ArrayList<>();
		for(User item: user){
			UserOnlyReponse userOnlyReponse = new UserOnlyReponse();
			userOnlyReponse.setId(item.getId());
			userOnlyReponse.setUsername(item.getUsername());
			userOnlyReponse.setPhone(item.getPhone());
			userOnlyReponse.setDate_of_birth(item.getDate_of_birth());
			userOnlyReponse.setGender(item.getGender());
			userOnlyReponse.setEmail(item.getEmail());
			userOnlyReponse.setStatus(item.getStatus());
			userOnlyReponse.setCreatedAt(item.getCreatedAt());

			userReponeseList.add(userOnlyReponse);
		}

		return userReponeseList;
	}

	@Override
	public List<User> findByUsernameContainingOrEmailContainingOrPhoneContaining(String name) {
		return userRepository.findByUsernameContainingOrEmailContainingOrPhoneContaining(name);
	}

	private String sendForgotPasswordMail(String name, String email, String code) throws Exception {
			log.info("sendForgotPasswordMail name=[{}] email=[{}] code=[{}]",
					name, email, code);

			String generateLink = domainUrl + "/auth/new-password?code=" + code;
			String headerContent = "Forgot your password?";
			Map<String, String> map = new HashMap<>();
			map.put("generateLink", generateLink);
			map.put("userName", name);
			map.put("emailAddress", email);
			map.put("header", headerContent);
			String mailSubject = "Change Password Request";
			return mailService.sendEmail(email, adminEmail, mailSubject, map, "forgotPassword.vm", null);
	}

	private String sendResetPasswordMail(String name, String email, String newPassword) throws Exception {
		log.info("sendResetPasswordMail name=[{}] email=[{}]",
				name, email);

		String headerContent = "Reset your password";
		Map<String, String> map = new HashMap<>();
		map.put("newPassword", newPassword);
		map.put("userName", name);
		map.put("emailAddress", email);
		map.put("header", headerContent);
		String mailSubject = "Reset Password Request";
		return mailService.sendEmail(email, adminEmail, mailSubject, map, "resetPassword.vm", null);
	}

}
