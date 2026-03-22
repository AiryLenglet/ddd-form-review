package ch.lenglet.core_;

public class Forms {

    private Forms() {
    }

    static Form.ScoringForm create() {
        final var data = new FormData(new Form.Status.Scoring());
        return new ScoringFormImpl(data);
    }
}
