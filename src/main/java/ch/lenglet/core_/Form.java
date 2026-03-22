package ch.lenglet.core_;

import static ch.lenglet.core_.Form.*;

public sealed interface Form permits CancelledForm, EscalationForm, ReviewForm, ScoringForm, CompleteForm {

    //CancelledForm cancel(String cancellationReason);

    non-sealed interface ReviewForm extends Form {
        Next review();

        sealed interface Next {

            record Complete(CompleteForm form) implements Next {}
            record Escalation(EscalationForm form) implements Next {}
        }
    }

    non-sealed interface ScoringForm extends Form {
        ReviewForm score();
    }

    non-sealed interface CompleteForm extends Form {
    }

    non-sealed interface CancelledForm extends Form {
    }

    non-sealed interface EscalationForm extends Form {
    }

    sealed interface Status {

        record Completed() implements Status {
        }

        record Conclusion(
                Decision decision,
                String comment
        ) {
        }

        enum Decision {
            APPROVE,
            NOT_APPROVE,
        }

        record Cancelled(String cancellationReason) implements Status {
        }

        record Scoring() implements Status {
        }

        record Review() implements Status {
        }

        record Escalation() implements Status {
        }
    }

    static void test(Form form) {
        switch (form) {
            case ScoringForm scoringForm -> {
                scoringForm.score();
            }
            case ReviewForm reviewForm -> {
                reviewForm.review();
            }
            case CancelledForm cancelledForm -> {
            }
            case CompleteForm completeForm -> {
            }
            case EscalationForm escalationForm -> {
            }
        }
    }

    static void main(String... args) {
        test(Forms.create());
    }
}
