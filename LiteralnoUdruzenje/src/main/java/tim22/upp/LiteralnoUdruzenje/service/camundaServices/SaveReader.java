package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import java.util.*;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.dto.FormSubmissionDTO;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.service.IGenreService;
import tim22.upp.LiteralnoUdruzenje.service.ReaderService;

@Service
public class SaveReader implements JavaDelegate{

    @Autowired
    private IdentityService identityService;

    @Autowired
    private  ReaderService readerService;

    @Autowired
    private IGenreService genreService;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        HashMap<String, Object> registration = (HashMap<String, Object>) execution.getVariable("registration");
        ArrayList<LinkedHashMap<String,String>> betaGenres = ( ArrayList<LinkedHashMap<String,String>>) execution.getVariable("betaGenres");

        Reader reader = new Reader();
        reader.setUsername(registration.get("username").toString());
        reader.setPassword(registration.get("password").toString());
        reader.setEmail(registration.get("email").toString());
        reader.setFirstName(registration.get("firstname").toString());
        reader.setLastName(registration.get("lastname").toString());
        reader.setCountry(registration.get("country").toString());
        reader.setCity(registration.get("city").toString());
        reader.setBetaReader(Boolean.parseBoolean(registration.get("betaReader").toString()));
        ArrayList<LinkedHashMap<String,String>> genres = (ArrayList<LinkedHashMap<String, String>>) registration.get("Genres");
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
        Reader isSaved = readerService.saveReader(reader);
        if(reader != null) {
            runtimeService.setVariable(execution.getProcessInstanceId(), "registeredReader",isSaved);
        }
    }
}
