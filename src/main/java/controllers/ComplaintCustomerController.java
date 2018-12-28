
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.CustomerService;
import domain.Complaint;
import domain.FixUpTask;

@Controller
@RequestMapping("/complaint/customer")
public class ComplaintCustomerController extends AbstractController {

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private CustomerService		customerService;


	public ComplaintCustomerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		Collection<Complaint> complaints;

		complaints = this.customerService.showComplaints();

		result = new ModelAndView("complaint/customer/list");

		result.addObject("complaints", complaints);
		result.addObject("requestURI", "complaint/customer/list.do");

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Complaint c;

		c = this.complaintService.create();
		result = this.createEditModelAndView(c);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Complaint complaint, BindingResult binding, @RequestParam String newAttachments, @RequestParam FixUpTask fixUpTask) {
		ModelAndView result;
		List<String> attachments = new ArrayList<String>();

		if (!newAttachments.trim().equals("")) {
			attachments.add(newAttachments);
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(complaint);
		} else {
			try {
				complaint.setAttachments(attachments);
				this.customerService.createComplaint(fixUpTask, complaint);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(complaint, "complaint.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Complaint complaint) {
		ModelAndView result;

		result = this.createEditModelAndView(complaint, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Complaint complaint, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("complaint/customer/create");
		result.addObject("complaint", complaint);
		result.addObject("message", messageCode);

		return result;
	}
}
