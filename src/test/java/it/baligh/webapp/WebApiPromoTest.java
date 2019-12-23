package it.baligh.webapp;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import it.baligh.webapp.entities.Promo;
import it.baligh.webapp.repository.PromoRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WebApiPromoTest 
{
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private PromoRepository promoRepository;
			
	@Before
	public void setup()
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void A_testlistAllPromo() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/promo/")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(6)))
				.andDo(print());
	}
	
	
	String JsonData =
			" {\r\n" + 
			"        \"idPromo\": \"\",\r\n" + 
			"        \"anno\": 2019,\r\n" + 
			"        \"codice\": \"UT01\",\r\n" + 
			"        \"descrizione\": \"CAMPAGNA TEST INSERIMENTO\",\r\n" + 
			"        \"dettPromo\": [\r\n" + 
			"			{\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"riga\": 1,\r\n" + 
			"                \"codart\": \"058310201\",\r\n" + 
			"                \"codfid\": \"\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-12-31\",\r\n" + 
			"                \"oggetto\": \"1.99\",\r\n" + 
			"                \"isfid\": \"No\",\r\n" + 
			"                \"tipoPromo\": {\r\n" + 
			"                    \"idTipoPromo\": \"1\",\r\n" + 
			"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
			"                }\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"riga\": 2,\r\n" + 
			"                \"codart\": \"000020030\",\r\n" + 
			"                \"codfid\": \"\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-12-31\",\r\n" + 
			"                \"oggetto\": \"0.39\",\r\n" + 
			"                \"isfid\": \"Si\",\r\n" + 
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
	
	
	@Test
	public void B_testCreatePromo() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api/promo/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());
	}
	
	@Test
	public void C_testlistPromoById() throws Exception
	{
		Promo promo = promoRepository.findByAnnoAndCodice(2019, "UT01");
		
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/promo/id/" + promo.getIdPromo())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				
				.andExpect(jsonPath("$.idPromo").exists())
				.andExpect(jsonPath("$.idPromo").value(promo.getIdPromo()))
				
				//Prima riga Promozione
				.andExpect(jsonPath("$.dettPromo[0].id").exists())
				.andExpect(jsonPath("$.dettPromo[0].riga").exists())
				.andExpect(jsonPath("$.dettPromo[0].riga").value("1"))
				.andExpect(jsonPath("$.dettPromo[0].codart").exists())
				.andExpect(jsonPath("$.dettPromo[0].codart").value("058310201"))
				.andExpect(jsonPath("$.dettPromo[0].oggetto").exists())
				.andExpect(jsonPath("$.dettPromo[0].oggetto").value("1.99")) 
				.andExpect(jsonPath("$.dettPromo[0].isfid").exists())
				.andExpect(jsonPath("$.dettPromo[0].isfid").value("No")) 
				
				//Tipo Promozione
				.andExpect(jsonPath("$.dettPromo[0].tipoPromo.descrizione").exists())
				.andExpect(jsonPath("$.dettPromo[0].tipoPromo.descrizione").value("TAGLIO PREZZO")) 
				
				//Seconda riga Promozione
				.andExpect(jsonPath("$.dettPromo[1].id").exists())
				.andExpect(jsonPath("$.dettPromo[1].riga").exists())
				.andExpect(jsonPath("$.dettPromo[1].riga").value("2"))
				.andExpect(jsonPath("$.dettPromo[1].codart").exists())
				.andExpect(jsonPath("$.dettPromo[1].codart").value("000020030"))
				.andExpect(jsonPath("$.dettPromo[1].oggetto").exists())
				.andExpect(jsonPath("$.dettPromo[1].oggetto").value("0.39")) 
				.andExpect(jsonPath("$.dettPromo[1].isfid").exists())
				.andExpect(jsonPath("$.dettPromo[1].isfid").value("Si")) 
				
				.andExpect(jsonPath("$.dettPromo[1].tipoPromo.descrizione").exists())
				.andExpect(jsonPath("$.dettPromo[1].tipoPromo.descrizione").value("TAGLIO PREZZO")) 
				
				.andReturn();
	}
	
	private String GetNewData()
	{
		
	String retVal =
			
			" {\r\n" + 
			"        \"idPromo\": \"" + promoRepository.findByAnnoAndCodice(2019, "UT01").getIdPromo() + "\",\r\n" + 
			"        \"anno\": 2019,\r\n" + 
			"        \"codice\": \"UT01\",\r\n" + 
			"        \"descrizione\": \"CAMPAGNA TEST INSERIMENTO\",\r\n" + 
			"        \"dettPromo\": [\r\n" + 
			"			{\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"riga\": 1,\r\n" + 
			"                \"codart\": \"058310201\",\r\n" + 
			"                \"codfid\": \"\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-12-31\",\r\n" + 
			"                \"oggetto\": \"1.89\",\r\n" +  //Modificato oggetto
			"                \"isfid\": \"No\",\r\n" + 
			"                \"tipoPromo\": {\r\n" + 
			"                    \"idTipoPromo\": \"1\",\r\n" + 
			"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
			"                }\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"id\": -1,\r\n" + 
			"                \"riga\": 2,\r\n" + 
			"                \"codart\": \"000020030\",\r\n" + 
			"                \"codfid\": \"\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-12-31\",\r\n" + 
			"                \"oggetto\": \"0.39\",\r\n" + 
			"                \"isfid\": \"No\",\r\n" +  //Modificato isfid
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
	
		return retVal;
	}
	
	@Test
	public void D_testUpdatePromo() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.put("/api/promo/modifica")
				.contentType(MediaType.APPLICATION_JSON)
				.content(GetNewData())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());
	}
	
	@Test
	public void E_listPromoByCodice() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/promo/codice?anno=2019&codice=UT01")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				
				.andExpect(jsonPath("$.idPromo").exists())
				.andExpect(jsonPath("$.idPromo").value(promoRepository.findByAnnoAndCodice(2019, "UT01").getIdPromo()))
				
				.andExpect(jsonPath("$.dettPromo[0].id").exists())
				.andExpect(jsonPath("$.dettPromo[0].riga").exists())
				.andExpect(jsonPath("$.dettPromo[0].riga").value("1"))
				.andExpect(jsonPath("$.dettPromo[0].codart").exists())
				.andExpect(jsonPath("$.dettPromo[0].codart").value("058310201"))
				.andExpect(jsonPath("$.dettPromo[0].oggetto").exists())
				.andExpect(jsonPath("$.dettPromo[0].oggetto").value("1.89")) 
				.andExpect(jsonPath("$.dettPromo[0].isfid").exists())
				.andExpect(jsonPath("$.dettPromo[0].isfid").value("No")) 
				
				.andExpect(jsonPath("$.dettPromo[0].tipoPromo.descrizione").exists())
				.andExpect(jsonPath("$.dettPromo[0].tipoPromo.descrizione").value("TAGLIO PREZZO")) 
				
				.andExpect(jsonPath("$.dettPromo[1].id").exists())
				.andExpect(jsonPath("$.dettPromo[1].riga").exists())
				.andExpect(jsonPath("$.dettPromo[1].riga").value("2"))
				.andExpect(jsonPath("$.dettPromo[1].codart").exists())
				.andExpect(jsonPath("$.dettPromo[1].codart").value("000020030"))
				.andExpect(jsonPath("$.dettPromo[1].oggetto").exists())
				.andExpect(jsonPath("$.dettPromo[1].oggetto").value("0.39")) 
				.andExpect(jsonPath("$.dettPromo[1].isfid").exists())
				.andExpect(jsonPath("$.dettPromo[1].isfid").value("No")) 
				
				.andExpect(jsonPath("$.dettPromo[1].tipoPromo.descrizione").exists())
				.andExpect(jsonPath("$.dettPromo[1].tipoPromo.descrizione").value("TAGLIO PREZZO")) 
				
				.andDo(print());
				
				
	}
	
	
	@Test
	public void F_testDeletePromo() throws Exception
	{
		Promo promo = promoRepository.findByAnnoAndCodice(2019, "UT01");
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/promo/elimina/" + promo.getIdPromo())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andDo(print());
	}
	
	String JsonData2 = 
			"[\r\n" + 
			"    {\r\n" + 
			"        \"idPromo\": \"274147E6-CE80-4320-A28E-7EBC0EBEF228\",\r\n" + 
			"        \"anno\": 2018,\r\n" + 
			"        \"codice\": \"PF01      \",\r\n" + 
			"        \"descrizione\": \"PROMO FIDELITY ONLY YOU\",\r\n" + 
			"        \"dettPromo\": [\r\n" + 
			"            {\r\n" + 
			"                \"id\": 5,\r\n" + 
			"                \"riga\": 1,\r\n" + 
			"                \"codart\": \"049477701\",\r\n" + 
			"                \"codfid\": \"67000056\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-12-31\",\r\n" + 
			"                \"oggetto\": \"2,99\",\r\n" + 
			"                \"isfid\": \"Si\",\r\n" + 
			"                \"tipoPromo\": {\r\n" + 
			"                    \"idTipoPromo\": \"1\",\r\n" + 
			"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
			"                }\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"id\": 18,\r\n" + 
			"                \"riga\": 2,\r\n" + 
			"                \"codart\": \"058310201\",\r\n" + 
			"                \"codfid\": \"67000056\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-12-31\",\r\n" + 
			"                \"oggetto\": \"1,99\",\r\n" + 
			"                \"isfid\": \"Si\",\r\n" + 
			"                \"tipoPromo\": {\r\n" + 
			"                    \"idTipoPromo\": \"1\",\r\n" + 
			"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
			"                }\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"id\": 109,\r\n" + 
			"                \"riga\": 3,\r\n" + 
			"                \"codart\": \"000001501\",\r\n" + 
			"                \"codfid\": \"\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-12-31\",\r\n" + 
			"                \"oggetto\": \"3,90\",\r\n" + 
			"                \"isfid\": \"Si\",\r\n" + 
			"                \"tipoPromo\": {\r\n" + 
			"                    \"idTipoPromo\": \"1\",\r\n" + 
			"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
			"                }\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"id\": 110,\r\n" + 
			"                \"riga\": 4,\r\n" + 
			"                \"codart\": \"000001501\",\r\n" + 
			"                \"codfid\": \"\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-12-31\",\r\n" + 
			"                \"oggetto\": \"3,90\",\r\n" + 
			"                \"isfid\": \"Si\",\r\n" + 
			"                \"tipoPromo\": {\r\n" + 
			"                    \"idTipoPromo\": \"1\",\r\n" + 
			"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
			"                }\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"id\": 111,\r\n" + 
			"                \"riga\": 5,\r\n" + 
			"                \"codart\": \"049477701\",\r\n" + 
			"                \"codfid\": \"67000056\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-12-31\",\r\n" + 
			"                \"oggetto\": \"2,89\",\r\n" + 
			"                \"isfid\": \"Si\",\r\n" + 
			"                \"tipoPromo\": {\r\n" + 
			"                    \"idTipoPromo\": \"1\",\r\n" + 
			"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
			"                }\r\n" + 
			"            }\r\n" + 
			"        ],\r\n" + 
			"        \"depRifPromo\": []\r\n" + 
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
			"                \"codart\": \"058310201\",\r\n" + 
			"                \"codfid\": \"\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-05-31\",\r\n" + 
			"                \"oggetto\": \"1.99\",\r\n" + 
			"                \"isfid\": \"No\",\r\n" + 
			"                \"tipoPromo\": {\r\n" + 
			"                    \"idTipoPromo\": \"1\",\r\n" + 
			"                    \"descrizione\": \"TAGLIO PREZZO\"\r\n" + 
			"                }\r\n" + 
			"            },\r\n" + 
			"            {\r\n" + 
			"                \"id\": 125,\r\n" + 
			"                \"riga\": 2,\r\n" + 
			"                \"codart\": \"057549001\",\r\n" + 
			"                \"codfid\": \"\",\r\n" + 
			"                \"inizio\": \"2019-01-01\",\r\n" + 
			"                \"fine\": \"2019-05-31\",\r\n" + 
			"                \"oggetto\": \"2.89\",\r\n" + 
			"                \"isfid\": \"No\",\r\n" + 
			"                \"tipoPromo\": {\r\n" + 
			"                    \"idTipoPromo\": \"1\",\r\n" + 
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
	
	
	@Test
	public void G_testListPromoActive() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/promo/active")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(content().json(JsonData2)) 
			.andReturn();
	}
	
	@Test
	public void G_testListPromoActive2() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/promo/active")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andDo(print());
	}
	
	@Test
	public void E_ErrListPromoById() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/promo/id/ABC")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.codice").value(404))
				.andExpect(jsonPath("$.messaggio").value("Promozione Assente o Id Errato"))
				.andDo(print());
	}
	
	
}
