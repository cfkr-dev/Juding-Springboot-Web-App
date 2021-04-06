package es.dawgroup2.juding.posts;

import es.dawgroup2.juding.users.UserService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@Service
public class PostService {

    @Autowired
    UserService userService;
    @Autowired
    private PostRepository postRepository;

    /**
     * This service method search on post database the current post given by id and deletes it.
     *
     * @param id Current post id.
     */
    public void deleteById(String id) {
        postRepository.deleteById(Integer.parseInt(id));
    }

    /**
     * This service method search a post given by id on data base and return an instance of this.
     *
     * @param id Current post id.
     * @return an instance of the searched post.
     */
    public Post findById(String id) {
        return postRepository.findById(Integer.parseInt(id)).orElse(null);
    }

    /**
     * This method finds all the post saved on database and return a list with all the database posts instances
     *
     * @return a list with all post instances.
     */
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    /**
     * Returns the list with all posts ordering them by descendant timestamp.
     *
     * @return a list with all post instances in the specific order.
     */
    public List<Post> findAllDesc() {
        return postRepository.findAllByOrderByTimestampDesc();
    }

    /**
     * Returns a list with the 5 most recent news. If the news in paramter is included, it gets automatically excluded.
     *
     * @param idPost Post to exclude
     * @return List with posts
     */
    public List<Post> findFirst5Desc(String idPost) {
        return postRepository.findFirst5ByIdPostNotOrderByTimestampDesc(Integer.parseInt(idPost));
    }

    /**
     * Returns a page of posts ordering them by descendant timestamp.
     *
     * @param num   Number of page
     * @param items Number of items per page
     * @return A page of posts
     */
    public Page<Post> getPostsInPages(int num, int items) {
        return postRepository.findAllByOrderByTimestampDesc(PageRequest.of(num, items));
    }

    /**
     * @param idPost
     * @param request
     * @param title
     * @param body
     * @param image
     * @return
     */
    public Post save(String idPost, HttpServletRequest request, String title, String body, MultipartFile image) {
        Post post;
        if (idPost == null)
            post = new Post();
        else
            try {
                post = postRepository.findById(Integer.parseInt(idPost)).orElse(new Post());
            } catch (Exception e) {
                return null;
            }
        post.setAuthor(userService.findByNickname(request.getUserPrincipal().getName()))
                .setTitle(title)
                .setBody(body)
                .setTimestamp(new Timestamp(System.currentTimeMillis()));
        if (image != null) {
            // Image should be only changed if another is sent; if not, keep previous one
            if (!image.isEmpty()) {
                try {
                    post.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
                } catch (Exception e) {
                    return null;
                }
                post.setMimeImage(image.getContentType());
            }
        }
        return postRepository.save(post);
    }

    /**
     * This method saves new posts on database.
     *
     * @param post New posts instances to add.
     * @return List of saved posts.
     */
    public List<Post> addAll(List<Post> post) {
        return postRepository.saveAll(post);
    }
}

