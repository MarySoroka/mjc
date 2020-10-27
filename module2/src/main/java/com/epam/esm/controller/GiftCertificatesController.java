package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateControllerException;
import com.epam.esm.exception.GiftCertificateServiceException;
import com.epam.esm.service.GiftCertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/certificates")
public class GiftCertificatesController {

    private final GiftCertificatesService giftCertificatesService;

    @Autowired
    public GiftCertificatesController(GiftCertificatesService giftCertificatesService) {
        this.giftCertificatesService = giftCertificatesService;
    }

    @GetMapping()
    public String getAllCertificates() {
        List<GiftCertificate> allCertificates = giftCertificatesService.getAllCertificates();
        return null;
    }

    @GetMapping("/{id}")
    public String getCertificateById(@PathVariable("id") Long id) throws GiftCertificateControllerException {
        try {
            giftCertificatesService.getCertificateById(id);
        } catch (GiftCertificateServiceException e) {
            throw new GiftCertificateControllerException();
        }
        return null;
    }

    @PostMapping()
    public String createGiftCertificate(@RequestBody GiftCertificate giftCertificate) throws GiftCertificateControllerException {
        try {
            giftCertificatesService.createCertificate(giftCertificate);
            return "redirect:/people";
        } catch (GiftCertificateServiceException e) {
            throw new GiftCertificateControllerException();
        }

    }

    @PatchMapping("/{id}")
    public String update(@RequestBody GiftCertificate giftCertificate) {
        giftCertificatesService.updateCertificate(giftCertificate);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        giftCertificatesService.deleteCertificate(id);
        return "redirect:/people";
    }
}
