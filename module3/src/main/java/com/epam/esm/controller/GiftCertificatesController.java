package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ControllerEntityDeleteException;
import com.epam.esm.exception.ControllerEntityNotFoundException;
import com.epam.esm.exception.ControllerEntityUpdateException;
import com.epam.esm.exception.ControllerSaveEntityException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.GiftCertificateServiceException;
import com.epam.esm.service.GiftCertificateService;
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

  private final GiftCertificateService giftCertificateService;

  @Autowired
  public GiftCertificatesController(GiftCertificateService giftCertificateService) {
    this.giftCertificateService = giftCertificateService;
  }

  /**
   * method return all certificates and sort them by sort field or find by tag name
   *
   * @param name  tag name
   * @param sort  sort field
   * @param order sort order
   * @return certificates by this parameters
   */
  @GetMapping
  public ResponseEntity<List<GiftCertificate>> getAllCertificates(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String sort,
      @RequestParam(required = false) String order) {

    Map<String, String> queryParams = new HashMap<>();
    queryParams.computeIfAbsent("name", val -> name);
    queryParams.computeIfAbsent("sort", val -> sort);
    queryParams.computeIfAbsent("order", val -> order);
    List<GiftCertificate> allCertificates = giftCertificateService.getAllCertificates(queryParams);
    return new ResponseEntity<>(allCertificates,
        HttpStatus.OK);

  }

  /**
   * method find certificate by id
   *
   * @param id certificate id
   * @return certificate
   * @throws ControllerEntityNotFoundException if certificate wasn't found
   */
  @GetMapping("/{id}")
  public ResponseEntity<GiftCertificate> getCertificateById(@PathVariable("id") Long id)
      throws ControllerEntityNotFoundException {
    try {
      return new ResponseEntity<>(giftCertificateService.getCertificateById(id), HttpStatus.OK);
    } catch (EntityNotFoundException e) {
      throw new ControllerEntityNotFoundException(
          "Controller exception : Couldn't get by id certificate", e);
    }
  }

  /**
   * method create certificate
   *
   * @param giftCertificate ResponseBody that represents gift certificate entity
   * @return created gift certificate
   * @throws ControllerSaveEntityException     if entity was not created
   */
  @PostMapping
  public ResponseEntity<GiftCertificate> createGiftCertificate(
      @RequestBody GiftCertificate giftCertificate)
      throws ControllerSaveEntityException {
    try {
      GiftCertificate certificate = giftCertificateService.createCertificate(giftCertificate);
      return new ResponseEntity<>(certificate,
          HttpStatus.CREATED);
    } catch (GiftCertificateServiceException e) {
      throw new ControllerSaveEntityException(
          "Controller exception : Couldn't create certificate", e);
    }
  }

  /**
   * method update entity
   *
   * @param id              certificate id
   * @param giftCertificate ResponseBody that represents gift certificate entity
   * @return updated gift certificate
   * @throws ControllerEntityNotFoundException if certificate was not found
   * @throws ControllerEntityUpdateException   if certificate was not updated
   */
  @PatchMapping("/{id}")
  public ResponseEntity<GiftCertificate> update(@PathVariable("id") Long id,
      @RequestBody GiftCertificate giftCertificate)
      throws ControllerEntityNotFoundException, ControllerEntityUpdateException {
    try {
      giftCertificate.setId(id);
      giftCertificateService.updateCertificate(giftCertificate);
      GiftCertificate certificateById = giftCertificateService
          .getCertificateById(giftCertificate.getId());
      return new ResponseEntity<>(
          certificateById,
          HttpStatus.OK);
    } catch (GiftCertificateServiceException e) {
      throw new ControllerEntityUpdateException(
          "Controller exception : Couldn't update certificate"
          , e);
    } catch (EntityNotFoundException e) {
      throw new ControllerEntityNotFoundException(
          "Controller exception : Couldn't find certificate", e);
    }
  }

  /**
   * methods delete certificate by id
   *
   * @param id certificate id
   * @return HTTPStatus.NO_CONTENT if delete successfully
   * @throws ControllerEntityDeleteException if delete failed
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id)
      throws ControllerEntityDeleteException {
    try {
      giftCertificateService.deleteCertificate(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (GiftCertificateServiceException e) {
      throw new ControllerEntityDeleteException(
          "Controller exception : Couldn't delete certificate", e);
    }

  }


}
