package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ControllerEntityDeleteException;
import com.epam.esm.exception.ControllerEntityNotFoundException;
import com.epam.esm.exception.ControllerEntityUpdateException;
import com.epam.esm.exception.ControllerSaveEntityException;
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
  public ResponseEntity<List<GiftCertificate>> getAllCertificates(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String sort,
      @RequestParam(required = false) String order) {

    Map<String, String> queryParams = new HashMap<>();
    queryParams.computeIfAbsent("name", val -> name);
    queryParams.computeIfAbsent("sort", val -> sort);
    queryParams.computeIfAbsent("order", val -> order);
    List<GiftCertificate> allCertificates = giftCertificatesService.getAllCertificates(queryParams);
    if (allCertificates.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(allCertificates,
        HttpStatus.OK);

  }

  @GetMapping("/{id}")
  public ResponseEntity<GiftCertificate> getCertificateById(@PathVariable("id") Long id)
      throws ControllerEntityNotFoundException {
    try {
      return new ResponseEntity<>(giftCertificatesService.getCertificateById(id), HttpStatus.OK);
    } catch (GiftCertificateNotFoundException e) {
      throw new ControllerEntityNotFoundException(
          "Controller exception : Couldn't get by id certificate", e);
    }
  }


  @PostMapping
  public ResponseEntity<GiftCertificate> createGiftCertificate(
      @RequestBody GiftCertificate giftCertificate)
      throws ControllerSaveEntityException {
    try {
      Long certificateId = giftCertificatesService.createCertificate(giftCertificate);
      GiftCertificate certificateById = giftCertificatesService.getCertificateById(certificateId);
      return new ResponseEntity<>(certificateById,
          HttpStatus.CREATED);
    } catch (GiftCertificateServiceException | GiftCertificateNotFoundException e) {
      throw new ControllerSaveEntityException(
          "Controller exception : Couldn't create certificate", e);
    }

  }

  @PatchMapping("/{id}")
  public ResponseEntity<GiftCertificate> update(@PathVariable("id") Long id,@RequestBody GiftCertificate giftCertificate)
      throws ControllerEntityNotFoundException, ControllerEntityUpdateException {
    try {
      giftCertificate.setId(id);
      giftCertificatesService.updateCertificate(giftCertificate);
      GiftCertificate certificateById = giftCertificatesService
          .getCertificateById(giftCertificate.getId());
      return new ResponseEntity<>(
          certificateById,
          HttpStatus.OK);
    } catch (GiftCertificateServiceException e) {
      throw new ControllerEntityUpdateException(
          "Controller exception : Couldn't update certificate"
          , e);
    } catch (GiftCertificateNotFoundException e) {
      throw new ControllerEntityNotFoundException(
          "Controller exception : Couldn't find certificate", e);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id)
      throws ControllerEntityDeleteException {
    try {
      giftCertificatesService.deleteCertificate(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (GiftCertificateServiceException e) {
      throw new ControllerEntityDeleteException(
          "Controller exception : Couldn't delete certificate", e);
    }

  }


}
