package com.company.controllers;

import com.company.model.Profession;
import com.company.service.ProfessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProfessionController {

    private ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @RequestMapping(value = "profession/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Profession getEmployee(@PathVariable Integer id) {
        return professionService.get(id);
    }

    @RequestMapping(value = "profession", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Profession> getAll() {
        return professionService.getAll();
    }

    @RequestMapping(value = "profession", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Profession> createProfession(@RequestBody Profession profession) throws URISyntaxException {
        try {
            Profession result = professionService.save(profession);
            return ResponseEntity.created(new URI("/api/profession/" + result.getId())).body(result);
        } catch (EntityExistsException e) {
            return new ResponseEntity<Profession>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "profession", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Profession> updateProfession(@RequestBody Profession profession) throws URISyntaxException {
        if (profession.getId() == null) {
            return new ResponseEntity<Profession>(HttpStatus.NOT_FOUND);
        }

        try {
            Profession result = professionService.update(profession);

            return ResponseEntity.created(new URI("/api/profession/" + result.getId())).body(result);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<Profession>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/profession/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        professionService.delete(id);

        return ResponseEntity.ok().build();
    }
}
