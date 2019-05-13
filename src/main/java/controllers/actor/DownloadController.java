
package controllers.actor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import security.Authority;
import services.ActorService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import domain.Actor;
import domain.Administrator;
import domain.Manager;
import domain.RaceDirector;
import domain.Representative;
import domain.Rider;
import domain.Sponsor;

@Controller
@RequestMapping("/download")
public class DownloadController {

	@Autowired
	ActorService				actorService;

	private static final String	APPLICATION_PDF	= "application/pdf";


	@RequestMapping(value = "/myPersonalData", method = RequestMethod.GET, produces = DownloadController.APPLICATION_PDF)
	public @ResponseBody
	void downloadMyPersonalData(final HttpServletResponse response) throws IOException, DocumentException {

		final File file = new File("MyPersonalData.pdf");
		final FileOutputStream fileout = new FileOutputStream(file);
		final Document document = new Document();
		PdfWriter.getInstance(document, fileout);
		document.addAuthor("Actor");
		document.addTitle("My personal data");
		document.open();

		final Actor principal = this.actorService.findByPrincipal();

		final Rider rider = new Rider();
		final Authority authRider = new Authority();
		authRider.setAuthority(Authority.RIDER);

		final RaceDirector raceDirector = new RaceDirector();
		final Authority authRaceDirector = new Authority();
		authRaceDirector.setAuthority(Authority.RACEDIRECTOR);

		final Representative representative = new Representative();
		final Authority authRepresentative = new Authority();
		authRepresentative.setAuthority(Authority.REPRESENTATIVE);

		final Sponsor sponsor = new Sponsor();
		final Authority authSponsor = new Authority();
		authSponsor.setAuthority(Authority.SPONSOR);

		final Manager manager = new Manager();
		final Authority authManager = new Authority();
		authManager.setAuthority(Authority.MANAGER);

		final Administrator admin = new Administrator();
		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);

		final ObjectMapper mapper = new ObjectMapper();

		final Paragraph paragraph = new Paragraph();
		paragraph.add(principal.toString());
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		if (principal != null)
			if (principal.getUserAccount().getAuthorities().contains(authRider)) {
				final Rider riderPrincipal = (Rider) this.actorService.findByPrincipal();
				rider.setName(riderPrincipal.getName());
				rider.setSurnames(riderPrincipal.getSurnames());
				rider.setPhoto(riderPrincipal.getPhoto());
				rider.setEmail(riderPrincipal.getEmail());
				rider.setPhone(riderPrincipal.getPhone());
				rider.setAddress(riderPrincipal.getAddress());
				rider.setNumber(riderPrincipal.getNumber());
				rider.setCountry(riderPrincipal.getCountry());
				rider.setAge(riderPrincipal.getAge());
				rider.setScore(riderPrincipal.getScore());
				final String json = mapper.writeValueAsString(rider);
				paragraph.add(json);
			} else if (principal.getUserAccount().getAuthorities().contains(authRaceDirector)) {
				final RaceDirector raceDirectorPrincipal = (RaceDirector) this.actorService.findByPrincipal();
				raceDirector.setName(raceDirectorPrincipal.getName());
				raceDirector.setSurnames(raceDirectorPrincipal.getSurnames());
				raceDirector.setPhoto(raceDirectorPrincipal.getPhoto());
				raceDirector.setEmail(raceDirectorPrincipal.getEmail());
				raceDirector.setPhone(raceDirectorPrincipal.getPhone());
				raceDirector.setAddress(raceDirectorPrincipal.getAddress());
				final String json = mapper.writeValueAsString(raceDirector);
				paragraph.add(json);
			} else if (principal.getUserAccount().getAuthorities().contains(authRepresentative)) {
				final Representative representativePrincipal = (Representative) this.actorService.findByPrincipal();
				representative.setName(representativePrincipal.getName());
				representative.setSurnames(representativePrincipal.getSurnames());
				representative.setPhoto(representativePrincipal.getPhoto());
				representative.setEmail(representativePrincipal.getEmail());
				representative.setPhone(representativePrincipal.getPhone());
				representative.setAddress(representativePrincipal.getAddress());
				representative.setScore(representativePrincipal.getScore());
				final String json = mapper.writeValueAsString(representative);
				paragraph.add(json);
			} else if (principal.getUserAccount().getAuthorities().contains(authSponsor)) {
				final Sponsor sponsorPrincipal = (Sponsor) this.actorService.findByPrincipal();
				sponsor.setName(sponsorPrincipal.getName());
				sponsor.setSurnames(sponsorPrincipal.getSurnames());
				sponsor.setPhoto(sponsorPrincipal.getPhoto());
				sponsor.setEmail(sponsorPrincipal.getEmail());
				sponsor.setPhone(sponsorPrincipal.getPhone());
				sponsor.setAddress(sponsorPrincipal.getAddress());
				final String json = mapper.writeValueAsString(sponsor);
				paragraph.add(json);
			} else if (principal.getUserAccount().getAuthorities().contains(authManager)) {
				final Manager managerPrincipal = (Manager) this.actorService.findByPrincipal();
				manager.setName(managerPrincipal.getName());
				manager.setSurnames(managerPrincipal.getSurnames());
				manager.setPhoto(managerPrincipal.getPhoto());
				manager.setEmail(managerPrincipal.getEmail());
				manager.setPhone(managerPrincipal.getPhone());
				manager.setAddress(managerPrincipal.getAddress());
				final String json = mapper.writeValueAsString(manager);
				paragraph.add(json);
			} else if (principal.getUserAccount().getAuthorities().contains(authAdmin)) {
				final Administrator adminPrincipal = (Administrator) this.actorService.findByPrincipal();
				admin.setName(adminPrincipal.getName());
				admin.setSurnames(adminPrincipal.getSurnames());
				admin.setPhoto(adminPrincipal.getPhoto());
				admin.setEmail(adminPrincipal.getEmail());
				admin.setPhone(adminPrincipal.getPhone());
				admin.setAddress(adminPrincipal.getAddress());
				final String json = mapper.writeValueAsString(admin);
				paragraph.add(json);
			}

		document.add(paragraph);
		document.close();

		final InputStream in = new FileInputStream(file);

		response.setContentType(DownloadController.APPLICATION_PDF);
		response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
		response.setHeader("Content-Length", String.valueOf(file.length()));

		FileCopyUtils.copy(in, response.getOutputStream());

	}

}
