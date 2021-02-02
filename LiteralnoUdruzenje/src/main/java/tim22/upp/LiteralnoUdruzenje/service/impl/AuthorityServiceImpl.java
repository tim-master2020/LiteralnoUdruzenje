package tim22.upp.LiteralnoUdruzenje.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Authority;
import tim22.upp.LiteralnoUdruzenje.repository.AuthorityRepository;
import tim22.upp.LiteralnoUdruzenje.service.IAuthorityService;

@Service
public class AuthorityServiceImpl implements IAuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority findByName(String name) {
        return authorityRepository.findByName(name);
    }
}
