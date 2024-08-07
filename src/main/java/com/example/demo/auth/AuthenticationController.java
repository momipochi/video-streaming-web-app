package com.example.demo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.ApiResponse;
import com.example.demo.constants.AuthApiConstants;

@RestController
@RequestMapping(AuthApiConstants.REQUEST_MAPPING_V1)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping(AuthApiConstants.REGISTER)
    public ResponseEntity<ApiResponse<AuthenticationResponse>> register(@RequestBody RegisterRequest req) {
        try {
            return ResponseEntity.ok(new ApiResponse<AuthenticationResponse>(true, authService.register(req), null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest()
                .body(new ApiResponse<AuthenticationResponse>(false, null, "User already exists"));
    }

    @PostMapping(AuthApiConstants.AUTHENTICATE)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest req) {
        return ResponseEntity.ok(authService.authenticate(req));
    }

}
