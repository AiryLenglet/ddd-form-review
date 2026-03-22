package ch.lenglet.core_;

abstract class AbstractForm {

    protected FormData formData;

    public AbstractForm(
            FormData formData
    ) {
        this.formData = formData;
    }
}
