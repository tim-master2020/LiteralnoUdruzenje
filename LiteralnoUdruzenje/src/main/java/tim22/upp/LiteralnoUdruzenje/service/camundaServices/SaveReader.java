package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import java.util.*;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Authority;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.model.Role;
import tim22.upp.LiteralnoUdruzenje.service.IAuthorityService;
import tim22.upp.LiteralnoUdruzenje.service.IGenreService;
import tim22.upp.LiteralnoUdruzenje.service.IReaderService;

@Service
public class SaveReader implements JavaDelegate{

    @Autowired
    private IdentityService identityService;

    @Autowired
    private IReaderService IReaderService;

    @Autowired
    private IGenreService genreService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAuthorityService authorityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        HashMap<String, Object> registration = (HashMap<String, Object>) execution.getVariable("registrationReader");
        ArrayList<LinkedHashMap<String,String>> betaGenres = ( ArrayList<LinkedHashMap<String,String>>) execution.getVariable("betaGenres");

        Reader reader = new Reader();
        reader.setUsername(registration.get("username").toString());
        reader.setPassword(passwordEncoder.encode(registration.get("password").toString()));
        reader.setEmail(registration.get("email").toString());
        reader.setFirstName(registration.get("firstname").toString());
        reader.setLastName(registration.get("lastname").toString());
        reader.setCountry(registration.get("country").toString());
        reader.setCity(registration.get("city").toString());
        reader.setBetaReader(Boolean.parseBoolean(registration.get("betaReader").toString()));
        reader.setRole(Role.READER);
        ArrayList<LinkedHashMap<String,String>> genres = (ArrayList<LinkedHashMap<String, String>>) registration.get("Genres");

        Authority authoritie = authorityService.findByName("READER");
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authoritie);
        reader.setAuthorities(authorities);


        Set<Genre> readerGenres =  new HashSet<>();
        for (LinkedHashMap<String,String> oneOption : genres){
            readerGenres.add(genreService.findByName(oneOption.get("value")));
        }
        reader.setGenres(readerGenres);

        Set<Genre> betas =  new HashSet<>();
        if(betaGenres != null){
            for (LinkedHashMap<String,String> oneOption : betaGenres){
                betas.add(genreService.findByName(oneOption.get("value")));
            }
            reader.setBetaGenres(betas);
        }
        Reader isSaved = IReaderService.saveReader(reader);
        if(reader != null) {
            runtimeService.setVariable(execution.getProcessInstanceId(), "registeredReader",isSaved);
        }
    }
}
