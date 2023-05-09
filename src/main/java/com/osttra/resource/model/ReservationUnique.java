package com.osttra.resource.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ReservationUnique implements Serializable {

    private Date date;
    private String seatName;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationUnique that = (ReservationUnique) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(seatName, that.seatName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, seatName);
    }

    @Override
    public String toString() {
        return "ReservationUnique{" +
                "date=" + date +
                ", seatName='" + seatName + '\'' +
                '}';
    }
}
