
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import domain.Complaint;

@Controller
@RequestMapping("/attachment/customer")
public class AttachmentCustomerController extends AbstractController {

	@Autowired
	private ComplaintService	ComplaintService;


	// Constructor --------------------------------------------------------------

	public AttachmentCustomerController() {
		super();
	}

	// ModelAndView -------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int complaintId) {

		ModelAndView result;

		Collection<String> attachments;

		Complaint c = new Complaint();

		c = this.ComplaintService.findOne(complaintId);

		attachments = c.getAttachments();

		//TODO
		for (String a : attachments) {
			if (!a.startsWith("http://")) {
				throw new IllegalArgumentException();
			}
		}

		result = new ModelAndView("attachment/customer/list");

		result.addObject("attachments", attachments);
		result.addObject("requestURI", "attachment/customer/list.do");

		return result;

	}

}
