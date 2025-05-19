package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.AuthenticationRequest;
import org.example.dto.AuthenticationResponse;
import org.example.dto.RegisterRequest;
import org.example.model.*;
import org.example.repository.AdminRepository;
import org.example.repository.ClientRepository;
import org.example.repository.DietitianRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final DietitianRepository dietitianRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // ✅ Sadece CLIENT kayıt olabilir
    public AuthenticationResponse register(RegisterRequest request) {
        Client client = Client.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .gender(request.getGender())
                .age(request.getAge())
                .role(Role.CLIENT)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        clientRepository.save(client);
        String jwtToken = jwtService.generateToken(client);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    // ✅ Giriş yapma işlemi (tüm roller için)
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails user;

        Admin admin = adminRepository.findByEmail(request.getEmail()).orElse(null);
        if (admin != null) {
            user = admin;
        } else {
            Client client = clientRepository.findByEmail(request.getEmail());
            if (client != null) {
                user = client;
            } else {
                Dietitian dietitian = dietitianRepository.findByEmail(request.getEmail()).orElse(null);
                if (dietitian != null) {
                    user = dietitian;
                } else {
                    throw new UsernameNotFoundException("Kullanıcı bulunamadı: " + request.getEmail());
                }
            }
        }

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
