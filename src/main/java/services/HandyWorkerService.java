
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Box;
import domain.Complaint;
import domain.Curriculum;
import domain.Customer;
import domain.EducationRecord;
import domain.Endorser;
import domain.EndorserRecord;
import domain.Endorsment;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Message;
import domain.MiscellaneousRecord;
import domain.Note;
import domain.PersonalRecord;
import domain.Phase;
import domain.ProfessionalRecord;
import domain.Report;
import domain.SocialProfile;
import domain.Status;
import domain.Tutorial;

@Service
@Transactional
public class HandyWorkerService {

	// Managed repository ---------------------------------------------------------------------------------------------

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	// Supporting Services --------------------------------------------------------------------------------------------

	@Autowired
	private TutorialService			tutorialService;
	@Autowired
	private EndorsmentService		endorsmentService;
	@Autowired
	private CurriculumService		curriculumService;
	@Autowired
	private NoteService				noteService;
	@Autowired
	private ReportService			reportService;
	@Autowired
	private PhaseService			phaseService;
	@Autowired
	private ApplicationService		applicationService;
	@Autowired
	private FinderService			finderService;
	@Autowired
	private FixUpTaskService		fixUpTaskService;
	@Autowired
	private ComplaintService		complaintService;
	@Autowired
	private EndorserService			endorserSevice;
	@Autowired
	private CustomerService			customerService;


	// Simple CRUD methods --------------------------------------------------------------------------------------------

	public HandyWorker createHandyWorker() {

		HandyWorker handyWorker = new HandyWorker();

		List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		List<Box> boxes = new ArrayList<Box>();
		List<Endorsment> endorsments = new ArrayList<Endorsment>();
		List<Application> applications = new ArrayList<Application>();
		List<Tutorial> tutorials = new ArrayList<Tutorial>();

		Finder finder = new Finder();

		UserAccount userAccountActor = new UserAccount();
		userAccountActor.setUsername("");
		userAccountActor.setPassword("");

		Box spamBox = new Box();
		List<Message> messages1 = new ArrayList<>();
		spamBox.setIsSystem(true);
		spamBox.setMessages(messages1);
		spamBox.setName("Spam");

		Box trashBox = new Box();
		List<Message> messages2 = new ArrayList<>();
		trashBox.setIsSystem(true);
		trashBox.setMessages(messages2);
		trashBox.setName("Trash");

		Box sentBox = new Box();
		List<Message> messages3 = new ArrayList<>();
		sentBox.setIsSystem(true);
		sentBox.setMessages(messages3);
		sentBox.setName("Sent messages");

		Box receivedBox = new Box();
		List<Message> messages4 = new ArrayList<>();
		receivedBox.setIsSystem(true);
		receivedBox.setMessages(messages4);
		receivedBox.setName("Received messages");

		boxes.add(receivedBox);
		boxes.add(sentBox);
		boxes.add(spamBox);
		boxes.add(trashBox);

		handyWorker.setTutorials(tutorials);
		handyWorker.setName("");
		handyWorker.setSurname("");
		handyWorker.setAddress("");
		handyWorker.setEmail("");
		handyWorker.setMiddleName("");
		handyWorker.setPhoneNumber("");
		handyWorker.setSocialProfiles(socialProfiles);
		handyWorker.setScore(0.0);
		handyWorker.setEndorsments(endorsments);
		handyWorker.setMake("");
		handyWorker.setApplications(applications);
		handyWorker.setPhoto("");
		handyWorker.setFinder(finder);

		handyWorker.setHasSpam(false);

		List<Authority> authorities = new ArrayList<Authority>();

		Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		authorities.add(authority);

		userAccountActor.setAuthorities(authorities);
		//NOTLOCKED A TRUE EN LA INICIALIZACION, O SE CREARA UNA CUENTA BANEADA
		userAccountActor.setIsNotLocked(true);

		handyWorker.setUserAccount(userAccountActor);

		return handyWorker;

	}

	public HandyWorker createHandyWorker(String name, String middleName, String surname, String photo, String email, String phoneNumber, String address, String userName, String password, Double score, List<Tutorial> tutorials, Curriculum curriculum) {

		HandyWorker handyWorker = new HandyWorker();

		List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		List<Box> boxes = new ArrayList<Box>();
		List<Endorsment> endorsments = new ArrayList<Endorsment>();
		List<FixUpTask> f = new ArrayList<FixUpTask>();
		List<Application> applications = new ArrayList<Application>();

		UserAccount userAccountActor = new UserAccount();
		userAccountActor.setUsername(userName);
		userAccountActor.setPassword(password);

		Box spamBox = new Box();
		List<Message> messages1 = new ArrayList<>();
		spamBox.setIsSystem(true);
		spamBox.setMessages(messages1);
		spamBox.setName("Spam");

		Box trashBox = new Box();
		List<Message> messages2 = new ArrayList<>();
		trashBox.setIsSystem(true);
		trashBox.setMessages(messages2);
		trashBox.setName("Trash");

		Box sentBox = new Box();
		List<Message> messages3 = new ArrayList<>();
		sentBox.setIsSystem(true);
		sentBox.setMessages(messages3);
		sentBox.setName("Sent messages");

		Box receivedBox = new Box();
		List<Message> messages4 = new ArrayList<>();
		receivedBox.setIsSystem(true);
		receivedBox.setMessages(messages4);
		receivedBox.setName("Received messages");

		boxes.add(receivedBox);
		boxes.add(sentBox);
		boxes.add(spamBox);
		boxes.add(trashBox);

		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime());
		Date afterMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() + 1);

		Finder finder = this.finderService.createFinder("finder", "s", "s", 0.0, 0.0, thisMoment, afterMoment, f);

		handyWorker.setFinder(finder);
		handyWorker.setCurriculum(curriculum);
		handyWorker.setTutorials(tutorials);
		handyWorker.setName(name);
		handyWorker.setUserAccount(userAccountActor);
		handyWorker.setAddress(address);
		handyWorker.setEmail(email);
		handyWorker.setMiddleName(middleName);
		handyWorker.setPhoneNumber(phoneNumber);
		handyWorker.setSocialProfiles(socialProfiles);
		handyWorker.setScore(score);
		handyWorker.setEndorsments(endorsments);
		handyWorker.setMake(name + "" + middleName + "" + surname);
		handyWorker.setApplications(applications);
		handyWorker.setPhoto(photo);
		handyWorker.setSurname(surname);

		handyWorker.setHasSpam(false);

		List<Authority> authorities = new ArrayList<Authority>();

		Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		authorities.add(authority);

		handyWorker.getUserAccount().setAuthorities(authorities);
		//NOTLOCKED A TRUE EN LA INICIALIZACION, O SE CREARA UNA CUENTA BANEADA
		handyWorker.getUserAccount().setIsNotLocked(true);

		return handyWorker;

	}
	public void editHandyWorker(HandyWorker handyworker) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getId() == handyworker.getId());

		this.handyWorkerRepository.save(handyworker);

	}
	public Collection<HandyWorker> findAll() {
		return this.handyWorkerRepository.findAll();
	}

	public HandyWorker findOne(int id) {
		return this.handyWorkerRepository.findOne(id);
	}

	public HandyWorker save(HandyWorker handyWorker) {
		handyWorker.setMake(handyWorker.getName() + "" + handyWorker.getMiddleName() + "" + handyWorker.getSurname());
		return this.handyWorkerRepository.save(handyWorker);
	}
	public void delete(HandyWorker handyWorker) {
		this.handyWorkerRepository.delete(handyWorker);
	}

	private HandyWorker securityAndHandy() {
		UserAccount userAccount = LoginService.getPrincipal();
		String username = userAccount.getUsername();
		HandyWorker loggedHandy = this.handyWorkerRepository.findOne(userAccount.getId());

		Assert.isTrue(userAccount.getAuthorities().contains("HANDYWORKER"));

		return loggedHandy;
	}

	public Report showReport(Report report) {
		HandyWorker loggedHandy = this.securityAndHandy();
		Assert.isTrue(report.getFinalMode());
		return this.reportService.findOne(report.getId());
	}

	public List<Report> listReports() {
		HandyWorker loggedHandy = this.securityAndHandy();
		List<Report> lr = this.reportService.findAll();
		for (Report report : lr) {
			Assert.isTrue(report.getFinalMode());
		}
		return lr;
	}

	public Curriculum addCurriculum() {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isNull(logguedHandyWorker.getCurriculum());

		Curriculum curriculum = this.curriculumService.create();
		logguedHandyWorker.setCurriculum(curriculum);
		this.handyWorkerRepository.save(logguedHandyWorker);
		this.curriculumService.save(curriculum);
		return curriculum;

	}

	public Curriculum addCurriculum(PersonalRecord personalRecord, List<ProfessionalRecord> professionalRecords, List<EducationRecord> educationRecords, List<MiscellaneousRecord> miscellaneousRecords, List<EndorserRecord> endorserRecords) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isNull(logguedHandyWorker.getCurriculum());

		Curriculum curriculum = this.curriculumService.create(endorserRecords, miscellaneousRecords, educationRecords, professionalRecords, personalRecord);
		logguedHandyWorker.setCurriculum(curriculum);
		this.handyWorkerRepository.save(logguedHandyWorker);
		this.curriculumService.save(curriculum);
		return curriculum;

	}

	public void deleteCurriculum(Curriculum curriculum) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isTrue(logguedHandyWorker.getCurriculum().equals(curriculum));

		this.curriculumService.delete(curriculum);

	}

	public Curriculum editCurriculum(Curriculum curriculum, PersonalRecord personalRecord, List<ProfessionalRecord> professionalRecords, List<EducationRecord> educationRecords, List<MiscellaneousRecord> miscellaneousRecords,
		List<EndorserRecord> endorserRecords) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isTrue(logguedHandyWorker.getCurriculum().equals(curriculum));
		curriculum.setEducationRecords(educationRecords);
		curriculum.setEndorserRecords(endorserRecords);
		curriculum.setMiscellaneousRecords(miscellaneousRecords);
		curriculum.setPersonalRecord(personalRecord);
		curriculum.setProfessionalRecords(professionalRecords);
		return this.curriculumService.save(curriculum);
	}
	// Other business methods -------------------------------------------------------------------------------------------

	//11.1 ------------------------------------------------------------------------------------------------------------------

	public Collection<FixUpTask> showFixUpTasks() {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		return this.fixUpTaskService.findAll();
	}

	public Map<FixUpTask, Customer> showFixUpTasksAndCustomer() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		Map<FixUpTask, Customer> res = new HashMap<FixUpTask, Customer>();
		Collection<Customer> customers = this.customerService.findAll();

		for (Customer c : customers) {
			for (FixUpTask f : c.getFixUpTasks()) {
				res.put(f, c);
			}
		}
		return res;

	}
	public Map<Customer, Collection<FixUpTask>> getFixUpTaskPerCustomer(int idFixUpTask) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		Map<Customer, Collection<FixUpTask>> res = new HashMap<Customer, Collection<FixUpTask>>();

		Customer customer = this.handyWorkerRepository.getCustomerByFixUpTask(idFixUpTask);
		FixUpTask fixUpTask = this.fixUpTaskService.findOne(idFixUpTask);
		Assert.isTrue(customer.getFixUpTasks().contains(fixUpTask));

		res.put(customer, customer.getFixUpTasks());

		return res;

	}
	//11.2 ------------------------------------------------------------------------------------------------------------------

	public void filterFixUpTasksByFinder() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Finder finder = logguedHandyWorker.getFinder();
		Assert.isTrue(logguedHandyWorker.getFinder().getId() == finder.getId());

		List<FixUpTask> result = new ArrayList<FixUpTask>();
		result = this.fixUpTaskService.findAll();
		Collection<FixUpTask> filter = new ArrayList<FixUpTask>();

		if (finder.getKeyWord().equals(null)) {
			filter = this.handyWorkerRepository.getFixUpTaskByKeyWord(finder.getKeyWord());
			result.retainAll(filter);
		}
		if (finder.getCategory().equals(null)) {
			filter = this.handyWorkerRepository.getFixUpTaskByCategory(finder.getCategory());
			result.retainAll(filter);
		}
		if (finder.getWarranty().equals(null)) {
			filter = this.handyWorkerRepository.getFixUpTasksByWarranty(finder.getWarranty());
			result.retainAll(filter);
		}
		if (finder.getMinPrice().equals(null) || finder.getMaxPrice().equals(null)) {
			Assert.isTrue(finder.getMinPrice() <= finder.getMaxPrice());
			filter = this.handyWorkerRepository.getFixUpTasksByPrice(finder.getId());
			result.retainAll(filter);
		}
		if (finder.getStartDate().equals(null) || finder.getEndDate().equals(null)) {
			Assert.isTrue(finder.getStartDate().before(finder.getEndDate()));
			filter = this.handyWorkerRepository.getFixUpTasksByDate(finder.getId());
			result.retainAll(filter);
		}
		Finder finderResult = new Finder();
		finderResult = finder;
		finderResult.setFixUpTasks(result);
		this.finderService.save(finderResult);

	}
	//11.3 ------------------------------------------------------------------------------------------------------------------

	public Collection<Application> showApplications() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker h = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Collection<Application> listApplications = this.handyWorkerRepository.getAllApplicationsFromAHandyWorker(h.getId());
		return listApplications;
	}

	public Application createApplicationHandyWorker(double offeredPrice, List<String> comments, FixUpTask fixUpTask) {

		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Application application = this.applicationService.createApplication(offeredPrice, fixUpTask, logguedHandyWorker);

		application.setComments(comments);
		//Revisar
		List<Application> applications = logguedHandyWorker.getApplications();
		applications.add(application);
		logguedHandyWorker.setApplications(applications);

		Collection<Application> applicationsF = fixUpTask.getApplications();
		applicationsF.add(application);
		this.handyWorkerRepository.save(logguedHandyWorker);
		this.fixUpTaskService.save(fixUpTask);
		this.applicationService.save(application);

		return application;
	}

	//11.4 ------------------------------------------------------------------------------------------------------------------
	public Collection<Phase> showPhaseForHandyWorker(FixUpTask fixUpTask) {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isTrue(this.handyWorkerRepository.getFixUpTasksFromHandyWorker(logguedHandyWorker.getId()).contains(fixUpTask));

		Collection<Phase> phases = this.handyWorkerRepository.getPhasesByFixUpTask(fixUpTask.getId());

		return phases;
	}

	public void createPhaseForApplication(Application app, Phase newPhase) {

		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Application application = this.applicationService.findOne(app.getId());
		Assert.isTrue(application.getHandyWorker().equals(logguedHandyWorker));
		Assert.isTrue(application.getStatus().equals(Status.ACCEPTED));

		List<Phase> newPhases = (List<Phase>) this.handyWorkerRepository.getPhasesByApplication(application.getId());
		newPhases.add(newPhase);

		FixUpTask newFixUpTask = application.getFixUpTask();
		Assert.notNull(newFixUpTask);
		newFixUpTask.setPhases(newPhases);

		this.fixUpTaskService.save(newFixUpTask);

	}
	public void deletePhaseForApplication(int idPhase) {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Phase phase = new Phase();
		phase = this.phaseService.findOne(idPhase);

		Collection<Phase> phases = this.handyWorkerRepository.getPhasesByHandyWorker(logguedHandyWorker.getId());
		Assert.isTrue(phases.contains(phase));

		FixUpTask f = this.handyWorkerRepository.getFixUpTaskByPhase(idPhase);
		List<Phase> phasesF = (List<Phase>) f.getPhases();
		phasesF.remove(phase);
		f.setPhases(phasesF);
		this.fixUpTaskService.save(f);
		this.phaseService.delete(phase);
	}

	public void updatePhaseForHandyWorker(Phase phase) {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Collection<Phase> phases = this.handyWorkerRepository.getPhasesByHandyWorker(logguedHandyWorker.getId());
		Assert.isTrue(phases.contains(phase));

		FixUpTask f = this.handyWorkerRepository.getFixUpTaskByPhase(phase.getId());

		this.phaseService.save(phase);
	}
	//REQUISITO FUNCIONAL 37 *******************************************************************************************************************

	//37.1 --------------------------------------------------------------------------------------------------------------------------------------

	public void updateFinderFromHandyWorker(Finder finder) {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isTrue(logguedHandyWorker.getFinder().getId() == finder.getId());

		logguedHandyWorker.setFinder(finder);
		this.finderService.save(finder);
		this.handyWorkerRepository.save(logguedHandyWorker);
	}

	//37.2 --------------------------------------------------------------------------------------------------------------------------------------
	public List<FixUpTask> showFinderResult() {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		List<FixUpTask> resultFixUpTask = new ArrayList<FixUpTask>();

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Finder finder = new Finder();

		finder = this.handyWorkerRepository.getFinderFromAHandyWorker(logguedHandyWorker.getId());
		resultFixUpTask = finder.getFixUpTasks();
		return resultFixUpTask;
	}

	//37.3 --------------------------------------------------------------------------------------------------------------------------------------
	public List<Complaint> showComplaintsFromHandyWorker() {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		List<Complaint> complaints = new ArrayList<Complaint>();

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		complaints = this.handyWorkerRepository.getComplaintsFromHandyWorker(logguedHandyWorker.getId());

		return complaints;
	}

	//37.4 --------------------------------------------------------------------------------------------------------------------------------------
	public void createNoteFromHandyWorker(int idComplaint, Note note, int idReport) {

		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isTrue(!note.getMandatoryComment().equals(""));
		Assert.notNull(note.getMandatoryComment());
		Assert.notNull(note.getMoment());

		List<Complaint> complaints = new ArrayList<Complaint>();
		complaints = this.handyWorkerRepository.getComplaintsFromHandyWorker(logguedHandyWorker.getId());
		Complaint complaint = new Complaint();
		complaint = this.complaintService.findOne(idComplaint);

		Assert.isTrue(complaints.contains(complaint));

		List<Report> reports = new ArrayList<Report>();
		reports = complaint.getReports();
		Report report = new Report();
		report = this.reportService.findOne(idReport);

		Assert.isTrue(reports.contains(report));

		//	this.noteService.save(note);

		List<Note> notes = new ArrayList<Note>();
		notes = report.getNotes();
		notes.add(note);
		report.setNotes(notes);

		this.reportService.save(report);

	}
	//37.5 --------------------------------------------------------------------------------------------------------------------------------------
	public void writeCommentFromHandyWorker(int idComplaint, String comment, int idReport, int idNote) {

		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		List<Complaint> complaints = new ArrayList<Complaint>();
		complaints = this.handyWorkerRepository.getComplaintsFromHandyWorker(logguedHandyWorker.getId());
		Complaint complaint = new Complaint();
		complaint = this.complaintService.findOne(idComplaint);

		Assert.isTrue(complaints.contains(complaint));

		List<Report> reports = new ArrayList<Report>();
		reports = complaint.getReports();
		Report report = new Report();
		report = this.reportService.findOne(idReport);

		Assert.isTrue(reports.contains(report));
		Note note = new Note();
		note = this.noteService.findOne(idNote);

		Assert.isTrue(report.getNotes().contains(note));
		note.getOptionalComments().add(comment);

		this.noteService.save(note);

	}
	//REQUISITO FUNCIONAL 49--------------------------------------------------------------------------------------------------------------------

	//49.1
	public List<Tutorial> showTutorials() {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		List<Tutorial> tutorials = new ArrayList<Tutorial>();

		tutorials = this.handyWorkerRepository.getAllTutorialsFromAHandyWorker(logguedHandyWorker.getId());

		return tutorials;
	}

	public void deleteTutorial(Tutorial tutorial) {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isTrue(logguedHandyWorker.getTutorials().contains(tutorial));

		List<Tutorial> tutorials = logguedHandyWorker.getTutorials();
		tutorials.remove(tutorial);

		logguedHandyWorker.setTutorials(tutorials);

		this.tutorialService.delete(tutorial);
		this.handyWorkerRepository.save(logguedHandyWorker);
	}

	public void updateTutorial(Tutorial tutorial) {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isTrue(logguedHandyWorker.getTutorials().contains(tutorial));

		this.tutorialService.save(tutorial);
	}

	public void createTutorial(Tutorial newTutorial) {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isTrue(!logguedHandyWorker.getTutorials().contains(newTutorial));

		Tutorial tutorial = this.tutorialService.save(newTutorial);
		List<Tutorial> tutorials = logguedHandyWorker.getTutorials();
		tutorials.add(tutorial);
		logguedHandyWorker.setTutorials(tutorials);

		this.handyWorkerRepository.save(logguedHandyWorker);

	}

	//49.2
	public void deleteEndorsment(Endorsment endorsment) {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isTrue(logguedHandyWorker.getEndorsments().contains(endorsment));
		List<Endorsment> endorsments = logguedHandyWorker.getEndorsments();
		endorsments.remove(endorsment);
		logguedHandyWorker.setEndorsments(endorsments);

		this.endorsmentService.delete(endorsment);
		this.handyWorkerRepository.save(logguedHandyWorker);
	}

	public void updateEndorsment(Endorsment endorsment) {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));
		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isTrue(logguedHandyWorker.getEndorsments().contains(endorsment));

		this.endorsmentService.save(endorsment);
	}

	//TODO COMPROBAR
	public void createEndorsment(Endorsment endorsment) {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		Assert.isTrue(endorsment.getWrittenBy().getUserAccount().equals(userAccount));

		authorities = (List<Authority>) endorsment.getWrittenTo().getUserAccount().getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("CUSTOMER"));

		List<Integer> ids = new ArrayList<Integer>();
		ids = this.handyWorkerRepository.getCustomersFromHandyWorker(logguedHandyWorker.getId());

		Customer customer = this.customerService.getCustomerByUserName(endorsment.getWrittenTo().getUserAccount().getUsername());
		Assert.isTrue(ids.contains(customer.getId()));

		Endorsment newEndorsment = this.endorsmentService.save(endorsment);
		List<Endorsment> end = endorsment.getWrittenTo().getEndorsments();
		end.add(newEndorsment);

		Endorser endorser1 = this.endorserSevice.findOne(endorsment.getWrittenTo().getId());
		endorser1.setEndorsments(end);
		this.endorserSevice.save(endorser1);

		end = endorsment.getWrittenBy().getEndorsments();
		end.add(endorsment);
		Endorser endorser2 = this.endorserSevice.findOne(endorsment.getWrittenBy().getId());
		endorser2.setEndorsments(end);
		this.endorserSevice.save(endorser2);
		this.endorsmentService.save(newEndorsment);

	}

	public List<Endorsment> showEndorsments() {
		UserAccount userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("HANDYWORKER"));

		HandyWorker logguedHandyWorker = new HandyWorker();
		logguedHandyWorker = this.handyWorkerRepository.getHandyWorkerByUsername(userAccount.getUsername());

		List<Endorsment> endorsments = logguedHandyWorker.getEndorsments();
		return endorsments;
	}

	//Test Methods ------------------------------------------
	public HandyWorker getHandyWorkerByUsername(String username) {

		return this.handyWorkerRepository.getHandyWorkerByUsername(username);
	}
	public List<FixUpTask> getFixUpTaskByHandyWorker(HandyWorker handyWorker) {

		return this.handyWorkerRepository.getFixUpTasksFromHandyWorker(handyWorker.getId());
	}
	public Collection<Phase> getPhasesByApplication(Application application) {

		return this.handyWorkerRepository.getPhasesByApplication(application.getId());
	}
	public List<Integer> getIdCustomersByHandyWorker(HandyWorker handyWorker) {
		return this.handyWorkerRepository.getCustomersFromHandyWorker(handyWorker.getId());
	}
}
