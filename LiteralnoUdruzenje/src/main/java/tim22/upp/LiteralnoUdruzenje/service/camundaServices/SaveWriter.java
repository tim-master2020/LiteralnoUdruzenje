package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.*;
import tim22.upp.LiteralnoUdruzenje.model.enums.Role;
import tim22.upp.LiteralnoUdruzenje.service.IAuthorityService;
import tim22.upp.LiteralnoUdruzenje.service.IGenreService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

import java.util.*;

@Service
public class SaveWriter implements JavaDelegate {

    @Autowired
    private IdentityService identityService;

    @Autowired
    private IWriterService writerService;

    @Autowired
    private IGenreService genreService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAuthorityService authorityService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        HashMap<String, Object> registration = (HashMap<String, Object>) delegateExecution.getVariable("registration");

        Writer writer = new Writer();
        writer.setUsername(registration.get("username").toString());
        writer.setPassword(passwordEncoder.encode(registration.get("password").toString()));
        writer.setEmail(registration.get("email").toString());
        writer.setFirstName(registration.get("firstname").toString());
        writer.setLastName(registration.get("lastname").toString());
        writer.setCountry(registration.get("country").toString());
        writer.setCity(registration.get("city").toString());
        writer.setRole(Role.WRITER);
        ArrayList<LinkedHashMap<String,String>> genres = (ArrayList<LinkedHashMap<String, String>>) registration.get("Genres");

        Authority authoritie = authorityService.findByName("WRITER");
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authoritie);
        writer.setAuthorities(authorities);

        Set<Genre> writerGenres =  new HashSet<>();
        for (LinkedHashMap<String,String> oneOption : genres){
            writerGenres.add(genreService.findById(Long.parseLong(oneOption.get("value"))));
        }

        writer.setGenres(writerGenres);
        Writer writerSaved = writerService.saveWriter(writer);

        if(writerSaved != null) {
            delegateExecution.setVariable("isWriterSaved",true);
            runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "registeredUser", writerSaved);
            User camundaUser = identityService.newUser(writerSaved.getUsername());
            camundaUser.setLastName(writerSaved.getLastName());
            camundaUser.setFirstName(writerSaved.getFirstName());
            camundaUser.setEmail(writerSaved.getEmail());
            camundaUser.setPassword(writerSaved.getPassword());
            camundaUser.setId((writerSaved.getId()));
            identityService.saveUser(camundaUser);
            delegateExecution.setVariable("writer",writerSaved.getUsername());
        }
    }
}
