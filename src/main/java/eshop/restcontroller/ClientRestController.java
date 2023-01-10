package eshop.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import eshop.entity.Client;
import eshop.exception.ClientException;
import eshop.jsonview.Views;
import eshop.service.ClientService;
import eshop.util.Check;

@RestController
@RequestMapping("/api/client")
public class ClientRestController {

    @Autowired
    private ClientService  clientService;

    @GetMapping("")
    @JsonView(Views.Common.class)
    public List<Client> getAll(){
        return clientService.getAll();
    }
    @GetMapping("/page/{pageNumber}")
    @JsonView(Views.Common.class)
    public List<Client> getAll(@PathVariable int pageNumber){
    	if (pageNumber < 0 || pageNumber > clientService.getAll(PageRequest.ofSize(20)).getTotalPages()) {
			throw new ClientException("Numero page errone");
		}
        return clientService.getAll(PageRequest.of(pageNumber, 20)).getContent();
    }
    
    @GetMapping("/test")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/{id}")
    @JsonView(Views.Common.class)
    public Client getById(@PathVariable Long id) {
        return clientService.getById(id);
    }

    @GetMapping("/{id}/commandes")
    @JsonView(Views.Common.class)
    public Client getClientCommandes(@PathVariable Long id) {
        return clientService.getClientCommandesId(id);
    }

    @GetMapping("/name/{nom}")
    @JsonView(Views.Common.class)
    public List<Client> getByName(@PathVariable String nom) {
        return clientService.getByNom(nom);
    }

    @GetMapping("/name/{email}")
    @JsonView(Views.Common.class)
    public List<Client> getByEmail(@PathVariable String email) {
        return clientService.getByEmail(email);
    }

    @PostMapping("")
    @JsonView(Views.Common.class)
    public Client create(@Valid @RequestBody Client client, BindingResult br) {
        Check.checkBindingResulHasError(br);
        return clientService.create(client);
    }


    @PutMapping("/{id}")
    @JsonView(Views.Common.class)
    public Client update(@Valid @RequestBody Client client,
                         BindingResult br, @PathVariable Long id) {
        Check.checkBindingResulHasError(br);
        client.setId(id);
        return clientService.update(client);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }

}
