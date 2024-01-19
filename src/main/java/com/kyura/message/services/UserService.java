package com.kyura.message.services;

import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.models.User;
import com.kyura.message.payload.request.*;
import com.kyura.message.payload.response.UserOnlyReponse;
import com.kyura.message.payload.response.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
	ResponseEntity<?> login(LoginRequest loginRequest);
	ResponseEntity<?> register(SignupRequest signUpRequest);
	ResponseEntity<?> refreshToken(TokenRefreshRequest request);

	ResponseEntity<?> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws Exception;

	ResponseEntity<?> setNewPassword(NewPasswordRequest newPasswordRequest);

	ResponseEntity<?> getAllUser(PagingRequest request);

	ResponseEntity<?> updateStatus(Long userId, ACTIVE_STATUS status);

	ResponseEntity<?> resetPass(Long userId) throws Exception;

	ResponseEntity<?> updatePassword(String oldPass, String newPass);

	ResponseEntity<?> getUserInfo();

	ResponseEntity<?> update(UpdateUserRequest signUpRequest);

	List<UserOnlyReponse> findAllUsersWithUserRole();

	List<User> findByUsernameContainingOrEmailContainingOrPhoneContaining(String name);

}
