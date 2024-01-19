package com.example.newsservicesecurity.service;

import com.example.newsservicesecurity.api.v1.request.UserRq;
import com.example.newsservicesecurity.api.v1.response.UserRs;
import com.example.newsservicesecurity.aspects.GetUserIdAop;
import com.example.newsservicesecurity.entity.NewsEntity;
import com.example.newsservicesecurity.entity.Role;
import com.example.newsservicesecurity.entity.RoleType;
import com.example.newsservicesecurity.entity.User;
import com.example.newsservicesecurity.exception.AccessDeniedException;
import com.example.newsservicesecurity.exception.EntityNotFoundException;
import com.example.newsservicesecurity.mappers.UserMapper;
import com.example.newsservicesecurity.repository.CommentNewsRepository;
import com.example.newsservicesecurity.repository.NewsRepository;
import com.example.newsservicesecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final CommentNewsRepository commentNewsRepository;

    private final NewsRepository newsRepository;


    public User findByUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Username not found! " + username));
    }

    public List<UserRs> getAllUsers(Integer offset, Integer perPage) {

        if (offset == null) offset = 0;
        if (perPage == null) perPage = 10;

        Pageable pageable = PageRequest.of(offset, perPage);
        List<User> content = userRepository.findAll(pageable).getContent();

        return UserMapper.INSTANCE.toDTO(content);
    }

    @GetUserIdAop
    public UserRs getIdUser(Long id, UserDetails userDetails) {

        Optional<User> byId = userRepository.findById(id);

        if (byId.isPresent()) {
            log.info("getIdUser: " + byId.get().getUsername() + ", " + id);
            return UserMapper.INSTANCE.toDTO(byId.get());
        }

        log.info("User not found! id=" + id);
        throw new EntityNotFoundException("User not found!" + id);
    }

    @GetUserIdAop
    public UserRs putIdUser(Long id, UserDetails userDetails, UserRq userRq) {

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!" + id));

            if (userRq.getUsername() != null) user.setUsername(userRq.getUsername());
            if (userRq.getPassword() != null) user.setPassword(passwordEncoder.encode(user.getPassword()));

            if (userRq.getRoleType() != null) {
                List<Role> rolesOld = user.getRoles();
                Role role = Role.from(userRq.getRoleType());
                user.setRoles(setRoles(rolesOld, role));
                role.setUser(user);
            }

            User user1 = userRepository.saveAndFlush(user);
            log.info("putIdUser: " + user1.getId() + ", " + userRq);

            return UserMapper.INSTANCE.toDTO(user1);
    }

    public List<Role> setRoles(List<Role> rolesOld, Role role) {

        if (!rolesOld.isEmpty()) {
            boolean flagRole = false;
            for (Role role1 : rolesOld) {
                if (role1.getAuthority().equals(role.getAuthority())) {
                    flagRole = true;
                    break;
                }
            }
            if (!flagRole) {
                rolesOld.add(role);
                return rolesOld;
            }
        } else {
            return Collections.singletonList(role);
        }
        return rolesOld;
    }

    public UserRs addUser(UserRq userRq) {

        var user = new User();
        user.setPassword(userRq.getPassword());
        user.setUsername(userRq.getUsername());
        var createUser = createNewAccount(user, Role.from(userRq.getRoleType()));

        return UserRs.builder()
                .id(createUser.getId())
                .username(createUser.getUsername())
                .password(createUser.getPassword())
                .roles(Collections.singletonList(userRq.getRoleType()))
                .build();

    }

    public User createNewAccount(User user, Role role) {

        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        role.setUser(user);

        return userRepository.saveAndFlush(user);
    }

    @GetUserIdAop
    @Transactional
    public void deleteUser(Long id, UserDetails userDetails) {

        List<NewsEntity> newsEntities = newsRepository.findAllByUserId(id);
        if (!newsEntities.isEmpty()) {
            for (NewsEntity newsEntity : newsEntities) {
                commentNewsRepository.deleteAllByNewsId((int) newsEntity.getId());
                newsRepository.deleteById(newsEntity.getId());
                log.info("delete news id " + newsEntity.getId());
            }
        }

        userRepository.deleteById(id);
        log.info("deleteUser: " + id);
    }

    public void testAccessUserGetId(Long id, UserDetails userDetails) {

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found: username " + userDetails.getUsername()));

        List<Role> roles = user.getRoles();
        boolean flagRole = false;
        for (Role role1 : roles) {
            if (role1.getAuthority().equals(RoleType.ROLE_ADMIN)
                    || role1.getAuthority().equals(RoleType.ROLE_MODERATOR)) {
                flagRole = true;
                break;
            }
        }
        if (!flagRole && !user.getId().equals(id)) {
            throw new AccessDeniedException("User who only has ROLE_USER can only get information about himself");
        }
    }

    public User getUser(String username){

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found " + username));
    }

    public void getUserId(Long id){

        userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found " + id));
    }
}
