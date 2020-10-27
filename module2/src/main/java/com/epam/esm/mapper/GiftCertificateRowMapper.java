package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        final GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getLong(GiftCertificateRows.ID.getRowName()));
        giftCertificate.setCertificateCreateDate(rs.getDate(GiftCertificateRows.CREATE_DATE.getRowName()).toLocalDate());
        giftCertificate.setCertificateLastUpdateDate(rs.getDate(GiftCertificateRows.LAST_UPDATE_DATE.getRowName()).toLocalDate());
        giftCertificate.setCertificateDescription(rs.getString(GiftCertificateRows.DESCRIPTION.getRowName()));
        giftCertificate.setCertificateDuration(rs.getInt(GiftCertificateRows.DURATION.getRowName()));
        giftCertificate.setCertificateName(rs.getString(GiftCertificateRows.NAME.getRowName()));
        giftCertificate.setCertificatePrice(rs.getBigDecimal(GiftCertificateRows.PRICE.getRowName()));
        return giftCertificate;
    }
}
