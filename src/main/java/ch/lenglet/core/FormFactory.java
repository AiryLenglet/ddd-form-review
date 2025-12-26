package ch.lenglet.core;

import java.util.Set;

public class FormFactory {

    public static Form createFromJson(String jsonString) {
        return AuthorizationForm.wrap(
                FormImpl.fromJson(jsonString)
        );
    }

    public static Form newForm(long caseId, Set<InternalAnswer> answers) {
        return AuthorizationForm.wrap(
                FormImpl.of(caseId, answers)
        );
    }
}
