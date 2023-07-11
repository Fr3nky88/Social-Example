package it.develhope.social.services;

import it.develhope.social.entities.User;
import it.develhope.social.exceptions.UserException;
import it.develhope.social.model.PostDto;
import it.develhope.social.model.UserDto;
import it.develhope.social.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public UserService() {

    }

    public List<UserDto> getAll() {
        List<User> users = userRepo.findAll();
//        return users.stream().map(u -> modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
        return users.stream().map(u -> {
            UserDto dto = new UserDto();
            dto.setId(u.getId());
            dto.setNome(u.getNome());
            dto.setCognome(u.getCognome());
            dto.setEmail(u.getEmail());
            dto.setPosts(u.getPosts().stream().map(p -> {
                PostDto pdto = new PostDto();
                pdto.setId(p.getId());
                pdto.setTitolo(p.getTitolo());
                pdto.setContenuto(p.getContenuto());
                return pdto;
            }).collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
    }

    public void insert(UserDto user) throws UserException {
        if (user.getId() != null) {
            throw new UserException("Errore: lo user non può essere inserito");
        }
        userRepo.save(modelMapper.map(user, User.class));
    }
    public void update(UserDto user) throws Exception {
        if(user.getId() == null && !userRepo.existsById(user.getId())) {
            throw new UserException("Errore: lo user non può essere aggiornato");
        }
        userRepo.save(modelMapper.map(user, User.class));
    }

    public void insertBulk(List<UserDto> users) throws Exception {
        List<UserDto> userError = users.stream().filter(u -> u.getId() != null).toList();
        if (userError.size() > 0) {
            throw new UserException("Errore: lo user non può essere inserito");
        }
        List<User> usersList = users.stream().map(u -> modelMapper.map(u, User.class)).collect(Collectors.toList());
        userRepo.saveAll(usersList);
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
    }



    public List<User> findByNome(String nome) {
        return userRepo.findByNome(nome);
    }

    public List<UserDto> findByCognome(String cognome) {
        List<User> users = userRepo.findByCognomeOrderByIdAsc(cognome);
        return users.stream().map(u -> modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
    }

    public List<UserDto> findByNomeAndByCognome(String nome, String cognome) {
        List<User> users = userRepo.findByNomeAndCognomeOrderByIdAsc(nome, cognome);
        return users.stream().map(u -> modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
    }

    public List<UserDto> findContainsLetter(String letter) {
        List<User> users = userRepo.findContainsLetter(letter);
        return users.stream().map(u -> modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
    }

    public List<UserDto> findByEmail(String email) {
        Optional<List<User>> userOpt = userRepo.findByEmailContainingIgnoreCase(email);
        if (userOpt.isEmpty()) {
            return null;
        }
        List<User> users = userOpt.get();
        return users.stream().map(u -> modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
    }
}
