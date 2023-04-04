package ch.es.pl.quotes.api.endpoints;

import org.openapitools.api.QuotesApi;
import ch.es.pl.quotes.api.exceptions.QuoteNotFoundException;
import org.openapitools.model.Quote;
import ch.es.pl.quotes.api.entities.QuoteEntity;
import ch.es.pl.quotes.api.repositories.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class QuotesEndPoint implements QuotesApi {

    @Autowired
    private QuoteRepository quoteRepository;

    @Override
    public ResponseEntity<List<Quote>> getQuotes() {
        List<QuoteEntity> quoteEntities= quoteRepository.findAll();
        List<Quote> quotes  = new ArrayList<>();
        for (QuoteEntity quoteEntity : quoteEntities) {
            Quote quote = new Quote();
            quote.setId(quoteEntity.getId());
            quote.setAuthor(quoteEntity.getAuthor());
            quote.setCitation(quoteEntity.getCitation());
            quotes.add(quote);
        }
        return new ResponseEntity<List<Quote>>(quotes,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addQuote(@RequestBody Quote quote) {
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setAuthor(quote.getAuthor());
        quoteEntity.setCitation(quote.getCitation());
        QuoteEntity quoteAdded = quoteRepository.save(quoteEntity);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(quoteAdded.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<Quote> getQuote(Integer id) {
        Optional<QuoteEntity> opt = quoteRepository.findById(id);
        if (opt.isPresent()) {
            QuoteEntity quoteEntity = opt.get();
            Quote quote = new Quote();
            quote.setId(quoteEntity.getId());
            quote.setAuthor(quoteEntity.getAuthor());
            quote.setCitation(quoteEntity.getCitation());
            return new ResponseEntity<Quote>(quote, HttpStatus.OK);
        } else {
//            return ResponseEntity.notFound().build();
            throw new QuoteNotFoundException(id);
        }
    }

}
