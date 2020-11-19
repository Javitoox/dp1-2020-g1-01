package org.springframework.samples.petclinic.web;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.ModelMap;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.service.SolicitudService;
@CrossOrigin("*")
@RestController
@RequestMapping("/solicitudes")


public class SolicitudController {
	   @Autowired
	   SolicitudService solicitudServ;
	   @GetMapping("/all")
	   public Collection<Solicitud> getSolicitudes(ModelMap model) {
		   return solicitudServ.getAllSolicitudes();
	   }
}
