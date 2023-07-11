package it.develhope.social.services;

import it.develhope.social.entities.Post;
import it.develhope.social.entities.User;
import it.develhope.social.exceptions.PostException;
import it.develhope.social.model.PostDto;
import it.develhope.social.model.PostEto;
import it.develhope.social.model.UserDto;
import it.develhope.social.model.UserEto;
import it.develhope.social.repository.PostRepo;
import it.develhope.social.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper mapper;

    public List<Post> getAll() {
        return postRepo.findAll();
    }

    public List<PostDto> getAllDto() {
        return postRepo.findAll().stream().map( p -> {
            PostDto pd = new PostDto();
            pd.setId(p.getId());
            pd.setContenuto(p.getContenuto());
            pd.setTitolo(p.getTitolo());
            User u = p.getUser();
            UserDto ud = new UserDto();
            ud.setId(u.getId());
            ud.setCognome(u.getCognome());
            ud.setNome(u.getNome());
            ud.setEmail(u.getEmail());
            pd.setUser(ud);
            return pd;
        }).collect(Collectors.toList());
    }

    public List<PostEto> getAllEto() {
        return postRepo.findAll().stream().map( p -> {
            PostEto pd = new PostEto();
            pd.setId(p.getId());
            pd.setContenuto(p.getContenuto());
            pd.setTitolo(p.getTitolo());
            pd.setUser_id(p.getUser().getId());
//            User u = p.getUser();
//            UserEto ud = new UserEto();
//            ud.setId(u.getId());
//            ud.setCognome(u.getCognome());
//            ud.setNome(u.getNome());
//            ud.setEmail(u.getEmail());
//            pd.setUser(ud);
//            pd.setUser_id(p.getUser().getId());
            return pd;
//            return mapper.map(p, PostEto.class);
        }).collect(Collectors.toList());
    }

    public void insertPost(PostDto post) throws PostException {
        if (post.getId() != null) {
            throw new PostException("Errore: il post non può essere inserito");
        }
        postRepo.save(mapper.map(post, Post.class));
    }

    public void insertPost(PostEto post) throws PostException {
        if (post.getId() != null) {
            throw new PostException("Errore: il post non può essere inserito");
        }
//        postRepo.save(mapper.map(post, Post.class));
//        Post p = new Post();
//        p.setTitolo(post.getTitolo());
//        p.setContenuto(post.getContenuto());
//        Optional<User> userOptional = userRepo.findById(post.getUser_id());
//        if (userOptional.isEmpty()) {
//            throw new PostException("L'utente non esiste");
//        }
//        p.setUser(userOptional.get());
        postRepo.savePost(post.getContenuto(), post.getTitolo(), post.getUser_id());
    }

    public void updatePost(PostDto post) throws PostException {
        if (post.getId() == null) {
            throw new PostException("Errore: il post non può essere inserito");
        }
        postRepo.save(mapper.map(post, Post.class));
    }

    public void insertPostDto(PostDto postDto) {
//        Post post = new Post();
//        post.setTitolo(postDto.getTitolo());
//        post.setContenuto(postDto.getContenuto());
//        User u = new User();
//        u.setId(postDto.getUser().getId());
//        u.setNome(postDto.getUser().getNome());
//        u.setCognome(postDto.getUser().getCognome());
//        u.setEmail(postDto.getUser().getEmail());
//        post.setUser(u);
//        postRepo.save(post);
        Post p = mapper.map(postDto, Post.class);
        postRepo.save(p);
    }

    public void delete(Long id) {
        postRepo.deleteById(id);
    }
}
