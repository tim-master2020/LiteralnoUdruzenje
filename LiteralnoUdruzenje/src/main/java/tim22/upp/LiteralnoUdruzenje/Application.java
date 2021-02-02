package tim22.upp.LiteralnoUdruzenje;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class Application {

  @Autowired
  private IdentityService identityService;

  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }

  @PostConstruct
  private void InitGroups(){
    List<Group> groups = identityService.createGroupQuery()
            .groupIdIn("readers", "betaReaders", "writers", "editors", "committee", "lectors", "headCommittee", "headEditor").list();
    if (groups.isEmpty()) {

      Group readerGroup = identityService.newGroup("readers");
      identityService.saveGroup(readerGroup);

      Group betaReaderGroup = identityService.newGroup("betaReaders");
      identityService.saveGroup(betaReaderGroup);

      Group writerGroup = identityService.newGroup("writers");
      identityService.saveGroup(writerGroup);

      Group editorGroup = identityService.newGroup("editors");
      identityService.saveGroup(editorGroup);

      Group committeeGroup = identityService.newGroup("committee");
      identityService.saveGroup(committeeGroup);

      Group lectorGroup = identityService.newGroup("lectors");
      identityService.saveGroup(lectorGroup);

      Group headCommitteeGroup = identityService.newGroup("headCommittee");
      identityService.saveGroup(headCommitteeGroup);

      Group headEditorGroup = identityService.newGroup("headEditor");
      identityService.saveGroup(headEditorGroup);
    }}

    }