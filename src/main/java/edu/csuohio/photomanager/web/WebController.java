package edu.csuohio.photomanager.web;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.csuohio.photomanager.data.ImageItem;
import edu.csuohio.photomanager.data.service.ImageItemService;

/**
 * Web controller handling the web URLs.
 */
@Controller
public class WebController {

	@Autowired
	ImageItemService imageItemService;

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

	@GetMapping("/image")
	public String image(@RequestParam URL url) {
		try {
			String imageId = url.getFile().split("-")[1].split("\\.")[0];
			ImageItem imageItem = imageItemService.findById(imageId);
			imageItem.getImageName();

			return "redirect:/images/" + imageItem.getImageName();
		} catch (Exception e) {
			System.out.println("Couldn't access the original image. Displaying thumbnail instead.");
		}

		return "";
	}

	@GetMapping("/upload")
	public String upload(Model model) {
		model.addAttribute(IS_UPLOAD, true);
		return "upload.html";
	}
}