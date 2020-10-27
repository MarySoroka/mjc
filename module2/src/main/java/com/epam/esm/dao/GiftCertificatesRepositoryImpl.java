package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.GiftCertificateRowMapper;
import com.epam.esm.exception.DaoSaveException;
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
public class GiftCertificatesRepositoryImpl implements GiftCertificatesRepository {
    //change tag statement to certificate with group by
    private static final String INSERT_CERTIFICATES_QUERY = "INSERT INTO gift_certificates.gift_certificate (name,description, price, create_date, last_update_date, duration ) values (?,?,?,?,?,?)";
    private static final String UPDATE_CERTIFICATES_QUERY = "UPDATE gift_certificates.gift_certificate set name=? ,description=?, price=?, create_date=?, last_update_date=?, duration=? where id =?";

    private static final String DELETE_CERTIFICATES_QUERY = "DELETE FROM gift_certificates.gift_certificate WHERE id = ?";
    private static final String SELECT_ALL_CERTIFICATES_QUERY = "SELECT id,name,description, price, create_date, last_update_date, duration FROM gift_certificates.gift_certificate";
    private static final String SELECT_CERTIFICATE_BY_ID_QUERY = "SELECT id,name,description, price, create_date, last_update_date, duration FROM gift_certificates.gift_certificate WHERE `id`=?";
    private static final String SELECT_CERTIFICATE_BY_TAG_NAME_QUERY = "SELECT id,name,description, price, create_date, last_update_date, duration name FROM gift_certificates.gift_certificate WHERE `name`=?";

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateRowMapper giftCertificateRowMapper;

    @Autowired
    public GiftCertificatesRepositoryImpl(JdbcTemplate jdbcTemplate, GiftCertificateRowMapper giftCertificateRowMapper) {
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
    public Long save(GiftCertificate giftCertificate) throws DaoSaveException {
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
            throw new DaoSaveException("Couldn't save gift certificate");
        }
        return key.longValue();

    }

    private static int setGiftCertificateRows(PreparedStatement preparedStatement, GiftCertificate giftCertificate) throws SQLException {
        int i = 0;
        preparedStatement.setString(++i, giftCertificate.getCertificateName());
        preparedStatement.setString(++i, giftCertificate.getCertificateDescription());
        preparedStatement.setBigDecimal(++i, giftCertificate.getCertificatePrice());
        preparedStatement.setDate(++i, Date.valueOf(giftCertificate.getCertificateCreateDate()));
        preparedStatement.setDate(++i, Date.valueOf(giftCertificate.getCertificateLastUpdateDate()));
        preparedStatement.setInt(++i, giftCertificate.getCertificateDuration());
        return i;

    }

    @Override
    public boolean update(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int isUpdate = jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(UPDATE_CERTIFICATES_QUERY, new String[]{"id"});
                    int i = setGiftCertificateRows(ps, giftCertificate);
                    ps.setLong(++i, giftCertificate.getId());
                    return ps;
                },
                keyHolder);

        return isUpdate > 0;
    }
}
