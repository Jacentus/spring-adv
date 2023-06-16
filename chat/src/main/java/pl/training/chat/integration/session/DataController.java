package pl.training.chat.integration.session;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DataController {

    @PostMapping("process")
    public String process(HttpSession httpSession) {
        httpSession.setAttribute("data", "Shared data");
        return "redirect:index.html";
    }

}
