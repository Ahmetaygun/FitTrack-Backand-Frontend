package org.example.service;

import org.example.model.Admin;
import org.example.model.Client;
import org.example.model.Dietitian;
import org.example.repository.AdminRepository;
import org.example.repository.ClientRepository;
import org.example.repository.DietitianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final DietitianRepository dietitianRepository;

    @Autowired
    public UserDetailsServiceImpl(AdminRepository adminRepository,
                                  ClientRepository clientRepository,
                                  DietitianRepository dietitianRepository) {
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.dietitianRepository = dietitianRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Önce Admin kontrolü
        Admin admin = adminRepository.findByEmail(email).orElse(null);
        if (admin != null) {
            return admin;
        }

        //Sonra Client kontrolü
        Client client = clientRepository.findByEmail(email);
        if (client != null) {
            return client;
        }

        //Son olarak Dietitian kontrolü
        Dietitian dietitian = dietitianRepository.findByEmail(email).orElse(null);
        if (dietitian != null) {
            return dietitian;
        }

        //Hiçbiri bulunamadıysa hata fırlat
        throw new UsernameNotFoundException("Kullanıcı bulunamadı: " + email);
    }
}
