package com.osttra.resource.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Resource")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "docStationEnabled")
    private boolean docStationEnabled;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resource")
    private Collection<Reservation> reservations;

    public Resource() {
    }

    public Resource(String seatName, String description, boolean docStationEnabled) {
        this.name = seatName;
        this.description = description;
        this.docStationEnabled = docStationEnabled;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDocStationEnabled() {
        return docStationEnabled;
    }

    public void setDocStationEnabled(boolean docStationEnabled) {
        this.docStationEnabled = docStationEnabled;
    }

    @Override
    public String toString() {
        return "Resource [id=" + id + ", name=" + name + ", desc=" + description + ", docStationEnabled=" + docStationEnabled + "]";
    }

}
