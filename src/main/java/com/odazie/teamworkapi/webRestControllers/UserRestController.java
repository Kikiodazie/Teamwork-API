package com.odazie.teamworkapi.webRestControllers;

import com.odazie.teamworkapi.business.model.AuthToken;
import com.odazie.teamworkapi.business.service.UserService;
import com.odazie.teamworkapi.data.entity.User;
import com.odazie.teamworkapi.data.repository.JobRolesRepository;
import com.odazie.teamworkapi.securityConfig.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    private final UserService userService;
    private final JobRolesRepository jobRolesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    public UserRestController(UserService userService, JobRolesRepository jobRolesRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.jobRolesRepository = jobRolesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/auth/create-user")
    public ResponseEntity<Void> userSignUp(@RequestBody User user){
        if (userService.getUserRepository().findUserByEmail(user.getEmail()) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }


    @PostMapping("/auth/signin")
    public ResponseEntity<?> loginUser(@RequestBody User currentUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        currentUser.getEmail(),
                        currentUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }



}
