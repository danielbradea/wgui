package com.bid90.wgui.services.impl;

import com.bid90.wgui.exceptions.CustomException;
import com.bid90.wgui.models.User;
import com.bid90.wgui.models.dto.AddUserDTO;
import com.bid90.wgui.models.dto.UpdateUserDTO;
import com.bid90.wgui.models.dto.UserDTO;
import com.bid90.wgui.repositorys.UserRepository;
import com.bid90.wgui.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for managing users.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new instance of UserServiceImpl.
     *
     * @param userRepository  The user repository.
     * @param passwordEncoder The password encoder.
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Converts a User entity to a UserDTO.
     *
     * @param user The User entity to convert.
     * @return The converted UserDTO.
     */
    private UserDTO toDto(User user) {
        var userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setEnabled(user.getEnabled());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }

    /**
     * Retrieves all users.
     *
     * @return A list of UserDTO representing all users.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return An Optional containing the user if found, or an empty Optional if not found.
     */
    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    /**
     * Saves a user.
     *
     * @param user The user to save.
     * @return The saved user.
     */
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Deletes a user.
     *
     * @param user The user to delete.
     */
    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    /**
     * Retrieves a user by email.
     *
     * @param email The email of the user to retrieve.
     * @return An Optional containing the user if found, or an empty Optional if not found.
     */
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    /**
     * Adds a new user.
     *
     * @param userDTO The DTO containing the user details to add.
     * @return The added user.
     */
    @Override
    public User addUser(AddUserDTO userDTO) {
        var newUser = new User();
        newUser.setName(userDTO.getName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setRole(userDTO.getRole());
        newUser.setEnabled(userDTO.getEnabled());
        return saveUser(newUser);
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return The authenticated user.
     * @throws CustomException If the user is not authenticated.
     */
    @Override
    public User getAutenticatedUser() {
        try {
            var email = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.getUserByEmail(email).orElseThrow();
        } catch (Exception e) {
            throw new CustomException("Forbidden");
        }
    }

    /**
     * Updates a user.
     *
     * @param user          The user to update.
     * @param updateUserDTO The DTO containing the updated user details.
     * @return The updated user.
     */
    @Override
    public User updateUser(User user, UpdateUserDTO updateUserDTO) {
        Optional.ofNullable(updateUserDTO.getEmail()).filter(s -> !s.isBlank()).ifPresent(user::setEmail);
        Optional.ofNullable(updateUserDTO.getName()).filter(s -> !s.isBlank()).ifPresent(user::setName);
        Optional.ofNullable(updateUserDTO.getPassword()).filter(s -> !s.isBlank())
                .ifPresent(s -> user.setPassword(passwordEncoder.encode(s)));
        Optional.ofNullable(updateUserDTO.getRole()).ifPresent(user::setRole);
        Optional.ofNullable(updateUserDTO.getEnabled()).ifPresent(user::setEnabled);
        return userRepository.save(user);
    }
}
