package main.java.service;

import hockey.iceberg.domain.model.UserAccount;
import hockey.iceberg.domain.repo.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserAccountService implements UserDetailsService {

    private static final int minLoginLength = 4;

    private static final int maxLoginLength = 30;

    private PasswordEncoder passwordEncoder;

    private UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        if (Objects.isNull(userAccount)) throw new UsernameNotFoundException("Can't find username: " + username);
        return userAccount;
    }

    public Iterable<UserAccount> findAll() {
        return userAccountRepository.findAll();
    }

    public UserAccount save(UserAccount userAccount) {
        String regexp = String.format("^\\w{%d,%d}$", minLoginLength, maxLoginLength);
        if (!userAccount.getUsername().matches(regexp)) throw new IllegalArgumentException("Not valid login");
        UserAccount found = userAccountRepository.findByUsername(userAccount.getUsername());
        if (!Objects.isNull(found)) throw new IllegalArgumentException("Login already exists");
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        return userAccountRepository.save(userAccount);
    }

    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

}
