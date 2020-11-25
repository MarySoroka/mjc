package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.dto.GiftCertificateDTO;
import com.epam.esm.exception.ControllerEntityDeleteException;
import com.epam.esm.exception.ControllerSaveEntityException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.GiftCertificateServiceException;
import com.epam.esm.exception.RepositoryDeleteException;
import com.epam.esm.exception.RepositorySaveException;
import com.epam.esm.resource.GiftCertificateResource;
import com.epam.esm.service.GiftCertificateService;
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

  private final GiftCertificateService giftCertificateService;

  @Autowired
  public GiftCertificatesController(GiftCertificateService giftCertificateService) {
    this.giftCertificateService = giftCertificateService;
  }

  @GetMapping
  public ResponseEntity<List<GiftCertificate>> getAllCertificates(
      @RequestParam(required = false) Map<String, String> searchParams) {
    List<GiftCertificate> allCertificates = giftCertificateService.getAllCertificates(searchParams);
    return new ResponseEntity<>(allCertificates,
        HttpStatus.OK);

  }


  @GetMapping("/{id}")
  public ResponseEntity<GiftCertificateResource> getCertificateById(@PathVariable("id") Long id)
      throws EntityNotFoundException {
    GiftCertificate certificateById = giftCertificateService.getCertificateById(id);
    GiftCertificateResource giftCertificateResource = new GiftCertificateResource(certificateById);
    return ResponseEntity.ok(giftCertificateResource);

  }

  @PostMapping
  public ResponseEntity<GiftCertificateResource> createGiftCertificate(
      @RequestBody GiftCertificateDTO giftCertificate)
      throws ControllerSaveEntityException {
    try {
      GiftCertificate certificate = giftCertificateService.createCertificate(giftCertificate);
      GiftCertificateResource giftCertificateResource = new GiftCertificateResource(certificate);
      return ResponseEntity.ok(giftCertificateResource);
    } catch ( EntityNotFoundException | RepositorySaveException e) {
      throw new ControllerSaveEntityException(
          "Controller exception : Couldn't create certificate", e);
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GiftCertificateResource> update(@PathVariable("id") Long id,
      @RequestBody GiftCertificateDTO giftCertificate)
      throws GiftCertificateServiceException, EntityNotFoundException {
    giftCertificate.setId(id);
    giftCertificateService.updateCertificate(giftCertificate);
    GiftCertificate certificateById = giftCertificateService
        .getCertificateById(giftCertificate.getId());
    GiftCertificateResource giftCertificateResource = new GiftCertificateResource(certificateById);
    return ResponseEntity.ok(giftCertificateResource);

  }


  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id)
      throws ControllerEntityDeleteException {
    try {
      giftCertificateService.deleteCertificate(id);
      return ResponseEntity.noContent().build();
    } catch (RepositoryDeleteException e) {
      throw new ControllerEntityDeleteException(
          "Controller exception : couldn't delete certificate", e);
    }

  }


}
