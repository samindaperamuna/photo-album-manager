package edu.csuohio.photomanager.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	private static final String IS_HOME = "isHome";
	private static final String IS_GALLERY = "isGallery";
	private static final String IS_UPLOAD = "isUpload";

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute(IS_HOME, true);
		return "index.html";
	}

	@GetMapping("/gallery")
	public String gallery(Model model) {
		model.addAttribute(IS_GALLERY, true);
		return "gallery.html";
	}

	@GetMapping("/upload")
	public String upload(Model model) {
		model.addAttribute(IS_UPLOAD, true);
		return "upload.html";
	}
}