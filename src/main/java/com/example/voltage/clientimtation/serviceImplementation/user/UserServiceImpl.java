package com.example.voltage.clientimtation.serviceImplementation.user;

import com.example.voltage.clientimtation.dto.request.LoginRequest;
import com.example.voltage.clientimtation.dto.request.RegisterRequest;
import com.example.voltage.clientimtation.dto.response.JwtResponse;
import com.example.voltage.clientimtation.exception.RoleNotFoundException;
import com.example.voltage.clientimtation.exception.UserAlreadyExistsException;
import com.example.voltage.clientimtation.jwt.JwtUtils;
import com.example.voltage.clientimtation.model.ERole;
import com.example.voltage.clientimtation.model.Role;
import com.example.voltage.clientimtation.model.User;
import com.example.voltage.clientimtation.repository.RoleRepository;
import com.example.voltage.clientimtation.repository.UserRepository;
import com.example.voltage.clientimtation.service.JwtService.UserDetailsImpl;
import com.example.voltage.clientimtation.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final  RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication  = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(
                userDetails.getId(),
                jwt,
                userDetails.getUsername(),
                userDetails.getPhoneNumber(),
                userDetails.getEmail(),
                roles));
    }
    @Override
    public ResponseEntity<?> registerUser(RegisterRequest registerRequest)  {
        if((userNameExists(registerRequest.getUserName()))){
            throw new UserAlreadyExistsException("There is an account with that UserName " + registerRequest.getUserName());
        }
        if(emailExists(registerRequest.getEmail())){
            throw new UserAlreadyExistsException("There is an account with that email " + registerRequest.getEmail());
        }

        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null || strRoles.isEmpty()){
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(()->new RoleNotFoundException("Role is not found "));
            roles.add(userRole);
        }else{
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RoleNotFoundException("Role is not found"));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RoleNotFoundException("Role is not found"));
                    roles.add(userRole);
                }
            });
        }

        User user = new User();
        user.setUserName(registerRequest.getUserName());
        user.setEmail(registerRequest.getEmail());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(roles);

        User saveUser =  userRepository.save(user);
        return ResponseEntity.ok(saveUser);
    }

    private boolean userNameExists(final String userName) {
        return userRepository.existsByUserName(userName) != null;
    }
    private boolean emailExists(final String email) {
        return userRepository.existsByEmail(email) != null;
    }
}
