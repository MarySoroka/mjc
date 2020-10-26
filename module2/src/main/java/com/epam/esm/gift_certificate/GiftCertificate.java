package com.epam.esm.gift_certificate;

import com.epam.esm.tag.Tag;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class GiftCertificate {
    private Long id;
    private String certificateName;
    private String certificateDescription;
    private BigDecimal certificatePrice;
    private LocalDate certificateCreateDate;
    private LocalDate certificateLastUpdateDate;
    private Integer certificateDuration;
    private List<Tag> certificateTags;

    public GiftCertificate(Long id, String certificateName, String certificateDescription, BigDecimal certificatePrice, LocalDate certificateCreateDate, LocalDate certificateLastUpdateDate, Integer certificateDuration) {
        this.id = id;
        this.certificateName = certificateName;
        this.certificateDescription = certificateDescription;
        this.certificatePrice = certificatePrice;
        this.certificateCreateDate = certificateCreateDate;
        this.certificateLastUpdateDate = certificateLastUpdateDate;
        this.certificateDuration = certificateDuration;
    }

    public GiftCertificate() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getCertificateDescription() {
        return certificateDescription;
    }

    public void setCertificateDescription(String certificateDescription) {
        this.certificateDescription = certificateDescription;
    }

    public BigDecimal getCertificatePrice() {
        return certificatePrice;
    }

    public void setCertificatePrice(BigDecimal certificatePrice) {
        this.certificatePrice = certificatePrice;
    }

    public LocalDate getCertificateCreateDate() {
        return certificateCreateDate;
    }

    public void setCertificateCreateDate(LocalDate certificateCreateDate) {
        this.certificateCreateDate = certificateCreateDate;
    }

    public LocalDate getCertificateLastUpdateDate() {
        return certificateLastUpdateDate;
    }

    public void setCertificateLastUpdateDate(LocalDate certificateLastUpdateDate) {
        this.certificateLastUpdateDate = certificateLastUpdateDate;
    }

    public Integer getCertificateDuration() {
        return certificateDuration;
    }

    public void setCertificateDuration(Integer certificateDuration) {
        this.certificateDuration = certificateDuration;
    }

    public List<Tag> getCertificateTags() {
        return certificateTags;
    }

    public void setCertificateTags(List<Tag> certificateTags) {
        this.certificateTags = certificateTags;
    }
}
