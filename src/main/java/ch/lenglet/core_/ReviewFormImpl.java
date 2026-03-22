package ch.lenglet.core_;

class ReviewFormImpl extends AbstractForm implements Form.ReviewForm {

    public ReviewFormImpl(FormData formData) {
        super(formData);
    }

    @Override
    public Next review() {
        System.out.println("review");
        this.formData.status = new Status.Escalation();
        return new Next.Escalation(new EscalationFormImpl(this.formData));
    }
}
