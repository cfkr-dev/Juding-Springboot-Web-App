package es.dawgroup2.juding.posts;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/admin/post/list")
    public String postList(Model model) {
        List<Post> postList = postService.findAll();
        model.addAttribute("postList", postList);
        return "/admin/post/list";
    }

    @GetMapping("/index")
    public String indexPosts(Model model) {
        List<Post> postList = postService.findAll();
        model.addAttribute("postList", postList);
        return "/index";
    }

    @GetMapping("/news/{id}")
    public String NewsPost(Model model, @PathVariable String id) {
        Post post = postService.findById(id);
        List<Post> postList = postService.findAll();
        postList.remove(post);
        model.addAttribute("post", post);
        model.addAttribute("postList", postList);
        return "/news";
    }

    @GetMapping("/admin/post/list/{id}")
    public String postModal(Model model, @PathVariable String id) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "/admin/post/list";
    }

    @GetMapping("/admin/post/edit/{id}")
    public String postEdit(Model model, @PathVariable String id) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "/admin/post/edit";
    }

    @GetMapping("/admin/post/createNew")
    public String newPost(Model model) {
        return "/admin/post/createNew";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<Object> downloadImage(@PathVariable String id)
            throws SQLException {
        Post post = postService.findById(id);
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

    @PostMapping("/admin/post/edit/modify")
    public String updatingPost(@RequestParam String id, @RequestParam String title, @RequestParam MultipartFile image, @RequestParam String body) throws IOException, SQLException {
        Post post = postService.findById(id);
        post.setTitle(title);
        post.setBody(body);
        post.setTimestamp(new Timestamp(System.currentTimeMillis()));
        if (!image.isEmpty()) {
            post.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
        } else {
            Post dbpost = postService.findById(id);
            if (dbpost.getImageFile() != null) {
                post.setImageFile(BlobProxy.generateProxy(dbpost.getImageFile().getBinaryStream(),
                        dbpost.getImageFile().length()));
            }
        }
        postService.updatingInfoPost(post);
        return "redirect:/admin/post/list";
    }

    @PostMapping("/admin/post/createNew")
    public String addNewPost(@RequestParam String title, @RequestParam MultipartFile image, @RequestParam String body) throws IOException, SQLException {
        Post post = new Post();
        post.setAuthor("1234567890"); //How to reference this?
        post.setTitle(title);
        post.setBody(body);
        post.setTimestamp(new Timestamp(System.currentTimeMillis()));
        if (!image.isEmpty()) {
            post.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
        }
        postService.add(post);
        return "redirect:/admin/post/list";
    }

    @GetMapping("/admin/post/delete/{id}")
    public String deletePost(@PathVariable String id) {
        postService.deleteById(id);
        return "redirect:/admin/post/list";
    }
}
