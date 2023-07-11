package it.develhope.social.model;

import java.util.Set;

public class TagEto {

    private Long id;

    private String name;

    private Set<PostEto> posts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PostEto> getPosts() {
        return posts;
    }

    public void setPosts(Set<PostEto> posts) {
        this.posts = posts;
    }
}
