package com.godcoder.myhome.service;

import com.godcoder.myhome.model.Role;
import com.godcoder.myhome.model.User;
import com.godcoder.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        // 여기서 그냥 저장하면 username과 password 만 저장된다.
        // 우리는 password도 암호화해야하고, enabled 값도 setting 하고, 권한도 넣어줘야 한다. (service에서 처리)
        // 현재 이 방식은 단방향 암호화이다.
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);

        // DB에 있는 ROLE_USER 검색해서 가져오면 좋지만, 또 Service만들어야 하니까, 여기서는 그냥 하드코딩으로 했음
        Role role = new Role();
        role.setId(1l);
        user.getRoles().add(role);
        //--→ 이 상태로 저장하면, user_role 테이블에 role_id가 같이 저장될 것이다.

        return userRepository.save(user);
    }
}
