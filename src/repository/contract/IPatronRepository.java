package repository.contract;

import java.util.Optional;

import model.Patron;
import repository.contract.base.IBaseRepository;

public interface IPatronRepository extends IBaseRepository<Patron, String> {
    Optional<Patron> getByEmail(final String email);
}