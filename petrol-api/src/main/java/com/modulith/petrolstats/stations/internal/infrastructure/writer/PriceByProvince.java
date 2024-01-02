package com.modulith.petrolstats.stations.internal.infrastructure.writer;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class PriceByProvince {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private UUID executionId;
    private String provinceId;
    private Double petrol95;
    private Double petrol98;
    private Double diesel;
    private Double dieselPremium;
    @CreatedDate
    private Instant createdDate;

    public PriceByProvince() {
    }

    public PriceByProvince(UUID executionId, String provinceId, Double petrol95, Double petrol98, Double diesel, Double dieselPremium) {
        this.executionId = executionId;
        this.provinceId = provinceId;
        this.petrol95 = petrol95;
        this.petrol98 = petrol98;
        this.diesel = diesel;
        this.dieselPremium = dieselPremium;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public Double getPetrol95() {
        return petrol95;
    }

    public void setPetrol95(Double petrol95) {
        this.petrol95 = petrol95;
    }

    public Double getPetrol98() {
        return petrol98;
    }

    public void setPetrol98(Double petrol98) {
        this.petrol98 = petrol98;
    }

    public Double getDiesel() {
        return diesel;
    }

    public void setDiesel(Double diesel) {
        this.diesel = diesel;
    }

    public Double getDieselPremium() {
        return dieselPremium;
    }

    public void setDieselPremium(Double dieselPremium) {
        this.dieselPremium = dieselPremium;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public UUID getExecutionId() {
        return executionId;
    }

    public void setExecutionId(UUID executionId) {
        this.executionId = executionId;
    }
}
