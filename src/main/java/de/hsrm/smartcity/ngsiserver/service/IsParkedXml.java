package de.hsrm.smartcity.ngsiserver.service;


import lombok.Data;

    
@Data
public class IsParkedXml {
    private String parkingId;
    private String observedAt;
    private String providedBy;
}

