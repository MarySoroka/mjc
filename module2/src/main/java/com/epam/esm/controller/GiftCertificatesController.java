package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateControllerException;
import com.epam.esm.exception.GiftCertificateServiceException;
import com.epam.esm.service.GiftCertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/certificates")
public class GiftCertificatesController {

    private final GiftCertificatesService giftCertificatesService;

    @Autowired
    public GiftCertificatesController(GiftCertificatesService giftCertificatesService) {
        this.giftCertificatesService = giftCertificatesService;
    }


    @GetMapping()
    @ResponseBody
    public List<GiftCertificate> getAllCertificates(@RequestParam(required = false) String name,
                                                    @RequestParam(required = false) String sort,
                                                    @RequestParam(required = false) String order) {

        Map<String, String> queryParams = new HashMap<>();
        queryParams.computeIfAbsent("name", val -> name);
        queryParams.computeIfAbsent("sort", val -> sort);
        queryParams.computeIfAbsent("order", val -> order);
        return giftCertificatesService.getAllCertificates(queryParams);

    }

    @GetMapping("/{id}")
    @ResponseBody
    public GiftCertificate getCertificateById(@PathVariable("id") Long id) throws GiftCertificateControllerException {
        try {
            return giftCertificatesService.getCertificateById(id);
        } catch (GiftCertificateServiceException e) {
            throw new GiftCertificateControllerException();
        }
    }


    @PostMapping()
    public void createGiftCertificate(@RequestBody GiftCertificate giftCertificate) throws GiftCertificateControllerException {
        try {
            giftCertificatesService.createCertificate(giftCertificate);
        } catch (GiftCertificateServiceException e) {
            throw new GiftCertificateControllerException();
        }

    }

    @PatchMapping("/{id}")
    public void update(@RequestBody GiftCertificate giftCertificate) {
        giftCertificatesService.updateCertificate(giftCertificate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        giftCertificatesService.deleteCertificate(id);
    }
}
