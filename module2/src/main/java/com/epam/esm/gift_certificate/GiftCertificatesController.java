package com.epam.esm.gift_certificate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/certificates")
public class GiftCertificatesController {

    @GetMapping()
    public String getAllCertificates() {
        return null;
    }

    @GetMapping("/{id}")
    public String getCertificateById(@PathVariable("id") Long id) {
        return null;
    }
}
