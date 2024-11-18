package dev.fpsaraiva.ecommerce_api.service;

import dev.fpsaraiva.ecommerce_api.dto.UserDto;
import dev.fpsaraiva.ecommerce_api.model.User;
import dev.fpsaraiva.ecommerce_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    public UserDto save(UserDto userDto) {
        User user = userRepository.save(User.toModel(userDto));
        return UserDto.toDto(user);
    }

    public UserDto delete(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            userRepository.delete(user.get());
        }
        return null;
    }

    public List<UserDto> queryByName(String name) {
        List<User> users = userRepository.queryByNameLike(name);
        return users
                .stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }
}
