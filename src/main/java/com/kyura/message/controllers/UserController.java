package com.kyura.message.controllers;

import javax.validation.Valid;

import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.models.User;
import com.kyura.message.payload.request.*;
import com.kyura.message.payload.response.BaseResponse;
import com.kyura.message.payload.response.UserResponse;
import com.kyura.message.services.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
		return userService.login(loginRequest);
	}

	@GetMapping("/info")
	public ResponseEntity<?> userInfo() {
		return userService.getUserInfo();
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest) {
		return userService.register(signUpRequest);
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody UpdateUserRequest signUpRequest) {
		return userService.update(signUpRequest);
	}

	@PostMapping("/forgotPassword")
	public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) throws Exception {
		return userService.forgotPassword(forgotPasswordRequest);
	}

	@PutMapping("/newPassword")
	public ResponseEntity<?> setNewPassword(@Valid @RequestBody NewPasswordRequest newPasswordRequest) {
		return userService.setNewPassword(newPasswordRequest);
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
		return userService.refreshToken(request);
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getAllUser(PagingRequest request) {
		return userService.getAllUser(request);
	}

	@PutMapping("/status")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateStatus(@Valid @RequestBody UpdateStatusRequest request) {
		return userService.updateStatus(request.getUserId(), request.getStatus());
	}

	@GetMapping("/resetPass")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> resetPass(@RequestParam("userId") Long userId) throws Exception {
		return userService.resetPass(userId);
	}

	@PutMapping("/updatePass")
	public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
		return userService.updatePassword(request.getOldPass(), request.getPassword());
	}

	@GetMapping("/getUsersWithUserRole")
	public ResponseEntity<?> findAllUsersWithUserRole(){
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setData(userService.findAllUsersWithUserRole());
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	@GetMapping("/searchUsers")
	public List<User> searchUsers( @RequestParam String name) {
		return userService.findByUsernameContainingOrEmailContainingOrPhoneContaining(name);
	}

}
