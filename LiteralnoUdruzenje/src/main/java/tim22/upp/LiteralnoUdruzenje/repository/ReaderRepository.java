package tim22.upp.LiteralnoUdruzenje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim22.upp.LiteralnoUdruzenje.model.Reader;

public interface ReaderRepository extends JpaRepository<Reader, Long> {

    Reader save(Reader reader);
}