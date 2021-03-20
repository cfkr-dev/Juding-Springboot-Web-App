package es.dawgroup2.juding.posts;

import es.dawgroup2.juding.users.UserService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class AdminPostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    /**
     * This method inflates the all post list view.
     * @param model Post data model.
     * @return All post view.
     */
    @GetMapping("/admin/post/list")
    public String postList(Model model) {
        model.addAttribute("postList", postService.findAllDesc());
        return "/admin/post/list";
    }

    /**
     * This method inflates the individual post edit section view.
     * @param model Post data model.
     * @param id Current post id.
     * @return Individual post edit section view.
     */
    @GetMapping("/admin/post/edit/{id}")
    public String postEdit(Model model, @PathVariable String id) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "/admin/post/edit";
    }

    /**
     * This method inflates the post creation view.
     * @return Post creation view.
     */
    @GetMapping("/admin/post/createNew")
    public String newPost() {
        return "/admin/post/createNew";
    }


    /*OBJECTS CREATION*/

    /**
     * This method creates a new post using the post creation form fields as parameters.
     * Image is optional. If it's not submitted, the previous image will be set.
     * Finally, the new post will be added to database by autogenerated Id.
     * @param title Post title form field.
     * @param image Post image form field.
     * @param body Post body form field.
     * @return Redirects to all post list view.
     * @throws IOException In case of the image file input fails.
     * @throws SQLException In case the previous image is not found on database.
     */
    @PostMapping("/admin/post/createNew")
    public String addNewPost(@RequestParam String title,
                             @RequestParam MultipartFile image,
                             @RequestParam String body,
                             HttpServletRequest request) throws IOException, SQLException {
        Post post = new Post();
        post.setAuthor(userService.findByNickname(request.getUserPrincipal().getName()))
                .setTitle(title)
                .setBody(body)
                .setTimestamp(new Timestamp(System.currentTimeMillis()));
        if (!image.isEmpty()) {
            post.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
            post.setMimeProfileImage(image.getContentType());
        }
        postService.add(post);
        return "redirect:/admin/post/list";
    }


    /*OBJECTS MODIFICATION*/

    /**
     * This method modifies a post (searched by Id) using the post creation form fields as parameters.
     * Image is optional. If it's not submitted, the previous image will be set.
     * Finally, the new post will be update from database.
     * @param id Current post id.
     * @param title Post title form field.
     * @param image Post image form field.
     * @param body Post body form field.
     * @return Redirects to all post list view.
     * @throws IOException In case of the image file input fails.
     * @throws SQLException In case the previous image is not found on database.
     */
    @PostMapping("/admin/post/edit/modify")
    public String updatingPost(@RequestParam String id,
                               @RequestParam String title,
                               @RequestParam MultipartFile image,
                               @RequestParam String body) throws IOException, SQLException {
        Post post = postService.findById(id);
        post.setTitle(title)
                .setBody(body)
                .setTimestamp(new Timestamp(System.currentTimeMillis()));
        if (!image.isEmpty()) {
            post.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
        } else {
            Post dbPost = postService.findById(id);
            if (dbPost.getImageFile() != null) {
                post.setImageFile(BlobProxy.generateProxy(dbPost.getImageFile().getBinaryStream(),
                        dbPost.getImageFile().length()));
                post.setMimeProfileImage(image.getContentType());
            }
        }
        postService.updatingInfoPost(post);
        return "redirect:/admin/post/list";
    }


    /*OBJECTS DELETE*/

    /**
     * This method deletes a post (search by Id) form database.
     * @param id Current post id.
     * @return Redirects to all post list view.
     */
    @GetMapping("/admin/post/delete/{id}")
    public String deletePost(@PathVariable String id) {
        postService.deleteById(id);
        return "redirect:/admin/post/list";
    }
}
