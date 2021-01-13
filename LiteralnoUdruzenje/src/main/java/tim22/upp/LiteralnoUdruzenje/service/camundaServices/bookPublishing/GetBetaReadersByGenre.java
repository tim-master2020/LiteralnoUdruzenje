package tim22.upp.LiteralnoUdruzenje.service.camundaServices.bookPublishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.model.enums.Role;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.service.IGenreService;
import tim22.upp.LiteralnoUdruzenje.service.IReaderService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetBetaReadersByGenre implements JavaDelegate {

    @Autowired
    private IReaderService readerService;

    @Autowired
    private IGenreService genreService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<Reader> betaReaders = readerService.findBetaReaders();

        HashMap<String, Object> generalBookData = (HashMap<String, Object>) delegateExecution.getVariable("generalBookData");
        LinkedHashMap<String,String> genres = (LinkedHashMap<String, String>) generalBookData.get("Genres");

        List<String> betaUsernames = betaReaders.stream()
                .filter(r -> r.getBetaGenres().contains(genreService.findById(Long.parseLong(genres.get("value")))))
                .map(User::getUsername).collect(Collectors.toList());

        delegateExecution.setVariable("betaReaders", betaUsernames);
        delegateExecution.setVariable("betaSum", betaUsernames.size());
    }
}
