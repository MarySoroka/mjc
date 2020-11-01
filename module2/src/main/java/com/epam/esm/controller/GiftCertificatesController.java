package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateControllerException;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.exception.GiftCertificateServiceException;
import com.epam.esm.service.GiftCertificatesService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/certificates")
public class GiftCertificatesController {

  private final GiftCertificatesService giftCertificatesService;

  @Autowired
  public GiftCertificatesController(GiftCertificatesService giftCertificatesService) {
    this.giftCertificatesService = giftCertificatesService;
  }


  @GetMapping
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
  public GiftCertificate getCertificateById(@PathVariable("id") Long id)
      throws GiftCertificateControllerException {
    try {
      return giftCertificatesService.getCertificateById(id);
    } catch (GiftCertificateNotFoundException e) {
      throw new GiftCertificateControllerException(e.getMessage());
    }
  }


  @PostMapping
  public ResponseEntity<String> createGiftCertificate(@RequestBody GiftCertificate giftCertificate)
      throws GiftCertificateControllerException {
    try {
      return new ResponseEntity<>(
          "Create gift certificate entity successfully. Id :" + giftCertificatesService
              .createCertificate(giftCertificate),
          HttpStatus.CREATED);

    } catch (GiftCertificateServiceException e) {
      throw new GiftCertificateControllerException(e.getMessage());
    }

  }

  @PatchMapping("/{id}")
  public GiftCertificate update(@RequestBody GiftCertificate giftCertificate)
      throws GiftCertificateControllerException {
    try {
      giftCertificatesService.updateCertificate(giftCertificate);
      return giftCertificatesService.getCertificateById(giftCertificate.getId());
    } catch (GiftCertificateServiceException | GiftCertificateNotFoundException e) {
      throw new GiftCertificateControllerException(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable("id") Long id)
      throws GiftCertificateControllerException {
    try {
      giftCertificatesService.deleteCertificate(id);
      return new ResponseEntity<>("Delete gift certificate entity successfully. Id :" + id,
          HttpStatus.OK);
    } catch (GiftCertificateServiceException e) {
      throw new GiftCertificateControllerException(e.getMessage());
    }

  }

}
