package ch.lenglet.core_;

public class ScoreService {

    void score(Form form) {
        if (form instanceof Form.ScoringForm scoringForm) {
            scoringForm.score();
        } else throw new IllegalStateException("Form is not in scoring state");
    }
}
