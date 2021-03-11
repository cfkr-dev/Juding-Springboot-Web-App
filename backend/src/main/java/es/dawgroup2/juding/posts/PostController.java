package es.dawgroup2.juding.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/admin/post/list")
    public String postList(Model model){
        List<Post> postList = postRepository.findAll();
        model.addAttribute("postList", postList);
        return "/admin/post/list";
    }

    @GetMapping("/admin/post/edit/{id}")
    public String postEdit(Model model, @PathVariable String id){
        Post post = postRepository.findById(Integer.parseInt(id)).orElseThrow();
        model.addAttribute("post", post);
        return "/admin/post/edit";
    }

    @GetMapping("/admin/post/edit/image/{id}")
    public ResponseEntity<Object> downloadImage(@PathVariable String id)
            throws SQLException {
        Post post = postRepository.findById(Integer.parseInt(id)).orElseThrow();
        if (post.getImageFile() != null) {
            Resource file = new InputStreamResource(
                    post.getImageFile().getBinaryStream());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(post.getImageFile().length())
                    .body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
