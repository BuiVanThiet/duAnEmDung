package org.example.baiemdung.implement;

import org.example.baiemdung.repositores.UserRepository;
import org.example.baiemdung.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserImplement implements UserService {
    @Autowired
    UserRepository userRepository;

}
