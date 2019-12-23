package it.baligh.webapp;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import it.baligh.webapp.entities.DettPromo;
import it.baligh.webapp.entities.Promo;
import it.baligh.webapp.repository.PromoRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes =Application.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PromoControllerTest {
	
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private PromoRepository promoRepository;
	@Before
	public void setup() {
		this.mvc=MockMvcBuilders.webAppContextSetup(wac).build();
		
	}
	@Test
	public void A_seltutti() throws Exception {
		System.out.println("-------------------------------A-----------------------------------");
		mvc.perform(MockMvcRequestBuilders.get("/api/promo")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$", hasSize(6)))
		        .andDo(print());
		
	}
	@Test
	public void B_testCreatePromo() throws Exception{
		
		System.out.println("--------------------------------B----------------------------------");
		mvc.perform(MockMvcRequestBuilders.post("/api/promo/insert")
		   .contentType(MediaType.APPLICATION_JSON)
		   .content(JsonDataCreate)
		   .accept(MediaType.APPLICATION_JSON))
		   .andExpect(status().isCreated())
		   .andDo(print());
	}
	@Test
	public void C_testfindPromoById() throws Exception {
		System.out.println("-----------------------------C-------------------------------------");
		Promo promo=promoRepository.findByAnnoAndCodice(2019, "UT02");
		Set<DettPromo> set=promo.getDettPromo();
		
		System.out.println("-----------------------------C----------------------------------");
		mvc.perform(MockMvcRequestBuilders.get("/api/promo/cerca/"+promo.getIdPromo())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.idPromo").exists())
				.andExpect(jsonPath("$.idPromo").value(promo.getIdPromo()))
				.andExpect(jsonPath("$.dettPromo[0].id").exists())
				.andExpect(jsonPath("$.dettPromo[0].riga").exists())
				.andExpect(jsonPath("$.dettPromo[0].riga").value("1"))
				.andExpect(jsonPath("$.dettPromo[0].codArt").exists())
				.andExpect(jsonPath("$.dettPromo[0].codArt").value("058310201"))
				.andExpect(jsonPath("$.dettPromo[0].oggetto").exists())
				.andExpect(jsonPath("$.dettPromo[0].oggetto").value("1.99"))
				.andExpect(jsonPath("$.dettPromo[0].isFid").exists())
				.andExpect(jsonPath("$.dettPromo[0].isFid").value("No"))
				
				
				.andExpect(jsonPath("$.dettPromo[0].tipoPromo.idTipoPromo").exists())
				.andExpect(jsonPath("$.dettPromo[0].tipoPromo.idTipoPromo").value("1"))
				.andExpect(jsonPath("$.dettPromo[0].tipoPromo.descrizione").exists())
				.andExpect(jsonPath("$.dettPromo[0].tipoPromo.descrizione").value("TAGLIO PREZZO"))
				
				
				.andExpect(jsonPath("$.dettPromo[1].id").exists())
				.andExpect(jsonPath("$.dettPromo[1].riga").exists())
				.andExpect(jsonPath("$.dettPromo[1].riga").value("2"))
				.andExpect(jsonPath("$.dettPromo[1].codArt").exists())
				.andExpect(jsonPath("$.dettPromo[1].codArt").value("000020030"))
				.andExpect(jsonPath("$.dettPromo[1].oggetto").exists())
				.andExpect(jsonPath("$.dettPromo[1].oggetto").value("0.39"))
				.andExpect(jsonPath("$.dettPromo[1].isFid").exists())
				.andExpect(jsonPath("$.dettPromo[1].isFid").value("Si"))
				

				.andExpect(jsonPath("$.dettPromo[1].tipoPromo.descrizione").exists())
				.andExpect(jsonPath("$.dettPromo[1].tipoPromo.descrizione").value("TAGLIO PREZZO")) 
				.andDo(print());
				
		 
		
	}
	
	@Test
	public void D_testUpdatePromo() throws Exception {
		System.out.println("--------------------------D---------UPDATE-------------------------------");
		mvc.perform(MockMvcRequestBuilders.put("/api/promo/modifica")
		           .contentType(MediaType.APPLICATION_JSON)
		           .content(getstring()).accept(MediaType.APPLICATION_JSON))
		           .andExpect(status().isCreated())
		           .andDo(print());
				   
	}
	@Test
	public void E_testFindByCodiceAnno() throws Exception {
		System.out.println("----------------------------E--------------------------------------");
		Promo promo=promoRepository.findByAnnoAndCodice(2019, "UT02");
		mvc.perform(MockMvcRequestBuilders.get("/api/promo/codice?anno=2019&&codice=UT02")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idPromo").exists())
				.andExpect(jsonPath("$.idPromo").value(promo.getIdPromo()))
				.andDo(print());
				
		
	}
    @Test
	public void F_testEliminaPromo() throws Exception {
		System.out.println("----------------------------F------ELIMINA--------------------------------");
		Promo promo=promoRepository.findByAnnoAndCodice(2019, "UT02");
		mvc.perform(MockMvcRequestBuilders.delete("/api/promo/elimina/"+promo.getIdPromo())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andDo(print());
		
	}
    @Test
	public void G_testListPromoActive() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/api/promo/active")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(content().json(JsonActive)) 
			.andReturn();
	}
	
    @Test
    public void G_testPromoActive2() throws Exception {
    	System.out.println("----------------------------KKKKKK-------------------------------");
    	mvc.perform(MockMvcRequestBuilders.get("/api/promo/active")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
		        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$", hasSize(2)))
		        .andDo(print());
    	
    }
//    MockHttpServletRequest:
//    HTTP Method = GET
//    	      Request URI = /api/promo/cerca/abc
//    	       Parameters = {}
//    	          Headers = [Content-Type:"application/json", Accept:"application/json", Content-Length:"1278"]
//    	             Body = <no character encoding set>
//    	    Session Attrs = {}
//    MockHttpServletResponse:
//        Status = 404
// Error message = null
//       Headers = [Content-Type:"application/json"]
//  Content type = application/json
//          Body = {"codice":404,"messagio":"promo assente o id errato"}
    @Test
    public void H_testPromoNotFound() throws Exception {
    	mvc.perform(MockMvcRequestBuilders.get("/api/promo/cerca/abc")
    			
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isNotFound())
    			.andExpect(jsonPath("$.codice").value("404"))
    			.andExpect(jsonPath("$.messagio").value("promo assente o id errato"))
    			.andDo(print());
    	   
    }
    String JsonActive="[\r\n" + 
    		"    {\r\n" + 
    		"        \"idPromo\": \"041f7662-dbcf-45b7-a5cc-808dd18e0432\",\r\n" + 
    		"        \"anno\": 2019,\r\n" + 
    		"        \"codice\": \"UT01      \",\r\n" + 
    		"        \"descrizione\": \"CAMPAGNA TEST INSERIMENTO\",\r\n" + 
    		"        \"dettPromo\": [\r\n" + 
    		"            {\r\n" + 
    		"                \"id\": 278,\r\n" + 
    		"                \"riga\": 1,\r\n" + 
    		"                \"codArt\": \"058310201\",\r\n" + 
    		"                \"codFid\": null,\r\n" + 
    		"                \"inizio\": \"2019-01-01\",\r\n" + 
    		"                \"fine\": \"2019-12-31\",\r\n" + 
    		"                \"oggetto\": \"1.99\",\r\n" + 
    		"                \"isFid\": \"Si\",\r\n" + 
    		"                \"tipoPromo\": {\r\n" + 
    		"                    \"idTipoPromo\": 1,\r\n" + 
    		"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
    		"                }\r\n" + 
    		"            },\r\n" + 
    		"            {\r\n" + 
    		"                \"id\": 277,\r\n" + 
    		"                \"riga\": 2,\r\n" + 
    		"                \"codArt\": \"000020030\",\r\n" + 
    		"                \"codFid\": null,\r\n" + 
    		"                \"inizio\": \"2019-01-01\",\r\n" + 
    		"                \"fine\": \"2019-12-31\",\r\n" + 
    		"                \"oggetto\": \"0.40\",\r\n" + 
    		"                \"isFid\": \"Si\",\r\n" + 
    		"                \"tipoPromo\": {\r\n" + 
    		"                    \"idTipoPromo\": 1,\r\n" + 
    		"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
    		"                }\r\n" + 
    		"            }\r\n" + 
    		"        ],\r\n" + 
    		"        \"depRifPromo\": [\r\n" + 
    		"            {\r\n" + 
    		"                \"id\": 206,\r\n" + 
    		"                \"idDeposito\": 526\r\n" + 
    		"            },\r\n" + 
    		"            {\r\n" + 
    		"                \"id\": 205,\r\n" + 
    		"                \"idDeposito\": 525\r\n" + 
    		"            }\r\n" + 
    		"        ]\r\n" + 
    		"    },\r\n" + 
    		"    {\r\n" + 
    		"        \"idPromo\": \"2d39bd25-06af-42b6-bfaf-05c319035a19\",\r\n" + 
    		"        \"anno\": 2019,\r\n" + 
    		"        \"codice\": \"TST01     \",\r\n" + 
    		"        \"descrizione\": \"CAMPAGNA TEST INSERIMENTO\",\r\n" + 
    		"        \"dettPromo\": [\r\n" + 
    		"            {\r\n" + 
    		"                \"id\": 126,\r\n" + 
    		"                \"riga\": 1,\r\n" + 
    		"                \"codArt\": \"058310201\",\r\n" + 
    		"                \"codFid\": \"\",\r\n" + 
    		"                \"inizio\": \"2019-01-01\",\r\n" + 
    		"                \"fine\": \"2019-12-31\",\r\n" + 
    		"                \"oggetto\": \"1.99\",\r\n" + 
    		"                \"isFid\": \"No\",\r\n" + 
    		"                \"tipoPromo\": {\r\n" + 
    		"                    \"idTipoPromo\": 1,\r\n" + 
    		"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
    		"                }\r\n" + 
    		"            },\r\n" + 
    		"            {\r\n" + 
    		"                \"id\": 125,\r\n" + 
    		"                \"riga\": 2,\r\n" + 
    		"                \"codArt\": \"057549001\",\r\n" + 
    		"                \"codFid\": \"\",\r\n" + 
    		"                \"inizio\": \"2019-01-01\",\r\n" + 
    		"                \"fine\": \"2019-12-31\",\r\n" + 
    		"                \"oggetto\": \"2.89\",\r\n" + 
    		"                \"isFid\": \"No\",\r\n" + 
    		"                \"tipoPromo\": {\r\n" + 
    		"                    \"idTipoPromo\": 1,\r\n" + 
    		"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
    		"                }\r\n" + 
    		"            }\r\n" + 
    		"        ],\r\n" + 
    		"        \"depRifPromo\": [\r\n" + 
    		"            {\r\n" + 
    		"                \"id\": 48,\r\n" + 
    		"                \"idDeposito\": 526\r\n" + 
    		"            },\r\n" + 
    		"            {\r\n" + 
    		"                \"id\": 47,\r\n" + 
    		"                \"idDeposito\": 525\r\n" + 
    		"            }\r\n" + 
    		"        ]\r\n" + 
    		"    }\r\n" + 
    		"]";
	
	String JsonDataCreate =
			" {\r\n" + 
			"        \"idPromo\": \"\",\r\n" + 
			"        \"anno\": 2019,\r\n" + 
			"        \"codice\": \"UT02\",\r\n" + 
			"        \"descrizione\": \"CAMPAGNA TEST INSERIMENTO\",\r\n" + 
			"        \"dettPromo\": [\r\n" + 
			"			{\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"riga\": 1,\r\n" + 
			"                \"codArt\": \"058310201\",\r\n" + 
			"                \"codFid\": \"\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-12-31\",\r\n" + 
			"                \"oggetto\": \"1.99\",\r\n" + 
			"                \"isFid\": \"No\",\r\n" + 
			"                \"tipoPromo\": {\r\n" + 
			"                    \"idTipoPromo\": \"1\",\r\n" + 
			"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
			"                }\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"riga\": 2,\r\n" + 
			"                \"codArt\": \"000020030\",\r\n" + 
			"                \"codFid\": \"\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-12-31\",\r\n" + 
			"                \"oggetto\": \"0.39\",\r\n" + 
			"                \"isFid\": \"Si\",\r\n" + 
			"                \"tipoPromo\": {\r\n" + 
			"                    \"idTipoPromo\": \"1\",\r\n" + 
			"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
			"                }\r\n" + 
			"            }\r\n" + 
			"        ],\r\n" + 
			"        \"depRifPromo\": [\r\n" + 
			"            {\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"idDeposito\": 526\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"idDeposito\": 525\r\n" + 
			"            }\r\n" + 
			"        ]\r\n" + 
			"}";
	
	String JsonDataCreate22 =
			" {\r\n" + 
			"        \"idPromo\": \"abc\",\r\n" + 
			"        \"anno\": 2019,\r\n" + 
			"        \"codice\": \"UT02\",\r\n" + 
			"        \"descrizione\": \"CAMPAGNA TEST INSERIMENTO\",\r\n" + 
			"        \"dettPromo\": [\r\n" + 
			"			{\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"riga\": 1,\r\n" + 
			"                \"codArt\": \"058310201\",\r\n" + 
			"                \"codFid\": \"\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-12-31\",\r\n" + 
			"                \"oggetto\": \"1.99\",\r\n" + 
			"                \"isFid\": \"No\",\r\n" + 
			"                \"tipoPromo\": {\r\n" + 
			"                    \"idTipoPromo\": \"1\",\r\n" + 
			"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
			"                }\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"riga\": 2,\r\n" + 
			"                \"codArt\": \"000020030\",\r\n" + 
			"                \"codFid\": \"\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-12-31\",\r\n" + 
			"                \"oggetto\": \"0.39\",\r\n" + 
			"                \"isFid\": \"Si\",\r\n" + 
			"                \"tipoPromo\": {\r\n" + 
			"                    \"idTipoPromo\": \"1\",\r\n" + 
			"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
			"                }\r\n" + 
			"            }\r\n" + 
			"        ],\r\n" + 
			"        \"depRifPromo\": [\r\n" + 
			"            {\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"idDeposito\": 526\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"idDeposito\": 525\r\n" + 
			"            }\r\n" + 
			"        ]\r\n" + 
			"}";
	public String getstring() {
	Promo promo=promoRepository.findByAnnoAndCodice(2019, "UT02");
	String s=promo.getIdPromo();
	String JsonDataCreate2 =
			" {\r\n" + 
			"        \"idPromo\": \""+s+"\",\r\n" + 
			"        \"anno\": 2019,\r\n" + 
			"        \"codice\": \"UT02\",\r\n" + 
			"        \"descrizione\": \"CAMPAGNA TEST INSERIMENTO\",\r\n" + 
			"        \"dettPromo\": [\r\n" + 
			"			{\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"riga\": 1,\r\n" + 
			"                \"codArt\": \"058310201\",\r\n" + 
			"                \"codFid\": \"\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-12-31\",\r\n" + 
			"                \"oggetto\": \"2.99\",\r\n" + 
			"                \"isFid\": \"No\",\r\n" + 
			"                \"tipoPromo\": {\r\n" + 
			"                    \"idTipoPromo\": \"1\",\r\n" + 
			"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
			"                }\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"riga\": 2,\r\n" + 
			"                \"codArt\": \"000020030\",\r\n" + 
			"                \"codFid\": \"\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-12-31\",\r\n" + 
			"                \"oggetto\": \"2.39\",\r\n" + 
			"                \"isFid\": \"Si\",\r\n" + 
			"                \"tipoPromo\": {\r\n" + 
			"                    \"idTipoPromo\": \"1\",\r\n" + 
			"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
			"                }\r\n" + 
			"            }\r\n" + 
			"        ],\r\n" + 
			"        \"depRifPromo\": [\r\n" + 
			"            {\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"idDeposito\": 526\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"idDeposito\": 525\r\n" + 
			"            }\r\n" + 
			"        ]\r\n" + 
			"}";
	return JsonDataCreate2;
	}

}
