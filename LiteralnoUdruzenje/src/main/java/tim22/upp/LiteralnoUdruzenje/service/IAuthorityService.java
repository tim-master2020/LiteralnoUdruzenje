package tim22.upp.LiteralnoUdruzenje.service;

import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Authority;

public interface IAuthorityService {
    Authority findByName(String name);
}
