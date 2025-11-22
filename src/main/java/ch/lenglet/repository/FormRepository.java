package ch.lenglet.repository;

import ch.lenglet.core.Form;

public interface FormRepository {
    Form findLatestByCaseId();
    void save(Form form);
}
