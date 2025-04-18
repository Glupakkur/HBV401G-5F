package com.example.verkefni.vidmot;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public enum View {
    SEATMAP("saeti-view.fxml"),
    VELJAFLUG("VeljaFlugVidmot.fxml"),
    TICKET("Ticket.fxml");

    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
