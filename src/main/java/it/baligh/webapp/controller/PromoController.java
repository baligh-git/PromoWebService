package it.baligh.webapp.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.baligh.webapp.entities.DettPromo;
import it.baligh.webapp.entities.Promo;
import it.baligh.webapp.exception.IdNotFoundException;
import it.baligh.webapp.exception.PromoNotFoundException;
import it.baligh.webapp.service.PromoService;

@RestController
@RequestMapping("/api/promo")
public class PromoController {
	
	private static final Logger log = LoggerFactory.getLogger(PromoController.class);


	@Autowired
	private PromoService promoService;
	
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Promo>> getListePromo(){
		log.info("****Selezione di Lista di promo*****");
		List<Promo> liste=promoService.selTutti();
		 if (liste.isEmpty()) {
			 return new ResponseEntity<List<Promo>>(HttpStatus.NO_CONTENT);
			
		}
		 log.info("****Il numero di record è :"+liste.size()+"********");
		 return new ResponseEntity<List<Promo>>(liste,HttpStatus.OK);
		
	}
	
	
	@GetMapping(value="/cerca/{idpromo}",produces = "application/json")
	public ResponseEntity<Promo> getPromoById(@PathVariable("idpromo") String idPromo)
	throws PromoNotFoundException
	{
		log.info("****Selezione della promo*****");
		Promo promo=promoService.findByIdPromo(idPromo);
		if (promo==null) {
			throw new PromoNotFoundException("promo assente o id errato");
			//return new ResponseEntity<Promo>(HttpStatus.NO_CONTENT);
		}
		log.info("****la promo con Codice eguale a:"+promo.getIdPromo()+ "*****"+promo.getCodice().length());
		return new ResponseEntity<Promo>(promo,HttpStatus.OK);
	}
	
	@GetMapping(value="/active",produces = "application/json")
	public ResponseEntity<List<Promo>> getPromoBydata(){
		log.info("****Selezione della promo data*****");
		Date data =new Date();
		List<Promo> promo=promoService.selPromoByData(data);
		if (promo==null) {
			return new ResponseEntity<List<Promo>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Promo>>(promo,HttpStatus.OK);
	}
	
	
	@GetMapping(value="/codice",produces = "application/json")
	public ResponseEntity<Promo> getPromoByAnnoAndCodice(@RequestParam("anno") String anno,
			@RequestParam("codice") String codice){
		log.info("****Selezione della promo per anno e codice*****");
		Promo promo=promoService.findByAnnoAndCodice(anno, codice);
		if (promo==null) {
			return new ResponseEntity<Promo>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Promo>(promo,HttpStatus.OK);
	}
	
	@PostMapping(value = "/insert")
	public ResponseEntity<Promo> IsertModificaPromo(@RequestBody Promo promo) 
	throws IdNotFoundException
	{
		
		
		if (promo.getIdPromo().length()==0) {
			String uid=UUID.randomUUID().toString();
			promo.setIdPromo(uid);
			
			log.info("**********************"+uid);
			promoService.insertPromo(promo);
			
			
			return new ResponseEntity<>(new HttpHeaders(),HttpStatus.CREATED);
			
		}
		else {
			
			log.info("message la modifica se fa con il metodo PUT");
			throw new IdNotFoundException("message la modifica se fa con il metodo PUT");
			//return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
//			promoService.insertPromo(promo);
//			return new ResponseEntity<>(new HttpHeaders(),HttpStatus.CREATED);
		}
		
		
//       *****  Modifica con put		
		
		@PutMapping(value = "/modifica")
		public ResponseEntity<Promo> modificaPromo(@RequestBody Promo promo) {
		
			if (promo.getIdPromo().length()>0) {
				
				
				log.info("**********************"+promo.getIdPromo());
				promoService.insertPromo(promo);
				
				
				return new ResponseEntity<>(new HttpHeaders(),HttpStatus.CREATED);
				
			}
			else {
				
				log.info("message modificare senza id");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//				promoService.insertPromo(promo);
//				return new ResponseEntity<>(new HttpHeaders(),HttpStatus.CREATED);
			}
		
	}
	@DeleteMapping(value = "/elimina/{id}")
	public ResponseEntity<?> eliminaPromo(@PathVariable("id") String id) {
		Promo promo=promoService.findByIdPromo(id);
		HttpHeaders header =new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper mapper=new ObjectMapper();
		ObjectNode node =mapper.createObjectNode();
		
		if (promo==null) {
			log.warn("questo id non è presente nel nostro database");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else
		{
			promoService.eliminaPromo(promo);
			node.put("code", HttpStatus.OK.toString());
			node.put("message","è stato eliminato la promo de Id :" +id);
			
			return new ResponseEntity<>(node,header,HttpStatus.OK);
			
		}	
		
	}
	
	

}
