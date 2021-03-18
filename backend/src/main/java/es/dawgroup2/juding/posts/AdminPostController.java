package es.dawgroup2.juding.posts;

import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminPostController {

    @Autowired
    PostService postService;

    /**
     * This method inflates the individual post (shown by Id) visualization view.
     * A list with other post is also shown.
     * @param model Post data model.
     * @param id Current post id.
     * @return Individual post visualization view (news).
     */
    @GetMapping("/news/{id}")
    public String NewsPost(Model model, @PathVariable String id) {
        Post post = postService.findById(id);
        List<Post> postList = postService.findAll();
        postList.remove(post);
        model.addAttribute("post", post);
        model.addAttribute("postList", postList);
        return "/news";
    }
}
