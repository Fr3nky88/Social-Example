package it.develhope.social.repository;

import it.develhope.social.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo  extends JpaRepository<Post, Long> {

    @Query(value = "INSERT INTO post (contenuto, titolo, user_id) VALUES (:contenuto, :titolo, :user_id)", nativeQuery = true)
    Integer savePost(@Param("contenuto") String contenuto, @Param("titolo") String titolo, @Param("user_id") Long user_id);
}
