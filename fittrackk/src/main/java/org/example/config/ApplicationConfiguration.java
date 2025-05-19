package org.example.config;

import org.example.model.Admin;
import org.example.model.Client;
import org.example.model.Dietitian;
import org.example.repository.AdminRepository;
import org.example.repository.ClientRepository;
import org.example.repository.DietitianRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class ApplicationConfiguration {

    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final DietitianRepository dietitianRepository;

    public ApplicationConfiguration(AdminRepository adminRepository,
                                    ClientRepository clientRepository,
                                    DietitianRepository dietitianRepository) {
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.dietitianRepository = dietitianRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            Optional<Admin> adminOpt = adminRepository.findByEmail(email);
            if (adminOpt.isPresent()) {
                return adminOpt.get();
            }

            Optional<Client> clientOpt = Optional.ofNullable(clientRepository.findByEmail(email));
            if (clientOpt.isPresent()) {
                return clientOpt.get();
            }

            Optional<Dietitian> dietitianOpt = dietitianRepository.findByEmail(email);
            if (dietitianOpt.isPresent()) {
                return dietitianOpt.get();
            }

            throw new UsernameNotFoundException("User not found with email: " + email);
        };
    }



    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
