package com.srh.ee.lennu_soovitaja;

public class Istekoht {
    
    private String label;
    private String status;
    private boolean selected = false;

    public Istekoht(String label, String status) {
        this.label = label;
        this.status = status;
    }

    public String getLabel() {
        return label;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void toggleStatus() {
        if (status.equals("available")) {
            status = "occupied";
        } else {
            status = "available";
        }
    }

    public void setSelected(boolean selected) {
        if (status.equals("available")) {
            status = selected ? "selected" : "available";
        }
    }

    public boolean isSelected() {
        return status.equals("selected");
    }

    public void setSelected() {
        this.selected = true;
    }
}
