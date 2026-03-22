package ch.lenglet.core_;

class ScoringFormImpl extends AbstractForm implements Form.ScoringForm {

    public ScoringFormImpl(FormData formData) {
        super(formData);
    }

    @Override
    public ReviewForm score() {
        this.formData.status = new Status.Review();
        return new ReviewFormImpl(this.formData);
    }
}
