package eshop.restcontroller;

import com.fasterxml.jackson.annotation.JsonView;
import eshop.entity.Client;
import eshop.jsonview.Views;
import eshop.service.ClientService;
import eshop.util.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @GetMapping("")
    @JsonView(Views.Common.class)
    public List<Client> getAll(){
        return clientService.getAll();
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
        Check.checkBindingResultError(br);
        return clientService.update(client);
    }


    @PutMapping("/{id}")
    @JsonView(Views.Common.class)
    public Client update(@Valid @RequestBody Client client,
                         BindingResult br, @PathVariable Long id) {
        Check.checkBindingResultError(br);
        client.setId(id);
        return clientService.update(client);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }

}
