package posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
