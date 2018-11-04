package bol.model.api;

import javax.validation.constraints.NotNull;

public class UserInput {

    @NotNull
    private String mail;

    private int selectedPitIndex;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getSelectedPitIndex() {
        return selectedPitIndex;
    }

    public void setSelectedPitIndex(int selectedPitIndex) {
        this.selectedPitIndex = selectedPitIndex;
    }


}
