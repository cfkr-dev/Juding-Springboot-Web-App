package es.dawgroup2.juding.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByOrderByTimestampDesc();

    List<Post> findFirst5ByIdPostNotOrderByTimestampDesc(int postId);

    Page<Post> findAllByOrderByTimestampDesc(Pageable page);
}
