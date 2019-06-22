package light.mvc.controller.utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/ckfinder")
public class CkController {
	@RequestMapping(value="/index.html",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView ckIndex(){
		ModelAndView model = new ModelAndView();
		model.addObject("msg", "Hello CKEditor");
		model.setViewName("index");
		return model;
	}
}
