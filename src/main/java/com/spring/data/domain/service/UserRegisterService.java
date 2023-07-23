package com.spring.data.domain.service;

import com.spring.data.domain.exception.BusinessException;
import com.spring.data.domain.exception.UserNotFoundException;
import com.spring.data.domain.model.Party;
import com.spring.data.domain.model.User;
import com.spring.data.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserRegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartyRegisterService partyRegisterService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {
        userRepository.detach(user);

        Optional<User> existentUser = userRepository.findByEmail(user.getEmail());

        if (existentUser.isPresent() && !existentUser.get().equals(user)) {
            throw new BusinessException(
                    String.format("User with email: %s already exists", user.getEmail())
            );
        }

        if (user.isNew()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Transactional
    public void alterPassword(Long userId, String currentPassword, String newPassword) {
        User user = searchOrFail(userId);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BusinessException("Password is different");
        }
        user.setPassword(currentPassword);
    }

    @Transactional
    public void dissociateParty(Long userId, Long partyId) {
        User user = searchOrFail(userId);
        Party party = partyRegisterService.searchOrFail(partyId);

        user.addParty(party);
    }

    @Transactional
    public void associateParty(Long userId, Long partyId) {
        User user = searchOrFail(userId);
        Party party = partyRegisterService.searchOrFail(partyId);

        user.addParty(party);
    }

    public User searchOrFail(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
