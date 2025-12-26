package ch.lenglet.core;

public class FormFactory {

    public static Form createFromJson(String jsonString) {
        return AuthorizationForm.wrap(
                FormImpl.fromJson(jsonString)
        );
    }
}
