package com.epam.esm.gift_certificate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificatesDaoImpl implements GiftCertificatesDao {
    //change tag statement to certificate with group by
    private static final String INSERT_CERTIFICATES_QUERY = "INSERT INTO gift_certificates.tag (`name`) values (?)";
    private static final String UPDATE_CERTIFICATES_QUERY = "UPDATE gift_certificates.tag set name=? where id =?";

    private static final String DELETE_CERTIFICATES_QUERY = "DELETE FROM gift_certificates.tag WHERE id = ?";
    private static final String SELECT_ALL_CERTIFICATES_QUERY = "SELECT id, name FROM gift_certificates.tag";
    private static final String SELECT_CERTIFICATE_BY_ID_QUERY = "SELECT id, name FROM gift_certificates.tag WHERE `id`=?";
    private static final String SELECT_CERTIFICATE_BY_TAG_NAME_QUERY = "SELECT id, name FROM gift_certificates.tag WHERE `name`=?";
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateRowMapper giftCertificateRowMapper;

    @Autowired
    public GiftCertificatesDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateRowMapper giftCertificateRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateRowMapper = giftCertificateRowMapper;
    }



    @Override
    public Optional<GiftCertificate> getById(Long id) {
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                SELECT_CERTIFICATE_BY_ID_QUERY,
                                new Object[]{id},
                                giftCertificateRowMapper)
                )
        );
    }

    @Override
    public List<GiftCertificate> getAll() {
        return jdbcTemplate.query(SELECT_ALL_CERTIFICATES_QUERY, giftCertificateRowMapper);
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_CERTIFICATES_QUERY, id) == 1;
    }


    @Override
    public Long save(GiftCertificate giftCertificate) throws GiftCertificateSaveException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_CERTIFICATES_QUERY, new String[]{"id"});
                    setGiftCertificateRows(ps, giftCertificate);
                    return ps;
                },
                keyHolder);
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new GiftCertificateSaveException("Couldn't save gift certificate");
        }
        return key.longValue();

    }
    private static void setGiftCertificateRows(PreparedStatement preparedStatement, GiftCertificate giftCertificate) throws SQLException {
        int i = 0;
        preparedStatement.setString(++i, giftCertificate.getCertificateName());
        preparedStatement.setString(++i, giftCertificate.getCertificateDescription());
        preparedStatement.setDate(++i, Date.valueOf(giftCertificate.getCertificateCreateDate()));
        preparedStatement.setDate(++i, Date.valueOf(giftCertificate.getCertificateLastUpdateDate()));
        preparedStatement.setInt(++i, giftCertificate.getCertificateDuration());
        preparedStatement.setBigDecimal(++i, giftCertificate.getCertificatePrice());

    }

    @Override
    public boolean update(GiftCertificate giftCertificate) {
        //create right update
        return jdbcTemplate.update(UPDATE_CERTIFICATES_QUERY, new Object[]{},giftCertificate.getId()) == 1;
    }
}
