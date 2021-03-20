package es.dawgroup2.juding.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    /**
     * This method inflates the individual post (shown by Id) visualization view.
     * A list with other post is also shown.
     *
     * @param model Post data model.
     * @param id    Current post id.
     * @return Individual post visualization view (news).
     */
    @GetMapping("/news/{id}")
    public String newsPost(Model model, @PathVariable String id) {
        Post bigPost = postService.findById(id);
        if (bigPost != null) {
            List<Post> recentPosts = postService.findFirst5Desc(id);
            recentPosts.remove(bigPost);
            model.addAttribute("bigPost", bigPost)
                    .addAttribute("recentPosts", recentPosts);
            return "/news/index";
        }
        return "redirect:/error/404";
    }

    /**
     * Returns a inflated row of posts in page format (3 posts per row, in index format).
     *
     * @param page  Number of page.
     * @param model Model.
     * @return Inflated row of posts.
     */
    @GetMapping("/news/page/{page}")
    public String getPage(@PathVariable String page, Model model) {
        Page<Post> postRow = postService.getPostsInPages(Integer.parseInt(page), 3);
        model.addAttribute("postRow", postRow.getContent());
        return "/news/rowOfNews";
    }
}
