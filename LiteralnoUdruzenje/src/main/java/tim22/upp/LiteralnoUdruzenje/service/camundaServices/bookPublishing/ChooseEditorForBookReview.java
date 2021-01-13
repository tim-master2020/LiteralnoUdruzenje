package tim22.upp.LiteralnoUdruzenje.service.camundaServices.bookPublishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Book;
import tim22.upp.LiteralnoUdruzenje.model.enums.Role;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

import java.util.List;
import java.util.Random;

@Service
public class ChooseEditorForBookReview  implements JavaDelegate {

    @Autowired
    private IUserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Choosing one editor to review this book");

        List<User> editors = userService.findByRole(Role.EDITOR);
        Random r = new Random();
        int low = 0;
        int high = editors.size();
        int result = r.nextInt(high-low) + low;
        User editor = editors.get(result);

        delegateExecution.setVariable("editorsUsername",editor.getUsername());
        System.out.println(editor.getUsername());
    }
}
