package com.music.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.music.application.entity.Album;
import com.music.application.entity.Artist;
import com.music.application.entity.Customer;
import com.music.application.entity.Genre;
import com.music.application.entity.Invoice;
import com.music.application.entity.InvoiceLine;
import com.music.application.entity.MediaType;
import com.music.application.entity.Track;

@SpringBootTest
@Transactional
public class InvoiceLineServiceIntegrationTest {

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

    private InvoiceLine invoiceLine;

    @BeforeEach
    void setUp() {
        Artist artist = new Artist();
        artist.setName("Test Artist");
        artist = artistService.save(artist);

        Album album = new Album();
        album.setTitle("Test Album");
        album.setArtist(artist);
        album = albumService.save(album);

        Genre genre = new Genre();
        genre.setName("Test Genre");
        genre = genreService.save(genre);

        MediaType mediaType = new MediaType();
        mediaType.setName("Test MediaType");
        mediaType = mediaTypeService.save(mediaType);

        Track track = new Track();
        track.setName("Test Track");
        track.setAlbum(album);
        track.setGenre(genre);
        track.setMediaType(mediaType);
        track.setMilliseconds(1000);
        track.setUnitPrice(1.99);
        track = trackService.save(track);

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com"); // Added email to satisfy NOT NULL constraint
        customer = customerService.save(customer);

        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setInvoiceDate(java.time.LocalDate.now());
        invoice.setTotal(10.0);
        invoice = invoiceService.save(invoice);

        invoiceLine = new InvoiceLine();
        invoiceLine.setInvoice(invoice);
        invoiceLine.setTrack(track);
        invoiceLine.setUnitPrice(1.99);
        invoiceLine.setQuantity(1);
        invoiceLine = invoiceLineService.save(invoiceLine);
    }

    @Test
    void testCreateAndFindInvoiceLine() {
        InvoiceLine found = invoiceLineService.findById(invoiceLine.getInvoiceLineId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getUnitPrice()).isEqualTo(1.99);
    }

    @Test
    void testUpdateInvoiceLine() {
        invoiceLine.setQuantity(2);
        InvoiceLine updated = invoiceLineService.save(invoiceLine);
        assertThat(updated.getQuantity()).isEqualTo(2);
    }

    @Test
    void testDeleteInvoiceLine() {
        Integer id = invoiceLine.getInvoiceLineId();
        invoiceLineService.deleteById(id);
        assertThat(invoiceLineService.findById(id)).isEmpty();
    }

    @Test
    void testCreateInvoiceLine() {
        InvoiceLine newLine = new InvoiceLine();
        newLine.setInvoice(invoiceLine.getInvoice());
        newLine.setTrack(invoiceLine.getTrack());
        newLine.setUnitPrice(2.99);
        newLine.setQuantity(3);
        InvoiceLine saved = invoiceLineService.save(newLine);
        assertThat(saved.getInvoiceLineId()).isNotNull();
        assertThat(saved.getQuantity()).isEqualTo(3);
    }

    @Test
    void testReadInvoiceLine() {
        InvoiceLine found = invoiceLineService.findById(invoiceLine.getInvoiceLineId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getUnitPrice()).isEqualTo(1.99);
    }

    @Test
    void testUpdateInvoiceLineCRUD() {
        invoiceLine.setQuantity(5);
        InvoiceLine updated = invoiceLineService.save(invoiceLine);
        assertThat(updated.getQuantity()).isEqualTo(5);
    }

    @Test
    void testDeleteInvoiceLineCRUD() {
        InvoiceLine newLine = new InvoiceLine();
        newLine.setInvoice(invoiceLine.getInvoice());
        newLine.setTrack(invoiceLine.getTrack());
        newLine.setUnitPrice(3.99);
        newLine.setQuantity(4);
        InvoiceLine saved = invoiceLineService.save(newLine);
        Integer id = saved.getInvoiceLineId();
        invoiceLineService.deleteById(id);
        assertThat(invoiceLineService.findById(id)).isEmpty();
    }
}
