package it.develhope.social.services;

import it.develhope.social.entities.Post;
import it.develhope.social.entities.Tag;
import it.develhope.social.entities.User;
import it.develhope.social.exceptions.PostException;
import it.develhope.social.exceptions.UserException;
import it.develhope.social.model.*;
import it.develhope.social.repository.PostRepo;
import it.develhope.social.repository.TagRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService
{

    @Autowired
    private TagRepo tagRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<TagEto> getAll() {
//        return tagRepo.findAll().stream().map(t -> modelMapper.map(t, TagDto.class)).collect(Collectors.toList());
        return tagRepo.findAll().stream().map(t -> {
            TagEto tag = new TagEto();
            tag.setId(t.getId());
            tag.setName(t.getName());
            tag.setPosts(t.getPosts().stream().map(post -> modelMapper.map(post, PostEto.class)).collect(Collectors.toSet()));
            return tag;
        }).collect(Collectors.toList());
    }


    public Tag insert(TagInput tag) throws PostException {
//        if (user.getId() != null) {
//            throw new UserException("Errore: lo user non può essere inserito");
//        }
        Tag t = new Tag();
        t.setName(tag.getNome());
        Optional<Post> post = postRepo.findById(tag.getPostId());
        if (post.isEmpty()) {
            throw new PostException("Post non trovato");
        }
        t.setPosts(Set.of(post.get()));
        return tagRepo.save(t);
    }
}
