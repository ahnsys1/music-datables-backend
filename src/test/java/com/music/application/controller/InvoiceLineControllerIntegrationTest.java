package com.music.application.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.music.application.dto.InvoiceLineDTO;
import com.music.application.entity.Album;
import com.music.application.entity.Artist;
import com.music.application.entity.Customer;
import com.music.application.entity.Genre;
import com.music.application.entity.Invoice;
import com.music.application.entity.InvoiceLine;
import com.music.application.entity.Track;
import com.music.application.service.AlbumService;
import com.music.application.service.ArtistService;
import com.music.application.service.CustomerService;
import com.music.application.service.GenreService;
import com.music.application.service.InvoiceLineService;
import com.music.application.service.InvoiceService;
import com.music.application.service.MediaTypeService;
import com.music.application.service.TrackService;

@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceLineControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InvoiceLineService invoiceLineService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TrackService trackService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private MediaTypeService mediaTypeService;

    @Test
    void testCreateAndGetInvoiceLine() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Create dependencies
        Artist artist = new Artist();
        artist.setName("IntegrationTest InvoiceLine Artist");
        artist = artistService.save(artist);
        Album album = new Album();
        album.setTitle("IntegrationTest InvoiceLine Album");
        album.setArtist(artist);
        album = albumService.save(album);
        Genre genre = new Genre();
        genre.setName("IntegrationTest InvoiceLine Genre");
        genre = genreService.save(genre);
        com.music.application.entity.MediaType mediaType = new com.music.application.entity.MediaType();
        mediaType.setName("IntegrationTest InvoiceLine MediaType");
        mediaType = mediaTypeService.save(mediaType);
        Track track = new Track();
        track.setName("IntegrationTest InvoiceLine Track");
        track.setAlbum(album);
        track.setGenre(genre);
        track.setMediaType(mediaType);
        track.setMilliseconds(1000);
        track.setUnitPrice(1.99);
        track = trackService.save(track);
        Customer customer = new Customer();
        customer.setFirstName("IntegrationTest");
        customer.setLastName("InvoiceLineCustomer");
        customer.setEmail("integration.invoiceline@example.com");
        customer = customerService.save(customer);
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setInvoiceDate(java.time.LocalDate.now());
        invoice.setTotal(200.0);
        invoice = invoiceService.save(invoice);
        InvoiceLineDTO invoiceLineDTO = new InvoiceLineDTO();
        invoiceLineDTO.setInvoiceId(invoice.getInvoiceId());
        invoiceLineDTO.setTrackId(track.getTrackId());
        invoiceLineDTO.setUnitPrice(2.99);
        invoiceLineDTO.setQuantity(5);
        String json = objectMapper.writeValueAsString(invoiceLineDTO);
        // Create
        String response = mockMvc.perform(post("/api/invoicelines")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains("2.99");

        // Find by unitPrice (using service for id)
        InvoiceLine invoiceLine = invoiceLineService.findAll().stream()
                .filter(il -> il.getUnitPrice() == 2.99)
                .findFirst().orElse(null);
        assertThat(invoiceLine).isNotNull();

        // Get by id
        mockMvc.perform(get("/api/invoicelines/" + invoiceLine.getInvoiceLineId()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("2.99")));

        // Delete
        mockMvc.perform(delete("/api/invoicelines/" + invoiceLine.getInvoiceLineId()))
                .andExpect(status().isNoContent());
        invoiceService.deleteById(invoice.getInvoiceId());
        customerService.deleteById(customer.getCustomerId());
        trackService.deleteById(track.getTrackId());
        albumService.deleteById(album.getAlbumId());
        artistService.deleteById(artist.getArtistId());
        genreService.deleteById(genre.getGenreId());
        mediaTypeService.deleteById(mediaType.getMediaTypeId());
    }
}
