package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateControllerException;
import com.epam.esm.exception.GiftCertificateServiceException;
import com.epam.esm.service.GiftCertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value ="/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificatesController {

    private final GiftCertificatesService giftCertificatesService;

    @Autowired
    public GiftCertificatesController(GiftCertificatesService giftCertificatesService) {
        this.giftCertificatesService = giftCertificatesService;
    }


    @GetMapping()
    public List<GiftCertificate> getAllCertificates() {
        return giftCertificatesService.getAllCertificates();
    }

    @GetMapping("/{id}")
    public GiftCertificate getCertificateById(@PathVariable("id") Long id) throws GiftCertificateControllerException {
        try {
            return giftCertificatesService.getCertificateById(id);
        } catch (GiftCertificateServiceException e) {
            throw new GiftCertificateControllerException();
        }
    }

    @PostMapping()
    public String createGiftCertificate(@RequestBody GiftCertificate giftCertificate) throws GiftCertificateControllerException {
        try {
            giftCertificatesService.createCertificate(giftCertificate);
            return "redirect:/certificates";
        } catch (GiftCertificateServiceException e) {
            throw new GiftCertificateControllerException();
        }

    }

    @PatchMapping("/{id}")
    public String update(@RequestBody GiftCertificate giftCertificate) {
        giftCertificatesService.updateCertificate(giftCertificate);
        return "redirect:/certificates";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        giftCertificatesService.deleteCertificate(id);
        return "redirect:/certificates";
    }
}
