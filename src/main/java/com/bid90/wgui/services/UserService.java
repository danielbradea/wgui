package com.bid90.wgui.services;

import com.bid90.wgui.models.User;
import com.bid90.wgui.models.dto.AddUserDTO;
import com.bid90.wgui.models.dto.UpdateUserDTO;
import com.bid90.wgui.models.dto.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserService {
    List<UserDTO> getAllUsers();
    Optional<User> getUserById(UUID id);
    User saveUser(User user);
    void deleteUser(User user);
    Optional<User> getUserByEmail(String email);
    User addUser(AddUserDTO userDTO);
    User getAutenticatedUser();
    User updateUser(User user, UpdateUserDTO updateUserDTO);
}
